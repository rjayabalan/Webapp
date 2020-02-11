import pojo.RegisterUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet
public class DemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        PostgresConn pgconn = new PostgresConn();

        out.println("<h2> Total number of users registered are : " + pgconn.getUserCount() + "</h2>");

        //loading userData from postgres
        ArrayList<RegisterUser> userArrayList = pgconn.getUsers();

        req.getSession().setAttribute("userArrayList",userArrayList);

        resp.sendRedirect( req.getContextPath() + "/home.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h2> Hello Word </h2>");

    }
}
