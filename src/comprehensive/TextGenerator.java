package comprehensive;

/**
 * @author Zifan Zuo
 * @version f(n) = 2025-n-n^2, n = 4
 */
public class TextGenerator {
    public static void main(String[] args) {
        MarkovChain artificialIdiot = new MarkovChain(args[0]);
        System.out.println(artificialIdiot.generate(args[1], Integer.parseInt(args[2]), args[3]));
    }

    public static void test(String filePath, String seed, int wordNumber, String outputType) {
        MarkovChain artificialIdiot = new MarkovChain(filePath);
        System.out.println(artificialIdiot.generate(seed, wordNumber, outputType));
    }
}
