package org.other;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class emailSender {

  private Properties props = new Properties();

  public emailSender() {
    props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
  }
  
  public void send(Address[] enviar_a, Address[] responder_a, String asunto, String contenido) throws Exception {
    try {

      Session mailSession = Session.getInstance(props);
      MimeMessage message = new MimeMessage(mailSession);
      
      message.addRecipients(Message.RecipientType.TO, enviar_a);
      message.setReplyTo(responder_a);
      message.setSubject(asunto);
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(contenido);
      Multipart multiparte = new MimeMultipart();
      multiparte.addBodyPart(messageBodyPart);
      //multiparte.addBodyPart(adjunto);
      message.setContent(multiparte);
      Transport transporte = mailSession.getTransport("smtp");

      transporte.connect("lafargenotificacion@gmail.com", "lafarge123");
      transporte.sendMessage(message, message.getAllRecipients());
      transporte.close();
    } catch (Exception ex) {

    }
  }
}
