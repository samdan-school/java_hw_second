/* MessageQueue.java: A message queue with synchronized methods for queuing
and consuming messages. */
package hw_9.fruit;

import java.util.ArrayList;
import java.util.List;

public class Market {
    private int stockSize;
    private int apple;
    private int orange;
    private int grape;
    private int watermelon;

    public Market(int stockSize) {
        if (stockSize <= 0)
            throw new IllegalArgumentException("Size is illegal.");
        this.stockSize = stockSize;
    }

    public synchronized boolean isFull() {
        return (apple + orange + grape + watermelon) == stockSize;
    }

    public synchronized boolean isEmpty() {
        return (apple + orange + grape + watermelon) == 0;
    }

    public synchronized void put(int type, int amount) {
        if (amount <= 0) {
            System.out.println("Should add more than 1 item!");
            return;
        }
        while (isFull() || (apple + orange + grape + watermelon + amount) >= stockSize) {
            System.out.println("Stock is full.");
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }

        String typeString;
        switch (type) {
            case 1:
                typeString = "apple";
                apple += amount;
                break;
            case 2:
                typeString = "orange";
                orange += amount;
                break;
            case 3:
                typeString = "grape";
                grape += amount;
                break;
            case 4:
                typeString = "watermelon";
                watermelon += amount;
                break;
            default:
                return;
        }
        System.out.println("Market add " + amount +" ‘" + typeString + "’");
        notifyAll();
    }

    public synchronized String get(int type, int amount) {
        String message = "";
        if (amount <= 0) {
            System.out.println("Should buy more than 1 item!");
            return message;
        }
        while (isEmpty() || (apple + orange + grape + watermelon - amount) <= 0) {
            System.out.println("There is not enough fruits in market.");
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        message += amount + " ";
        switch (type) {
            case 1:
                message += "apple";
                apple -= amount;
                break;
            case 2:
                message += "orange";
                orange -= amount;
                break;
            case 3:
                message += "grape";
                grape -= amount;
                break;
            case 4:
                message += "watermelon";
                watermelon -= amount;
                break;
            default:
                return message;
        }
        notifyAll();
        return message;
    }
}