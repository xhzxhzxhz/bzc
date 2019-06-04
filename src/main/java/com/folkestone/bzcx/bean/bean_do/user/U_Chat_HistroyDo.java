package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;

/**
 * 聊天历史对应数据库的实体
 * @author folkestone-6
 *
 */
public class U_Chat_HistroyDo {
	/**
	 * 聊天id
	 */
    private String chatId = UUIDUtil.getUUID("chat");
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
    private Date chatDate = new Date();
    /**
     * 状态（已读未读）
     */
    private Short status;

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