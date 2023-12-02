package site.letterforyou.spring.sample.service;

import java.util.List;

import site.letterforyou.spring.sample.domain.SampleVO;

public interface SampleService {

	/**
	 조건에 맞는 회원 정보를 조회한다.
	 */
	public List<SampleVO> getSampleMemberList(SampleVO svo);
}
