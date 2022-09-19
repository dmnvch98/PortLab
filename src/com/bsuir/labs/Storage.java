package com.bsuir.labs;

import java.util.Stack;

public class Storage {
    private Stack<String> goods = new Stack<>();

    private Stack<String> getGoods() {
        return goods;
    }

    private Ship ship;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setGoods(Stack<String> goods) {
        this.goods = goods;
    }

    public synchronized void loadShip(Ship ship) {
        String product = goods.pop();
        ship.setProduct(product);
        System.out.println("Корабль " + ship.getName() + " загружен грузом " + product);
    }

    public synchronized void unloadShip(Ship ship) {
        String product = ship.getProduct();
        goods.push(product);
        ship.setProduct(null);
        System.out.println("Корабль " + ship.getName() + " отгрузил груз " + product);
    }

}
