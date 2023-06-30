package com.vigimod.api;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vigimod.api.Report.ReportFileUtils;
import com.vigimod.api.entity.Ad;
import com.vigimod.api.entity.Report;
import com.vigimod.api.utils.ReportCategory;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);

	}

}
