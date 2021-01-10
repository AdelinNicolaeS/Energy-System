package network;

import database.DistributorDB;
import utils.Utils;

public class Consumer extends Player {
    private long monthlyIncome;
    private long price = 0;
    private long remainedContractMonths = 0;
    private Distributor distributor = new Distributor();

    public final Distributor getDistributor() {
        return distributor;
    }

    public final void setDistributor(final Distributor distributor) {
        this.distributor = distributor;
    }

    public final long getPrice() {
        return price;
    }

    public final void setPrice(final long price) {
        this.price = price;
    }

    public final long getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public final void setRemainedContractMonths(final long remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public final long getMonthlyIncome() {
        return monthlyIncome;
    }

    public final void setMonthlyIncome(final long monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * gaseste un nou distribuitor pentru consumator si efectueaza principalele
     * modificari cauzate de aceasta actiune
     * @param distributorDB baza de date din care este ales distribuitorul
     */
    public void findDistributor(final DistributorDB distributorDB) {
        if (distributorDB.getDistributorsList().size() == 0) {
            return; // nu mai sunt distribuitori nefalimentari
        }
        boolean found = false;
        long mn = Integer.MAX_VALUE;
        Distributor chosen = null;
        for (int i = 0; !found && i < distributorDB.getDistributorsList().size(); i++) {
            if (!distributorDB.getDistributorsList().get(i).isBankrupt()) {
                mn = distributorDB.getDistributorsList().get(i).getPrice();
                chosen = distributorDB.getDistributorsList().get(i);
                found = true;
            }
        }
        for (Distributor distributor1 : distributorDB.getDistributorsList()) {
            if (!distributor1.isBankrupt() && distributor1.getPrice() < mn) {
                mn = distributor1.getPrice();
                chosen = distributor1;
            }
        }
        // caz special cand in ultima luna face restanta la distribuitorul vechi, dar il schimba
        if (this.getDebt() > 0) { // verifica daca are datorii la cel vechi
            long sum = mn + Math.round(Math.floor((1 + Utils.PROFIT_PERCENT) * this.getDebt()));
            long futureBudget = monthlyIncome + this.getBudget(); // suma de care va dispune
            if (futureBudget > sum) { // verifica daca poate plati totul
                long oldDebt = Math.round(Math.floor((1 + Utils.PROFIT_PERCENT) * this.getDebt()));
                this.distributor.setBudget(this.distributor.getBudget() + oldDebt);
            }
        }
        this.distributor.getClients().remove(this);
        this.distributor = chosen; // consumatorul si-a ales distribuitorul nou
        this.distributor.addConsumer(this); // distribuitorul are un nou consumator
        this.price = mn; // pretul spabilit in contract
        remainedContractMonths = this.distributor.getContractLength();
    }

    /**
     * in timpul lunii consumatorul isi primeste salariul daca nu e in faliment,
     * isi cauta un distribuitor daca cel vechi a falimentat, dupa care incearca
     * sa isi plateasca sumele din contract, eventual plus restanta
     * @param distributorDB baza de date cu distribuitorii
     */
    public void goThroughRound(final DistributorDB distributorDB) {
        if (isBankrupt()) {
            return;
        }
        this.setBudget(this.getBudget() + monthlyIncome);
        if (this.distributor.isBankrupt()) {
            findDistributor(distributorDB);
        }
        if (this.getDebt() > 0) { // are deja o luna de datorie
            long oldDebt = Math.round(Math.floor((1 + Utils.PROFIT_PERCENT) * this.getDebt()));
            long sum = this.getPrice() + oldDebt;
            if (this.getBudget() < sum) {
                this.setBankrupt(true);
            } else { // intra aici numai daca schimba distribuitorul intre timp
                this.setBudget(this.getBudget() - sum);
                this.distributor.setBudget(this.distributor.getBudget() + this.getPrice());
                this.setDebt(0); // vechiul distribuitor are deja suma platita
            }
        } else { // nu are datorie pana acum
            if (this.getBudget() < price) {
                this.setDebt(price);
            } else {
                this.setBudget(this.getBudget() - price);
                this.distributor.setBudget(this.distributor.getBudget() + this.getPrice());
            }
        }
        remainedContractMonths--;
    }
}
