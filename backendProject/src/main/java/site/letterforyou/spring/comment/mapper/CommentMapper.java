package site.letterforyou.spring.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.board.domain.CommentVO;
public interface CommentMapper {

	public Long getCommentCount(Long boardNo);
	
	public List<CommentVO> getCommentList(@Param("boardNo")Long boardNo);
	
	public void deleteCommentByBoardNo(@Param("boardNo") Long boardNo);
	
	public void deleteCommentByCommentId(@Param("commentId") Long commentId);
	
	public void postComment(CommentVO commentVo);
	
	public void modifyComment(CommentVO commentVo);
	
	public void deleteCommnet(Long commentNo);
	
	
}
