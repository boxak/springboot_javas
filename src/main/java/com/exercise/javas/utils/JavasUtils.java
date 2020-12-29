package com.exercise.javas.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@PropertySource("/properties/File_Path.properties")
public class JavasUtils {

    private JavasUtils() {
    }

    @Value("${photo.directory}")
    static String photoUploadDir;

    public static void uploadPhoto(String id, MultipartFile photo) {
        byte[] content = null;
        String path = photoUploadDir + id + ".png";
        log.info("photo path :::: {}",path);
        File f = new File(path);
        try(FileOutputStream fos = new FileOutputStream(f)) {
            if (! photo.isEmpty()) {
                content = photo.getBytes();
                fos.write(content);
            }
        } catch (IOException e) {
            log.info(messegingExLog(e.toString(), e.getMessage()));
        }
    }

    public static String messegingExLog(String exception, String msg) {
        StringFormattedMessage format = new StringFormattedMessage(" :::: {}! :::: {}"
                , exception, msg);
        return format.toString();
    }

    @Value("${PAGE_LIST_TXT_PATH}")
    static String pagelistPath;

    @Value("${PREVOIUS_BTN_PATH}")
    static String preBtnPath;

    @Value("${NEXT_BTN_PATH}")
    static String nextBtnPath;

    public static String getPageLinkList(String boardType, int curPage, String linkStr, long totalPostCnt) {
        PagingControl page = new PagingControl(curPage, totalPostCnt);
        StringBuilder builder = new StringBuilder();
        long startPage = page.getStartPage();
        long endPage = page.getEndPage();

        if (page.hasPreData()) {
            builder.append(readFile(preBtnPath, StandardCharsets.UTF_8));
        }

        builder.append(readFile(pagelistPath, StandardCharsets.UTF_8));

        if (page.hasNextData()) {
            builder.append(readFile(nextBtnPath, StandardCharsets.UTF_8));
        }

        String pageStr = builder.toString();

        pageStr = pageStr.replace("\\{boardType}", boardType);
        pageStr = pageStr.replace("\\{startPage}", startPage+"");
        pageStr = pageStr.replace("\\{endPage}", endPage+"");
        pageStr = pageStr.replace("\\{linkStr}", linkStr);

        return pageStr;
    }

    public static String readFile(String path, Charset charset) {
        String txt = "";
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
            txt = new String(encoded, charset);
        } catch (IOException e) {
            messegingExLog(e.toString(), e.getMessage());
        }
        return txt;
    }

    public static String getTodayString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }
}
