package data;

import database.ProducerDB;
import network.MonthlyStats;
import network.Producer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class WriterProducer implements Writer {
    private ProducerDB producerDB;

    public WriterProducer(ProducerDB producerDB) {
        this.producerDB = producerDB;
    }

    public ProducerDB getProducerDB() {
        return producerDB;
    }

    public void setProducerDB(ProducerDB producerDB) {
        this.producerDB = producerDB;
    }

    @Override
    public JSONArray writeJSON(final String args) {
        JSONArray producerArray = new JSONArray();
        for (Producer producer : producerDB.getProducersList()) {
            JSONObject jsonObject = new JSONObject();
            //noinspection unchecked
            jsonObject.put("id", producer.getId());
            //noinspection unchecked
            jsonObject.put("maxDistributors", producer.getMaxDistributors());
            //noinspection unchecked
            jsonObject.put("priceKW", producer.getPrice());
            //noinspection unchecked
            jsonObject.put("energyType", producer.getEnergyType().getLabel());
            //noinspection unchecked
            jsonObject.put("energyPerDistributor", producer.getEnergy());
            JSONArray stats = new JSONArray();
            for (MonthlyStats monthlyStats : producer.getMonthlyStats()) {
                JSONObject object = new JSONObject();
                //noinspection unchecked
                object.put("month", monthlyStats.getMonth());
                //noinspection unchecked
                object.put("distributorsIds", monthlyStats.getIds());
                //noinspection unchecked
                stats.add(object);
            }
            //noinspection unchecked
            jsonObject.put("monthlyStats", stats);
            //noinspection unchecked
            producerArray.add(jsonObject);
        }
        return producerArray;
    }
}
