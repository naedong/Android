package kr.co.bteam.mvc.controller.myloginlog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.bteam.mvc.dao.BossDaoInter;
import kr.co.bteam.mvc.dao.MyLoginLoggerDaoInter;
import kr.co.bteam.mvc.dao.ShopDaoInter;
import kr.co.bteam.mvc.service.pubService;
import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.ShopDTO;
import lombok.extern.java.Log;

@Controller
@RequestMapping(value = "/log")
public class MyLoginLogController {
	
	
	@Autowired
	private MyLoginLoggerDaoInter logList;
		

	@RequestMapping(value = "/logList")
	public String logList(Model model, String cPage) {
		int totalRecord = logList.totalLog();
		System.out.println("total"+totalRecord);
		model.addAttribute("totalRecord", totalRecord);
		return "master/LogForm";
	}
	
		
}
