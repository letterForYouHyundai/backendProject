package site.letterforyou.spring.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.board.domain.AttachVO;
import site.letterforyou.spring.board.domain.BoardVO;
import site.letterforyou.spring.member.domain.MemberDTO;

public interface BoardMapper {

	
	public List<BoardVO> getBoardList(@Param("sortBy") String sortBy, 
            @Param("inOrder") String inOrder, 
            @Param("offset") Long offset, 
            @Param("size") Long size);
	
	public int getTotalCountBoard();
	
	public BoardVO getBoard(Long boardNo);
	
	public Long addBoard(@Param("boardVo")BoardVO boardVo);
	
	public void addAttach(@Param("boardNo") Long boardNo, @Param("attachVo") AttachVO attachVo);
	
	public void modifyBoard(@Param("boardNo")Long boardNo , @Param("boardVo")BoardVO boardVo);
	
	public void deleteBoard(Long boardNo);
	
	public void updateBoardLike(@Param("boardNo")Long boardNo, @Param("userId")String userId);
	
	public List<AttachVO> getAttachByBoardNo(Long boardNo);
	
	public BoardVO getBoardLike(@Param("boardNo")Long boardNo, @Param("userId")String userId);
	
	public void updateBoardView(@Param("boardNo") Long boardNo);
	
	public Long getLatestBoardNo(); 
	
	public MemberDTO getUser(String userId);
}	
	
