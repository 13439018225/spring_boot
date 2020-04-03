package com.huaxin.sboot.util;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceUtil {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Value("${mail.fromMail.addr}")
    private String from;

    //发送普通邮件
    public  void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }
    //发送html格式邮件
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (Exception e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }
    //发送带附件的邮件 
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            //添加多个附件可以使用多条 helper.addAttachment(fileName, file)
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }
    //发送带背景的邮件
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            //添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
    @Test
    public void testSimpleMail() throws Exception {
        sendSimpleMail("285800302@qq.com","开会通知"," 3月20日下午2点整某单位在会议室召开全体职工大会,"
        		+ "讨论单位年终工作事项，全体人员务必准时参加，"
        		+ "不可迟到，自带笔纸记录，收到请回复，大家互相转告");
    }
    @Test
    public void testHtmlMail() throws Exception {
    	String content="<html>\n" +
                "<body>\n" +
                "    <h3>3月20日下午2点整某单位在会议室召开全体职工大会,"
		        		 + "讨论单位年终工作事项，全体人员务必准时参加，"
		        		 + "不可迟到，自带笔纸记录，收到请回复，大家互相转告!</h3>\n" +
                "</body>\n" +
                "</html>";
        sendSimpleMail("285800302@qq.com","开会通知",content);
    }
    @Test
    public void sendAttachmentsMail() {
        String filePath="C:\\Users\\fdz\\Pictures\\Images\\haarcascade_frontalface_alt.xml";
        sendAttachmentsMail("285800302@qq.com","开会通知", "有附件，请查收！", filePath);
    }
    @Test
    public void sendInlineResourceMail() {
        String rscId = "0001";
        String imgPath = "C:\\Users\\fdz\\Pictures\\Images\\lena.png";
        String content="<html><body>有邮件，请查收：<img src=\'cid:" + rscId + "\' ></body></html>";
        sendInlineResourceMail("285800302@qq.com","开会通知", content, imgPath, rscId);
    }
}
