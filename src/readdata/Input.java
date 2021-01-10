package readdata;

import database.ConsumerDB;
import database.DistributorDB;
import network.Consumer;
import network.Distributor;
import network.Player;
import network.PlayersFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class Input {
    private JSONParser jsonParser;
    private static final Input INSTANCE = new Input();
    private long numberOfTurns;
    private JSONObject initialData;
    private JSONArray monthlyUpdates;
    private ConsumerDB consumerDB;
    private DistributorDB distributorDB;

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

    private Input() {
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
    }
    public static Input getInstance() {
        return INSTANCE;
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
            PlayersFactory playersFactory = new PlayersFactory();
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
            PlayersFactory playersFactory = new PlayersFactory();
            Player player = playersFactory.createPlayer(Utils.DISTRIBUTORS);
            long id = (long) jsonObject.get(Utils.ID);
            long initialBudget = (long) jsonObject.get(Utils.INITIAL_BUDGET);
            long contractLength = (long) jsonObject.get(Utils.CONTRACT_LENGTH);
            long infrastructureCost = (long) jsonObject.get(Utils.INITIAL_INFRASTRUCTURE_COST);
            long productionCost = (long) jsonObject.get(Utils.INITIAL_PRODUCTION_COST);
            player.setId(id);
            player.setBudget(initialBudget);
            ((Distributor) player).setContractLength(contractLength);
            ((Distributor) player).setInfrastructureCost(infrastructureCost);
            ((Distributor) player).setProductionCost(productionCost);
            distributorDB.getDistributorsList().add((Distributor) player);
        }
    }

    /**
     * aplica schimbarile care se produc in fiecare luna de la input
     * acestea pot fi atat asupra distribuitorilor, cat si asupra
     * setului de consumatori
     * @param i despre a cata luna se vorbeste
     */
    public void applyChanges(final int i) {
        JSONObject element = (JSONObject) monthlyUpdates.get(i - 1);
        JSONArray newConsumers = (JSONArray) element.get(Utils.NEW_CONSUMERS);
        JSONArray costsChanges = (JSONArray) element.get(Utils.COSTS_CHANGES);
        for (Object object : newConsumers) {
            JSONObject jsonObject = (JSONObject) object;
            PlayersFactory playersFactory = new PlayersFactory();
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
        for (Object object : costsChanges) {
            JSONObject jsonObject = (JSONObject) object;
            long id = (long) jsonObject.get(Utils.ID);
            long infrastructureCost = (long) jsonObject.get("infrastructureCost");
            long productionCost = (long) jsonObject.get("productionCost");
            Distributor distributor = distributorDB.getDistributorsList().get((int) id);
            distributor.setInfrastructureCost(infrastructureCost);
            distributor.setProductionCost(productionCost);
            distributor.setPrice();
            distributor.setProducerPrice();
        }
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
            applyChanges(i);
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
        return true;
    }

    /**
     * printeaza rezultatul sub forma unui fisier JSON
     * @param args fisierul de iesire sub forma de String
     * @throws IOException trateaza cazurile de nereusita
     */
    public void printJSON(final String args) throws IOException {
        FileWriter fileWriter = new FileWriter(args);
        JSONObject output = new JSONObject();
        JSONArray consumersArray = new JSONArray();
        JSONArray distributorArray = new JSONArray();
        for (Consumer consumer : consumerDB.getConsumersList()) {
            JSONObject jsonObject = new JSONObject();
            //noinspection unchecked
            jsonObject.put("id", consumer.getId());
            //noinspection unchecked
            jsonObject.put("isBankrupt", consumer.isBankrupt());
            //noinspection unchecked
            jsonObject.put("budget", consumer.getBudget());
            //noinspection unchecked
            consumersArray.add(jsonObject);
        }
        for (Distributor distributor : distributorDB.getDistributorsList()) {
            JSONObject jsonObject = new JSONObject();
            //noinspection unchecked
            jsonObject.put("id", distributor.getId());
            //noinspection unchecked
            jsonObject.put("budget", distributor.getBudget());
            //noinspection unchecked
            jsonObject.put("isBankrupt", distributor.isBankrupt());
            JSONArray contracts = new JSONArray();
            for (Consumer consumer : distributor.getClients()) {
                JSONObject object = new JSONObject();
                //noinspection unchecked
                object.put("consumerId", consumer.getId());
                //noinspection unchecked
                object.put("price", consumer.getPrice());
                //noinspection unchecked
                object.put("remainedContractMonths", consumer.getRemainedContractMonths());
                //noinspection unchecked
                contracts.add(object);
            }
            //noinspection unchecked
            jsonObject.put("contracts", contracts);
            //noinspection unchecked
            distributorArray.add(jsonObject);
        }
        //noinspection unchecked
        output.put("consumers", consumersArray);
        //noinspection unchecked
        output.put("distributors", distributorArray);
        fileWriter.write(output.toJSONString() + "\n\n");
        fileWriter.flush();
        fileWriter.close();
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
    }

}
