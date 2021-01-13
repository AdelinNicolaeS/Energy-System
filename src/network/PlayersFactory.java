package network;

import utils.Utils;

public final class PlayersFactory {
    private static final PlayersFactory INSTANCE = new PlayersFactory();

    public static PlayersFactory getINSTANCE() {
        return INSTANCE;
    }

    private PlayersFactory() {
    }

    /**
     * creeaza un nou jucator in sistemul energetic in functie de tip
     * @param type tipul jucatorului (consumator, distribuitor, producator)
     * @return o noua instanta in functie de tipul cerut sau null
     * daca tipul cerut nu exista
     */
    public Player createPlayer(final String type) {
        return switch (type) {
            case Utils.CONSUMERS -> new Consumer();
            case Utils.DISTRIBUTORS -> new Distributor();
            case Utils.PRODUCER -> new Producer();
            default -> null;
        };
    }
}
