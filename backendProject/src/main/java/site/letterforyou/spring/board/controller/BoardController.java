package site.letterforyou.spring.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.board.dto.BoardModifyRequestDTO;
import site.letterforyou.spring.board.dto.BoardModifyResponseDTO;
import site.letterforyou.spring.board.dto.BoardPostRequestDTO;
import site.letterforyou.spring.board.dto.BoardPostResponseDTO;
import site.letterforyou.spring.board.dto.GetBoardListResponseDTO;
import site.letterforyou.spring.board.service.BoardService;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list")
	public ResponseEntity<ResponseSuccessDTO<List<GetBoardListResponseDTO>>> getBoardList() {
    	
    	
        log.info(": /board/list");
    
        return ResponseEntity.ok(boardService.getBoardList());

    }
	
	@PostMapping(value = "/register" , consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseSuccessDTO<BoardPostResponseDTO>> addBoard(@RequestPart(value= "multipartFiles", required= false) List<MultipartFile> multipartFiles, 
			@RequestPart BoardPostRequestDTO boardDTO) throws IOException{
		
		log.info(": /board/regist");
		
		
		return ResponseEntity.ok(boardService.addBoard(multipartFiles, boardDTO));
	}
	
	@PostMapping(value="/{boardNo}")
	public ResponseEntity<ResponseSuccessDTO<BoardModifyResponseDTO>> modifyBoard(@PathVariable("boardNo") Long boardNo,
			@RequestPart BoardModifyRequestDTO boardDTO){
		return ResponseEntity.ok(boardService.modifyBoard(boardNo, boardDTO));
	}
	

}
