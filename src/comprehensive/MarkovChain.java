package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MarkovChain {
    private class Children {
        public int totalCount;
        public PriorityQueue<Child> childrenQueue;
        private List<Child> rawChildren; // Store raw data

        // Add these fields
        private String[] weightedArray = null;

        public Children(List<Child> children) {
            this.totalCount = 0;
            this.rawChildren = new ArrayList<>(children);
            for (Child c : children) {
                totalCount += c.count;
            }
        }

        public PriorityQueue<Child> getQueue() {
            if (childrenQueue == null) {
                childrenQueue = new PriorityQueue<>(rawChildren);
            }
            return childrenQueue;
        }

        // Generate an array where each word appears proportional to its count
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

    private class Child implements Comparable<Child> {
        public String word;
        public int count;

        public Child(String word, int count) {
            this.word = word;
            this.count = count;
        }

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

    public MarkovChain(String trainingFile) {
        wordFollowers = new HashMap<>();
        allwords = new HashMap<>();
        getInput(trainingFile);
        training();
    }

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
     *
     * O(k)
     *
     * @param seed
     * @param k
     * @return
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
     * O(k)
     *
     * @param seed
     * @param k
     * @return
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
     * O(k)
     *
     * @param seed
     * @param k
     * @return
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
