package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class that implement the idea of MarkovChain, which can read from a source
 * file and generate output based on given input.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-16
 */
public class MarkovChain {
    /**
     * A customized class to store Children, contains a PriorityQueue to
     * store all child based on customized compareTo method, and a basic
     * array that store duplicate String based on their frequency.
     */
    private class Children {
        public int totalCount;
        public PriorityQueue<Child> childrenQueue;
        private List<Child> rawChildren;
        private String[] weightedArray;

        /**
         * The constructor of Children.
         *
         * @param children a list of child
         */
        public Children(List<Child> children) {
            this.totalCount = 0;
            this.rawChildren = new ArrayList<>(children);
            for (Child c : children) {
                totalCount += c.count;
            }
        }

        /**
         * Generate a queue where the word that have the highest possibility to
         * shows up stick at the top of queue.
         *
         * @return a queue of child which the highest possibility at the top.
         */
        public PriorityQueue<Child> getQueue() {
            if (childrenQueue == null) {
                childrenQueue = new PriorityQueue<>(rawChildren);
            }
            return childrenQueue;
        }

        /**
         * Generate an array where each word appears proportional to its count.
         *
         * @return an array of String that represent the correct possibility of
         *         each word that shows up.
         */
        public String[] getWeightedArray() {
            if (weightedArray == null) {
                weightedArray = new String[totalCount];
                int index = 0;

                for (Child child : rawChildren) {
                    for (int i = 0; i < child.count; i++) {
                        weightedArray[index++] = child.word;
                    }
                }
            }
            return weightedArray;
        }
    }

    /**
     * A customized class to store Child, which contains a String,
     * and its count as an integer.
     */
    private class Child implements Comparable<Child> {
        public String word;
        public int count;

        /**
         * Constructor for Child class.
         *
         * @param word the word store in this child.
         * @param count the count of this word.
         */
        public Child(String word, int count) {
            this.word = word;
            this.count = count;
        }

        /**
         * Customized compareTo method, sort the data from biggest to the smallest
         * based on count, if there is a tie, then use lexicographical order to break
         * the tie.
         *
         * @param c the object to be compared.
         * @return < 0 if this is smaller, > 0 if this is bigger, = 0 if equals.
         */
        @Override
        public int compareTo(Child c) {
            int diff = c.count - this.count;
            if (diff != 0) {
                return diff;
            } else {
                return this.word.compareTo(c.word);
            }
        }
    }

    private HashMap<String, Children> allwords;
    private HashMap<String, HashMap<String, Integer>> wordFollowers;

    /**
     * The constructor of MarkovChain class, call getInput to store the passage
     * from the outer source textfile into our data structure. And call training
     * to prepare the data for further text generation task.
     *
     * @param trainingFile the path of input text file.
     */
    public MarkovChain(String trainingFile) {
        wordFollowers = new HashMap<>();
        allwords = new HashMap<>();
        getInput(trainingFile);
        training();
    }

    /**
     * Based on given seed, length k, and type, to call the corresponding
     * private helper method generate a String.
     *
     * @param seed the initial word.
     * @param k the length of generating paragraph.
     * @param type which type of output we need.
     * @return a String start with seed and length of k.
     */
    public String generate(String seed, int k, String type) {
        return switch (type) {
            case "probable" -> probable(seed, k);
            case "deterministic" -> deterministic(seed, k);
            case "random" -> random(seed, k);
            default -> null;
        };
    }

    /**
     * The output will be the k most probable words that could come after the seed word.
     * The words must be output in descending order from most probable to least. Ties
     * for probability will be broken using the lexicographical ordering of the words.
     * O(k)
     *
     * @param seed the initial word.
     * @param k the length of generating paragraph.
     * @return a String of kth most possible output.
     */
    private String probable(String seed, int k) {
        String target = seed.toLowerCase();
        if (!allwords.containsKey(target)) {
            return "";
        }
        PriorityQueue<Child> tempQueue = new PriorityQueue<>(allwords.get(target).getQueue());
        List<String> probableWords = new ArrayList<>();
        for (int i = 0; i < k && !tempQueue.isEmpty(); i++) {
            probableWords.add(tempQueue.poll().word);
        }
        return String.join(" ", probableWords);
    }

