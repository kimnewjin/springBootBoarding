package boarding.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import boarding.interceptor.LoggerInterceptor;
@Configuration

public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
	
	@Bean 
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultiPartResolver = new CommonsMultipartResolver();	
		commonsMultiPartResolver.setDefaultEncoding("UTF-8"); // 파일의 인코딩을 UTF-8로 설정한다.	
		commonsMultiPartResolver.setMaxUploadSizePerFile(5*1024*1024); //업로드 되는 파일의 크기를 설정한다.	
		return commonsMultiPartResolver;
	}

}