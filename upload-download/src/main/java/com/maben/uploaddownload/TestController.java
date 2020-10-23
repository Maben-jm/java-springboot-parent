package com.maben.uploaddownload;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
public class TestController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public void importZip(HttpServletRequest request, HttpServletResponse response) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");
            final String originalFilename = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            //首先生成临时zip文件
            final String path = "temp/";
            final File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            final String fileName = path + originalFilename;
            final File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (final FileOutputStream writer = new FileOutputStream(file, true)) {
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    writer.write(bytes, 0, len);
                }
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
