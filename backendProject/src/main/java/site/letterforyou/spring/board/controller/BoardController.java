package site.letterforyou.spring.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import site.letterforyou.spring.board.service.BoardService;
import site.letterforyou.spring.common.dto.PageRequestDTO;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.PageUtil;
import site.letterforyou.spring.common.util.SessionUtil;

@RestController
@RequestMapping("/board/*")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private PageUtil pageUtil;

	@Autowired
	private SessionUtil sessionUtil;

	@ApiOperation(value = "자유게시판 - 게시글 리스트", notes = "자유게시판 리스트를 가져옵니다.")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, dataTypeClass = Long.class, paramType = "query", defaultValue = "1L"),
	    @ApiImplicitParam(name = "sortBy", value = "조회 기준", required = false, dataType = "string", paramType = "query", defaultValue = "None"),
	    @ApiImplicitParam(name = "inOrder", value = "정렬 기준", required = false, dataType = "Integer", paramType = "query", defaultValue = "1")
	})
	@GetMapping("/list")
	public ResponseEntity<ResponseSuccessDTO<BoardGetListResponseDTO>> getBoardList(
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "inOrder", required = false) Integer inOrder) {
		
		PageRequestDTO pageRequestDTO = pageUtil.parsePaginationComponents(page, sortBy, inOrder);
		log.info(pageRequestDTO.getSortBy() + " " + pageRequestDTO.getInOrder() + " " + pageRequestDTO.getPage());
		log.info(": /board/list/" + page);

		return ResponseEntity.ok(boardService.getBoardList(pageRequestDTO.getSortBy(), pageRequestDTO.getInOrder(),
				pageRequestDTO.getPage()));
	}

	@ApiOperation(value = "자유게시판 - 게시글 상세보기", notes = " 자유게시판 게시글 하나를 상세보기합니다. ")
	@GetMapping("/{boardNo}")
	public ResponseEntity<ResponseSuccessDTO<BoardGetResponseDTO>> getBoard(@PathVariable("boardNo") Long boardNo, HttpSession session) {
		log.info(": /board/" + boardNo);
		
		String userId = sessionUtil.validSession(session);

		return ResponseEntity.ok(boardService.getBoard(boardNo,userId));

	}

	@ApiOperation(value = "자유게시판 - 게시글 등록", notes = " 게시글을 등록합니다. ", produces = "multipart/form-data")
	@PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseSuccessDTO<BoardPostResponseDTO>> addBoard(
			@RequestPart(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles,
			@RequestPart BoardPostRequestDTO boardDTO, HttpSession session) throws IOException {
		
		String userId = sessionUtil.validSession(session);

		log.info(": /board/regist");

		return ResponseEntity.ok(boardService.addBoard(multipartFiles, boardDTO, userId));
	}

	@ApiOperation(value = "자유게시판 - 게시물 변경", notes = " 게시글을 수정합니다. ")
	@PostMapping(value = "/{boardNo}")
	public ResponseEntity<ResponseSuccessDTO<BoardModifyResponseDTO>> modifyBoard(@PathVariable("boardNo") Long boardNo,
			@RequestPart BoardModifyRequestDTO boardDTO,HttpSession session) {
		String userId = sessionUtil.validSession(session);
		return ResponseEntity.ok(boardService.modifyBoard(boardNo, boardDTO, userId));
	}

	@ApiOperation(value = "자유게시판 - 게시물 삭제", notes = " 게시글을 삭제합니다. ")
	@PutMapping(value = "/{boardNo}")
	public ResponseEntity<ResponseSuccessDTO<BoardDeleteResponseDTO>> deleteBoard(
			@PathVariable("boardNo") Long boardNo) {
		return ResponseEntity.ok(boardService.deleteBoard(boardNo));
	}

	@ApiOperation(value = "자유게시판 - 댓글 등록", notes = " 댓글을 등록합니다. ")
	@PostMapping(value = "/comment")
	public ResponseEntity<ResponseSuccessDTO<CommentPostResponseDTO>> postComment(
			@RequestBody CommentPostRequestDTO commentDTO, HttpSession session) {
		String userId = sessionUtil.validSession(session);
		return ResponseEntity.ok(boardService.postComment(commentDTO, userId));
	}

	@ApiOperation(value = "자유게시판 - 댓글 수정", notes = " 댓글을 수정합니다. ")
	@PostMapping(value = "/comment/{commentNo}")
	public ResponseEntity<ResponseSuccessDTO<CommentModifyResponseDTO>> modifyComment(
			@PathVariable("commentNo") Long commentNo, @RequestBody CommentModifyRequestDTO commentDTO) {
		return ResponseEntity.ok(boardService.modifyComment(commentNo, commentDTO));
	}

	@ApiOperation(value = "자유게시판 - 댓글 삭제", notes = " 댓글을 삭제합니다. ")
	@PutMapping(value = "/comment/{commentNo}")
	public ResponseEntity<ResponseSuccessDTO<CommentDeleteResponseDTO>> deleteComment(
			@PathVariable("commentNo") Long commentNo) {
		return ResponseEntity.ok(boardService.deleteComment(commentNo));
	}

	@ApiOperation(value = "자유게시판 - 게시글 추천/비추천", notes = " 게시글을 추천 혹은 비추천합니다. ")
	@PostMapping(value = "/likes/{boardNo}")
	public ResponseEntity<ResponseSuccessDTO<BoardLikeUpdateResponseDTO>> updateBoardLike(
			@PathVariable("boardNo") Long boardNo, HttpSession session) {

		String userId = sessionUtil.validSession(session);
		return ResponseEntity.ok(boardService.updateBoardLike(boardNo, userId));

	}

}
