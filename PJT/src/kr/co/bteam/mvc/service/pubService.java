package kr.co.bteam.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bteam.mvc.dao.BossDaoInter;
import kr.co.bteam.mvc.dao.ShopDaoInter;
import kr.co.bteam.mvc.dao.UserDaoInter;
import kr.co.bteam.mvc.page.PageListInter;
import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.UserDTO;


@Service
public class pubService {
	
	@Autowired
	private UserDaoInter userList;
	
	@Autowired
	private BossDaoInter bossList;

	@Autowired
	private ShopDaoInter shopList;

	
	@Transactional
	public void insertUser(UserDTO vo) {
		userList.insertuser(vo);	
		bossList.bossin();	
	}

	@Transactional
	public ShopDTO bossdetail(int unum) {
		return bossList.bossdetail(unum);
	}
		
	@Transactional
	public ShopDTO myShop(int bnum) {
		return bossList.myshop(bnum);
	}
	
	
	@Transactional
	public int totalboss() {
		return bossList.totalboss();

	}
	
	
	
	
}
