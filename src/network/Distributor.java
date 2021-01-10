package network;

import strategies.EnergyChoiceStrategyType;
import utils.Utils;

import java.util.ArrayList;

public class Distributor extends Player {
    private long contractLength;
    private long infrastructureCost;
    private long productionCost;
    private long numberOfClients = 0;
    private long price = 0;
    private long producerPrice;
    private long energy;
    private EnergyChoiceStrategyType strategy;

    public EnergyChoiceStrategyType getStrategy() {
        return strategy;
    }

    public void setStrategy(EnergyChoiceStrategyType strategy) {
        this.strategy = strategy;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    private ArrayList<Consumer> clients = new ArrayList<>();

    /**
     * actualizeaza numarul de contracte
     */
    public final void calculateNumberClients() {
        numberOfClients = clients.size();
    }

    public final long getProducerPrice() {
        return producerPrice;
    }

    /**
     * recalculeaza cheltuielile lunare ale distribuitorului
     */
    public final void setProducerPrice() {
        producerPrice = infrastructureCost + productionCost * numberOfClients;
    }

    public final long getNumberOfClients() {
        return numberOfClients;
    }

    public final void setNumberOfClients(final long numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public final ArrayList<Consumer> getClients() {
        return clients;
    }

    public final void setClients(final ArrayList<Consumer> clients) {
        this.clients = clients;
    }

    public final long getPrice() {
        return price;
    }

    /**
     * recalculeaza pretul contractului pe car il propune distribuitorul
     */
    public void setPrice() {
        if (numberOfClients == 0) {
            price = infrastructureCost + productionCost;
            price += Math.round(Math.floor(Utils.PROFIT_PERCENT * productionCost));
            return;
        }
        price = Math.round(Math.floor((double) infrastructureCost / numberOfClients));
        price += Math.round(productionCost);
        price += Math.round(Math.floor(Utils.PROFIT_PERCENT * productionCost));
    }

    public final long getContractLength() {
        return contractLength;
    }

    public final void setContractLength(final long contractLength) {
        this.contractLength = contractLength;
    }

    public final long getInfrastructureCost() {
        return infrastructureCost;
    }

    public final void setInfrastructureCost(final long initialInfrastructureCost) {
        this.infrastructureCost = initialInfrastructureCost;
    }

    public final long getProductionCost() {
        return productionCost;
    }

    public final void setProductionCost(final long productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * adauga un nou contract si mareste numarul de contracte ale distribuitorului
     * @param consumer noul consumator al distribuitorului
     */
    public void addConsumer(final Consumer consumer) {
        clients.add(consumer);
        numberOfClients = clients.size();
    }

    /**
     * cand trece printr-o noua luna, distribuitorul isi achita datoriile, isi
     * recalculeaza contractele si recalculeaza costurile de intretinere
     */
    public void goThroughRound() {
        if (isBankrupt()) {
            return;
        }
        this.setBudget(this.getBudget() - producerPrice);
        clients.removeIf(Player::isBankrupt);
        setNumberOfClients(clients.size());
        setProducerPrice();
        if (this.getBudget() < 0) {
            this.setBankrupt(true);
        }
    }
}
