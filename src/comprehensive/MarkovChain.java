package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MarkovChain {
    private class Children {
        public int totalCount;
        public TreeMap<Integer, ChildWeighted> childrenMap;
        public PriorityQueue<Child> childrenQueue;

        public Children() {
            this.totalCount = 0;
            childrenMap = new TreeMap<>();
            childrenQueue = new PriorityQueue<>();
        }

        public void add(Child child) {
            childrenQueue.add(child);
            totalCount += child.count;
        }

        public void generateTree() {
            int weight = 0;
            childrenMap.clear();

            for (Child child : childrenQueue) {
                ChildWeighted cw = new ChildWeighted(child, weight);
                childrenMap.put(weight, cw);
                weight += child.count;
            }
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

    private class ChildWeighted extends Child {
        public int upper, lower;

        public ChildWeighted(Child child, int lastUpper) {
            super(child.word, child.count);
            this.lower = lastUpper;
            this.upper = lower + count - 1;
        }

        public int compareTo(ChildWeighted c) {
            if (this.lower >= c.lower && this.upper <= c.upper) {
                return 0;
            }
            return upper - c.upper;
        }
    }

    private HashMap<String, Children> allwords;
    private List<String> inputEssay;

    public MarkovChain(String trainingFile) {
        inputEssay = new ArrayList<>();
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
     * @param seed
     * @param k
     * @return
     */
    private String probable(String seed, int k) {
        String target = seed.toLowerCase(); // Ensure seed is lowercase to match our training data
        if (!allwords.containsKey(target)) {
            return "";
        }
        // Create a copy of the queue to avoid modifying the original
        PriorityQueue<Child> tempQueue = new PriorityQueue<>(allwords.get(target).childrenQueue);
        // Extract k most probable words
        List<String> probableWords = new ArrayList<>();
        for (int i = 0; i < k && !tempQueue.isEmpty(); i++) {
            probableWords.add(tempQueue.poll().word);
        }
        return String.join(" ", probableWords);
    }

    private String deterministic(String seed, int k) {
        String target = seed;
        StringBuilder returnString = new StringBuilder();
        returnString.append(seed);

        for (int i = 0; i < k - 1; i++) {
            if (allwords.get(target) == null || allwords.get(target).childrenQueue.isEmpty()) {
                target = seed;
            } else {
                target = allwords.get(target).childrenQueue.peek().word;
            }
            returnString.append(" ").append(target);
            target = target;
        }
        return returnString.toString();
    }

    private String random(String seed, int k) {
        Random rng = new Random();
        String target = seed;
        StringBuilder returnString = new StringBuilder();
        returnString.append(seed);

        for (int i = 0; i < k - 1; i++) {
            Children c = allwords.get(target);
            if (c == null || c.childrenMap.isEmpty()) {
                target = seed;
            } else {
                int rdnum = rng.nextInt(c.totalCount);
                // Use floorEntry to find the appropriate weighted child
                Map.Entry<Integer, ChildWeighted> entry = c.childrenMap.floorEntry(rdnum);
                if (entry != null) {
                    target = entry.getValue().word;
                } else {
                    target = seed;
                }
            }
            returnString.append(" ").append(target);
        }
        return returnString.toString();
    }

    private void getInput(String trainingFile) {
        File file = new File(trainingFile);
        Scanner sc;
        try {
            sc = new Scanner(file);
            while (sc.hasNext()) {
                // Get the next token
                String token = sc.next();
                // Replace any punctuation other than _ and ' with spaces
                token = token.replaceAll("[^a-zA-Z0-9_']+", " ");
                // Split the token into words using whitespace
                String[] words = token.split("\\s+");
                // Add each non-empty word to the input essay
                for (String word : words) {
                    if (!word.isEmpty()) {
                        inputEssay.add(word.toLowerCase());
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    private void training() {
        HashMap<String, HashMap<String, Integer>> wordFollowers = new HashMap<>();

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

        // Create Children objects from word followers
        for (String word : wordFollowers.keySet()) {
            HashMap<String, Integer> followers = wordFollowers.get(word);
            Children children = new Children();

            for (String follower : followers.keySet()) {
                int count = followers.get(follower);
                Child child = new Child(follower, count);
                children.add(child);
            }

            allwords.put(word, children);
        }

        // generate treeSet in every Children object
        for (Children c : allwords.values()) {
            c.generateTree();
        }
    }
}
