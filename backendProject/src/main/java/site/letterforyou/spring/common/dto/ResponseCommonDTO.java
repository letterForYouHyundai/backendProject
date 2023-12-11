package site.letterforyou.spring.common.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommonDTO {

	
	private String timeStamp;
	private int code;
	private HttpStatus status;
	
	
}
