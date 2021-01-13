package data;

import database.ConsumerDB;
import database.DistributorDB;
import database.ProducerDB;
import entities.EnergyType;
import network.Consumer;
import network.Distributor;
import network.MonthlyStats;
import network.Player;
import network.PlayersFactory;
import network.Producer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import strategies.EnergyChoiceStrategyType;
import strategies.Strategy;
import strategies.StrategyFactory;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public final class Simulator {
    private JSONParser jsonParser;
    private long numberOfTurns;
    private JSONObject initialData;
    private JSONArray monthlyUpdates;
    private ConsumerDB consumerDB;
    private DistributorDB distributorDB;
    private ProducerDB producerDB;

    public ProducerDB getProducerDB() {
        return producerDB;
    }

    public void setProducerDB(ProducerDB producerDB) {
        this.producerDB = producerDB;
    }

    public JSONParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(final JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public JSONObject getInitialData() {
        return initialData;
    }

    public void setInitialData(final JSONObject initialData) {
        this.initialData = initialData;
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final long numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public JSONArray getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final JSONArray monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }

    public ConsumerDB getConsumerDB() {
        return consumerDB;
    }

    public void setConsumerDB(final ConsumerDB consumerDB) {
        this.consumerDB = consumerDB;
    }

    public DistributorDB getDistributorDB() {
        return distributorDB;
    }

    public void setDistributorDB(final DistributorDB distributorDB) {
        this.distributorDB = distributorDB;
    }


    /**
     * la fiecare rulare, initializam atributele pe care le
     * vom folosi in rezolvarea cerintelor
     */
    public void init() {
        jsonParser = new JSONParser();
        numberOfTurns = 0;
        consumerDB = new ConsumerDB();
        distributorDB = new DistributorDB();
        producerDB = new ProducerDB();
    }
    
    /**
     * calculeaza atributele pentru principalele componente ale fisierului JSON
     * @param fileReader fisierul din care se parcurge
     * @throws IOException trateaza exceptiile
     * @throws ParseException rol in tratarea exceptiilor
     */
    public void parseInput(final FileReader fileReader) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
        numberOfTurns = (long) jsonObject.get(Utils.NUMBER_OF_TURNS);
        initialData = (JSONObject) jsonObject.get(Utils.INITIAL_DATA);
        monthlyUpdates = (JSONArray) jsonObject.get(Utils.MONTHLY_UPDATES);
    }

    /**
     * parcurge setul initial de date si obtine lista de consumatori
     */
    public void obtainConsumers() {
        JSONArray jsonArray = (JSONArray) initialData.get(Utils.CONSUMERS);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            PlayersFactory playersFactory = PlayersFactory.getINSTANCE();
            Player player = playersFactory.createPlayer(Utils.CONSUMERS);
            long id = (long) jsonObject.get(Utils.ID);
            long initialBudget = (long) jsonObject.get(Utils.INITIAL_BUDGET);
            long monthlyIncome = (long) jsonObject.get(Utils.MONTHLY_INCOME);
            player.setId(id);
            player.setBudget(initialBudget);
            ((Consumer) player).setMonthlyIncome(monthlyIncome);
            consumerDB.getConsumersList().add((Consumer) player);
        }
    }

    /**
     * parcurge setul initial de date si obtine lista de distribuitori
     */
    public void obtainDistributors() {
        JSONArray jsonArray = (JSONArray) initialData.get(Utils.DISTRIBUTORS);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            PlayersFactory playersFactory = PlayersFactory.getINSTANCE();
            Player player = playersFactory.createPlayer(Utils.DISTRIBUTORS);
            long id = (long) jsonObject.get(Utils.ID);
            long initialBudget = (long) jsonObject.get(Utils.INITIAL_BUDGET);
            long contractLength = (long) jsonObject.get(Utils.CONTRACT_LENGTH);
            long infrastructureCost = (long) jsonObject.get(Utils.INITIAL_INFRASTRUCTURE_COST);
            long energy = (long) jsonObject.get(Utils.ENERGY_NEEDED);
            String type = (String) jsonObject.get(Utils.PRODUCER_STRATEGY);
            StrategyFactory strategyFactory = StrategyFactory.getINSTANCE();
            Strategy strategy = strategyFactory.createStrategy(type);
            EnergyChoiceStrategyType strategyType = EnergyChoiceStrategyType.convertString(type);

            player.setId(id);
            player.setBudget(initialBudget);
            ((Distributor) player).setContractLength(contractLength);
            ((Distributor) player).setInfrastructureCost(infrastructureCost);
            ((Distributor) player).setEnergy(energy);
            ((Distributor) player).setStrategy(strategy);
            ((Distributor) player).setProducerStrategy(strategyType);
            distributorDB.getDistributorsList().add((Distributor) player);
        }
    }

    /**
     * parcurge setul de date si genereaza setul de producatori
     */
    public void obtainProducers() {
        JSONArray jsonArray = (JSONArray) initialData.get(Utils.PRODUCERS);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            PlayersFactory playersFactory = PlayersFactory.getINSTANCE();
            Player player = playersFactory.createPlayer(Utils.PRODUCER);
            long id = (long) jsonObject.get(Utils.ID);
            String type = (String) jsonObject.get(Utils.ENERGY_TYPE);
            EnergyType energyType = EnergyType.convertString(type);
            long maxDistributors = (long) jsonObject.get(Utils.MAX_DISTRIBUTORS);
            double price = (double) jsonObject.get(Utils.PRICE_KW);
            long energy = (long) jsonObject.get(Utils.ENERGY_PER_DISTRIBUTOR);
            player.setId(id);
            ((Producer) player).setEnergyType(energyType);
            ((Producer) player).setMaxDistributors(maxDistributors);
            ((Producer) player).setPrice(price);
            ((Producer) player).setEnergy(energy);
            producerDB.getProducersList().add((Producer) player);
        }
       for (Distributor distributor : distributorDB.getDistributorsList()) {
           distributor.setProducerDB(producerDB);
       }
    }

    /**
     * aplica schimbarile care se produc in fiecare luna de la input
     * acestea pot fi atat asupra distribuitorilor, cat si asupra
     * setului de consumatori
     * @param i despre a cata luna se vorbeste
     */
    public void changesConsumersDistributors(final int i) {
        JSONObject element = (JSONObject) monthlyUpdates.get(i - 1);
        JSONArray newConsumers = (JSONArray) element.get(Utils.NEW_CONSUMERS);
        JSONArray distributorChanges = (JSONArray) element.get(Utils.DISTRIBUTOR_CHANGES);
        for (Object object : newConsumers) {
            JSONObject jsonObject = (JSONObject) object;
            PlayersFactory playersFactory = PlayersFactory.getINSTANCE();
            Player player = playersFactory.createPlayer(Utils.CONSUMERS);
            Consumer consumer = (Consumer) player;
            long id = (long) jsonObject.get(Utils.ID);
            long initialBudget = (long) jsonObject.get(Utils.INITIAL_BUDGET);
            long monthlyIncome = (long) jsonObject.get(Utils.MONTHLY_INCOME);
            consumer.setId(id);
            consumer.setBudget(initialBudget);
            consumer.setMonthlyIncome(monthlyIncome);
            consumerDB.getConsumersList().add(consumer);
        }
        for (Object object : distributorChanges) {
            JSONObject jsonObject = (JSONObject) object;
            long id = (long) jsonObject.get(Utils.ID);
            long infrastructureCost = (long) jsonObject.get("infrastructureCost");
            Distributor distributor = distributorDB.getDistributorsList().get((int) id);
            distributor.setInfrastructureCost(infrastructureCost);
            distributor.setPrice();
            distributor.setProducerPrice();
        }
    }

    /**
     * aplica schimbarile care se produc in fiecare luna de la input
     * asupra producatorilor
     * @param i despre a cata luna se vorbeste
     */
    public void changesProducers(final int i) {
        producerDB.getObservers().clear();
        JSONObject element = (JSONObject) monthlyUpdates.get(i - 1);
        JSONArray producerChanges = (JSONArray) element.get(Utils.PRODUCER_CHANGES);
        for (Object object : producerChanges) {
            JSONObject jsonObject = (JSONObject) object;
            long id = (long) jsonObject.get(Utils.ID);
            long energy = (long) jsonObject.get(Utils.ENERGY_PER_DISTRIBUTOR);
            Producer producer = producerDB.getProducersList().get((int) id);
            producer.setEnergy(energy);
            producerDB.addAllObservers(producer);
        }
        producerDB.notifyObservers();
    }

    /**
     * efectueaza toate modificarile care apar pe parcursul lunii
     * @param i despre a cata luna este vorba
     * @return daca distribuitorii au dat faliment, caz in care
     * jocul se termina
     */
    public boolean parseRound(final int i) {
        if (distributorDB.allBankrupt()) {
            return false; // nu mai sunt distribuitori pe piata
        }
        if (i > 0) {
            changesConsumersDistributors(i);
        }
        if (i == 0) {  // in runda initiala, distribuitorii isi aleg producatorii la inceput
            for (Distributor distributor : distributorDB.getDistributorsList()) {
                distributor.update();
            }
        }
        for (Distributor distributor : distributorDB.getDistributorsList()) {
            distributor.setNumberOfClients(distributor.getClients().size());
            distributor.setPrice();
        }
        for (Distributor distributor : distributorDB.getDistributorsList()) {
            distributor.getClients().removeIf(cons -> cons.getRemainedContractMonths() == 0);
        }

        for (Consumer consumer : consumerDB.getConsumersList()) {
            if (!consumer.isBankrupt() && consumer.getRemainedContractMonths() == 0) {
                consumer.findDistributor(distributorDB);
            }
        }
        for (Distributor distributor : distributorDB.getDistributorsList()) {
            distributor.setNumberOfClients(distributor.getClients().size());
            distributor.setProducerPrice();
        }
        for (Consumer consumer : consumerDB.getConsumersList()) {
            consumer.goThroughRound(distributorDB);
        }
        for (Distributor distributor : distributorDB.getDistributorsList()) {
            distributor.goThroughRound();
        }
        if (i > 0) {
            changesProducers(i);
            for (Producer producer : producerDB.getProducersList()) {
                MonthlyStats monthlyStats = new MonthlyStats();
                monthlyStats.setMonth(i);
                for (Distributor distributor : producer.getDistributorDB().getDistributorsList()) {
                    monthlyStats.getIds().add((int) distributor.getId());
                }
                Collections.sort(monthlyStats.getIds());
                producer.getMonthlyStats().add(monthlyStats);
            }
        }
        return true;
    }

    /**
     * elibereaza memoria alocata la testul curent
     * fara aceasta metoda, informatia s-ar pastra
     * pe tot parcursul rularii testelor
     */
    public void clear() {
        jsonParser = null;
        numberOfTurns = 0;
        initialData = null;
        monthlyUpdates = null;
        consumerDB = null;
        distributorDB = null;
        producerDB = null;
    }

}
