package xjtu.mes.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
* @author 姜文雷
* @version 创建时间：2016年11月24日 上午9:03:45
* 类说明
*/
public class OutError {
	
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

}
