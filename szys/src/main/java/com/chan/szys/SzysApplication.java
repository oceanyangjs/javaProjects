package com.chan.szys;

import com.chan.szys.util.SensitiveWordFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chan.szys.mapper")
public class SzysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SzysApplication.class, args);
		SensitiveWordFilter filter = new SensitiveWordFilter();
	}

}
