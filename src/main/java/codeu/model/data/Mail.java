package codeu.model.data;



import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private Mail() {

    }


    public static void sendEmail(String recipient) {


        Properties prop = new Properties();
        Session session = Session.getDefaultInstance(prop,null);
        try{
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress("lgaselle@codeustudents.com","Team9ChatApp"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject("Password Reset");
            msg.setText("https://t9-laaph.appspot.com/newPassword");

            Transport.send(msg);
            //System.out.println("Successfull Delivery.");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }





    }
}
