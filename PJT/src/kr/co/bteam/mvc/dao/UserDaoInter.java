package kr.co.bteam.mvc.dao;

import kr.co.bteam.mvc.page.PageListInter;
import kr.co.bteam.mvc.vo.MyLoginLogDTO;
import kr.co.bteam.mvc.vo.UserDTO;


public interface UserDaoInter extends PageListInter {
	public String getTier(String mid);
	public UserDTO loginCheck(UserDTO vo);
	public void addLoginLogging(MyLoginLogDTO vo);
	public void insertuser(UserDTO vo);
	public int totalUser();

}
