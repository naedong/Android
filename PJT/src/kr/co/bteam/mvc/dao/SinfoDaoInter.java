package kr.co.bteam.mvc.dao;

import kr.co.bteam.mvc.page.PageListInter;
import kr.co.bteam.mvc.vo.UserDTO;


public interface SinfoDaoInter extends PageListInter {
	public int totalsinfo();

	public int toinfo(int snum);

}
