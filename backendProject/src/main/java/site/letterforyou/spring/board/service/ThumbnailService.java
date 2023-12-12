package site.letterforyou.spring.board.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ThumbnailService {
	public MultipartFile makeThumbNail (MultipartFile file, int width, int height) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String fileName = "thumb_"+file.getOriginalFilename();
        String contentType ="image/jpeg";
        Thumbnails.of(file.getInputStream())
                .size(width, height)
                .outputFormat("jpg")
                .toOutputStream(outputStream);

        return new MockMultipartFile(fileName, fileName, contentType, new ByteArrayInputStream(outputStream.toByteArray()));
    }
	
	 
}
