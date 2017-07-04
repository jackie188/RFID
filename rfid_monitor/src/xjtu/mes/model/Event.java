package xjtu.mes.model;

import java.util.Date;

/**
* @author 姜文雷
* @version 创建时间：2016年11月14日 下午7:29:00
* 类说明
*/
public class Event {
	private int id=0;
	private String workpieceId;
	private String processId;
	private String event;
	private Date date;
	public String getWorkpieceId() {
		return workpieceId;
	}
	public void setWorkpieceId(String workpieceId) {
		this.workpieceId = workpieceId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
