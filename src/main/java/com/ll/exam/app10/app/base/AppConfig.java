package com.ll.exam.app10.app.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static String GET_FILE_DIR_PATH;     // static으로 선언

    @Value("${custom.genFileDirPath}")  // 경로
    public void setFileDirPath(String genFileDirPath) {     // Setter 역할
        GET_FILE_DIR_PATH = genFileDirPath;
    };
}