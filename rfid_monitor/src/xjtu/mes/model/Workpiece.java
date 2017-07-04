package xjtu.mes.model;

import java.util.Date;

/**
* @author 姜文雷
* @version 创建时间：2016年11月7日 下午7:00:25
* 类说明
*/
public class Workpiece {
	private String workpieceId;
	private String workpieceName;
	private String workpieceRfid;
	private Date time;
	private String flag ;
	private String userId;
	private String finish="0";
	private int processCount =0;
	private String isBanding ="0";
	
	public Workpiece(){
		
	}
	public Workpiece(String workpieceId, String workpieceName, String workpieceRfid) {
		super();
		this.workpieceId = workpieceId;
		this.workpieceName = workpieceName;
		this.workpieceRfid = workpieceRfid;
	}
	public Workpiece(String workpieceId, String workpieceName, String workpieceRfid,String userId) {
		super();
		this.workpieceId = workpieceId;
		this.workpieceName = workpieceName;
		this.workpieceRfid = workpieceRfid;
		this.userId = userId;
	}
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	
	public String getWorkpieceId() {
		return workpieceId;
	}
	public void setWorkpieceId(String workpieceId) {
		this.workpieceId = workpieceId;
	}
	public String getWorkpieceName() {
		return workpieceName;
	}
	public void setWorkpieceName(String workpieceName) {
		this.workpieceName = workpieceName;
	}
	public String getWorkpieceRfid() {
		return workpieceRfid;
	}
	public void setWorkpieceRfid(String workpieceRfid) {
		this.workpieceRfid = workpieceRfid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public int getProcessCount() {
		return processCount;
	}
	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}
	public String getIsBanding() {
		return isBanding;
	}
	public void setIsBanding(String isBanding) {
		this.isBanding = isBanding;
	}
	
	
}
