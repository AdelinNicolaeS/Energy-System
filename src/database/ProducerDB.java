package database;

import network.Producer;

import java.util.ArrayList;

public class ProducerDB {
    private ArrayList<Producer> producersList = new ArrayList<>();

    public ArrayList<Producer> getProducersList() {
        return producersList;
    }

    public void setProducersList(ArrayList<Producer> producersList) {
        this.producersList = producersList;
    }

    public ProducerDB(ArrayList<Producer> producersList) {
        this.producersList = producersList;
    }

    public ProducerDB() {
    }

}
