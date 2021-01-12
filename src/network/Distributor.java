package network;

import database.ProducerDB;
import strategies.EnergyChoiceStrategyType;
import strategies.Strategy;
import utils.Utils;

import java.util.ArrayList;

public final class Distributor extends Player {
    private long contractLength;
    private long infrastructureCost;
    private long productionCost;
    private long numberOfClients = 0;
    private long price = 0;
    private long producerPrice;
    private long energy;
    private EnergyChoiceStrategyType producerStrategy;
    private Strategy strategy;
    private ArrayList<Producer> personalProducers = new ArrayList<>();
    private ProducerDB producerDB; // baza de date la nivelul sistemului

    public ProducerDB getProducerDB() {
        return producerDB;
    }

    public void setProducerDB(ProducerDB producerDB) {
        this.producerDB = producerDB;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public ArrayList<Producer> getPersonalProducers() {
        return personalProducers;
    }

    public void setPersonalProducers(ArrayList<Producer> personalProducers) {
        this.personalProducers = personalProducers;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
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
    public void calculateNumberClients() {
        numberOfClients = clients.size();
    }

    public long getProducerPrice() {
        return producerPrice;
    }

    /**
     * recalculeaza cheltuielile lunare ale distribuitorului
     */
    public void setProducerPrice() {
        producerPrice = infrastructureCost + productionCost * numberOfClients;
    }

    public long getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(final long numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public ArrayList<Consumer> getClients() {
        return clients;
    }

    public void setClients(final ArrayList<Consumer> clients) {
        this.clients = clients;
    }

    public long getPrice() {
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

    public long getContractLength() {
        return contractLength;
    }

    public void setContractLength(final long contractLength) {
        this.contractLength = contractLength;
    }

    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final long initialInfrastructureCost) {
        this.infrastructureCost = initialInfrastructureCost;
    }

    public long getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final long productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * recalculeaza costul de productie dupa modificarea
     * listei de furnizori
     */
    public void calculateProductionCost() {
        float cost = 0;
        for (Producer producer : personalProducers) {
            cost += producer.getEnergy() * producer.getPrice();
        }
        productionCost = Math.round(Math.floor(cost / Utils.PRODUCTION_RATE));
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
            for (Producer producer : personalProducers) {
                producer.removeDistributor(this);
            }
            personalProducers.clear();
        }
    }

    /**
     * aplica strategia aleasa pentru a gasi noi producatori
     * actualizeaza listele de producatori si distribuitori ale
     * entitatilor implicate
     */
    public void update() {
        for (Producer producer : personalProducers) {
            producer.removeDistributor(this);
        }
        personalProducers.clear();
        strategy.applyStrategy(this);
        for (Producer producer : personalProducers) {
            producer.addDistributor(this);
        }
        calculateProductionCost(); // noi producatori => nou cost de productie
    }
}
