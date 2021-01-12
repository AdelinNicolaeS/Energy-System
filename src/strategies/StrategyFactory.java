package strategies;

import utils.Utils;

public final class StrategyFactory {
    /**
     * creeaza o noua strategie in functie de
     * cerintele utilizatorului
     * @param type strategia sub forma de input
     * @return noua strategie sau null, daca nu
     * exista tipul cerut
     */
    public Strategy createStrategy(String type) {
        if (type.equals(Utils.GREEN)) {
            return new GreenStrategy();
        }
        if (type.equals(Utils.PRICE)) {
            return new PriceStrategy();
        }
        if (type.equals(Utils.QUANTITY)) {
            return new QuantityStrategy();
        }
        return null;
    }
}
