package site.letterforyou.spring.board.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service{

	@Value("${cloud.aws.s3.bucket}")
	private String S3Bucket;
	
	@Autowired
	private final AmazonS3 amazonS3Client;
	
	public List<String> uploadFile(String boardNo, List<MultipartFile> multipartFiles) throws IOException{
		
		 String filePath = boardNo+"/";
		 List<String> returnURL = new ArrayList<>();
		 for(MultipartFile multipartFile : multipartFiles) {
		 String fileName = multipartFile.getOriginalFilename();
		 String originalName = filePath+URLEncoder.encode(fileName,"UTF-8");
		 
		 ObjectMetadata objectMetaData = new ObjectMetadata();
		 
		 objectMetaData.setContentType(multipartFile.getContentType());
		 objectMetaData.setContentLength(multipartFile.getSize());
		 
		 amazonS3Client.putObject(
				 new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(),objectMetaData)
				 .withCannedAcl(CannedAccessControlList.PublicRead)
				 );
		 
		 returnURL.add(amazonS3Client.getUrl(S3Bucket, originalName).toString());
		 }
		 
		return returnURL;
	}
	 
	 
}
