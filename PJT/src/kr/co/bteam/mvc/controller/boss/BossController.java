package kr.co.bteam.mvc.controller.boss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.bteam.mvc.dao.BossDaoInter;
import kr.co.bteam.mvc.dao.ShopDaoInter;
import kr.co.bteam.mvc.service.pubService;
import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.ShopDTO;

@Controller
@RequestMapping(value = "/boss")
public class BossController {
	
	
	@Autowired
	private BossDaoInter bossList;
		
	@Autowired
	private pubService service;
	
	
	@RequestMapping(value = "/bossList")
	public String bossList(Model model, String cPage) {
		int totalRecord = bossList.totalboss();
		model.addAttribute("totalRecord", totalRecord);
		return "main/index";
	}
	
	@RequestMapping(value = { "/", "/main" })
	public String Default() {
		return "main/index";
	}
	
	@RequestMapping(value = "/mypage")
	public String mypage(Model m, HttpSession session) {
		ShopDTO sho = service.bossdetail((Integer) session.getAttribute("sessionNum"));
		m.addAttribute("sho", sho);
		
		return "boss/MyPage";
	}
	

	@RequestMapping("/entry")
	public String Entry() {
		return "boss/entry";
	}
	
	@RequestMapping("/adressPopup")
	public String popUp() {
		return "boss/adressPopup";
	}
	
	
	@GetMapping(value = "/BoardForm/{bnum}")
	public String boardForm(Model m, @PathVariable Integer bnum) {
		System.out.println(bnum);
		ShopDTO shop = service.myShop(bnum);
		m.addAttribute("shop", shop);
		return "master/BoardForm";
	}
	
	@RequestMapping("/UpdateForm")
	public String upDateForm() {
		return "boss/UpdateForm";
	}
	
	@GetMapping("/upset")
	public String Upset(Model m, HttpServletRequest request, @ModelAttribute("shop") ShopDTO shop) {
		System.out.println("upsetForm"+shop.getSnum());

		return "redirect:mypage";
	}
	
	@PostMapping(value = "/entryShop")
    public ModelAndView entryShop(Model m, HttpSession session, @ModelAttribute("vo") ShopDTO vo) {
        System.out.println("EntryShop ����!");
        Map<String, Object> map = new HashMap<>();
        map.put("uno", (Integer) session.getAttribute("sessionNum"));
        map.put("sno", vo.getSnum());
        map.put("sna", vo.getSname());
        map.put("slo", vo.getSloc());
        map.put("sca", vo.getScate());
        map.put("onof", vo.getOnoff());
     
        ModelAndView mav = new ModelAndView("redirect:boss/mypage");
        return mav;
    }
	
	
}
