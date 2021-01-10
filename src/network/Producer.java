package network;

import database.DistributorDB;
import entities.EnergyType;

public class Producer extends Player {
    private EnergyType energyType;
    private int maxDistributors;
    private float price;
    private long energy;
    private DistributorDB distributorDB = new DistributorDB();
    private int numberOfDistributors;

    public void calculateDistributors() {
        numberOfDistributors = distributorDB.getDistributorsList().size();
    }
    public int getNumberOfDistributors() {
        return numberOfDistributors;
    }

    public void setNumberOfDistributors(int numberOfDistributors) {
        this.numberOfDistributors = numberOfDistributors;
    }

    public DistributorDB getDistributorDB() {
        return distributorDB;
    }

    public void setDistributorDB(DistributorDB distributorDB) {
        this.distributorDB = distributorDB;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }
}
