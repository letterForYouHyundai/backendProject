package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.board.domain.AttachVO;
import site.letterforyou.spring.board.domain.BoardVO;
import site.letterforyou.spring.board.domain.CommentVO;
import site.letterforyou.spring.board.dto.BoardDTO;
import site.letterforyou.spring.board.dto.BoardDeleteResponseDTO;
import site.letterforyou.spring.board.dto.BoardGetListResponseDTO;
import site.letterforyou.spring.board.dto.BoardGetResponseDTO;
import site.letterforyou.spring.board.dto.BoardLikeUpdateResponseDTO;
import site.letterforyou.spring.board.dto.BoardModifyRequestDTO;
import site.letterforyou.spring.board.dto.BoardModifyResponseDTO;
import site.letterforyou.spring.board.dto.BoardPostRequestDTO;
import site.letterforyou.spring.board.dto.BoardPostResponseDTO;
import site.letterforyou.spring.board.dto.BoardUpdateDTO;
import site.letterforyou.spring.board.dto.CommentDeleteResponseDTO;
import site.letterforyou.spring.board.dto.CommentGetResponseDTO;
import site.letterforyou.spring.board.dto.CommentModifyRequestDTO;
import site.letterforyou.spring.board.dto.CommentModifyResponseDTO;
import site.letterforyou.spring.board.dto.CommentPostRequestDTO;
import site.letterforyou.spring.board.dto.CommentPostResponseDTO;
import site.letterforyou.spring.board.mapper.BoardMapper;
import site.letterforyou.spring.comment.mapper.CommentMapper;
import site.letterforyou.spring.common.domain.PageVO;
import site.letterforyou.spring.common.domain.Pagination;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.exception.service.EntityNullException;
import site.letterforyou.spring.member.domain.MemberDTO;

