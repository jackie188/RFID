

package xjtu.mes.manager;

import com.alien.enterpriseRFID.reader.*;
import com.alien.enterpriseRFID.tags.*;
import com.alien.enterpriseRFID.util.Converters;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Connects to a Reader on COM port #1 and asks it to read tags.
 *
 * @version 1.2 Feb 2004
 * @author David Krull
 */
public class AlienClass1ReaderManager {
	AlienClass1Reader reader =null;
	private static AlienClass1ReaderManager instance=null;

	public  synchronized static AlienClass1ReaderManager getInstance(){
		if(instance==null){
			try {
				instance= new AlienClass1ReaderManager();
			} catch (AlienReaderException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Constructor
	 */
	protected AlienClass1ReaderManager() throws AlienReaderException {			
	  this.reader = new AlienClass1Reader();
	 	
	}

	public boolean writeRFID(String number)throws AlienReaderException{
		boolean flag=false;
		String resp;
		this.reader = new AlienClass1Reader();
		reader.setConnection(this.getWriterIp(), 23);
		reader.setUsername("alien");
		reader.setPassword("password");
		reader.open();
		System.out.println(number);
		byte[] f =number.getBytes();
		number= Converters.toHexString(f,0,f.length," ");
		System.out.println(number);
		resp =this.reader.doReaderCommand("program tag = "+number+"\n");
		if(resp.indexOf("Program Tag =")!=-1){
			flag=true;
			
		}
		System.out.println(resp);
		reader.close();
		return flag;
	}
	protected String getWriterIp(){
		String ip ="192.168.1.129";
		ip =RfidSystemManager.getInstance().getRfidByRfidId("writer").getRfidIp();
		return ip;
	}
	public String getTagId()throws AlienReaderException{
		JSONObject jsonObj = new JSONObject();
		JSONArray rows = new JSONArray();
		String resp="no";
		this.reader = new AlienClass1Reader();
		reader.setConnection(this.getWriterIp(), 23);
		reader.setUsername("alien");
		reader.setPassword("password");
		reader.open();
		Tag tagList[] = reader.getTagList();
		int n=50;
		while(n!=0 && tagList == null){
			n--;
			tagList= reader.getTagList();
		}
		if (tagList == null) {
		    System.out.println("No Tags Found");
		    resp="no";
		 }else{
		    for (int i=0; i<tagList.length; i++) {
		    	Tag tag = tagList[i];
		    	if(tag.getAntenna()==0){
		    		JSONObject cell = new JSONObject();
		    		//System.out.println(" Tags Found");
		    		resp=tag.getTagID();
		    		System.out.println(resp);
		    		byte[] h =Converters.fromHexString(resp);//0x61 0x61 0x61 0x61<-"6161"
		    		resp= new String(h);//0x61 0x61 0x61 0x61->"aaaa"
		    		cell.put("RfidId", resp);
		    		rows.add(cell);	
		    	}
		    }
		 }
		if(!rows.isEmpty()){
			jsonObj.put("rows", rows);
			reader.close();
			return jsonObj.toString();
		}else{
			return "no";
		}
		
	}
	
	/**
	 * Main
	 */
	public static final void main(String args[]){
		byte[] a =Converters.fromHexString("1f1f");//"1f1f"->0x1f=31 0x1f=31
		byte[] b =Converters.fromBinaryStringMSB("00000001");//二进制字符串转2进制数，用byte表示
		String c = Converters.toAsciiString("123");
		String d = Converters.toBinaryString(a,1);//1f1f->"11111 00011111"
		String e = Converters.toHexString(a,0,2,":");//1f1f->"1F:1F"
		//这段代码可用于rfid的id的编码与解码
		String str="00103030201tp001";
		byte[] f =str.getBytes();//"aaaa"->97 97 97 97 =0x61 0x61 0x61 0x61
		String g = Converters.toHexString(f,0,f.length,"");//0x61 0x61 0x61 0x61->"6161"
		
		byte[] h =Converters.fromHexString(g);//0x61 0x61 0x61 0x61<-"6161"
		String i= new String(h);//0x61 0x61 0x61 0x61->"aaaa"
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(g);
		System.out.println(i);
		System.out.println("结束");
	 try {
		 AlienClass1ReaderManager aa= new AlienClass1ReaderManager();
	   while( aa.getTagId().equals("no"))
			   ;
	  } catch(AlienReaderException e1) {
	    System.out.println("Error: " + e1.toString());
	  }
	}

} // End of class AlienClass1ReaderTest