package com.ll.exam.app10.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @RequestMapping("")
    @ResponseBody
    public String upload(@RequestParam("img1") MultipartFile img1) {
        File file = new File("/Users/eunjung/Documents/temp/app10");

        try {
            img1.transferTo(new File("/Users/eunjung/Documents/temp/app10/1png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "업로드 완료";
    }
}
