package strategies;

import database.ProducerDB;
import network.Distributor;
import network.Producer;

import java.util.Comparator;

public final class GreenStrategy implements Strategy {
    @Override
    public void applyStrategy(Distributor distributor) {
        int countEnergy = 0, i = 0;
        ProducerDB db = new ProducerDB(distributor.getProducerDB().getProducersList());
        db.getProducersList().removeIf((v) -> v.getMaxDistributors() == v.getDistributors());
        Comparator<Producer> priceSort = Comparator.comparing(Producer::getPrice)
                .thenComparing(Comparator.comparing(Producer::getEnergy).reversed())
                .thenComparing(Producer::getId);
        Comparator<Producer> greenSort = Comparator.comparing(Producer::getRenewable).reversed()
                .thenComparing(priceSort);
        db.getProducersList().sort(greenSort);
        while (distributor.getEnergy() > countEnergy) {
            countEnergy += db.getProducersList().get(i).getEnergy();
            i++;
            distributor.getPersonalProducers().add(db.getProducersList().get(i - 1));
        }
    }
}
