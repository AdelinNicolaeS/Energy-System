package strategies;

import network.Producer;

import java.util.Comparator;

public class QuantitySort implements Comparator<Producer> {

    @Override
    public int compare(Producer o1, Producer o2) {
        if(o1.getEnergy() > o2.getEnergy()) {
            return 1;
        }
        if(o2.getEnergy() < o2.getEnergy()) {
            return -1;
        }
        return 0;
    }
}
