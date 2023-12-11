package site.letterforyou.spring.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TemplateDTO {
	
	private Long templateNo;
	private String templateTitle;
	private String templateContent;
	private String registDate;
	private Long templateLikes;
	
	
}
