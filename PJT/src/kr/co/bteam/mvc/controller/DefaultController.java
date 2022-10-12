package kr.co.bteam.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.bteam.mvc.controller.user.MailSendService;

@Controller
public class DefaultController {

	
	@Autowired
	private MailSendService mailService;
	
	@RequestMapping(value = { "/", "/main" })
	public String defaultIndex() {
		return "main/index";
	}
	
	@RequestMapping(value =  "/korea")
	public String Index() {
		return "main/korea";
	}
	
	
	@RequestMapping(value =  "/Veu")
	public String Veu() {
		return "main/Veu";
	}
	
	@GetMapping(value =  "/indexJson")
	public String Indexjson() {
		return "main/korea";
	}
	
	
	
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody	
	public String mailCheck(String email) {
		System.out.println("From Ajax Email : "+email);
		return mailService.joinEmail(email);
	}
	
	
	
	
}
