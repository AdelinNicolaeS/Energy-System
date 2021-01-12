package strategies;

import database.ProducerDB;
import network.Distributor;

public final class GreenStrategy implements Strategy {
    @Override
    public void applyStrategy(Distributor distributor) {
        int countEnergy = 0, i = 0;
        ProducerDB db = new ProducerDB(distributor.getProducerDB().getProducersList());
        db.getProducersList().removeIf((v) -> v.getMaxDistributors() == v.getDistributors());
        db.getProducersList().sort(new GreenSort());
        while (distributor.getEnergy() > countEnergy) {
            countEnergy += db.getProducersList().get(i).getEnergy();
            i++;
            distributor.getPersonalProducers().add(db.getProducersList().get(i - 1));
        }
    }
}
