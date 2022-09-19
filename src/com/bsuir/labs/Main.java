package com.bsuir.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Port port = new Port();
        List<Berth> berths = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Berth berth = new Berth();
            berth.setNumber(i);
            berths.add(berth);
        }
        port.setBerths(berths);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(
                    () -> {
                       Ship ship = new Ship("Ship " + finalI);
                        try {
                            port.addShipToQueueOutside(ship);
                            while (!ship.isFinishedTask()) {
                                if (!ship.isQueuePassed()) {
                                    if (port.askPermissionGoToBerth()) {
                                        port.sendShipToBerth(ship);
                                        ship.setQueuePassed(true);
                                    }
                                }
                                if (ship.isQueuePassed() && !ship.isBerthPassed()) {
                                    port.sendShipFromBerthToStorage(ship);
                                    ship.setBerthPassed(true);
                                }
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            thread.start();
        }
        Runnable helloRunnable = () -> System.out.println(port);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 5, TimeUnit.SECONDS);
    }


}
