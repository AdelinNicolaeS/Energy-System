package database;

import network.Distributor;
import network.Producer;

import java.util.Set;
import java.util.TreeSet;


public abstract class Observable {
    private Set<Distributor> observers = new TreeSet<>(new IDSort());

    public final Set<Distributor> getObservers() {
        return observers;
    }

    public final void setObservers(Set<Distributor> observers) {
        this.observers = observers;
    }

    /**
     * adauga toti observatorii producatorului in lista de observatori
     * @param producer producatorul caruia i se schimba informatiile
     */
    public final void addAllObservers(Producer producer) {
        observers.addAll(producer.getDistributorDB().getDistributorsList());
    }

    /**
     * adauga separat un nou observator in lista
     * @param distributor noul observator
     */
    public final void addObserver(Distributor distributor) {
        observers.add(distributor);
    }

    /**
     * elimina un observator din lista de observatori
     * @param distributor observatorul ce va fi sters
     */
    public final void removeObserver(Distributor distributor) {
        observers.remove(distributor);
    }

    /**
     * notifica toti observatorii de schimbarile aparute la observabil
     */
    public abstract void notifyObservers();
}
