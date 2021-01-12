package database;

import network.Distributor;
import network.Producer;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public abstract class Observable {
    private Set<Distributor> observers = new TreeSet<>(new IDSort());
    //private Producer changedProducer = new Producer();
    private ArrayList<Producer> changedProducers = new ArrayList<>();

    public Set<Distributor> getObservers() {
        return observers;
    }

    public void setObservers(Set<Distributor> observers) {
        this.observers = observers;
    }

    public ArrayList<Producer> getChangedProducers() {
        return changedProducers;
    }

    public void addChangedProducer(Producer producer) {
        changedProducers.add(producer);
        //observers = new DistributorDB(this.changedProducer.getDistributorDB().getDistributorsList());
        observers.addAll(producer.getDistributorDB().getDistributorsList());
    }

    public void addObserver(Distributor distributor) {
        observers.add(distributor);
    }
    public void removeObserver(Distributor distributor) {
        observers.remove(distributor);
    }

    public abstract void notifyObservers();
}
