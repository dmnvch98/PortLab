package com.bsuir.labs;

public class Ship {
    private String product;
    private String name;
    private boolean finishedTask = false;
    private boolean queuePassed = false;

    private boolean storagePassed = false;


    public Ship() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isQueuePassed() {
        return queuePassed;
    }

    public void setQueuePassed(boolean queuePassed) {
        this.queuePassed = queuePassed;
    }

    public boolean isFinishedTask() {
        return finishedTask;
    }

    public void setFinishedTask(boolean finishedTask) {
        this.finishedTask = finishedTask;
    }

    public boolean isStoragePassed() {
        return storagePassed;
    }

    public void setStoragePassed(boolean storagePassed) {
        this.storagePassed = storagePassed;
    }

    @Override
    public String toString() {
        return name;
    }
}
