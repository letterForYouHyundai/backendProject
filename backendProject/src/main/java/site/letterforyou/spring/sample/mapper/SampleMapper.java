package site.letterforyou.spring.sample.mapper;
import java.util.List;

import org.springframework.stereotype.Repository;

import site.letterforyou.spring.sample.domain.SampleVO;

@Repository
public interface SampleMapper {

	/**
	 조건에 맞는 회원 정보를 조회한다.
	 */
	public List<SampleVO> getSampleMemberList(SampleVO svo);
}
