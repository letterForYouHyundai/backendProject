package site.letterforyou.spring.letter.service;

import site.letterforyou.spring.letter.domain.LetterDTO;

public interface LetterService {

	void insertLetter(LetterDTO ldto);
	
	String sendKaoKaoMessage(LetterDTO ldto);
	

}
