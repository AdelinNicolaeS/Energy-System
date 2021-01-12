import data.Output;
import data.Simulator;
import data.WriterConsumer;
import data.WriterDistributor;
import data.WriterProducer;

import java.io.FileReader;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        Simulator simulator = Simulator.getInstance();
        simulator.init();
        simulator.parseInput(new FileReader(args[0]));
        simulator.obtainConsumers();
        simulator.obtainDistributors();
        simulator.obtainProducers();
        for (int i = 0; i <= simulator.getNumberOfTurns(); i++) {
            boolean ok = simulator.parseRound(i);
            if (!ok) {
                break;
            }
        }
        WriterConsumer writerConsumer = new WriterConsumer(simulator.getConsumerDB());
        WriterDistributor writerDistributor = new WriterDistributor(simulator.getDistributorDB());
        WriterProducer writerProducer = new WriterProducer(simulator.getProducerDB());
        Output output = new Output(writerConsumer, writerDistributor, writerProducer);
        output.printJSON(args[1]);
        simulator.clear();
    }
}
