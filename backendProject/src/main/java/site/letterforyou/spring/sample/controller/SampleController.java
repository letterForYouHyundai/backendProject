package site.letterforyou.spring.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/sample")
@Log
public class SampleController {

	@RequestMapping("/test")
	public void sampleTest() {
		log.info("샘플테스트");
	}
}
