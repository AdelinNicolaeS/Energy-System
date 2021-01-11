package strategies;

import utils.Utils;

public class StrategyFactory {
    public Strategy createStrategy(String type) {
        if(type.equals(Utils.GREEN)) {
            return new GreenStrategy();
        }
        if(type.equals(Utils.PRICE)) {
            return new PriceStrategy();
        }
        if(type.equals(Utils.QUANTITY)) {
            return new QuantityStrategy();
        }
        return null;
    }
}
