package xjtu.mes.model;
/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午4:58:38
* 类说明
* 机床和rfid系统绑定实体类
*/
public class MachRfid {
	private String machId;
	private String machName;
	private String rfidSysId;
	private String rfidSysName;
	
	public String getRfidSysName() {
		return rfidSysName;
	}
	public void setRfidSysName(String rfidSysName) {
		this.rfidSysName = rfidSysName;
	}
	public String getMachId() {
		return machId;
	}
	public void setMachId(String machId) {
		this.machId = machId;
	}
	public String getMachName() {
		return machName;
	}
	public void setMachName(String machName) {
		this.machName = machName;
	}
	public String getRfidSysId() {
		return rfidSysId;
	}
	public void setRfidSysId(String rfidSysId) {
		this.rfidSysId = rfidSysId;
	}
	
}
