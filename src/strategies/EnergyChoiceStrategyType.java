package strategies;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN("GREEN"),
    PRICE("PRICE"),
    QUANTITY("QUANTITY");
    public final String label;

    EnergyChoiceStrategyType(String label) {
        this.label = label;
    }

    public static EnergyChoiceStrategyType convertString(String type) {
        for (EnergyChoiceStrategyType strategyType  : EnergyChoiceStrategyType.values()) {
            if (strategyType.label.equals(type)) {
                return strategyType;
            }
        }
        return null;
    }
}
