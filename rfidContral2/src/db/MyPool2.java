package db;
/**
* @author 姜文雷
* @version 创建时间：2016年11月28日 下午3:52:37
* 类说明
*/
import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
import java.lang.reflect.Proxy;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.util.LinkedList; 
public class MyPool2 {
	 int init_count = 3; // 初始化连接数目  
	    int max_count = 6; // 最大连接数  
	    int current_count = 0; // 记录当前使用连接数  
	  
	    // 连接池 （存放所有的初始化连接）  
	    LinkedList<Connection> pool = new LinkedList<Connection>();  
	  
	    // 1. 构造函数中，把初始化连接放入连接池  
	    public MyPool2() {  
	        // 初始化连接  
	        for (int i = 0; i < init_count; i++) {  
	            // 记录当前连接数目  
	            current_count++;  
	            // 创建原始的连接对象  
	            Connection con = createConnection();  
	            // 把连接加入连接池  
	            pool.addLast(con);  
	        }  
	    }  
	  
	    private Connection createConnection() {  
	        try {  
	            Class.forName("com.mysql.jdbc.Driver");  
	            // 原始的目标对象  如果想自己写一个连接池 当然要用DriverManagerle  
	            final   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mes_online", "root", "xjtucad");  
	            /** ********对con对象代理************* */  
	  
	            // 对con创建其代理对象  
	            Connection proxy = (Connection) Proxy.newProxyInstance(con.getClass().getClassLoader(), // 类加载器  
	                    // con.getClass().getInterfaces(), // 当目标对象是一个具体的类的时候  
	                    new Class[] { Connection.class }, // 目标对象实现的接口  
	                    new InvocationHandler() { // 当调用con对象方法的时候， 自动触发事务处理器  
	                        public Object invoke(Object proxy, Method method,  
	                                Object[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {  
	                            // 方法返回值  
	                            Object result = null;  
	                            // 当前执行的方法的方法名  
	                            String methodName = method.getName();  
	                            // 判断当执行了close方法的时候，把连接放入连接池  
	                            if ("close".equals(methodName) && current_count<=init_count) {  
	                                System.out.println("begin:当前执行close方法开始！");  
	                                // 连接放入连接池  
	                                pool.addLast(con);  
	                                current_count--;  
	                                System.out.println("end: 当前连接已经放入连接池了！");  
	                            }else if("close".equals(methodName)&current_count>init_count){  
	                                //销毁connection对象  
	                                if(con!=null){  
	                                    try {  
	                                        System.out.println("销毁当前连接对象");                                       
	                                        con.close();  
	                                        current_count--;  
	                                    } catch (SQLException e) {  
	                                        e.printStackTrace();  
	                                    }  
	                                }  
	                            }else {  
	                                // 调用目标对象方法  
	                                result = method.invoke(con, args);  
	                            }  
	                            return result;  
	                        }  
	                    });  
	            return proxy;  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	    }  
	      
	    //3. 获取连接  
	    public Connection getConnection(){        
	        // 3.1 判断连接池中是否有连接, 如果有连接，就直接从连接池取出  
	        if (pool.size() > 0){  
	            return pool.removeFirst();  
	        }         
	        // 3.2 连接池中没有连接： 判断，如果没有达到最大连接数，创建；  
	        if (current_count < max_count) {  
	            // 记录当前使用的连接数  
	            current_count++;  
	            // 创建连接  
	            return createConnection();  
	        }         
	        // 3.3 如果当前已经达到最大连接数，抛出异常  
	        throw new RuntimeException("当前连接已经达到最大连接数目 ！");  
	    }  
}
