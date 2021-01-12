package data;

import database.ConsumerDB;
import network.Consumer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class WriterConsumer implements Writer {
    private ConsumerDB consumerDB;

    public WriterConsumer(ConsumerDB consumerDB) {
        this.consumerDB = consumerDB;
    }

    public ConsumerDB getConsumerDB() {
        return consumerDB;
    }

    public void setConsumerDB(ConsumerDB consumerDB) {
        this.consumerDB = consumerDB;
    }

    @Override
    public JSONArray writeJSON(final String args) {
        JSONArray consumersArray = new JSONArray();
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
        return consumersArray;
    }
}
