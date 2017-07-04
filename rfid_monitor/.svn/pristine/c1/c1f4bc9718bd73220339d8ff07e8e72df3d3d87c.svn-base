package xjtu.mes.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 用于吧配置文件的xml内容，读取到配置实体类中
 * @author 61773
 *
 */
public class ReadConfigDb {
	
	private  DbConfig db_config = new DbConfig();
	//饿汉式
/*	private static  ReadConfigDb instance = new ReadConfigDb() ;
	private ReadConfigDb(){}
	private static ReadConfigDb getinstance (){
		return instance;
	}*/ 
	
	//懒汉式
	private static  ReadConfigDb instance = null ;
	
	/**
	 * 读取xml文件，将元素值配置到实体类,在构造方法中执行，只执行一次
	 */
	private ReadConfigDb(){
		SAXReader reader = new SAXReader();
	    //File file = new File("src/sys-config.xml");
		
		//因为文件在src目录下，可作为资源进行装载
		//Thread.currentThread()获取当前进程
		//getContextClassLoader()获取装载器
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys-config.xml");
		try {
			//读取文件
			Document document = reader.read(file);
			//取的根元素
			Element root = document.getRootElement();
			//root下级元素为其子元素，一个childElements包含该子元素的所有集合
			List<Element> childElements = root.elements();
			for(Element child : childElements ){
				//根据子元素的命名获取值
				db_config.setClassName( child.elementText("Class-Name") );
				db_config.setUser( child.elementText("user") );
				db_config.setUrl( child.elementText("url") );
				db_config.setPassword( child.elementText("password") );
				//只会打印一次，因为root标签<config>下的子标签<db-info>就一个
				//System.out.println(child.elementText("db-info"));
			}
				
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized  ReadConfigDb getInstance (){
		if(instance == null){
			instance=new ReadConfigDb();
		}
		return instance;
	} 
	
	/**
	 * 
	 * @return DbCofig 实体类
	 */
	public DbConfig getDbcofing(){
	
		return db_config;
	}

}
