package strategies;

import database.ProducerDB;
import network.Distributor;

public interface Strategy {
    void applyStrategy(Distributor distributor, ProducerDB producerDB);
}
