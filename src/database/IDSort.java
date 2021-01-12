package database;

import network.Distributor;

import java.util.Comparator;

public final class IDSort implements Comparator<Distributor> {
    @Override
    public int compare(Distributor o1, Distributor o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
