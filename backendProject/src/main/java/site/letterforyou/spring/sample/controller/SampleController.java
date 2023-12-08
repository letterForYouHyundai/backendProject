package site.letterforyou.spring.sample.controller;

import java.io.IOException;
import java.util.ArrayList; // ArrayList import 추가
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;
import site.letterforyou.spring.sample.domain.SampleVO;
import site.letterforyou.spring.sample.service.AmazonS3Service;
import site.letterforyou.spring.sample.service.SampleService;

@RestController
@RequestMapping("/sample")
@Log
public class SampleController {

    @Autowired
    private SampleService sampleService;
    
    @Autowired
    private AmazonS3Service s3service;

    @RequestMapping("/test")
    public ResponseEntity<Map<String, Object>> sampleTest(SampleVO svo) {
    	
    	
    	Map <String, Object> map = new HashMap<String, Object>();
    	
        ArrayList<SampleVO> list = (ArrayList<SampleVO>) sampleService.getSampleMemberList(svo); //
        map.put("list", list);
        
        //log.info("샘플테스트");
    
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

    }
    
    @PostMapping("/postfile")
    public ResponseEntity<Map<String,Object>> s3Test(@RequestPart("multipartFile") MultipartFile multipartfile) throws IOException{
    	Map <String, Object> map = new HashMap<String, Object>();
    	
    	s3service.uploadFile(multipartfile);
    
    	return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }
    
    @GetMapping("/home")
    public ModelAndView testHome() {
    	ModelAndView mv = new ModelAndView();
    	
    	mv.setViewName("home");
    	
    	return mv;
    }
}
