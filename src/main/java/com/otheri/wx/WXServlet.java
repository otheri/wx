package com.otheri.wx;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class WXServlet extends HttpServlet {

	private static final long serialVersionUID = 6032945714705151791L;

	private static final String token = "zywx123";

	public static final String url = "http://zhangyu-1981.oicp.net/wx/service";

	private static final Logger logger = LoggerFactory
			.getLogger(WXServlet.class);

	public void init(ServletConfig config) throws ServletException {
		logger.info("-----init");

	}

	public static final void wxServerVerify(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");//

		// 验证
		if (Tools.checkSignature(token, signature, timestamp, nonce)) {
			response.getWriter().write(echostr);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("-----doGet");

		wxServerVerify(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("-----doPost");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");

		OutMessage oms = new OutMessage();
		ServletInputStream in = request.getInputStream();
		// 转换微信post过来的xml内容
		XStream xs = XStreamFactory.init(false);
		xs.alias("xml", InMessage.class);
		String xmlMsg = Tools.inputStream2String(in);
		InMessage msg = (InMessage) xs.fromXML(xmlMsg);
		// 获取自定消息处理器，如果自定义处理器则使用默认处理器。

		try {
			// 加载处理器
			MessageProcesser processingHandler = new MessageProcesser();

			oms = processingHandler.process(msg);

			// 设置发送信息
			oms.setCreateTime(System.currentTimeMillis());
			oms.setToUserName(msg.getFromUserName());
			oms.setFromUserName(msg.getToUserName());
		} catch (Exception e) {
			logger.error(e.toString());
			oms.setContent("系统错误！");
		}

		// 把发送发送对象转换为xml输出
		logger.info("xxxxxx   " + oms.getContent());
		xs = XStreamFactory.init(false);
		xs.alias("xml", OutMessage.class);
		xs.alias("item", Articles.class);
		xs.toXML(oms, response.getWriter());

	}
}
