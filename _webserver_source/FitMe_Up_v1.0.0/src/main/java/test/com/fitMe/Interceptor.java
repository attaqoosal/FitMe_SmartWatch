package test.com.fitMe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Service
public class Interceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle().....");

		String sPath = request.getServletPath();

		logger.info("sPath:" + sPath);

		HttpSession session = request.getSession();

		if (sPath.compareTo("/logout.do") == 0) {
			session.removeAttribute("id");
			session.removeAttribute("pw");
			logger.info("user_id:" + session.getAttribute("user_id"));
			logger.info("pw:" + session.getAttribute("pw"));
			response.sendRedirect("main.do");
		} else if (sPath.compareTo("admin_idCheck.do") == 0) {
			session.getAttribute("id");
			session.getAttribute("pw");
			logger.info("user_id:" + session.getAttribute("user_id"));
			logger.info("pw:" + session.getAttribute("pw"));
			response.sendRedirect("home.do");

		}

		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		/*
		 * String sPath=request.getServletPath();
		 * 
		 * logger.info("sPath:"+sPath);
		 * 
		 * HttpSession session = request.getSession();
		 * 
		 * if(sPath.compareTo("/loginOK.do")==0) { UserController uc = new
		 * UserController(); String id=uc.getResult(); String pw=uc.getResult2(); int
		 * u_check=uc.getResult3(); logger.info("user_id:"+id); logger.info("pw:"+pw);
		 * logger.info("u_check:"+u_check);
		 * 
		 * session.setAttribute("id", id); session.setAttribute("pw", pw);
		 * 
		 * response.sendRedirect("home.do");
		 * 
		 * 
		 * }
		 */
	}

}
