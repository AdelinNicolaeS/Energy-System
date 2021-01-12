package strategies;

import database.ProducerDB;
import network.Distributor;

public class QuantityStrategy implements Strategy {
    @Override
    public final void applyStrategy(Distributor distributor) {
        int countEnergy = 0, i = 0;
        ProducerDB db = new ProducerDB(distributor.getProducerDB().getProducersList());
        db.getProducersList().removeIf((v) -> v.getMaxDistributors() == v.getDistributors());
        db.getProducersList().sort(new QuantitySort());
        while (distributor.getEnergy() > countEnergy) {
            countEnergy += db.getProducersList().get(i).getEnergy();
            i++;
            distributor.getPersonalProducers().add(db.getProducersList().get(i - 1));
        }
    }
}
