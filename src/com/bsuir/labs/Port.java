package com.bsuir.labs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Port {
    private Queue<Ship> shipsInQueueOutside = new LinkedList<>();
    private List<Berth> berths = new ArrayList<>();
    private Storage storage = new Storage();

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Queue<Ship> getShipsInQueueOutside() {
        return shipsInQueueOutside;
    }

    public void setShipsInQueueOutside(Queue<Ship> shipsInQueueOutside) {
        this.shipsInQueueOutside = shipsInQueueOutside;
    }

    public List<Berth> getBerths() {
        return berths;
    }

    public void setBerths(List<Berth> berths) {
        this.berths = berths;
    }


    public synchronized boolean askPermissionGoToBerth() {
        return berths.stream().filter(b -> b.getShip() == null).toList().size() > 0;
    }

    public synchronized void sendShipToBerth(Ship ship) throws InterruptedException {
        Thread.sleep(200);
        Berth berth = berths.stream().filter(b -> b.getShip() == null).findAny().orElseThrow();
        int index = berths.indexOf(berth);
        berths.get(index).setShip(ship);
        System.out.println(ship.getName() + " пришвартовался к причалу номер " + berth.getNumber());
        shipsInQueueOutside.remove(ship);
    }

    public synchronized void addShipToQueueOutside(Ship ship) throws InterruptedException {
        Thread.sleep(200);
        System.out.println(ship.getName() + " отправился в очередь");
        shipsInQueueOutside.add(ship);
    }

    public synchronized void sendShipFromBerthToStorage(Ship ship) throws InterruptedException {
        int index;
        Thread.sleep(200);
        Berth berth = berths.stream().filter(b -> b.getShip() == ship).toList().get(0);
        storage.setShip(berth.getShip());
        index = berths.indexOf(berth);
        System.out.println(berth.getShip().getName() + " из причала номер " + berth.getNumber() + " отправился на склад");
        berths.get(index).setShip(null);
    }

    public void startWork(int shipIndex) {
        Ship ship = new Ship("Ship " + shipIndex);
        try {
            this.addShipToQueueOutside(ship);
            while (!ship.isFinishedTask()) {
                if (!ship.isQueuePassed()) {
                    if (this.askPermissionGoToBerth()) {
                        this.sendShipToBerth(ship);
                        ship.setQueuePassed(true);
                    }
                }
                if (ship.isQueuePassed() && !ship.isBerthPassed()) {
                    this.sendShipFromBerthToStorage(ship);
                    ship.setBerthPassed(true);
                    ship.setFinishedTask(true);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCurrentInfo() {
        Runnable helloRunnable = () -> System.out.println(this);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public String toString() {
        return "Port{" +
                "shipsInQueueOutside=" + shipsInQueueOutside +
                ", berths=" + berths +
                ", storage=" + storage +
                '}';
    }
}
