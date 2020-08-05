package com.jiangjiang.readimgs.controller;

import com.jiangjiang.readimgs.tools.BaiduOrcParse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {

    @PostMapping("imageupload")
    public String imageupload(@RequestParam(value = "file") MultipartFile[] file) {
        for (MultipartFile multipartFile : file) {
            System.out.println("haha ");
//            try {
//                BaiduOrcParse parse = new BaiduOrcParse();
//                parse.parse(multipartFile.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return "ok";
    }

    @GetMapping("/index")
    public String gotoIndex() {
        return "hello";
    }
}
