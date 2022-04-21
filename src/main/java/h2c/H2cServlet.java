package h2c;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "H2cServlet", urlPatterns = {"/test"})
public class H2cServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String requestBody = copyToString(req.getInputStream());
		System.out.println(requestBody);
		try (ServletOutputStream out = resp.getOutputStream()) {
			out.write(requestBody.getBytes());
			out.flush();
		}
	}

	String copyToString(InputStream in) throws IOException {
		if (in == null) {
			return "";
		}
		StringBuilder out = new StringBuilder(4096);
		InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
		char[] buffer = new char[4096];
		int charsRead;
		while ((charsRead = reader.read(buffer)) != -1) {
			out.append(buffer, 0, charsRead);
		}
		return out.toString();
	}

}
