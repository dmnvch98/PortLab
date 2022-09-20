package com.bsuir.labs;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    boolean isAvailable = true;
    private Queue<String> goods = new LinkedList<>();
    private Ship ship;

    public Storage() {
        for (int i = 0; i < 50; i++) {
            goods.add("Product of storage " + i);
        }
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Queue<String> getGoods() {
        return goods;
    }

    public void setGoods(Queue<String> goods) {
        this.goods = goods;
    }

    public synchronized boolean askPermissionGoToStorage(Ship ship) throws InterruptedException {
        Thread.sleep(50);
        System.out.println(ship.getName() + " запрашивает разрешение на выгрузку");
        if (isAvailable) {
            System.out.println(ship.getName() + " разрешено зайти на выгрузку");
            return true;
        } else {
            System.out.println(ship.getName() + " запрещено зайти на выгрузку");
            return false;
        }
    }

    public synchronized void loadShip(Ship ship) throws InterruptedException {
        Thread.sleep(50);
        String product = goods.poll();
        ship.setProduct(product);
        System.out.println(ship.getName() + " загружен грузом " + product);
    }

    public synchronized void unloadShip(Ship ship) throws InterruptedException {
        Thread.sleep(50);
        String product = ship.getProduct();
        goods.add(product);
        ship.setProduct(null);
        System.out.println(ship.getName() + " отгрузил груз " + product);
    }

    public synchronized void releaseShip(Ship ship) throws InterruptedException {
        Thread.sleep(50);
        this.setShip(null);
        System.out.println(ship.getName() + " покинул порт");
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "На складе находятся грузы" + goods + "\nкорабль=" + ship;
    }
}
