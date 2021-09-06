package service;

import dao.RaceDao;
import entity.RaceReport;
import entity.RaceStat;
import pojo.Bet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;


public class RaceService {
    private static final List<String> raceResult = new ArrayList<>();
    private final RaceDao raceDao = new RaceDao();
    private Bet bet;

    public RaceService() {
    }

    public void run(Bet bet) {
        this.bet = bet;
        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable barrierAction = () -> {
            System.out.println("Well done, guys!");
            System.out.println(raceResult.toString());
            saveReport();
        };

        CyclicBarrier cyclicBarrier = new CyclicBarrier(bet.getHorsesTotal(), barrierAction);
        Consumer<String> onFinish = (String name) -> {
            raceResult.add(name);
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < bet.getHorsesTotal(); i++) {
            HorseRun h = new HorseRun("Horse " + (i + 1), onFinish);
            executor.execute(h);
        }

        executor.shutdown();
    }

    public List<RaceStat> getStats() {
        return raceDao.getStats();
    }

    public RaceReport getById(int id) {
        return raceDao.getById(id);
    }

    private void saveReport() {
        RaceReport report = new RaceReport(
                LocalDateTime.now(),
                bet.getHorsesTotal(),
                String.join(", ", raceResult),
                getBetHorsePosition()
        );
        raceDao.createReport(report);
        raceResult.clear();
    }

    private int getBetHorsePosition() {
        String name = "Horse " + bet.getBetHorsePosition();
        for (int i = 0; i < raceResult.size(); i++) {
            if (raceResult.get(i).equals(name))
                return i + 1;
        }
        return -1;
    }
}
