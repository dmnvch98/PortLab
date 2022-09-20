package com.bsuir.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    static boolean flag = false;
    static Port port = new Port();
    public static void main(String[] args) throws InterruptedException {
        new Main().initData(port);
        getCurrentInfo(port);
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            es.execute(() -> port.startWork(finalI));
        }
        es.shutdown();
        flag = es.awaitTermination(1, TimeUnit.MINUTES);
        if (flag) {
            System.out.println("Все корабли закончили свое задание");
        }
    }

    public void initData(Port port) {
        List<Berth> berths = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Berth berth = new Berth();
            berth.setNumber(i);
            berths.add(berth);
        }
        port.setBerths(berths);
    }

    public static void getCurrentInfo(Port port) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!flag) {
                    System.out.println(port);
                } else {
                    timer.cancel();
                }
            }
        }, 0, 5000);
    }
}
