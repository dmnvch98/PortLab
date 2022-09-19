package com.bsuir.labs;

public class Berth {
    private Ship ship;
    private int number;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
//        return "Berth{" +
//                "ship=" + ship +
//                ", number=" + number +
//                '}';
        if (ship != null) {
            return "На пирсе номер " + number + " находится " + ship;
        } else {
            return "Пирс номер " + number + " пустой";
        }
    }
}
