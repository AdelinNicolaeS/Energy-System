package network;

public abstract class Player {
    private long id;
    private boolean isBankrupt = false;
    private long budget;
    private long debt = 0;

    public final long getDebt() {
        return debt;
    }

    public final void setDebt(final long debt) {
        this.debt = debt;
    }

    public final long getId() {
        return id;
    }

    public final void setId(final long id) {
        this.id = id;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public final long getBudget() {
        return budget;
    }

    public final void setBudget(final long budget) {
        this.budget = budget;
    }
}
