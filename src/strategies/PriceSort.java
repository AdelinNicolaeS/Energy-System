package strategies;

import network.Producer;

import java.util.Comparator;

public class PriceSort implements Comparator<Producer> {
    @Override
    public int compare(Producer o1, Producer o2) {
        int result = Double.compare(o1.getPrice(), o2.getPrice());
        if (result == 0) {
            return new QuantitySort().compare(o1, o2);
        }
        return result;
    }
}
