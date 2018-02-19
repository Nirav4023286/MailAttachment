package l;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mailattach")
public class Mailattachment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE="text/html";
    public Mailattachment() {
        super();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Attach(request,response);	
	}
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Attach(request,response);
	}
	
	protected void Attach(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out=response.getWriter();
    response.setContentType(CONTENT_TYPE);
    String to=request.getParameter("to");
    String subject=request.getParameter("subject");
    String messag=request.getParameter("messag");
    String attachment=request.getParameter("attachment");
    
    try
    {
    	Properties props=System.getProperties();
    	props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable","true");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nirajm2025@gmail.com","saibabaniru");
			}
		});
		
		
		MimeMessage message =new MimeMessage(session);
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		message.setSubject(subject);
		MimeBodyPart messageBodyPart=new MimeBodyPart();
		messageBodyPart.setText(messag);
		Multipart multipart=new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart=new MimeBodyPart();
		DataSource source = new FileDataSource(attachment);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(attachment);
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		Transport.send(message);
		out.println("<h1>MAIL SUCCESSFULLY SENT</h1>");
		
    }
    catch(Exception e)
    {
    	out.println("Error: "+e.getMessage());
    	e.printStackTrace();
    }
    out.close();
	}

}
