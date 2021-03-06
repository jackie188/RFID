package xjtu.mes.model;

import java.util.ArrayList;
import java.util.List;

import xjtu.mes.manager.WorkpieceProcessManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午3:18:38
* 类说明工序表
*/
public class WorkpieceProcess {
	private int Num=0;
 	private String workpieceId;//零件id
	private String  processName;//工序名
	private String processId;//工序id
	private String machId;//机床id
	private int level;//工序级别
	private int state;//工序状态
	
	
	public WorkpieceProcess(){
		
	}
	public WorkpieceProcess(String workpieceId, String processName, String processId, String machId) {
		super();
		this.workpieceId = workpieceId;
		this.processName = processName;
		this.processId = processId;
		this.machId = machId;
		/*List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
		workpieceProcessList = WorkpieceProcessManager.getInstance().getAllProcessByWorkpieceId(workpieceId);
		this.level = workpieceProcessList.size()+1;*/
	}
	public String getWorkpieceId() {
		return workpieceId;
	}
	public void setWorkpieceId(String workpieceId) {
		this.workpieceId = workpieceId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getMachId() {
		return machId;
	}
	public void setMachId(String machId) {
		this.machId = machId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getNum() {
		return Num;
	}
	public void setNum(int num) {
		Num = num;
	}
	
	
	
	
	
}
