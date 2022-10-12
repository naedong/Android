package kr.co.bteam.mvc.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String myHandlerException(Model m, Exception e) {
		e.printStackTrace();
		System.out.println("예외 메세지 : "+e.getMessage());

		if (e instanceof UnsatisfiedServletRequestParameterException  ) {
			m.addAttribute("emsg", "파라미터값을 올바르게 작성하시오");
			return "error/paramException";
		} else if (e instanceof MissingServletRequestParameterException) {
			m.addAttribute("emsg", "파라미터값을 올바르게 작성하시오");
			return "error/paramException";
		}else {
			m.addAttribute("emsg", "예외가 발생 되나요?");
			return "error/paramException";
		}
	}
}
