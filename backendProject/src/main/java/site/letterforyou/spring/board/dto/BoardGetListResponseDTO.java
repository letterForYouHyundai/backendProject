package site.letterforyou.spring.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.letterforyou.spring.common.domain.Pagination;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardGetListResponseDTO {

private List<BoardDTO> boardList;

private Pagination pagination;
	
}
