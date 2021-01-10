package database;

import network.Distributor;
import network.Producer;

public abstract class Observable {
    DistributorDB observers = new DistributorDB();
    Producer changedProducer = new Producer();
    public abstract void notifyObservers();
    public void addObserver(Distributor distributor) {
        observers.getDistributorsList().add(distributor);
    }
    public void removeObserver(Distributor distributor) {
        observers.getDistributorsList().remove(distributor);
    }
}
