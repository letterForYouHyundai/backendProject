package site.letterforyou.spring.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 스프링 실행시 설정파일
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

//	 @Bean
//	    public Docket api(){
//	        return new Docket(DocumentationType.SWAGGER_2)
//	                .select()
//					// 자기 프로젝트에 맞게 설정!
//	                .apis(RequestHandlerSelectors.basePackage("site.letterforyou.spring"))
//	                .paths(PathSelectors.any())
//	                .build()
//	                .pathMapping("/")
//	                .useDefaultResponseMessages(false)
//	                .apiInfo(apiInfo());
//	    }
	
	  public Docket getDocket(String groupName, Predicate<String> predicate) {
			return new Docket(DocumentationType.SWAGGER_2)
					.groupName(groupName)
					.apiInfo(apiInfo()).select()
					.apis(RequestHandlerSelectors.basePackage("site.letterforyou.spring"))
					.paths(predicate)
					.apis(RequestHandlerSelectors.any())
					.build();
		}

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("너를담은 글, Letter4U")
	                .description("Letter4U Swagger API")
	                .version("1.0.0")
	                .build();
	    }

	    @Bean
	    public UiConfiguration uiconfig() {
	        return UiConfigurationBuilder
	                .builder().operationsSorter(OperationsSorter.ALPHA)
	                .build();
	    }
	    
	    @Bean
		public Docket userApi() {
			return getDocket("member", Predicates.or(PathSelectors.regex("/member.*")));
		}
	    
	    @Bean
		public Docket letterApi() {
			return getDocket("letter", Predicates.or(PathSelectors.regex("/letter.*")));
		}
	    @Bean
		public Docket boardApi() {
			return getDocket("board", Predicates.or(PathSelectors.regex("/board.*")));
		}
	    @Bean
		public Docket templateApi() {
			return getDocket("template", Predicates.or(PathSelectors.regex("/template.*")));
		}

}
