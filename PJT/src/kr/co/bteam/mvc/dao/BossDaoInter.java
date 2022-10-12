package kr.co.bteam.mvc.dao;

import java.util.Map;

import kr.co.bteam.mvc.page.PageListInter;
import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.UserDTO;


public interface BossDaoInter extends PageListInter {
	public ShopDTO bossdetail(int unum);
	public void bossin();
	public int totalboss();
	public ShopDTO myshop(int bnum);
	public void entryshop(Map<String, Object> map);
}
