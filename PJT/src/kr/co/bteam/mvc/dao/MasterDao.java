package kr.co.bteam.mvc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.SuperDTO;
import kr.co.bteam.mvc.vo.UserDTO;

@Repository(value = "mastercShop")
public class MasterDao implements MasterDaoInter{
	
	@Autowired
	private SqlSessionTemplate ss;

	public int total() {
		
	return ss.selectOne("total");	
	}

	@Override
	public List<? extends SuperDTO> getShop(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ss.selectList("master.listshop", map);
	}

	@Override
	public int totalShop(String name) {
		// TODO Auto-generated method stub
		return ss.selectOne("master.total", name);
	}

	

}
