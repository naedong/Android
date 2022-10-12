package kr.co.bteam.mvc.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import kr.co.bteam.mvc.page.PageListInter;
import kr.co.bteam.mvc.page.ShopListInter;
import kr.co.bteam.mvc.vo.ShopDTO;
import kr.co.bteam.mvc.vo.SuperDTO;


@Component
@Aspect
public class PageModulesAdvice {

	 @Autowired
	 private ApplicationContext applicationContext;
	
	 private  int nowPage = 1;
	 private  int nowBlock = 1;
	 private  int totalRecord = 0; 
	 private  int numPerPage = 10;
	 private  int pagePerBlock = 5;
	 private  int totalPage = 0;
	 private  int totalBlock = 0;
	 private  int beginPerPage = 0;
	 private  int endPerPage = 0;
	 
	 @Around("execution(* kr.co.bteam.mvc.controller.*.*Controller.*List(..))")
	 public String pageModule(ProceedingJoinPoint jp) {
		 Object[] args = jp.getArgs();
		 Model m = (Model) args[0];
		 String cPage = (String) args[1];
		 int snum = 0;
		 if(args[2] != null) {
			 snum = (int) args[2];
		 }
		 
		 String myDao = jp.getSignature().getName();
		 PageListInter pageListInter =   applicationContext.getBean( myDao,PageListInter.class);
		 String url=null;
		 try {
			url = (String) jp.proceed();
			 totalRecord = (int) m.asMap().get("totalRecord");
			totalPage = (int) Math.ceil(totalRecord/(double)numPerPage);
			totalBlock = (int) Math.ceil((double) totalPage/pagePerBlock);
			String s_page = cPage;
			if(s_page != null){
				nowPage = Integer.parseInt(s_page);
		    }
			beginPerPage = (nowPage - 1) * numPerPage + 1;
			endPerPage = (beginPerPage-1) + numPerPage;
			Map<String, Integer> map = new HashMap<String, Integer>();
		    map.put("begin", beginPerPage);
		    map.put("end", endPerPage);
		    map.put("snum", snum);
		    List<? extends SuperDTO> list = pageListInter.getList(map); 
		    m.addAttribute("list", list);
			int startPage = (int)((nowPage-1)/pagePerBlock)*pagePerBlock+1;
			int endPage = startPage + pagePerBlock - 1;
			System.out.println(endPage);
			if(endPage > totalPage){
				endPage = totalPage;
		    }
			m.addAttribute("startPage", startPage);
			m.addAttribute("endPage", endPage);
			m.addAttribute("nowPage", nowPage);
			m.addAttribute("pagePerBlock", pagePerBlock);
			m.addAttribute("totalPage",totalPage);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		 System.out.println("url=>"+url);
		 return url; 
	 }
	 
	 @Around("execution(* kr.co.bteam.mvc.controller.*.*Controller.*cShop(..))")
	 public String pageShopModule(ProceedingJoinPoint jp) {
		 Object[] args = jp.getArgs();
		 Model m = (Model) args[0];
		 String cPage = (String) args[1];
		 String name = (String) args[2];
		 if(name.equals("Seoul")) {
			 name = "서울";
		 }
		 
		 String myDao = jp.getSignature().getName();
		 ShopListInter pageListInter =   applicationContext.getBean( myDao,ShopListInter.class);
		 String url=null;
		 try {
			url = (String) jp.proceed();
			 totalRecord = (int) m.asMap().get("totalRecord");
			 System.out.println(totalRecord);
			 totalPage = (int) Math.ceil(totalRecord/(double)numPerPage);
			totalBlock = (int) Math.ceil((double) totalPage/pagePerBlock);
			String s_page = cPage;
			if(s_page != null){
				nowPage = Integer.parseInt(s_page);
		    }
			
			beginPerPage = (nowPage - 1) * numPerPage + 1;
			endPerPage = (beginPerPage-1) + numPerPage;
			Map<String, Object> map = new HashMap<String, Object>();
		    map.put("begin", beginPerPage);
		    map.put("end", endPerPage);
		    map.put("name", name);
		    System.out.println(name);
		    List<? extends SuperDTO> list = pageListInter.getShop(map); 
		    m.addAttribute("list", list);
			int startPage = (int)((nowPage-1)/pagePerBlock)*pagePerBlock+1;
			int endPage = startPage + pagePerBlock - 1;
			System.out.println(startPage);
			System.out.println(endPage);
			if(endPage > totalPage){
				endPage = totalPage;
		    }
			m.addAttribute("startPage", startPage);
			m.addAttribute("endPage", endPage);
			m.addAttribute("nowPage", nowPage);
			m.addAttribute("pagePerBlock", pagePerBlock);
			m.addAttribute("totalPage",totalPage);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		 System.out.println("url=>"+url);
		 return url; 
	 }
	 
}