    /**
     * A private helper method to deal with give deterministic output.
     * Generate output based on given seed with length k, every
     * next word will select the most possible word.
     * O(k)
     *
     * @param seed the initial word.
     * @param k the length of generating paragraph.
     * @return a String start with seed and length of k.
     */
    private String deterministic(String seed, int k) {
        String target = seed.toLowerCase();
        StringBuilder returnString = new StringBuilder(k * 6);
        returnString.append(seed);

        for (int i = 0; i < k - 1; i++) {
            Children c = allwords.get(target);
            if (allwords.get(target) == null || c.getQueue().isEmpty()) {
                target = seed;
            } else {
                target = c.getQueue().peek().word;
            }
            returnString.append(" ").append(target);
        }
        return returnString.toString();
    }

    /**
     * A private helper method to deal with give random output.
     * Generate random output based on given seed with length k.
     * O(k)
     *
     * @param seed the initial word.
     * @param k the length of generating paragraph.
     * @return a String start with seed and have length of k.
     */
    private String random(String seed, int k) {
        Random rng = new Random();
        String target = seed.toLowerCase();
        StringBuilder returnString = new StringBuilder(k * 6);
        returnString.append(seed);

        for (int i = 0; i < k - 1; i++) {
            Children c = allwords.get(target);
            if (c == null || c.getWeightedArray().length == 0) {
                target = seed;
            } else {
                // O(1) random selection from array
                String[] arr = c.getWeightedArray();
                target = arr[rng.nextInt(arr.length)];
            }
            returnString.append(" ").append(target);
        }
        return returnString.toString();
    }

    /**
     * A private helper method to traverse all words from the input file,
     * and store into a List. From that list, generate the HashMap.
     *
     * @param trainingFile the input file path.
     */
    private void getInput(String trainingFile) {
        List<String> inputEssay = new ArrayList<>(1000000);
        try (Scanner sc = new Scanner(new File(trainingFile))) {
            // Process entire file using pattern and delimiters
            sc.useDelimiter("[^a-zA-Z0-9_']+");
            while (sc.hasNext()) {
                String word = sc.next().trim();
                if (!word.isEmpty()) {
                    inputEssay.add(word.toLowerCase());
                }
            }

            // Count followers for each word
            for (int i = 0; i < inputEssay.size() - 1; i++) {
                String current = inputEssay.get(i);
                String next = inputEssay.get(i + 1);

                // Initialize the map for current word if needed
                if (wordFollowers.containsKey(current)) {
                    // Update count for next word
                    HashMap<String, Integer> followers = wordFollowers.get(current);
                    if (followers.containsKey(next)) {
                        followers.put(next, followers.get(next) + 1);
                    } else {
                        followers.put(next, 1);
                    }
                } else {
                    HashMap<String, Integer> followers = new HashMap<>();
                    followers.put(next, 1);
                    wordFollowers.put(current, followers);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * A private helper method to train our model based on input text.
     * Counting the frequency of each word and the word after it. Make
     * Child objects in each Children objects, and store those Children
     * object in HashMap.
     * O(N) - N total count of the input word list.
     */
    private void training() {
        // Create Children objects from word followers
        for (String word : wordFollowers.keySet()) {
            HashMap<String, Integer> followers = wordFollowers.get(word);
            List<Child> childrenList = new ArrayList<>();
            for (String follower : followers.keySet()) {
                childrenList.add(new Child(follower, followers.get(follower)));
            }
            Children children = new Children(childrenList);
            allwords.put(word, children);
        }
    }
}
