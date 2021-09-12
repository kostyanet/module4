package servlet;

import entity.RaceReport;
import service.RaceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * /race/{id} – отображает информацию о забеге: дата проведения, общее кол-во лошадей,
 * место каждой из них и номер лошади на которую ставил пользователь.
 */
public class RaceServlet extends HttpServlet {
    private final static String NUM_REG_EXP = "/\\d+";
    private final RaceService service = RaceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        System.out.println("doGet");

        if (path.matches(NUM_REG_EXP)) {
            var id = Integer.parseInt(path.substring(1));
            System.out.println("id = " + id);
            var report = service.getById(id);

            if (report == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setStatus(200);
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");

                PrintWriter writer = resp.getWriter();
                writer.append(getRaceStat(report));
            }

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String getRaceStat(RaceReport report) {
        return "<!DOCTYPE html>" + "" +
                "<html><head><title>Race Page</title></head><body>" +
                "<header><h1>Race statistic</h1></header>" +
                "<table>" +
                "<tr><th>ID</th><td>" + report.getId() + "</td></tr>" +
                "<tr><th>Created</th><td>" + report.getCreated() + "</td></tr>" +
                "<tr><th>Horses total</th><td>" + report.getHorsesTotal() + "</td></tr>" +
                "<tr><th>Horses order</th><td>" + report.getHorsesOrdered() + "</td></tr>" +
                "<tr><th>Bet horse result</th><td>" + report.getBetHorseResult() + "</td></tr>" +
                "</table>" +
                "</body></html>";
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String body = HttpUtil.parseJsonRequest(req);
//        Bet bet = new Gson().fromJson(body, Bet.class);
//
//        var betHorsePosition = bet.getBetHorsePosition();
//        var horsesTotal = bet.getHorsesTotal();
//
//        if (betHorsePosition < 1 || betHorsePosition > horsesTotal) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        } else {
//            service.run(new Bet(horsesTotal, betHorsePosition));
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        }
//    }
}
