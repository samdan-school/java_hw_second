package hw_9.fruit;

public class Consumer extends Thread {
    private Market stock = null;
    private int type;
    private int amount;

    public Consumer(Market stock, int type, int amount) {
        this.stock = stock;
        this.type = type;
        this.amount = amount;
    }

    public void run() {
        System.out.println("Consumer bought " + stock.get(type, amount) + "from the stock.");
    }
}