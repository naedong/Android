package kr.co.bteam.mvc.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bteam.mvc.page.PageListInter;
import kr.co.bteam.mvc.vo.SuperDTO;
import kr.co.bteam.mvc.vo.UserDTO;

@Repository("logList")
public class MyLoginLoggerDao implements MyLoginLoggerDaoInter {


	@Autowired
	private SqlSessionTemplate ss;
	
	@Override
	public List<? extends SuperDTO> getList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return ss.selectList("log.loglist", map);
	}

	@Override
	public int totalLog() {
		// TODO Auto-generated method stub
		return ss.selectOne("log.totallog");
	}

	
	
}
