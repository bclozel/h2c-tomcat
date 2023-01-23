package h2c;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "H2cServlet", urlPatterns = {"/"})
public class H2cServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		byte[] bytes = req.getInputStream().readAllBytes();
		System.out.println(new String(bytes));
		resp.setStatus(204);
		ServletOutputStream out = resp.getOutputStream();
		out.flush();
		out.close();
	}

}
