package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import site.letterforyou.spring.board.dto.BoardDeleteResponseDTO;
import site.letterforyou.spring.board.dto.BoardLikeUpdateResponseDTO;
import site.letterforyou.spring.board.dto.BoardModifyRequestDTO;
import site.letterforyou.spring.board.dto.BoardModifyResponseDTO;
import site.letterforyou.spring.board.dto.BoardPostRequestDTO;
import site.letterforyou.spring.board.dto.BoardPostResponseDTO;
import site.letterforyou.spring.board.dto.CommentDeleteResponseDTO;
import site.letterforyou.spring.board.dto.CommentModifyRequestDTO;
import site.letterforyou.spring.board.dto.CommentModifyResponseDTO;
import site.letterforyou.spring.board.dto.CommentPostRequestDTO;
import site.letterforyou.spring.board.dto.CommentPostResponseDTO;
import site.letterforyou.spring.board.dto.GetBoardListResponseDTO;
import site.letterforyou.spring.board.dto.GetBoardResponseDTO;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;

public interface BoardService {

	
	public ResponseSuccessDTO<List<GetBoardListResponseDTO>> getBoardList();
	
	public ResponseSuccessDTO<GetBoardResponseDTO> getBoard(Long boardNo);

	ResponseSuccessDTO<BoardPostResponseDTO> addBoard(List<MultipartFile> multiPartFiles, BoardPostRequestDTO boardDTO) throws IOException;
	
	public ResponseSuccessDTO<BoardModifyResponseDTO> modifyBoard(Long boardNo ,BoardModifyRequestDTO boardDTO);
	
	public ResponseSuccessDTO<BoardDeleteResponseDTO> deleteBoard(Long boardNo);
	
	public ResponseSuccessDTO<CommentPostResponseDTO> postComment(CommentPostRequestDTO commentDTO);
	
	public ResponseSuccessDTO<CommentModifyResponseDTO> modifyComment(Long commentNo, CommentModifyRequestDTO commentDTO);

	
	public ResponseSuccessDTO<CommentDeleteResponseDTO> deleteComment(Long commentNo);
	
	public ResponseSuccessDTO<BoardLikeUpdateResponseDTO> updateBoardLike(Long boardNo);
	
	
}
