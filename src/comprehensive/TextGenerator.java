package comprehensive;

public class TextGenerator {
    public static void main(String[] args) {
        String filePath = args[0];
        String seed = args[1];
        int wordNumber = Integer.parseInt(args[2]);
        String outputType = args[3];

        MarkovChain artificialIdiot = new MarkovChain(filePath);
        System.out.println(artificialIdiot.generate(seed, wordNumber, outputType));

//        test("src/comprehensive/pg1998.txt", "my", 1000, "random");
    }

    public static void test(String filePath, String seed, int wordNumber, String outputType) {
        MarkovChain artificialIdiot = new MarkovChain(filePath);
        System.out.println(artificialIdiot.generate(seed, wordNumber, outputType));
    }
}
