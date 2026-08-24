import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
            "Java is a popular programming language.",
            "Java is object-oriented and powerful.",
            "Learning Java programming is fun and useful."
        };

        Map<String, Integer> wordCountMap = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        for (String sentence : sentences) {
            String cleaned = sentence.replaceAll("[,.]", "").toLowerCase();
            String[] words = cleaned.split("\\s+");

            for (String word : words) {
                if (word.isEmpty()) {
                    continue;
                }
                uniqueWords.add(word);
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("=== 1. 所有不重複單字 (Set) ===");
        System.out.println(uniqueWords);

        System.out.println("\n=== 2. 單字出現次數統計 (Map) ===");
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            System.out.println(String.format("單字: %-15s | 次數: %d", entry.getKey(), entry.getValue()));
        }

        System.out.println("\n=== 3. 出現至少兩次的單字 ===");
        List<String> frequentWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            if (entry.getValue() >= 2) {
                frequentWords.add(entry.getKey());
            }
        }
        System.out.println(frequentWords);
    }
}