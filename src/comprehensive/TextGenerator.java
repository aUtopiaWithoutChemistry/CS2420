package comprehensive;

/**
 * The class that contains main method and a test method for MarkovChain class.
 * Based on the input arguments from terminal environment to make new MarkovChain
 * object, and print the generated text.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-16
 */
public class TextGenerator {
    /**
     * Main method, read from the terminal arguments and use them to
     * build a MarkovChain, and print the generated String.
     *
     * @param args input terminal arguments
     */
    public static void main(String[] args) {
        MarkovChain artificialIdiot = new MarkovChain(args[0]);
        System.out.println(artificialIdiot.generate(args[1], Integer.parseInt(args[2]), args[3]));
    }

    /**
     * A test method, to simulate getting four terminal arguments and
     * generate String.
     *
     * @param filePath the file path
     * @param seed the seed word
     * @param wordNumber how many word should generate
     * @param outputType the output type.
     */
    public static void test(String filePath, String seed, int wordNumber, String outputType) {
        MarkovChain artificialIdiot = new MarkovChain(filePath);
        System.out.println(artificialIdiot.generate(seed, wordNumber, outputType));
    }
}
