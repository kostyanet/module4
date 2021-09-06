package servlet;

import com.google.gson.Gson;
import service.RaceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * /stats – отображает статистику о количестве проведенных забегов
 * и информации о месте лошади, на которую поставил пользователь.
 */
public class StatServlet extends HttpServlet {
    private final RaceService service = new RaceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        resp.setContentType("application/json");
        resp.setStatus(200);

        var body = new Gson().toJson(service.getStats());
        responseBody.println(body);
    }
}
