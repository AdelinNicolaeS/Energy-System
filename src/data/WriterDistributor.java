package data;

import database.DistributorDB;
import network.Consumer;
import network.Distributor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class WriterDistributor implements Writer {
    private DistributorDB distributorDB;

    public DistributorDB getDistributorDB() {
        return distributorDB;
    }

    public void setDistributorDB(DistributorDB distributorDB) {
        this.distributorDB = distributorDB;
    }

    public WriterDistributor(DistributorDB distributorDB) {
        this.distributorDB = distributorDB;
    }

    @Override
    public JSONArray writeJSON(final String args) {
        JSONArray distributorArray = new JSONArray();
        for (Distributor distributor : distributorDB.getDistributorsList()) {
            JSONObject jsonObject = new JSONObject();
            //noinspection unchecked
            jsonObject.put("id", distributor.getId());
            //noinspection unchecked
            jsonObject.put("energyNeededKW", distributor.getEnergy());
            //noinspection unchecked
            jsonObject.put("contractCost", distributor.getPrice());
            //noinspection unchecked
            jsonObject.put("budget", distributor.getBudget());
            //noinspection unchecked
            jsonObject.put("producerStrategy", distributor.getProducerStrategy().getLabel());
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
        return distributorArray;
    }
}
