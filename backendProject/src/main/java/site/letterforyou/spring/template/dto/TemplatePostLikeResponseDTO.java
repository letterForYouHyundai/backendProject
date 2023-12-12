package site.letterforyou.spring.template.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemplatePostLikeResponseDTO {
	
	private String likeYn;
	private String message;
}
