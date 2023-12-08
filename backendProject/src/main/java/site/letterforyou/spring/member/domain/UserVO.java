package site.letterforyou.spring.member.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	private String userName;
	
	private String userEmail;
	
	private String userNickname;
	
	private String userImage;
	
	private String signupDate;
	
}
