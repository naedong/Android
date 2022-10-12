package kr.co.bteam.mvc.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private int num;
	private String id, pwd, name, email, udate;
}
