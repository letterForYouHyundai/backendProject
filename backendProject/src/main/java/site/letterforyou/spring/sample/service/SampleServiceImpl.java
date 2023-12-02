package site.letterforyou.spring.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.letterforyou.spring.sample.domain.SampleVO;
import site.letterforyou.spring.sample.mapper.SampleMapper;

@Service
public class SampleServiceImpl implements SampleService{

	@Autowired
	private SampleMapper sampleMapper;
	
	@Override
	public List<SampleVO> getSampleMemberList(SampleVO svo) {
	
		/*유저 아이디가 특정 값(xci)인 list를 찾아 리턴. 
		 해당 요건이 필요 없는 경우 svo에 별다른 설정 X.
		 */
		svo.setUserId("xci");
		List<SampleVO> memberList = sampleMapper.getSampleMemberList(svo);
		
		return memberList;
	}

}
