package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import site.letterforyou.spring.board.dto.BoardModifyRequestDTO;
import site.letterforyou.spring.board.dto.BoardModifyResponseDTO;
import site.letterforyou.spring.board.dto.BoardPostRequestDTO;
import site.letterforyou.spring.board.dto.BoardPostResponseDTO;
import site.letterforyou.spring.board.dto.CommentDTO;
import site.letterforyou.spring.board.dto.GetBoardListResponseDTO;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;

public interface BoardService {

	
	public ResponseSuccessDTO<List<GetBoardListResponseDTO>> getBoardList();
	

	ResponseSuccessDTO<BoardPostResponseDTO> addBoard(List<MultipartFile> multiPartFiles, BoardPostRequestDTO boardDTO) throws IOException;
	
	public ResponseSuccessDTO<BoardModifyResponseDTO> modifyBoard(Long boardNo ,BoardModifyRequestDTO boardDTO);
	
	public GetBoardListResponseDTO deleteBoard(Long boardNo);
	
	public GetBoardListResponseDTO modifyBoardLike(Long boardNo);
	
	public CommentDTO postComment(CommentDTO commentDTO);
	
	public Long deleteComment(Long commentNo);
	
	public Long modifyComment(Long commentNo);

	
	
	
	
}
