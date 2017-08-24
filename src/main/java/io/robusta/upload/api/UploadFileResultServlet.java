package io.robusta.upload.api;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/uploadfileresults")
public class UploadFileResultServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("message", "Successfull upload");
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/uploadfile.jsp");
		dispatcher.forward(request, response);
	}
}
