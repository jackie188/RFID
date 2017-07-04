package db;
/**
* @author 姜文雷
* @version 创建时间：2016年11月24日 下午5:59:20
* 类说明
*/
import java.io.PrintWriter;  
import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.Method;  
import java.lang.reflect.Proxy;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.SQLFeatureNotSupportedException;  
import java.util.LinkedList;  
import java.util.List;  
import java.util.logging.Logger;  
  
import javax.sql.DataSource;  
  
public class MyPool implements DataSource{  
  
    //list集合保存数据库连接池中的connection对象  
    private static List<Connection> pool = new LinkedList<Connection>();  
    //静态代码块，用于初始化list集合，即初始化数据库连接池，创建5个connection对象保存其中以备使用  
    static {  
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
            for(int i = 0; i < 3; i++) {  
               // Connection conn = DriverManager.getConnection("jdbc:mysql://115.154.191.5:3306/mes_online", "root", "xjtucad");  
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mes_online", "root", "xjtucad");  
                pool.add(conn);  
            }  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } catch (SQLException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
    @Override  
    public PrintWriter getLogWriter() throws SQLException {  
        return null;  
    }  
    @Override  
    public void setLogWriter(PrintWriter out) throws SQLException {  
    }  
    @Override  
    public void setLoginTimeout(int seconds) throws SQLException {  
    }  
    @Override  
    public int getLoginTimeout() throws SQLException {  
        return 0;  
    }  
    @Override  
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {  
        return null;  
    }  
    @Override  
    public <T> T unwrap(Class<T> iface) throws SQLException {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public boolean isWrapperFor(Class<?> iface) throws SQLException {  
        // TODO Auto-generated method stub  
        return false;  
    }  
  
    //重写父类的getConnection()方法，返回数据库连接池中的一个connection对象，  
    //如果数据库连接池中connection对象都已被使用，即都被取走未返还,则创建3个connection对象保存其中供以后使用  
    @Override  
    public Connection getConnection() throws SQLException {  
        if(pool == null) {  
            for(int i = 0; i < 2; i++) {  
                //Connection conn = DriverManager.getConnection("jdbc:mysql://115.154.191.5:3306/mes_online", "root", "xjtucad");  
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mes_online", "root", "xjtucad");  
                pool.add(conn);  
            }  
        }  
        final Connection conn = pool.remove(0);  
          
        //使用动态代理改造close方法  
        //newProxyInstance(类加载器, 要改造的conn对象所实现的所有接口,  匿名内部类)  
        Connection proxy = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {  
              
            @Override  
            public Object invoke(Object proxy, Method method, Object[] args)  
                    throws Throwable {  
                if("close".equals(method.getName())) {  
                    //如果是close方法，我们进行重写  
                    returnConn(conn);  
                    return null;  
                } else {  
                    //如果是其他方法，直接调用  
                    return method.invoke(conn, args);  
                }  
            }  
        });  
       // System.out.println("获取一个连接对象,剩余连接对象：" + pool.size());  
        return proxy;  
    }  
    //创建新方法，用于返回数据库连接对象connection，因为dao层用完数据库的连接后，不应该将其销毁，而是应该将其返还给数据库连接池  
    public void returnConn(Connection conn) {  
        pool.add(conn);  
       // System.out.println("返还一个连接对象,剩余连接对象：" + pool.size());  
    }  
    @Override  
    public Connection getConnection(String username, String password)  
            throws SQLException {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
      
}  