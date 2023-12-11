package site.letterforyou.spring.common.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseErrorDTO<T> extends ResponseCommonDTO{
	private String path;
    private T error;
	
	@Builder
	public ResponseErrorDTO(String timeStamp, int code, HttpStatus status, String path , T error) {
		super(timeStamp, code, status);
		this.path = path;
		this.error = error;
	}
}
