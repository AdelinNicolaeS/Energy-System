package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public final class Output {
    private WriterConsumer writerConsumer;
    private WriterDistributor writerDistributor;
    private WriterProducer writerProducer;

    public Output(WriterConsumer writerConsumer,
                  WriterDistributor writerDistributor,
                  WriterProducer writerProducer) {
        this.writerConsumer = writerConsumer;
        this.writerDistributor = writerDistributor;
        this.writerProducer = writerProducer;
    }

    public WriterConsumer getWriterConsumer() {
        return writerConsumer;
    }

    public void setWriterConsumer(WriterConsumer writerConsumer) {
        this.writerConsumer = writerConsumer;
    }

    public WriterDistributor getWriterDistributor() {
        return writerDistributor;
    }

    public void setWriterDistributor(WriterDistributor writerDistributor) {
        this.writerDistributor = writerDistributor;
    }

    public WriterProducer getWriterProducer() {
        return writerProducer;
    }

    public void setWriterProducer(WriterProducer writerProducer) {
        this.writerProducer = writerProducer;
    }

    /**
     * printeaza rezultatul sub forma unui fisier JSON
     * @param args fisierul de iesire sub forma de String
     * @throws IOException trateaza cazurile de nereusita
     */
    public void printJSON(final String args) throws IOException {
        FileWriter fileWriter = new FileWriter(args);
        JSONObject output = new JSONObject();
        JSONArray consumersArray = writerConsumer.writeJSON(args);
        JSONArray distributorArray = writerDistributor.writeJSON(args);
        JSONArray producerArray = writerProducer.writeJSON(args);
        //noinspection unchecked
        output.put("consumers", consumersArray);
        //noinspection unchecked
        output.put("distributors", distributorArray);
        //noinspection unchecked
        output.put("energyProducers", producerArray);
        fileWriter.write(output.toJSONString() + "\n\n");
        fileWriter.flush();
        fileWriter.close();
        /*FileWriter fileWriter = new FileWriter(args);
        JSONObject output = new JSONObject();
        JSONArray consumersArray = new JSONArray();
        JSONArray distributorArray = new JSONArray();
        JSONArray producerArray = new JSONArray();
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
            jsonObject.put("energyNeededKW", distributor.getEnergy());
            //noinspection unchecked
            jsonObject.put("contractCost", distributor.getPrice());
            //noinspection unchecked
            jsonObject.put("budget", distributor.getBudget());
            //noinspection unchecked
            jsonObject.put("producerStrategy", distributor.getProducerStrategy().label);
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
        //noinspection unchecked
        output.put("consumers", consumersArray);
        //noinspection unchecked
        output.put("distributors", distributorArray);
        //noinspection unchecked
        output.put("energyProducers", producerArray);
        fileWriter.write(output.toJSONString() + "\n\n");
        fileWriter.flush();
        fileWriter.close();
         */
    }

}
