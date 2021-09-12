package servlet;

import pojo.Bet;
import service.RaceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * /race/start – страница на которой пользователь может запустить новый забег,
 * указав количество лошадей X и номер лошади на которую ставит Y
 */
public class RaceStartServlet extends HttpServlet {
    private final RaceService service = RaceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head><title>Form input</title></head>")
                .append("<body>")
                .append("<form action=\"/race/start\" method=\"POST\">")
                .append("Enter horses total: ")
                .append("<input type=\"text\" name=\"horseCount\" /></br>")
                .append("Enter bet horse place: ")
                .append("<input type=\"text\" name=\"betPosition\" /></br>")
                .append("<input type=\"submit\" value=\"Submit\" />")
                .append("</form>")
                .append("</body>")
                .append("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        int total = Integer.parseInt(req.getParameter("horseCount"));
        int position = Integer.parseInt(req.getParameter("betPosition"));
        String message;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        if (position < 1 || position > total) {
            message = "Wrong parameters combination";
        } else {
            service.run(new Bet(total, position));
            message = "Request has been successfully processed!";
        }

        writer.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<body>")
                .append("<h3>" + message + "</h3>")
                .append("</body></html>");
    }
}
