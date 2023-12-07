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
import site.letterforyou.spring.board.dto.BoardModifyRequestDTO;
import site.letterforyou.spring.board.dto.BoardModifyResponseDTO;
import site.letterforyou.spring.board.dto.BoardPostRequestDTO;
import site.letterforyou.spring.board.dto.BoardPostResponseDTO;
import site.letterforyou.spring.board.dto.CommentDTO;
import site.letterforyou.spring.board.dto.GetBoardListResponseDTO;
import site.letterforyou.spring.board.mapper.BoardMapper;
import site.letterforyou.spring.comment.mapper.CommentMapper;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.user.mapper.UserMapper;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	private ResponseUtil responseUtil;
	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private TimeService timeService;
	
	@Autowired
	private AmazonS3Service s3Service;
	@Autowired
	private ThumbnailService thumbnailService;
	
	public ResponseSuccessDTO<List<GetBoardListResponseDTO>> getBoardList() {
		List<BoardVO> boardVoList = boardMapper.getBoardList();
		List<GetBoardListResponseDTO> returnList = new ArrayList<>();
		for (BoardVO b : boardVoList) {
			GetBoardListResponseDTO boardDTO = new GetBoardListResponseDTO();
			boardDTO.builder().boardNo(b.getBoardNo()).boardTitle(b.getBoardTitle()).boardView(b.getBoardView())
					.userNickname(userMapper.getUserNickName(b.getUserId()))
					.commentCount(commentMapper.getCommentCount(b.getBoardNo()))
					.image(b.getBoardThumbNail())
					.likeCount(boardMapper.getBoardLikeCountByBoardNo(b.getBoardNo()))
					.registDate(timeService.parseLocalDateTime(b.getRegistDate())).build();
			
			returnList.add(boardDTO);
		}
//		GetBoardListResponseDTO bdto = new GetBoardListResponseDTO();
//		returnList.add(bdto.builder()
//				.boardNo(1L)
//				.boardTitle("Hello")
//				.boardView(10L)
//				.userNickname("sponge")
//				.commentCount(13L)
//				.image("image")
//				.likeCount(14L)
//				.registDate(LocalDateTime.now().toString())
//				.build());
		ResponseSuccessDTO<List<GetBoardListResponseDTO>> res =  responseUtil.successResponse(returnList, HttpStatus.OK);
		
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
	public GetBoardListResponseDTO deleteBoard(Long boardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetBoardListResponseDTO modifyBoardLike(Long boardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDTO postComment(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteComment(Long commentNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long modifyComment(Long commentNo) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
