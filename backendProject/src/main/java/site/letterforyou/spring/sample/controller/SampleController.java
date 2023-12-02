package site.letterforyou.spring.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import site.letterforyou.spring.sample.domain.SampleVO;
import site.letterforyou.spring.sample.service.SampleService;
@RestController
@RequestMapping("/sample")
@Log
public class SampleController {

	@Autowired
	private SampleService sampleService;
	
	@RequestMapping("/test")
	public void sampleTest(SampleVO svo) {
	
		List<SampleVO> list = sampleService.getSampleMemberList(svo);
		log.info("샘플테스트");
		log.info(list.toString());
	}
}
