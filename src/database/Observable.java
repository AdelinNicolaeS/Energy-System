package database;

import network.Distributor;
import network.Producer;

public abstract class Observable {
    private DistributorDB observers = new DistributorDB();
    private Producer changedProducer = new Producer();

    public DistributorDB getObservers() {
        return observers;
    }

    public void setObservers(DistributorDB observers) {
        this.observers = observers;
    }

    public Producer getChangedProducer() {
        return changedProducer;
    }

    public void setChangedProducer(Producer changedProducer) {
        this.changedProducer = changedProducer;
        setObservers(this.changedProducer.getDistributorDB());
    }

    public void addObserver(Distributor distributor) {
        observers.getDistributorsList().add(distributor);
    }
    public void removeObserver(Distributor distributor) {
        observers.getDistributorsList().remove(distributor);
    }

    public abstract void notifyObservers();
}
