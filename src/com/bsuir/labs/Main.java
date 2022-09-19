package com.bsuir.labs;

import java.util.ArrayList;
import java.util.List;

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
                        boolean isShipEndTask = false;
                        Ship ship = new Ship("Ship " + finalI);
                        if (port.askPermissionGoToBerth()) {
                            try {
                                port.sendShipToBerth(ship);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                port.addShipToQueueOutside(ship);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        while (!isShipEndTask) {

                        }
                    }
            );
            thread.start();
        }
    }
}
