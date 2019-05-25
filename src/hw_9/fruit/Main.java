package hw_9.fruit;

public class Main {
    public static void main(String[] args) {

        Market stock = new Market(10);
        new Producer(stock, 1, 4).start();
        new Consumer(stock, 1, -8).start();
    }
}
