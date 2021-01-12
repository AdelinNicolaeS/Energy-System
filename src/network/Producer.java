package network;

import database.DistributorDB;
import entities.EnergyType;

import java.util.ArrayList;

public final class Producer extends Player {
    private EnergyType energyType;
    private long maxDistributors;
    private double price;
    private long energy;
    private DistributorDB distributorDB = new DistributorDB();
    private int numberOfDistributors = 0;
    private ArrayList<MonthlyStats> monthlyStats = new ArrayList<>();

    public ArrayList<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(ArrayList<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    /**
     * adauga un nou distribuitor in lista de distribuitori
     * carora producatorul le furnizeaza energie
     * @param distributor noul client ce va fi adaugat in lista
     */
    public void addDistributor(Distributor distributor) {
        distributorDB.getDistributorsList().add(distributor);
        numberOfDistributors = distributorDB.getDistributorsList().size();
    }

    /**
     * elimina un distribuitor din lista celor carora le furnizeaza energie
     * @param distributor clientul ce va fi eliminat din lista contractelor
     */
    public void removeDistributor(Distributor distributor) {
        distributorDB.getDistributorsList().remove(distributor);
        numberOfDistributors = distributorDB.getDistributorsList().size();
    }
    public int getDistributors() {
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

    public long getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(long maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

}
