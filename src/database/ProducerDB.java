package database;

import network.Distributor;
import network.Producer;

import java.util.ArrayList;

public class ProducerDB extends Observable {
    private ArrayList<Producer> producersList = new ArrayList<>();

    public ArrayList<Producer> getProducersList() {
        return producersList;
    }

    public void setProducersList(ArrayList<Producer> producersList) {
        this.producersList = producersList;
    }

    public ProducerDB(ArrayList<Producer> producersList) {
        this.producersList = new ArrayList<>(producersList);
    }

    public ProducerDB() {
    }

    @Override
    public void notifyObservers() {
        for(Distributor distributor : this.getObservers()) {
            distributor.update();
        }
    }
}
