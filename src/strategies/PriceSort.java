package strategies;

import network.Producer;

import java.util.Comparator;

public class PriceSort implements Comparator<Producer> {
    @Override
    public int compare(Producer o1, Producer o2) {
        if(o1.getPrice() > o2.getPrice()) {
            return 1;
        }
        if(o2.getPrice() > o1.getPrice()) {
            return -1;
        }
        return new QuantitySort().compare(o1, o2);
    }
}
