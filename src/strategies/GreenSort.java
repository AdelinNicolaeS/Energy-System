package strategies;

import network.Producer;

import java.util.Comparator;

public class GreenSort implements Comparator<Producer> {

    @Override
    public int compare(Producer o1, Producer o2) {
        if(o1.getEnergyType().isRenewable() && !o2.getEnergyType().isRenewable()) {
            return -1;
        } else if(!o1.getEnergyType().isRenewable() && o2.getEnergyType().isRenewable()) {
            return 1;
        } else {
            return new PriceSort().compare(o1, o2);
        }

    }
}
