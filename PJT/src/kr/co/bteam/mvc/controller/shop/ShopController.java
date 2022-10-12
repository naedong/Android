package kr.co.bteam.mvc.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.bteam.mvc.dao.ShopDaoInter;
import kr.co.bteam.mvc.vo.ShopDTO;


@Controller
@RequestMapping(value = "/shop")
public class ShopController {
		
	
	@Autowired
	private ShopDaoInter shopLis;
	
	@RequestMapping(value = "/shopList")
	public String shopList(Model model, String cPage) {
		int totalRecord = shopLis.totalboss();
		System.out.println("total"+totalRecord);
		model.addAttribute("totalRecord", totalRecord);
		return "master/MasterList";
	}



	
	
}
