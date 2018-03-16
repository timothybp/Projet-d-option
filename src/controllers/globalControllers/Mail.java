package controllers.globalControllers;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;

public class Mail {

	public static void main(String[] args) throws GeneralSecurityException, UnsupportedEncodingException {
		// 收件人电子邮箱 
		  String to = "zengkai_575@163.com"; 
		  
		  // 发件人电子邮箱 
		  String from = "timothy93bp@163.com"; 
		  
		  // 指定发送邮件的主机为 smtp.qq.com 
		  String host = "smtp.163.com"; //QQ 邮件服务器 
		  
		  // 获取系统属性 
		  Properties properties = System.getProperties(); 
		  
		  // 设置邮件服务器 
		  properties.setProperty("mail.smtp.host", host); 
		  
		  properties.put("mail.smtp.auth", "true"); 
		  MailSSLSocketFactory sf = new MailSSLSocketFactory(); 
		  sf.setTrustAllHosts(true); 
		  properties.put("mail.smtp.ssl.enable", "true"); 
		  properties.put("mail.smtp.ssl.socketFactory", sf);
		  // 获取默认session对象 
		  Session session = Session.getDefaultInstance(properties,new Authenticator(){ 
		   public PasswordAuthentication getPasswordAuthentication() 
		   {  //qq邮箱服务器账户、第三方登录授权码 
		    return new PasswordAuthentication("timothy93bp@163.com", "*********"); //发件人邮件用户名、密码 
		   } 
		  }); 
		  
		  try{ 
		   // 创建默认的 MimeMessage 对象 
		   MimeMessage message = new MimeMessage(session); 
		  
		   // Set From: 头部头字段 
		   message.setFrom(new InternetAddress(from)); 
		  
		   // Set To: 头部头字段 
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
		  
		   // Set Subject: 主题文字 
		   message.setSubject("Projet Libre"); 
		  
		    // 创建消息部分 
		    BodyPart messageBodyPart = new MimeBodyPart(); 
		   
		    // 消息 
		    messageBodyPart.setText("Test e-mail"); 
		  
		    // 创建多重消息 
		    Multipart multipart = new MimeMultipart(); 
		   
		    // 设置文本消息部分 
		    multipart.addBodyPart(messageBodyPart); 
		   
		    // 附件部分 
		    messageBodyPart = new MimeBodyPart(); 
		    //设置要发送附件的文件路径 
		    String filename = "C:/Users/Clotaire/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/DistributionDeProjets/WEB-INF/classes/temp/solutions/Projet d'option[2017-2018]/solution_1.txt"; 
		    DataSource source = new FileDataSource(filename); 
		    messageBodyPart.setDataHandler(new DataHandler(source)); 
		     
		    //messageBodyPart.setFileName(filename); 
		    //处理附件名称中文（附带文件路径）乱码问题 
		    messageBodyPart.setFileName(MimeUtility.encodeText(filename)); 
		    multipart.addBodyPart(messageBodyPart); 
		   
		    // 发送完整消息 
		    message.setContent(multipart ); 
		   
		    // 发送消息 
		    Transport.send(message); 
		    System.out.println("Sent message successfully...."); 
		   }catch (MessagingException mex) { 
		    mex.printStackTrace(); 
		   } 

	}

}
