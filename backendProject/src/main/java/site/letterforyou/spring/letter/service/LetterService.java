package site.letterforyou.spring.letter.service;

import site.letterforyou.spring.letter.domain.LetterDTO;

public interface LetterService {

	String insertLetter(LetterDTO ldto);
	
	String sendKaoKaoMessage(LetterDTO ldto);
	
	String makeURL(LetterDTO ldto);
}
