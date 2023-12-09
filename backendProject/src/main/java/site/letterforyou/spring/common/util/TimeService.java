package site.letterforyou.spring.common.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

    public String parseTime(LocalDateTime localDateTime) {
    	
    	LocalDateTime now = LocalDateTime.now();
    	
    	if(localDateTime.toLocalDate().isEqual(now.toLocalDate())) {
    		Duration duration = Duration.between(localDateTime, now);
    		
    		long hours = duration.toHours();
    		long minutes = duration.minusHours(hours).toMinutes();
//    		return String.format("%02d:%02d", hours,minutes);
    		
    		if(hours>=1) {
    		return hours +"시간전";
    		}
    		else return minutes+"분전";
    	}
    	else {
    		long daysdiff = ChronoUnit.DAYS.between(localDateTime.toLocalDate(), LocalDate.now());
    		return  daysdiff + "일전";
    	}
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return localDateTime.format(formatter);
    }
    
    
    public String parseLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
    
    public String parseLocalDateTimeForLetter(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return localDateTime.format(formatter);
    }
    
}
