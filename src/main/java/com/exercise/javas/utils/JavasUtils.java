package com.exercise.javas.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class JavasUtils {

    @Autowired
    private static ResourceLoader resourceLoader;

    private JavasUtils() {
    }

    public static void uploadPhoto(String id, MultipartFile photo,
                                   HttpServletRequest request) {
        byte[] content = null;
        try {
            if (!photo.isEmpty()) {
                content = photo.getBytes();
                String path = request.getSession().getServletContext().getRealPath("/")
                        + "resources/images2/" + id + ".png";
                log.info("photo path :::: {}", path);
                File f = new File(path);
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content);
                fos.close();
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

    public static String getPageLinkList(String boardType, int curPage, String linkStr, long totalPostCnt) {
        curPage = curPage + 1;
        PagingControl page = new PagingControl(curPage, totalPostCnt);
        StringBuilder builder = new StringBuilder();
        long startPage = page.getStartPage();
        long endPage = page.getEndPage();

        String preBtnHtml = JavasConstants.PRE_BTN_HTML;
        String pageListHtml = JavasConstants.PAGE_LIST_HTML;
        String pageListHtmlI = JavasConstants.PAGE_LIST_HTML_I;
        String nextBtnHtml = JavasConstants.NEXT_BTN_HTML;

        if (page.hasPreData()) {
            builder.append(preBtnHtml);
        }

        for (int i = (int)page.getStartPage();i<=page.getEndPage();i++) {
            if (i == curPage) {
                builder.append(JavasConstants.PAGE_LIST_HTML_I.replace("{i}",i+""));
            } else {
                builder.append(JavasConstants.PAGE_LIST_HTML.replace("{i}",i+""));
            }
        }

        if (page.hasNextData()) {
            builder.append(nextBtnHtml);
        }

        String pageStr = builder.toString();

        pageStr = pageStr.replace("{boardType}", boardType);
        pageStr = pageStr.replace("{startPage}", startPage + "");
        pageStr = pageStr.replace("{endPage}", endPage + "");
        pageStr = pageStr.replace("{linkStr}", linkStr);

        return pageStr;
    }

//    public static String readFile(String path, Charset charset) {
//        StringBuilder builder = new StringBuilder();
//        Path filePath = Paths.get(path);
//        try (BufferedReader br = Files.newBufferedReader(filePath)) {
//            String line;
//            while((line = br.readLine()) != null) {
//                builder.append(line);
//            }
//        } catch (IOException e) {
//            messegingExLog(e.toString(), e.getMessage());
//        }
//        return builder.toString();
//    }

    public static String getTodayString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static void sendEmail(String email, String subject, String body) throws MessagingException {
        String host = "smtp.naver.com";
        final String username = "boxak";
        final String pw = "Second142857!";
        int port = 465;

        String recipient = email;

        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.enable", "true");
            properties.put("mail.smtp.ssl.trust", host);

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        String un = username;
                        String userpw = pw;

                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new javax.mail.PasswordAuthentication(un, userpw);
                        }
                    });
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username+"@naver.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject,"UTF-8");
            message.setText(body, "UTF-8");
            message.setHeader("content-Type", "text/html");
            Transport.send(message);
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
    }
}
