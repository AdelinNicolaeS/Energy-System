package strategies;

import database.ProducerDB;
import network.Distributor;

public class QuantityStrategy implements Strategy {
    @Override
    public void applyStrategy(Distributor distributor, ProducerDB producerDB) {
        int countEnergy = 0, i = 0;
        ProducerDB producerDB1 = new ProducerDB(producerDB.getProducersList());
        producerDB1.getProducersList().removeIf((v) -> v.getMaxDistributors() == v.getNumberOfDistributors());
        producerDB1.getProducersList().sort(new QuantitySort());
        while(distributor.getEnergy() > countEnergy) {
            countEnergy += producerDB1.getProducersList().get(i).getEnergy();
            i++;
            distributor.getProducers().add(producerDB1.getProducersList().get(i));
        }
    }
}
