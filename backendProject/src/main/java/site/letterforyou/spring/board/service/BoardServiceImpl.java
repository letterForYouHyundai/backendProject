package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.board.domain.AttachVO;
import site.letterforyou.spring.board.domain.BoardVO;
import site.letterforyou.spring.board.domain.CommentVO;
import site.letterforyou.spring.board.domain.PageVO;
import site.letterforyou.spring.board.domain.Pagination;
import site.letterforyou.spring.board.dto.BoardDTO;
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
import site.letterforyou.spring.board.dto.GetCommentResponseDTO;
import site.letterforyou.spring.board.mapper.BoardMapper;
import site.letterforyou.spring.comment.mapper.CommentMapper;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.member.domain.UserVO;
import site.letterforyou.spring.member.mapper.MemberMapper;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	private ResponseUtil responseUtil;
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private MemberMapper userMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private TimeService timeService;
	
	@Autowired
	private AmazonS3Service s3Service;
	@Autowired
	private ThumbnailService thumbnailService;
	
	public ResponseSuccessDTO<GetBoardListResponseDTO> getBoardList(String sortBy, int inOrder, Long page) {
		 
		PageVO pageVo = new PageVO(page);
		if(sortBy ==null) {
			inOrder =1;
			sortBy="DESC";
	    }
		pageVo.setSortBy(sortBy);
		
		if(inOrder==0)
			pageVo.setOrderBy("ASC");
		else if(inOrder==1)
			pageVo.setOrderBy("DESC");
		
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		log.info(pageVo.getSortBy()+" "+ pageVo.getSortBy()+" "+ pageVo.getOffset() +" "+pageVo.getRecordSize());
		List<BoardVO> boardVoList = boardMapper.getBoardList(pageVo.getSortBy(),pageVo.getOrderBy(),offset,size);

		GetBoardListResponseDTO result = new GetBoardListResponseDTO();
		List<BoardDTO> boardList= new ArrayList<>();
		for (BoardVO b : boardVoList) {
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setBoardNo(b.getBoardNo());
			boardDTO.setBoardTitle(b.getBoardTitle());
			boardDTO.setBoardView(b.getBoardView());
			boardDTO.setCommentCount(b.getCommentCount());
			boardDTO.setImage(b.getBoardThumbNail());
			boardDTO.setUserNickname(userMapper.getUserByUserId(b.getUserId()).getUserNickname());
			boardDTO.setRegistDate(timeService.parseLocalDateTime(b.getRegistDate()));
			boardDTO.setLikeCount(b.getLikeCount());

			boardList.add(boardDTO);
		}
		result.setBoardList(boardList);
		int count = boardMapper.getTotalCountBoard();
		log.info(" "+count);
		Pagination pagination = new Pagination(count, pageVo);
		result.setPagination(pagination);
		ResponseSuccessDTO<GetBoardListResponseDTO> res =  responseUtil.successResponse(result, HttpStatus.OK);
		
		return res;
	}
	
	@Override
	public ResponseSuccessDTO<GetBoardResponseDTO> getBoard(Long boardNo) {
		// 조회수도 검증
		GetBoardResponseDTO responseDTO = new GetBoardResponseDTO();
		
		BoardVO boardVo = boardMapper.getBoard(boardNo);
		
		List<CommentVO> commentVoList = commentMapper.getCommentList(boardNo);
		UserVO user = userMapper.getUserByUserId(boardVo.getUserId());
		List<AttachVO> attachVoList = boardMapper.getAttachByBoardNo(boardNo);
		
		List<GetCommentResponseDTO> commentList = new ArrayList<>();
		List<String> attachList = new ArrayList<>();
		
		for(CommentVO c: commentVoList) {
			GetCommentResponseDTO commentDTO = new GetCommentResponseDTO();
			commentDTO.setCommentId(c.getCommentId());
			commentDTO.setUserNickname(c.getUserId());
			commentDTO.setCommentDate(timeService.parseTime(c.getRegistDate()));
			commentDTO.setCommentContent(c.getCommentContent());
			commentDTO.setUserImage(userMapper.getUserByUserId(c.getUserId()).getUserImage());
			commentList.add(commentDTO);
		}
		
		for(AttachVO a : attachVoList) {
			attachList.add(a.getFilePath());
		}
		
		responseDTO.setBoardTitle(boardVo.getBoardTitle());
		responseDTO.setBoardContent(boardVo.getBoardContent());
		responseDTO.setBoardDate(timeService.parseTime(boardVo.getRegistDate()));
		responseDTO.setBoardLike(boardMapper.getBoardLikeCountByBoardNo(boardNo));
		responseDTO.setUserNickname(user.getUserNickname());
		responseDTO.setUserImage(user.getUserImage());
		responseDTO.setCommentList(commentList);
		responseDTO.setAttachList(attachList);
		
		
		ResponseSuccessDTO<GetBoardResponseDTO> res =  responseUtil.successResponse(responseDTO, HttpStatus.OK);
		
		return res;
	}

	
	
	@Override
	public ResponseSuccessDTO<BoardPostResponseDTO> addBoard(List<MultipartFile> multiPartFiles, BoardPostRequestDTO boardDTO) throws IOException {
		// Thumbnail은 처음 이미지 따서 board에 저장
//		
		BoardVO boardVo = new BoardVO();
		boardVo.setBoardContent(boardDTO.getBoardContent());
		boardVo.setBoardTitle(boardDTO.getBoardTitle());
		boardVo.setUserId(boardDTO.getUserId());
		if(multiPartFiles.size()>=1) {
			MultipartFile thumbnailFile = thumbnailService.makeThumbNail(multiPartFiles.get(0), 100, 100);
			List<MultipartFile> thumbFiles = new ArrayList<>();
			thumbFiles.add(thumbnailFile);
			List<String> thumbUrlList = s3Service.uploadFile("thumb", thumbFiles);
			boardVo.setBoardThumbNail(thumbUrlList.get(0));
		}
		else if(multiPartFiles== null  || multiPartFiles.size()<=0) {
			boardVo.setBoardThumbNail("https://letter4u-bucket.s3.ap-northeast-2.amazonaws.com/thumb/thumb_default.png");
		}
		Long returnBoardNo = boardMapper.addBoard(boardVo);
		
		if(multiPartFiles != null) {
			List<String> urlList = s3Service.uploadFile(returnBoardNo+"", multiPartFiles);
			for(int s =0; s<urlList.size();s++) {
				AttachVO attachVo = new AttachVO();
				attachVo.setBoardNo(returnBoardNo);
				attachVo.setFilePath(urlList.get(s));
				attachVo.setOriginalFileNm(multiPartFiles.get(s).getOriginalFilename());
				attachVo.setSaveFileNm(multiPartFiles.get(s).getOriginalFilename());
				
				boardMapper.addAttach(returnBoardNo, attachVo);
			}
		}
		
		
		
		ResponseSuccessDTO<BoardPostResponseDTO> res =  responseUtil.successResponse(" board 가 잘 올라갔습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardModifyResponseDTO> modifyBoard(Long boardNo, BoardModifyRequestDTO boardDTO) {
		BoardVO boardVo = new BoardVO();
		boardVo.setBoardTitle(boardDTO.getBoardTitle());
		boardVo.setBoardContent(boardDTO.getBoardContent());
		
		boardMapper.modifyBoard(boardNo, boardVo);
		
		ResponseSuccessDTO<BoardModifyResponseDTO> res =  responseUtil.successResponse(boardVo.getBoardTitle()+ "제목의 board 가 잘 수정되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardDeleteResponseDTO> deleteBoard(Long boardNo) {
		boardMapper.deleteBoard(boardNo);
		commentMapper.deleteCommentByBoardNo(boardNo);
		
		ResponseSuccessDTO<BoardDeleteResponseDTO> res =  responseUtil.successResponse( boardNo+ " 번 board 가 삭제되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<CommentPostResponseDTO> postComment(CommentPostRequestDTO commentDTO) {
		
	CommentVO commentVo = new CommentVO();
	Long boardNo = commentDTO.getBoardNo();
	commentVo.setBoardNo(commentDTO.getBoardNo());
	commentVo.setCommentContent(commentDTO.getCommentContent());
	commentVo.setUserId(commentDTO.getUserId());
	log.info(commentVo.toString());
		commentMapper.postComment(commentVo);
		ResponseSuccessDTO<CommentPostResponseDTO> res =  responseUtil.successResponse( " comment 가 등록되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<CommentModifyResponseDTO> modifyComment(Long commentId,
			CommentModifyRequestDTO commentDTO) {
		
		CommentVO commentVo = new CommentVO();
		commentVo.setCommentId(commentId);
		commentVo.setCommentContent(commentDTO.getCommentContent());
		commentMapper.modifyComment(commentVo);
		ResponseSuccessDTO<CommentModifyResponseDTO> res =  responseUtil.successResponse( commentId+ "번 comment 가 수정되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<CommentDeleteResponseDTO> deleteComment(Long commentNo) {
		Long commentId = commentNo;
		commentMapper.deleteCommentByCommentId(commentId);
		ResponseSuccessDTO<CommentDeleteResponseDTO> res =  responseUtil.successResponse( commentId+ "번 comment 가 삭제되었습니다.", HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<BoardLikeUpdateResponseDTO> updateBoardLike(Long boardNo) {
		
		ResponseSuccessDTO<BoardLikeUpdateResponseDTO> res =  responseUtil.successResponse( boardNo+ "번 게시물의 좋아요가 변경되었습니다.", HttpStatus.OK);
		return res;
	}

	
	

	

	

}
