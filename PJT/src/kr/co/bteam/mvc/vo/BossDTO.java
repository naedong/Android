package kr.co.bteam.mvc.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BossDTO {
	private int bnum, unum, mnum;
	private UserDTO suser;
}
