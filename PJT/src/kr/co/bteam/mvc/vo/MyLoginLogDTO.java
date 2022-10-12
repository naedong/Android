package kr.co.bteam.mvc.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyLoginLogDTO {
	private int num;
	private String reip,uagent, idn, sstime, eetime, status;
}
