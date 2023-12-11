package site.letterforyou.spring.letter.controller;





@RestController
@RequestMapping("/letter")
@Slf4j
public class LetterController {
	
	@Autowired
	private LetterService LetterService;
	
	@PostMapping("/insertLetter")
	public ResponseEntity<Map<String, Object>> insertLetter(LetterDTO ldto, HttpSession session){
		
		MemberDTO user = (MemberDTO) session.getAttribute("userInfo");
		
		Map <String, Object> map = new HashMap<String, Object>();
		//ldto.setLetterSendId(user.getUserId());
		LetterService.insertLetter(ldto);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/testKaKaoMesage")
	public ResponseEntity<Map<String, Object>> testKaKaoMesage(LetterDTO ldto){
		Map <String, Object> map = new HashMap<String, Object>();

		LetterService.sendKaoKaoMessage(ldto);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	
	}
@GetMapping("/receive/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetListResponseDTO>> getReceivedLetters(
			@RequestParam(value = "pageNo", required = false) Long page) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		log.info(": /letter/receive/list"+p);
		// user 검증 추가
		return ResponseEntity.ok(letterService.getLetterReceiveList(p, "user1"));

	}
	
	@GetMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getReceivedLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.getReceivedLetter(letterNo));
	}
	
	@PutMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteReceivedLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.deleteReceivedLetter(letterNo));
	}
	
	@GetMapping("/send/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetListResponseDTO>> getSendLetters(
			@RequestParam(value = "pageNo", required = false) Long page) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		log.info(": /letter/send/list"+p);
		// user 검증 추가
		return ResponseEntity.ok(letterService.getLetterSendList(p, "user2"));

	}
	
	@GetMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getSendLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.getSendLetter(letterNo));
	}
	
	@PutMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteSendLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.deleteSendLetter(letterNo));
	}
}


