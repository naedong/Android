package kr.co.bteam.mvc.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopDTO {
	private int bnum;
	private String snum, sname, sloc, scate, onoff, sdate;
	private String cnum, ctf, cend, edate;
	private BossDTO boss;
}
