package kr.co.bteam.mvc.page;

import java.util.List;
import java.util.Map;

import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.SinfoDTO;
import kr.co.bteam.mvc.vo.SuperDTO;



public interface PageListInter {

	public List<? extends SuperDTO> getList(Map<String, Integer> map);

}
