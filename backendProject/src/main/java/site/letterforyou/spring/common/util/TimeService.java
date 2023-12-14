package site.letterforyou.spring.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

    public String parseLocalDateTimeForBoardDetail(LocalDateTime localDateTime) {
    	
    	LocalDateTime now = LocalDateTime.now();
    	
    	if(localDateTime.toLocalDate().isEqual(now.toLocalDate())) {
    		Duration duration = Duration.between(localDateTime, now);
    		
    		long hours = duration.toHours();
    		long minutes = duration.minusHours(hours).toMinutes();

    		
    		if(hours>=1) {
    		return hours +"시간전";
    		}
    		else return minutes+"분전";
    	}
    	else {

    		return parseLocalDateTime(localDateTime);
    	}

    }
    
    
    public String parseLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }
    public String parseLocalDateTimeForBoardList(LocalDateTime localDatetime) {
    	LocalDateTime now = LocalDateTime.now();
    	if(localDatetime.toLocalDate().isEqual(now.toLocalDate())) {
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    		return localDatetime.format(formatter);
    		
    		
    	}
    	return parseLocalDateTime(localDatetime); 
    }
    
    public String parseLocalDateTimeForLetter(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return localDateTime.format(formatter);
    }
    
}
