package site.letterforyou.spring.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.template.domain.TemplateVO;

public interface TemplateMapper {

	public List<TemplateVO> getTemplateList(@Param("sortBy") String sortBy, @Param("inOrder") String inOrder,
			@Param("offset") Long offset, @Param("size") Long size);

	public List<TemplateVO> getTemplateSearchList(@Param("keyword") String keyword, @Param("sortBy") String sortBy,
			@Param("inOrder") String inOrder, @Param("offset") Long offset, @Param("size") Long size);

	public int getTotalCountTemplate();
	
	public int getTotalCountTemplateByKeyword(@Param("keyword") String keyword);
}
