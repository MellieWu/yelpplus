package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MySQLDBConnection;
import db.MongoDBConnection;

/**
 * Servlet implementation class UserAuthentication
 */
@WebServlet("/login")
public class UserAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConnection connection = new MySQLDBConnection();
//	DBConnection connection = new MongoDBConnection();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAuthentication() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		try {
		    if (request.getParameterMap().containsKey("user_id")
					&& request.getParameterMap().containsKey("password")) {
				// term is null or empty by default
				String userId = request.getParameter("user_id");
				String password = request.getParameter("password");
				Boolean ifUserValid = connection.verifyLogin(userId, password);
				if (ifUserValid){
					String name = connection.getFirstLastName(userId);
					result.put("status", "OK");
					result.put("user_id", userId);
					result.put("name", name);
				} else {
					result.put("status", "Invalid");
				}
			}
	    } catch (JSONException e) {
			e.printStackTrace();
		}
		RpcParser.writeOutput(response, result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
