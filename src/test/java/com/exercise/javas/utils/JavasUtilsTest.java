package com.exercise.javas.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class JavasUtilsTest {
    static MultipartFile photo;

    @Test
    void T1() throws IOException {
        String photo_path = "C:\\Users\\SEC\\Documents\\예지.png";
        File f = new File(photo_path);
        DiskFileItem fileItem = new DiskFileItem("file","image/png"
        ,false,f.getName(),(int)f.length()
        ,f.getParentFile());
        fileItem.getOutputStream();
        photo = new CommonsMultipartFile(fileItem);
        JavasUtils.uploadPhoto("예지",photo);
    }


}
