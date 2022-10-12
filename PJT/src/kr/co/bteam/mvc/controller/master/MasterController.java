package kr.co.bteam.mvc.controller.master;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bteam.mvc.dao.MasterDaoInter;
import kr.co.bteam.mvc.service.pubService;
import kr.co.bteam.mvc.vo.ShopDTO;


@Controller
@RequestMapping(value = "/master")
public class MasterController {

	@Autowired
	private pubService service;
	
	@Autowired
	private MasterDaoInter mastercShop;
	
	
	@RequestMapping(value = "/mypage")
	public String myPage(HttpSession session) {
		int totalRecord = service.totalboss();
		System.out.println("total"+totalRecord);
		session.setAttribute("totalRecord", totalRecord);
		return "master/MyPage";
	}
	
	@GetMapping(value = "UpdateForm")
	public String Update(Model m, ShopDTO shop) {
		m.addAttribute("sh", shop);
		return "master/UpdateForm";
	}
	
	
	
	@RequestMapping(value =  "/masterkorea")
	public String Index() {
		return "master/korea";
	}
	
	
	@GetMapping(value = "/mastercShop")
	public String mastercShop(Model model, String cPage,String name) {
		System.out.println("값 전달확인"+name);
		int num = mastercShop.totalShop(name);
		model.addAttribute("totalRecord", num);
		return "master/ShopList";
	}
	
	

}