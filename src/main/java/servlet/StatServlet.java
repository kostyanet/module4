package servlet;

import entity.RaceStat;
import service.RaceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * /stats – отображает статистику о количестве проведенных забегов
 * и информации о месте лошади, на которую поставил пользователь.
 */
public class StatServlet extends HttpServlet {
    private final RaceService service = RaceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head><title>Stats Page</title></head>")
                .append("<body>")
                .append("<header><h1>Race statistics</h1></header>")
                .append(getPageContent())
                .append("</body>")
                .append("</html>");
    }

    private String getPageContent() {
        List<RaceStat> stats = service.getStats();
        if (stats.size() == 0) {
            return "<h2>No races yet.</h2>";
        }
        String results = stats.stream().map((RaceStat s) ->
                "<tr>" +
                        "<td>" + s.getId() + "</td>" +
                        "<td>" + s.getBetHorseResult() + "</td>" +
                        "<td>" + s.getHorsesTotal() + "</td>" +
                        "</tr>"
        ).reduce((s1, s2) -> s1 + s2).get();

        return "<table>" +
                "<tr><th>ID</th><th>Bet horse position</th><th>Horses total</th></tr>" +
                results +
                "</table>";
    }
}
