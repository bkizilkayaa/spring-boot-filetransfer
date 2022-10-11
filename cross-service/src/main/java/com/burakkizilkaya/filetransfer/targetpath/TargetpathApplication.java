package com.burakkizilkaya.filetransfer.targetpath;

<<<<<<< HEAD
import com.burakkizilkaya.filetransfer.targetpath.service.FileDownloadService;
import com.burakkizilkaya.filetransfer.targetpath.service.FileUploadService;
=======
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a
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
<<<<<<< HEAD

	@Bean
	public FileUploadService fileUploadService(){
		return new FileUploadService();
	}
	@Bean
	public FileDownloadService fileDownloadService(){
		return new FileDownloadService();
	}


=======
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a
	public static void main(String[] args) {
		SpringApplication.run(TargetpathApplication.class, args);
	}

}
