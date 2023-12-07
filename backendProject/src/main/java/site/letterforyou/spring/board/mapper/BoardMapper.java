package site.letterforyou.spring.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.board.domain.AttachVO;
import site.letterforyou.spring.board.domain.BoardVO;
import site.letterforyou.spring.board.domain.CommentVO;

public interface BoardMapper {

	
	public List<BoardVO> getBoardList();
	
	public Long addBoard(BoardVO boardVO);
	
	public void addAttach(@Param("boardNo") Long boardNo, @Param("attachVo") AttachVO attachVo);
	
	public void modifyBoard(@Param("boardNo")Long boardNo , @Param("boardVo")BoardVO boardVo);
	
	public void deleteBoard(Long boardNo);
	
	public void modifyBoardLike(Long userId, Long boardNo);
	
	public void postComment(CommentVO commentVo);
	
	public void deleteCommnet(Long commentNo);
	
	public void modifyComment(Long commentNo);
	
	public AttachVO getAttachByBoardNo(Long boardNo);
	
	
	public Long getBoardLikeCountByBoardNo(Long boardNo);
}
	
