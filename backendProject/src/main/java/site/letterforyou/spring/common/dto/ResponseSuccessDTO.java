package site.letterforyou.spring.common.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSuccessDTO<T> extends ResponseCommonDTO {

    private T data;

    @Builder
    public ResponseSuccessDTO(String timeStamp, int code, HttpStatus status, T data){
        super(timeStamp, code, status);
        this.data = data;
    }
}