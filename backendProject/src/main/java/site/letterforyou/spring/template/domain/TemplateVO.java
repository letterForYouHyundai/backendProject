package site.letterforyou.spring.template.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateVO {
	
	private Long templateNo;
	private String templateTitle;
	private String templateContent;
	private LocalDateTime registDate;
	private String likeYn;
	private Long templateLikes;
	
	

}
