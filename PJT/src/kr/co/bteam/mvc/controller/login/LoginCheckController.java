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

	// id,pwd => DTO => Dao���� select
	// ������ => Session�� �ɾ�� ���� �ʳ�!
	@PostMapping("/loginProcess") // ���� ���� �߿�!
	public ModelAndView loginfProcess(HttpSession session, HttpServletRequest request, UserDTO vo,
			@RequestHeader("User-Agent") String uesrAgent) {
		
		System.out.println("User-Agent : " + uesrAgent); // ���� ������ ȯ�� ���
		System.out.println("Reip: " + request.getRemoteAddr()); // request ip���
		ModelAndView mav = new ModelAndView("redirect:/main");
		UserDTO uvo = userdao.loginCheck(vo);

		if (uvo == null) {
			mav.setViewName("error/paramException");
			mav.addObject("emsg", "�α��� ���� �Դϴ�.");
		} else {

			System.out.println("Reip: " + request.getRemoteAddr()); // request ip���

			String tier = userdao.getTier(vo.getId());

			// �α��� ������, �������� sessionName�̶� ������ DB����� �̸�,
			// sessionID ������� id���� ���ǿ� ����
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
