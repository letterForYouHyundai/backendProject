package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
	public List<String> uploadFile(String boardNo, List<MultipartFile> multipartFile) throws IOException;
}
