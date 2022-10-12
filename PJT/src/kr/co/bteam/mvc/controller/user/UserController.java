package kr.co.bteam.mvc.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.bteam.mvc.dao.MasterDaoInter;
import kr.co.bteam.mvc.dao.UserDaoInter;
import kr.co.bteam.mvc.dao.UserShopDAOInter;
import kr.co.bteam.mvc.service.pubService;
import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.UserDTO;


@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	
	@Autowired
	private UserDaoInter userList;
	
	@Autowired
	private UserShopDAOInter usercShop;
	
	
	@Autowired
	private pubService service;
	
		@RequestMapping(value="/userForm")
		public String userForm() {
			return "user/userForm";
		}
		
		@PostMapping(value="/userIn")
		public String userIn(UserDTO vo) {
			System.out.println(vo.getId());
			service.insertUser(vo);
			return "main/index";
		}
		
		
		@RequestMapping(value="/mypage")
		public String userMypage() {
			return "user/MyPage";
		}
		
		@RequestMapping(value="/setUP")
		public String userUP(BossDTO vo) {
			
			return "user/MyPage";
		}
		
		@RequestMapping(value="/stat")
		public String userstat(BossDTO vo) {
			return "user/MyPage";
		}
		
		
		@RequestMapping(value = "/userList")
		public String userList(Model model, String cPage) {
			int totalRecord = userList.totalUser();
			model.addAttribute("totalRecord", totalRecord);
			return "master/UserList";
		}
		
		
		@GetMapping(value = "/usercShop")
		public String usercShop(Model model, String cPage,String name) {
			System.out.println("값 전달확인"+name);
			int num = usercShop.totalUserShop(name);
			model.addAttribute("totalRecord", num);
			return "user/ShopList";
		}
		
		
}
