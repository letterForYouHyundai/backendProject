package site.letterforyou.spring.sample.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
	public void uploadFile(MultipartFile multipartFile) throws IOException;
}
