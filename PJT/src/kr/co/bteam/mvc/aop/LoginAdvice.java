package kr.co.bteam.mvc.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import kr.co.bteam.mvc.dao.UserDaoInter;
import kr.co.bteam.mvc.vo.MyLoginLogDTO;

@Component
@Aspect
public class LoginAdvice {
	private String userAgent;
	
	@Autowired
	private UserDaoInter userdao;
	
	@Pointcut("execution(* kr.co.bteam.mvc.controller.login.LoginCheckController.loginf*(..))")
	public void dumi() {}
	
	@Around(value = "dumi()")
	public ModelAndView loginLogger(ProceedingJoinPoint jp) {
		System.out.println("loginLogger 전처리");
		Object[] fd = jp.getArgs();
		ModelAndView rpath = null;
		String methodName = jp.getSignature().getName();
		System.out.println("loginLogger 수행 메서드 : " + methodName);
		
		if(methodName.equals("loginfProcess")) {
			MyLoginLogDTO vo = new MyLoginLogDTO();
			userAgent = (String) fd[3];
			try {
				rpath = (ModelAndView) jp.proceed();
				
				if(fd[0] instanceof HttpSession && fd[1] instanceof HttpServletRequest) {
					HttpSession session = (HttpSession) fd[0];
					HttpServletRequest request = (HttpServletRequest) fd[1];
					String uid = (String) session.getAttribute("sessionId");
					if(uid != null) {
						vo.setIdn(uid);
						vo.setStatus("login");
						vo.setReip(request.getRemoteAddr());
						vo.setUagent(userAgent);
						userdao.addLoginLogging(vo);
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else if(methodName.equals("loginfoutProcess")){
			System.out.println(userAgent);
			MyLoginLogDTO vo = new MyLoginLogDTO();
			userAgent = (String) fd[2];
			if(fd[0] instanceof HttpSession && fd[1] instanceof HttpServletRequest ) {
				HttpSession session = (HttpSession) fd[0];
				HttpServletRequest request = (HttpServletRequest) fd[1];
				String uid = (String) session.getAttribute("sessionId");
				if(uid != null) {
					vo.setIdn(uid);
					vo.setStatus("logout");
					vo.setReip(request.getRemoteAddr());
					vo.setUagent(userAgent);
					userdao.addLoginLogging(vo);
				}
			}
		}try {
			rpath = (ModelAndView) jp.proceed();
		}catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("loginLogger 후처리");
		return rpath;
	}
}
