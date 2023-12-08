package site.letterforyou.spring.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.board.domain.AttachVO;
import site.letterforyou.spring.board.domain.BoardVO;

public interface BoardMapper {

	
	public List<BoardVO> getBoardList();
	
	public BoardVO getBoard(Long boardNo);
	
	public Long addBoard(@Param("boardVo")BoardVO boardVo);
	
	public void addAttach(@Param("boardNo") Long boardNo, @Param("attachVo") AttachVO attachVo);
	
	public void modifyBoard(@Param("boardNo")Long boardNo , @Param("boardVo")BoardVO boardVo);
	
	public void deleteBoard(Long boardNo);
	
	public void modifyBoardLike(Long userId, Long boardNo);
	
	
	
	public List<AttachVO> getAttachByBoardNo(Long boardNo);
	
	
	public Long getBoardLikeCountByBoardNo(@Param("boardNo") Long boardNo);
}
	
