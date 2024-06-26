package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import site.letterforyou.spring.board.dto.BoardDeleteResponseDTO;
import site.letterforyou.spring.board.dto.BoardGetListResponseDTO;
import site.letterforyou.spring.board.dto.BoardGetResponseDTO;
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
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;

public interface BoardService {

	
	public ResponseSuccessDTO<BoardGetListResponseDTO> getBoardList(String sortBy, int inOrder , Long page);
	
	public ResponseSuccessDTO<BoardGetResponseDTO> getBoard(Long boardNo, String userId);

	ResponseSuccessDTO<BoardPostResponseDTO> addBoard(List<MultipartFile> multiPartFiles, BoardPostRequestDTO boardDTO, String userId) throws IOException;
	
	public ResponseSuccessDTO<BoardModifyResponseDTO> modifyBoard(Long boardNo ,BoardModifyRequestDTO boardDTO, String userId);
	
	public ResponseSuccessDTO<BoardDeleteResponseDTO> deleteBoard(Long boardNo);
	
	public ResponseSuccessDTO<CommentPostResponseDTO> postComment(CommentPostRequestDTO commentDTO, String userId);
	
	public ResponseSuccessDTO<CommentModifyResponseDTO> modifyComment(Long commentNo, CommentModifyRequestDTO commentDTO);

	
	public ResponseSuccessDTO<CommentDeleteResponseDTO> deleteComment(Long commentNo);
	
	public ResponseSuccessDTO<BoardLikeUpdateResponseDTO> updateBoardLike(Long boardNo, String userId);
	
	
}
