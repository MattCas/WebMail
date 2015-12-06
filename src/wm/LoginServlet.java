package wm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.imap.IMAPFolder;

/**
 * Servlet implementation class LoginClass
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String getname=request.getParameter("username");  
	    String getpassword=request.getParameter("password");
	    
	    Properties props = new Properties();
		Store store = null;	

		//Set mail properties to Properties object
		props.setProperty("mail.store.protocol", "imaps");
		//Set the acquired username and password as properties 
		props.setProperty("mail.user", getname);
		props.setProperty("mail.password", getpassword);
		//Establish mail session
		Session ssn = Session.getInstance(props);
		try {
			//Get store object from Session
			store = ssn.getStore("imaps");
			//Store new connects to the imap server (Gmail in this case) 
			store.connect("imap.googlemail.com", getname, getpassword);
			//Store a folder in the variable (Hardcoded Inbox in this case)
			IMAPFolder inbox = (IMAPFolder) store.getFolder("Inbox");
			IMAPFolder spam = (IMAPFolder) store.getFolder("[Gmail]/Spam");
		}catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	          
	    if(store.isConnected()){  
	        RequestDispatcher rd=request.getRequestDispatcher("FolderServlet");  
	        rd.forward(request, response);  
	    }  
	    else{   
	    	out.println("Connection failed! Please try again!");   
	        RequestDispatcher rd=request.getRequestDispatcher("/index.html");
	        // Why we use include??
	        rd.include(request, response);  
	                      
	        }  			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
