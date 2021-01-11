package entities;

/**
 * Types of energy produced by EnergyProducers
 */
public enum EnergyType {
    WIND("WIND", true),
    SOLAR("SOLAR", true),
    HYDRO("HYDRO", true),
    COAL("COAL", false),
    NUCLEAR("NUCLEAR", false);

    private final String label;

    private final boolean renewable;

    EnergyType(String label, boolean renewable) {
        this.label = label;
        this.renewable = renewable;
    }

    public String getLabel() {
        return label;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public static EnergyType convertString(String type) {
        for (EnergyType energyType : EnergyType.values()) {
            if (energyType.getLabel().equals(type)) {
                return energyType;
            }
        }
        return null;
    }
}
