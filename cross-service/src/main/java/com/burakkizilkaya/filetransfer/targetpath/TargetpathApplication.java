package com.burakkizilkaya.filetransfer.targetpath;

import com.burakkizilkaya.filetransfer.targetpath.service.FileDownloadService;
import com.burakkizilkaya.filetransfer.targetpath.service.FileUploadService;
import com.burakkizilkaya.filetransfer.targetpath.utilities.FileDownloadUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TargetpathApplication {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Bean
	public FileDownloadUtil fileDownloadUtil(){
		return new FileDownloadUtil();
	}
	@Bean
	public FileUploadService fileUploadService(){
		return new FileUploadService(new RestTemplate());
	}
	@Bean
	public FileDownloadService fileDownloadService(){
		return new FileDownloadService();
	}


	public static void main(String[] args) {
		SpringApplication.run(TargetpathApplication.class, args);
	}

}
