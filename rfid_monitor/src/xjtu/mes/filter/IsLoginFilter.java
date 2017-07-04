
  package xjtu.mes.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xjtu.mes.model.User;



public class IsLoginFilter implements Filter {
	//可以绕过过滤器的地址组
	String permitUrls[]=null;
	
	
	public IsLoginFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
       
        String permitUrls =filterConfig.getInitParameter("permitUrls");
       // System.out.println("允许访问的地址： ");
         if(permitUrls!=null&&permitUrls.length()>0){
        	 this.permitUrls=permitUrls.split(",");
         }
         for(String s:this.permitUrls){
        	 System.out.println("允许访问的地址： "+s);
         }
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req  = (HttpServletRequest) request;
		HttpServletResponse resp  = (HttpServletResponse) response;	
		
		if(!isPermitUrl(request)){
			HttpSession session = req.getSession();
			//这里是自己额外加的，避免测试简单进行登录
			/*User user1 = UserManager.getInstance().checkUser("root","root123");
			session.setAttribute("user_info", user1);*/
			
			User user = (User)session.getAttribute("user_info");
			if(null == user){
				System.out.println("检测到没有用户登录");
				resp.sendRedirect("/rfid_monitor/errorPage.jsp");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
    public boolean isPermitUrl(ServletRequest request){
        boolean isPermit=false;     
        if(permitUrls!=null&&permitUrls.length>0){
            for (int i = 0; i < permitUrls.length; i++) {
                if(permitUrls[i].equals(currentUrl(request))){
                    isPermit=true;
                    break;
                }
            }
        }
        return isPermit;
    }
    public String currentUrl(ServletRequest request){
        
        HttpServletRequest res=(HttpServletRequest) request;
        //这里暂时先不用task，这是别人程序里自己带的
        //String task=request.getParameter("task");
        String path=res.getContextPath();
        String uri=res.getRequestURI();
        //if(task!=null){//uri格式 xx/ser
            //uri=uri.substring(path.length(), uri.length())+"?"+"task="+task;
        //}else{
        uri=uri.substring(path.length(), uri.length());
        //}
        /*应为要根据permiturls循环，所以会多次打印*/
        System.out.println("当前请求地址:"+uri);
        return uri;
    }
}
