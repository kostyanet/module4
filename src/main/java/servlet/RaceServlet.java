package servlet;

import com.google.gson.Gson;
import pojo.Bet;
import service.RaceService;
import util.HttpUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * /race/{id} – отображает информацию о забеге: дата проведения, общее кол-во лошадей,
 * место каждой из них и номер лошади на которую ставил пользователь.
 *
 * /race/start?horseCount=X&betPosition=Y – страница на которой пользователь может запустить новый забег,
 * указав количество лошадей X и номер лошади на которую ставит Y
 */
public class RaceServlet extends HttpServlet {
    private final static String NUM_REG_EXP = "/\\d+";
    private final RaceService service = RaceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        String path = req.getPathInfo();
        System.out.println("doGet");

        if (path.matches(NUM_REG_EXP)) {
            var id = Integer.parseInt(path.substring(1));
            var report = service.getById(id);

            if (report == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setStatus(200);
                resp.setContentType("application/json");
                var body = new Gson().toJson(report);
                responseBody.println(body);
            }

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = HttpUtil.parseJsonRequest(req);
        Bet bet = new Gson().fromJson(body, Bet.class);

        var betHorsePosition = bet.getBetHorsePosition();
        var horsesTotal = bet.getHorsesTotal();

        if (betHorsePosition < 1 || betHorsePosition > horsesTotal) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            service.run(new Bet(horsesTotal, betHorsePosition));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
