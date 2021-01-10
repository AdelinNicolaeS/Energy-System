package database;

import network.Consumer;

import java.util.ArrayList;

public class ConsumerDB {
    private ArrayList<Consumer> consumersList = new ArrayList<>();

    public ConsumerDB(final ArrayList<Consumer> consumersList) {
        this.consumersList = new ArrayList<>(consumersList);
    }

    public ConsumerDB() {
    }

    public final ArrayList<Consumer> getConsumersList() {
        return consumersList;
    }

    public final void setConsumersList(final ArrayList<Consumer> consumersList) {
        this.consumersList = consumersList;
    }
}
