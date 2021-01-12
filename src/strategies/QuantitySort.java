package strategies;

import network.Producer;

import java.util.Comparator;

public class QuantitySort implements Comparator<Producer> {

    @Override
    public int compare(Producer o1, Producer o2) {
        int result = Long.compare(o1.getEnergy(), o2.getEnergy());
        if(result == 0) {
            return Long.compare(o1.getId(), o2.getId());
        }
        return -result;
    }
}
