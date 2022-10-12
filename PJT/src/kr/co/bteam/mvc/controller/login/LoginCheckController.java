package kr.co.bteam.mvc.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import kr.co.bteam.mvc.dao.MasterDaoInter;
import kr.co.bteam.mvc.dao.UserDaoInter;
import kr.co.bteam.mvc.vo.UserDTO;

@Controller
@RequestMapping(value = "/login")
public class LoginCheckController {

	@Autowired
	private MasterDaoInter masterdao;

	@Autowired
	private UserDaoInter userdao;

	@RequestMapping(value = "/loginForm")
	public String loginForm() {
		return "login/loginForm";
	}

	// id,pwd => DTO => Dao에서 select
	// 맞으면 => Session을 심어야 하지 않나!
	@PostMapping("/loginProcess") // 인자 순서 중요!
	public ModelAndView loginfProcess(HttpSession session, HttpServletRequest request, UserDTO vo,
			@RequestHeader("User-Agent") String uesrAgent) {
		
		System.out.println("User-Agent : " + uesrAgent); // 유저 브라우저 환경 출력
		System.out.println("Reip: " + request.getRemoteAddr()); // request ip출력
		ModelAndView mav = new ModelAndView("redirect:/main");
		UserDTO uvo = userdao.loginCheck(vo);

		if (uvo == null) {
			mav.setViewName("error/paramException");
			mav.addObject("emsg", "로그인 실패 입니다.");
		} else {

			System.out.println("Reip: " + request.getRemoteAddr()); // request ip출력

			String tier = userdao.getTier(vo.getId());

			// 로그인 성공시, 세션으로 sessionName이란 값으로 DB사용자 이름,
			// sessionID 사용자의 id값을 세션에 저장
			System.out.println("num: " + uvo.getNum());
			System.out.println("id: " + uvo.getId());
			System.out.println("tier: " + tier);

			session.setAttribute("sessionTier", tier);
			session.setAttribute("sessionId", uvo.getId());
			session.setAttribute("sessionNum", uvo.getNum());
		}
		return mav;
	}

	@GetMapping("/logout")
	public ModelAndView loginfoutProcess(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String uesrAgent) {
		ModelAndView mav = new ModelAndView();
		session.removeAttribute("sessionNum");
		session.removeAttribute("sessionTier");
		session.removeAttribute("sessionId");
		mav.setViewName("redirect:/main");
		return mav;
	}

	
}
