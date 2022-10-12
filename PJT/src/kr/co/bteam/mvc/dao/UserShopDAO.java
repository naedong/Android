package kr.co.bteam.mvc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bteam.mvc.vo.SuperDTO;

@Repository("usercShop")
public class UserShopDAO implements UserShopDAOInter{
	
	@Autowired
	private SqlSessionTemplate ss;
	
		@Override
		public int totalUserShop(String name) {
			// TODO Auto-generated method stub
			return ss.selectOne("user.total", name);
		}

		@Override
		public List<? extends SuperDTO> getShop(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return ss.selectList("user.listshop", map);
		}
		
	}

