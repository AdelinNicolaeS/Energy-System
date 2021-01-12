package strategies;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN("GREEN"),
    PRICE("PRICE"),
    QUANTITY("QUANTITY");
    private final String label;

    public String getLabel() {
        return label;
    }

    EnergyChoiceStrategyType(String label) {
        this.label = label;
    }

    /**
     * transforma inputul de la utilizator in enum-ul cerut
     * @param type tipul cerut de utilizator (label-ul)
     * @return enum-ul corespunzator sau null, daca nu exista
     */
    public static EnergyChoiceStrategyType convertString(String type) {
        for (EnergyChoiceStrategyType strategyType  : EnergyChoiceStrategyType.values()) {
            if (strategyType.label.equals(type)) {
                return strategyType;
            }
        }
        return null;
    }
}
