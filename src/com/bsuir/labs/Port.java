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

    public synchronized boolean askPermissionGoToBerth(Ship ship) throws InterruptedException {
        Thread.sleep(50);
        System.out.println(ship.getName() + " запрашивает разрешение на вход в причал");
        // В листе причалов происходит поиск причала, в котором нет корабля. Если такой причал есть, то корабль
        // отправляется туда
        Berth berth = berths.stream().filter(b -> b.getShip() == null).findAny().orElseThrow();
        System.out.println(ship.getName() + " резрешено войти в причал");
        //отправка
        sendShipToBerth(ship, berth);
        return true;
    }

    public synchronized void sendShipToBerth(Ship ship, Berth berth) throws InterruptedException {
        Thread.sleep(50);
        int index = berths.indexOf(berth);
        berths.get(index).setShip(ship);
        System.out.println(ship.getName() + " пришвартовался к причалу номер " + berth.getNumber());
        shipsInQueueOutside.remove(ship);
    }

    public synchronized void addShipToQueueOutside(Ship ship) throws InterruptedException {
        Thread.sleep(50);
        System.out.println(ship.getName() + " отправился в очередь");
        shipsInQueueOutside.add(ship);
    }

    public synchronized void sendShipFromBerthToStorage(Ship ship) throws InterruptedException {
        int index;
        Thread.sleep(50);
        Berth berth = berths.stream().filter(b -> b.getShip() == ship).toList().get(0);
        storage.setShip(berth.getShip());
        index = berths.indexOf(berth);
        System.out.println(berth.getShip().getName() + " из причала номер " + berth.getNumber() + " отправился на склад");
        berths.get(index).setShip(null);
    }

    public void startWork(int shipIndex) {
        Ship ship = new Ship();
        ship.setName("Ship " + shipIndex);
        ship.setProduct("Ship Product " + shipIndex);
        try {
            //Сначала все корабли отправляются в очередь
            this.addShipToQueueOutside(ship);
            //Если корабль не выполнил свою задачу, то он будет находится в цикле пока не выполнит ее
            while (!ship.isFinishedTask()) {
                // Отправка корабля из очереди на причал
                if (!ship.isQueuePassed()) {
                    if (this.askPermissionGoToBerth(ship)) {
                        ship.setQueuePassed(true);
                    }
                }
                //Отправка корабля из причала на склад
                if (ship.isQueuePassed() && !ship.isFinishedTask()) {
                    if (storage.askPermissionGoToStorage(ship)) {
                        storage.isAvailable = false;
                        this.sendShipFromBerthToStorage(ship);
                        //затем запуск выгрузки-загрузки корабля
                        storage.unloadShip(ship);
                        storage.loadShip(ship);
                        //Выпуск корабля из причала
                        ship.setFinishedTask(true);
                        storage.releaseShip(ship);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Порт:\n" +
                "Корабли в очереди=" + shipsInQueueOutside +
                "\nberths=" + berths +
                "\nstorage=" + storage;
    }
}
