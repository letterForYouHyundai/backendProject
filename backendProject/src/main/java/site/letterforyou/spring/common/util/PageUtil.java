package site.letterforyou.spring.common.util;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.dto.PageRequestDTO;

@Component
@Slf4j

public class PageUtil {

	public PageRequestDTO parsePaginationComponents(Long page, String sortBy, Integer inOrder) {

		Long defaultPage = 1L;
		String defaultSortBy = "dates";
		int defaultInOrder = 1;
		Long p = page == null ? defaultPage : page;
		String sb = sortBy == null ? defaultSortBy : sortBy;
		int io = inOrder == null ? defaultInOrder : inOrder;
		
		PageRequestDTO pageRequestDTO = new PageRequestDTO();
		pageRequestDTO.setPage(p);
		pageRequestDTO.setSortBy(sb);
		pageRequestDTO.setInOrder(io);
		
		return pageRequestDTO;
	}

}
