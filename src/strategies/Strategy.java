package strategies;

import network.Distributor;

public interface Strategy {
    /**
     * aplica strategia aleasa pe lista de producatori existenti
     * @param distributor distributorul pentru care se aplica strategia
     */
    void applyStrategy(Distributor distributor);
}
