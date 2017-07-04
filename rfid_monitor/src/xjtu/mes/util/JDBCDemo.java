package xjtu.mes.util;
/**
* @author 姜文雷
* @version 创建时间：2016年11月24日 下午6:28:49
* 类说明
*/
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;

  

  
public class JDBCDemo {  
  
    public static void main(String[] args) {  
          
        Connection conn = null;  
        PreparedStatement ps = null;  
        ResultSet rs = null;  
        MyPool pool = new MyPool();  
        try {  
            conn = pool.getConnection();  
            ps = conn.prepareStatement("select * from workpiece");  
            rs = ps.executeQuery();  
            while(rs.next()) {  
                String name = rs.getString(2);  
                String salary = rs.getString(3);  
                System.out.println(name + " : " + salary);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } finally {  
            //关闭数据库连接  
            if(rs != null) {  
                try {  
                    rs.close();  
                } catch (SQLException e) {  
                    rs = null;  
                }  
            }  
            if(ps != null) {  
                try {  
                    ps.close();  
                } catch (SQLException e) {  
                    ps = null;  
                }  
            }  
            if(conn != null) {  
                try {  
                    conn.close();  
                } catch (SQLException e) {  
                    conn = null;  
                }  
            }  
        }  
    }  
}  