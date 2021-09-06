package service;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;


public class HorseRun implements Runnable {
    private static final int FINISH = 1000;
    private static final int STEPS_PER_ITERATION = 10;
    private static final int MIN_ITER_DIST = 20;
    private static final int MAX_ITER_DIST = 30;
    private static final int MIN_SLEEP = 400;
    private static final int MAX_SLEEP = 500;

    private final String name;
    private final Consumer<String> onFinishAction;
    private int distance;
    private boolean isDone = false;

    HorseRun(String name, Consumer<String> onFinishAction) {
        this.name = name;
        this.onFinishAction = onFinishAction;
    }

    public void run() {
        try {
            while (!isDone) {
                makeIteration();
                System.out.println(name + " falls asleep");
                Thread.sleep(getSleepDuration());
            }
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeIteration() throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < STEPS_PER_ITERATION; i++) {
            distance += getDelta();

            if (distance >= FINISH) {
                System.out.println(name + " has finished");
                isDone = true;
                onFinishAction.accept(name);
                Thread.currentThread().join();
            }
        }
    }

    private static int getDelta() {
        return ThreadLocalRandom.current().nextInt(MIN_ITER_DIST, MAX_ITER_DIST);
    }

    private static int getSleepDuration() {
        return ThreadLocalRandom.current().nextInt(MIN_SLEEP, MAX_SLEEP);
    }
}