@Service
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private ResponseUtil responseUtil;
	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private TimeService timeService;

	@Autowired
	private AmazonS3Service s3Service;
	@Autowired
	private ThumbnailService thumbnailService;

	public ResponseSuccessDTO<BoardGetListResponseDTO> getBoardList(String sortBy, int inOrder, Long page) {

		PageVO pageVo = new PageVO(page, 15L, 10L);
		if (sortBy == null) {
			inOrder = 1;
			sortBy = "DESC";
		}
		pageVo.setSortBy(sortBy);

		if (inOrder == 0)
			pageVo.setOrderBy("ASC");
		else if (inOrder == 1)
			pageVo.setOrderBy("DESC");

		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();

		List<BoardVO> boardVoList = boardMapper.getBoardList(pageVo.getSortBy(), pageVo.getOrderBy(), offset, size);

		BoardGetListResponseDTO result = new BoardGetListResponseDTO();
		List<BoardDTO> boardList = new ArrayList<>();
		for (BoardVO b : boardVoList) {
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setBoardNo(b.getBoardNo());
			boardDTO.setBoardTitle(b.getBoardTitle());
			boardDTO.setBoardView(b.getBoardView());
			boardDTO.setCommentCount(b.getCommentCount());
			boardDTO.setImage(b.getBoardThumbNail());
			boardDTO.setUserNickname(b.getUserNickname());
			boardDTO.setRegistDate(timeService.parseLocalDateTimeForBoardList(b.getRegistDate()));
			boardDTO.setLikeCount(b.getLikeCount());

			boardList.add(boardDTO);
		}
		result.setBoardList(boardList);
		int count = boardMapper.getTotalCountBoard();

		Pagination pagination = new Pagination(count, pageVo);
		result.setPagination(pagination);
		ResponseSuccessDTO<BoardGetListResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardGetResponseDTO> getBoard(Long boardNo, String userId) {

		BoardGetResponseDTO responseDTO = new BoardGetResponseDTO();

		BoardVO boardVo = boardMapper.getBoard(boardNo);

		if (boardVo == null) {
			throw new EntityNullException("게시물이 존재하지 않습니다.");
		}
		if (!boardVo.getUserId().equals(userId)) { // 글쓴이가 아니라면 조회수 증가
			boardMapper.updateBoardLike(boardNo);
		}
		BoardVO bv = boardMapper.getBoardLike(boardNo, userId);

		// 해당 유저의 좋아요 판별
		String userLiked = "Y";
		if (bv == null) {
			userLiked = "N";
		}
		MemberDTO mdto = boardMapper.getUser(userId);
		String ownerNick = mdto.getUserNickname();

		List<CommentVO> commentVoList = commentMapper.getCommentList(boardNo);

		List<AttachVO> attachVoList = boardMapper.getAttachByBoardNo(boardNo);

		List<CommentGetResponseDTO> commentList = new ArrayList<>();
		List<String> attachList = new ArrayList<>();

		for (CommentVO c : commentVoList) {
			CommentGetResponseDTO commentDTO = new CommentGetResponseDTO();
			commentDTO.setCommentId(c.getCommentId());
			commentDTO.setUserNickname(c.getUserNickname());
			commentDTO.setCommentDate(timeService.parseLocalDateTimeForBoardDetail(c.getRegistDate()));
			commentDTO.setCommentContent(c.getCommentContent());
			commentDTO.setUseYn(c.getUseYn());
			commentDTO.setUserImage(c.getUserImage());
			commentDTO.setIsWriter(c.getUserNickname().equals(ownerNick) ? "Y" : "N");
			commentList.add(commentDTO);
		}

		for (AttachVO a : attachVoList) {
			attachList.add(a.getFilePath());
		}

		responseDTO.setBoardTitle(boardVo.getBoardTitle());
		responseDTO.setBoardContent(boardVo.getBoardContent());
		responseDTO.setBoardDate(timeService.parseLocalDateTimeForBoardDetail(boardVo.getRegistDate()));
		responseDTO.setBoardLike(boardVo.getLikeCount());
		responseDTO.setUserNickname(boardVo.getUserNickname());
		responseDTO.setUserImage(boardVo.getUserImage());
		responseDTO.setLikeYn(userLiked);
		responseDTO.setCommentList(commentList);
		responseDTO.setAttachList(attachList);

		ResponseSuccessDTO<BoardGetResponseDTO> res = responseUtil.successResponse(responseDTO, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardPostResponseDTO> addBoard(List<MultipartFile> multiPartFiles,
			BoardPostRequestDTO boardDTO, String userId) throws IOException {

		// Thumbnail은 처음 이미지 따서 board에 저장
		BoardVO boardVo = new BoardVO();
		boardVo.setBoardContent(boardDTO.getBoardContent());
		boardVo.setBoardTitle(boardDTO.getBoardTitle());
		boardVo.setUserId(userId);
		if (multiPartFiles.size() >= 1) {
			MultipartFile thumbnailFile = thumbnailService.makeThumbNail(multiPartFiles.get(0), 100, 100);
			List<MultipartFile> thumbFiles = new ArrayList<>();
			thumbFiles.add(thumbnailFile);
			List<String> thumbUrlList = s3Service.uploadFile("thumb", thumbFiles);
			boardVo.setBoardThumbNail(thumbUrlList.get(0));
		} else if (multiPartFiles.size() <= 0) {
			boardVo.setBoardThumbNail("https://letter4u-bucket.s3.ap-northeast-2.amazonaws.com/test/no_image.jpg");
		}

		boardMapper.addBoard(boardVo);

		Long returnBoardNo = boardMapper.getLatestBoardNo();
		
		// 첨부파일 추가
		if (multiPartFiles != null) {
			List<String> urlList = s3Service.uploadFile(returnBoardNo + "", multiPartFiles);
			for (int s = 0; s < urlList.size(); s++) {
				AttachVO attachVo = new AttachVO();
				attachVo.setBoardNo(returnBoardNo);
				attachVo.setFilePath(urlList.get(s));
				attachVo.setOriginalFileNm(multiPartFiles.get(s).getOriginalFilename());
				attachVo.setSaveFileNm(multiPartFiles.get(s).getOriginalFilename());

				boardMapper.addAttach(returnBoardNo, attachVo);
			}
		}

		ResponseSuccessDTO<BoardPostResponseDTO> res = responseUtil.successResponse(" board 가 잘 올라갔습니다.",
				HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardModifyResponseDTO> modifyBoard(Long boardNo, BoardModifyRequestDTO boardDTO,
			String userId) {
		BoardVO boardVo = new BoardVO();
		boardVo.setBoardTitle(boardDTO.getBoardTitle());
		boardVo.setBoardContent(boardDTO.getBoardContent());

		boardMapper.modifyBoard(boardNo, boardVo);

		ResponseSuccessDTO<BoardModifyResponseDTO> res = responseUtil
				.successResponse(boardVo.getBoardTitle() + "제목의 board 가 잘 수정되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardDeleteResponseDTO> deleteBoard(Long boardNo) {
		boardMapper.deleteBoard(boardNo);
		commentMapper.deleteCommentByBoardNo(boardNo);

		ResponseSuccessDTO<BoardDeleteResponseDTO> res = responseUtil.successResponse(boardNo + " 번 board 가 삭제되었습니다.",
				HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<CommentPostResponseDTO> postComment(CommentPostRequestDTO commentDTO, String userId) {

		CommentVO commentVo = new CommentVO();
		Long boardNo = commentDTO.getBoardNo();
		commentVo.setBoardNo(commentDTO.getBoardNo());
		commentVo.setCommentContent(commentDTO.getCommentContent());
		commentVo.setUserId(userId);
		log.info(commentVo.toString());
		commentMapper.postComment(commentVo);
		ResponseSuccessDTO<CommentPostResponseDTO> res = responseUtil.successResponse(" comment 가 등록되었습니다.",
				HttpStatus.OK);
		return res;
	}

	@Override

	public ResponseSuccessDTO<CommentModifyResponseDTO> modifyComment(Long commentId,
			CommentModifyRequestDTO commentDTO) {

		CommentVO commentVo = new CommentVO();
		commentVo.setCommentId(commentId);
		commentVo.setCommentContent(commentDTO.getCommentContent());
		commentMapper.modifyComment(commentVo);
		ResponseSuccessDTO<CommentModifyResponseDTO> res = responseUtil
				.successResponse(commentId + "번 comment 가 수정되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<CommentDeleteResponseDTO> deleteComment(Long commentNo) {
		Long commentId = commentNo;
		commentMapper.deleteCommentByCommentId(commentId);
		ResponseSuccessDTO<CommentDeleteResponseDTO> res = responseUtil
				.successResponse(commentId + "번 comment 가 삭제되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardLikeUpdateResponseDTO> updateBoardLike(Long boardNo, String userId) {

		if (boardMapper.getBoard(boardNo) == null) {
			throw new EntityNullException("게시물이 존재하지 않습니다.");
		}
		boardMapper.modifyBoardLike(boardNo, userId);

		BoardLikeUpdateResponseDTO result = new BoardLikeUpdateResponseDTO();
		BoardUpdateDTO boardDTO = new BoardUpdateDTO();
		BoardVO boardVo = boardMapper.getBoardLike(boardNo, userId);
		boardDTO.setBoardNo(boardVo.getBoardNo());
		boardDTO.setLikeYn(boardVo.getLikeYn());
		boardDTO.setMessage(boardNo + "번 게시물의 좋아요가 변경되었습니다.");
		result.setBoardDTO(boardDTO);
		ResponseSuccessDTO<BoardLikeUpdateResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);
		return res;
	}

}
