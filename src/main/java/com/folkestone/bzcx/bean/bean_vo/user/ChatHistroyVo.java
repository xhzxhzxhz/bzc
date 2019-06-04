package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;

/**
 * 聊天历史对应前台的实体
 * @author folkestone-6
 *
 */
public class ChatHistroyVo {
	/**
	 * 聊天id
	 */
    private String chatId;
    /**
     * 发送人
     */
    private String sender;
    /**
     * 接收人
     */
    private String receiver;
    /**
     * 内容
     */
    private Object content;
    /**
     * 发送时间
     */
    private Date chatDate;
    /**
     * 状态（已读未读）
     */
    private Short status;
    /**
     * 发件人的姓名
     */
    private String receiverName;

    public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId == null ? null : chatId.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Date getChatDate() {
        return chatDate;
    }

    public void setChatDate(Date chatDate) {
        this.chatDate = chatDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}