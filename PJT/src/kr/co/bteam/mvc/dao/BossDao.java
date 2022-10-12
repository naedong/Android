package kr.co.bteam.mvc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.SuperDTO;
import kr.co.bteam.mvc.vo.UserDTO;

@Repository("bossList")
public class BossDao implements BossDaoInter{
	@Autowired
	private SqlSessionTemplate ss;

	@Override
	public List<? extends SuperDTO> getList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return ss.selectList("boss.listpage");
	}


	@Override
	public ShopDTO bossdetail(int unum) {
		return ss.selectOne("boss.mylist", unum);
	}


	@Override
	public void bossin() {
		ss.selectOne("boss.bossin");
	}

	
	public int totalboss() {
		// TODO Auto-generated method stub
		return ss.selectOne("boss.totalboss");
	}


	@Override
	public ShopDTO myshop(int bnum) {
		// TODO Auto-generated method stub
		return ss.selectOne("boss.myshop", bnum);
	}
	
	@Override
	public void entryshop(Map<String, Object> map) {
		  ss.insert("shop.shopin", map);
	}
}
