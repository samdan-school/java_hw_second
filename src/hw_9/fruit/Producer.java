package hw_9.fruit;

public class Producer extends Thread {
    private Market stock = null;
    private int type;
    private int amount;

    public Producer(Market stock, int type, int amount) {
        this.stock = stock;
        this.type = type;
        this.amount = amount;
    }

    public void run() {
        stock.put(type, amount);
    }
}