package kr.co.bteam.mvc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bteam.mvc.vo.MyLoginLogDTO;
import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.SuperDTO;
import kr.co.bteam.mvc.vo.UserDTO;

@Repository("userList")
public class UserDao implements UserDaoInter{
	@Autowired
	private SqlSessionTemplate ss;
	
	@Override
	public String getTier(String mid) {
		return ss.selectOne("user.tier",mid);
	}

	@Override
	public UserDTO loginCheck(UserDTO vo) {
		return ss.selectOne("user.login",vo);
	}

	@Override
	public void addLoginLogging(MyLoginLogDTO vo) {
		ss.insert("user.logger_in", vo);
	}
	@Override
	public void insertuser(UserDTO vo) {
		System.out.print(vo.getId());
			ss.insert("user.userin", vo);
	}

	
	@Override
	public List<? extends SuperDTO> getList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return ss.selectList("user.listpage", map);
	}

	@Override
	public int totalUser() {
		// TODO Auto-generated method stub
		return ss.selectOne("user.totaluser");
	}
	
}
