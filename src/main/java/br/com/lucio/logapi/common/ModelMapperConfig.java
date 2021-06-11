package br.com.lucio.logapi.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	//ModelMapper mão é de nossa autoria, por isso devemos colocar o @Bean
	@Bean
	public ModelMapper modelaMapper() {
		return new ModelMapper();
	}
}
