package wm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NuclearLauchCodes
 */
@WebServlet("/SenderServlet")
public class SenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SenderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  

		String name = (String)session.getAttribute("un");  
		String password = (String)session.getAttribute("pwd");
		out.println("Welcome " + name + "!");	

		//Interrupt if session is inactive for over 5 mins (300000 ms)
		if (session.getLastAccessedTime() - session.getCreationTime() <= 300000){

			RequestDispatcher rd=request.getRequestDispatcher("/sender.jsp");
			// Why we use include??
			rd.include(request, response);  

			String smtphost = "smtp.gmail.com";

			// Step 1: Set all Properties
			// Get system properties
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.port", "587");


			// Set Property with username and password for authentication  
			props.setProperty("mail.user", name);
			props.setProperty("mail.password", password);

			//Step 2: Establish a mail session (java.mail.Session)
			Session ssn = Session.getDefaultInstance(props);
			try {

				// Step 3: Create a message
				MimeMessage message = new MimeMessage(ssn);
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(request.getParameter("recipient")));
				message.setSubject(request.getParameter("subject"));
				message.setText(request.getParameter("body"));
				message.saveChanges();

				// Step 4: Send the message by javax.mail.Transport .			
				Transport tr = ssn.getTransport("smtp");	// Get Transport object from session		
				tr.connect(smtphost, name, password); // We need to connect
				tr.sendMessage(message, message.getAllRecipients()); // Send message

				RequestDispatcher rd1=request.getRequestDispatcher("/sent.html");  
				rd1.forward(request, response); 

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		}
		else{
			RequestDispatcher rd2=request.getRequestDispatcher("LogoutServlet");  
			rd2.forward(request, response); 
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
