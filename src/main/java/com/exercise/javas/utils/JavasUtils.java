package com.exercise.javas.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import java.util.Properties;

@Slf4j
@PropertySource("/properties/File_Path.properties")
public class JavasUtils {

    private JavasUtils() {
    }

    @Value("${photo.directory}")
    static String photoUploadDir;

    public static void uploadPhoto(String id, MultipartFile photo,
                                   HttpServletRequest request) {
        byte[] content = null;
        try {
            if (!photo.isEmpty()) {
                content = photo.getBytes();
                String path = request.getSession().getServletContext().getRealPath("/")
                        + "resources/images2" + id + ".png";
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

        pageStr = pageStr.replace("{boardType}", boardType);
        pageStr = pageStr.replace("{startPage}", startPage + "");
        pageStr = pageStr.replace("{endPage}", endPage + "");
        pageStr = pageStr.replace("{linkStr}", linkStr);

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
