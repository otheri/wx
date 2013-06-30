package com.otheri.wx;


/**
 * 消息处理器
 * 
 * @author GodSon
 * 
 */
public class MessageProcesser {
	public final static String MSG_TYPE_TEXT = "text";
	public final static String MSG_TYPE_IMAGE = "image";
	public final static String MSG_TYPE_LOCATION = "location";

	public final static String MSG_TYPE_LINK = "link";
	public final static String MSG_TYPE_EVENT = "event";

	public final static String MSG_TYPE_NEWS = "news";
	public final static String MSG_TYPE_MUSIC = "music";

	public OutMessage process(InMessage msg) {
		OutMessage oms = new OutMessage();
		String type = msg.getMsgType();
		// 针对不同类型消息进行处理
		if (type.equals(MessageProcesser.MSG_TYPE_TEXT)) {
			oms = textTypeMsg(msg);
		} else if (type.equals(MessageProcesser.MSG_TYPE_LOCATION)) {
			oms = locationTypeMsg(msg);
		} else if (type.equals(MessageProcesser.MSG_TYPE_LINK)) {
			oms = linkTypeMsg(msg);
		} else if (type.equals(MessageProcesser.MSG_TYPE_IMAGE)) {
			oms = imageTypeMsg(msg);
		} else if (type.equals(MessageProcesser.MSG_TYPE_EVENT)) {
			oms = eventTypeMsg(msg);
		} else {
			oms = allType(msg);
		}
		return oms;
	}

	private OutMessage allType(InMessage msg) {
		OutMessage out = new OutMessage();
		out.setContent("你的消息已经收到！");
		return out;
	}

	/**
	 * 文字内容的消息处理
	 * 
	 * @param msg
	 * @return
	 */
	private OutMessage textTypeMsg(InMessage msg) {
		return allType(msg);
	}

	/**
	 * 地理位置类型的消息处理
	 * 
	 * @param msg
	 * @return
	 */
	private OutMessage locationTypeMsg(InMessage msg) {
		return allType(msg);
	}

	/**
	 * 图片类型的消息处理
	 * 
	 * @param msg
	 * @return
	 */
	private OutMessage imageTypeMsg(InMessage msg) {
		return allType(msg);
	}

	/**
	 * 链接类型的消息处理
	 * 
	 * @param msg
	 * @return
	 */
	private OutMessage linkTypeMsg(InMessage msg) {
		return allType(msg);
	}

	/**
	 * 事件类型的消息处理。<br/>
	 * 在用户首次关注公众账号时，系统将会推送一条subscribe的事件
	 * 
	 * @param msg
	 * @return
	 */
	private OutMessage eventTypeMsg(InMessage msg) {
		return allType(msg);
	}
}
