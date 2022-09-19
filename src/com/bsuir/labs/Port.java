package com.bsuir.labs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        Thread.sleep(500);
        Berth berth = berths.stream().filter(b -> b.getShip() == null).toList().get(0);
        int index = berths.indexOf(berth);
        berths.get(index).setShip(ship);
        System.out.println(ship.getName() + " пришвартовался к причалу номер " + berth.getNumber());
    }

    public synchronized void addShipToQueueOutside(Ship ship) throws InterruptedException {
        Thread.sleep(500);
        System.out.println(ship.getName() + " отправился в очередь");
        shipsInQueueOutside.add(ship);
    }

    public void sendShipFromBerthToStorage(Berth berth, Storage storage) {
        storage.setShip(berth.getShip());
        berth.setShip(null);
        System.out.println(berth.getShip().getName() + " из причала номер " + berth.getNumber() + " отправился на склад");
    }

}
