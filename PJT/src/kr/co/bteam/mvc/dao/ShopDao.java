package kr.co.bteam.mvc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bteam.mvc.vo.BossDTO;
import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.SinfoDTO;
import kr.co.bteam.mvc.vo.SuperDTO;
import kr.co.bteam.mvc.vo.UserDTO;

@Repository("shopList")
public class ShopDao implements ShopDaoInter{
	
	@Autowired
	private SqlSessionTemplate ss;

	@Override
	public List<? extends SuperDTO> getList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return ss.selectList("shop.listpage", map);
	}

	@Override
	public int totalboss() {
		// TODO Auto-generated method stub
		return ss.selectOne("shop.totalboss");
	}


	
}
