package xjtu.mes.model;
/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午12:39:30
* 类说明
* 机床实体类
*/
public class Machine {

	private String machName;
	private String machId;
	private String flag;
	
	
	public Machine() {
		
	}
	
	public Machine(String machName, String machId) {
	
		this.machName = machName;
		this.machId = machId;
	}
	public String getMachName() {
		return machName;
	}
	public void setMachName(String machName) {
		this.machName = machName;
	}
	public String getMachId() {
		return machId;
	}
	public void setMachId(String machId ) {
		this.machId = machId ;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
