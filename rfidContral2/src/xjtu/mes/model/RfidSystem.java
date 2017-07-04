package xjtu.mes.model;
/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 上午9:55:26
* 类说明 
* 此类为实体类，对应数据库的表 RFIDSystem
*/

public class RfidSystem {
	
	private String rfidSysId="0";
	private String rfidName;
	private String rfidModel;
	private int rfidPort;
	private String flag="0";
	private String rfidIp;
	public RfidSystem(){
		
	}
	
	public RfidSystem(String rfidSysId, String rfidName, String rfidIp) {
		super();
		this.rfidSysId = rfidSysId;
		this.rfidName = rfidName;
		this.rfidIp = rfidIp;
	}
	public String getRfidIp() {
		return rfidIp;
	}
	public void setRfidIp(String rfidIp) {
		this.rfidIp = rfidIp;
	}
	public String getRfidSysId() {
		return rfidSysId;
	}
	public void setRfidSysId(String rfidSysId) {
		this.rfidSysId = rfidSysId;
	}
	public String getRfidName() {
		return rfidName;
	}
	public void setRfidName(String rfidName) {
		this.rfidName = rfidName;
	}
	public String getRfidModel() {
		return rfidModel;
	}
	public void setRfidModel(String rfidModel) {
		this.rfidModel = rfidModel;
	}
	public int getRfidPort() {
		return rfidPort;
	}
	public void setRfidPort(int rfidPort) {
		this.rfidPort = rfidPort;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
