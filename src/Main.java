import readdata.Input;

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
        Input input = Input.getInstance();
        input.init();
        input.parseInput(new FileReader(args[0]));
        input.obtainConsumers();
        input.obtainDistributors();
        input.obtainProducers();
        for (int i = 0; i <= input.getNumberOfTurns(); i++) {
            boolean ok = input.parseRound(i);
            if (!ok) {
                break;
            }
        }
        input.printJSON(args[1]);
        input.clear();
    }
}
