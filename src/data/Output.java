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
    }

}
