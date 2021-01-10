package database;

import network.Distributor;

import java.util.ArrayList;

public class DistributorDB implements Observer {
    private ArrayList<Distributor> distributorsList = new ArrayList<>();

    public final ArrayList<Distributor> getDistributorsList() {
        return distributorsList;
    }

    public final void setDistributorsList(final ArrayList<Distributor> distributorsList) {
        this.distributorsList = distributorsList;
    }

    public DistributorDB(final ArrayList<Distributor> distributors) {
        this.distributorsList = new ArrayList<>(distributors);
    }

    public DistributorDB() {
    }

    /**
     * verific daca toti distribuitorii sunt in faliment
     * @return valoarea de adevar a acestei presupuneri
     */
    public final boolean allBankrupt() {
        for (Distributor distributor : distributorsList) {
            if (!distributor.isBankrupt()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        for(Distributor distributor : distributorsList) {
            distributor.update();
        }
    }
}
