package com.ll.exam.fileExam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FileExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileExamApplication.class, args);
	}

}
