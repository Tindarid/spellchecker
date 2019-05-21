package spell.check;

import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

public class SpellChecker implements Checker {
    private final Map<String, Integer> dict;
    private final String letters;
    private final Integer REDUCE = 10;

    public SpellChecker(final File dictionary, final String languageLetters) throws IOException {
        dict = new HashMap<>();
        letters = languageLetters;
        try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(dictionary))))) {
            while (in.hasNext()) {
                String word = in.next();
                Integer count = in.nextInt();
                dict.put(word, count);
            }
        }
    }

    public boolean check(final String word) {
        return dict.containsKey(word);
    }

    private List<Map.Entry<String, Integer>> sortHelper(final String word) {
        final List<String> candidates = candidates(word);
        final List<Map.Entry<String, Integer>> result = new ArrayList<>();
        for (String candidate : candidates) {
            int count = dict.get(candidate);
            result.add(new AbstractMap.SimpleEntry<String, Integer>(candidate, count));
        }
        Collections.sort(result, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });
        return result;
    }

    public List<String> formatSorted(final String word) {
        final List<Map.Entry<String, Integer>> candidates = sortHelper(word);
        final List<String> result = new ArrayList<>();
        int sum = 0;
        for (Map.Entry<String, Integer> entry : candidates) {
            sum += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry : candidates) {
            String candidate = String.format("%5.2f%% %s", 100.0 * entry.getValue() / sum, entry.getKey());
            result.add(candidate);
        }
        return result;
    }

    public List<String> sorted(final String word) {
        final List<Map.Entry<String, Integer>> candidates = sortHelper(word);
        final List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : candidates) {
            String candidate = entry.getKey();
            result.add(candidate);
        }
        return result;
    }

    public String probably(final String word) {
        String ans = null;
        int count = 0;
        for (String candidate : candidates(word)) {
            int temp = dict.get(candidate);
            if (temp > count) {
                ans = candidate;
                count = temp;
            }
        }
        return ans;
    }

    public List<String> candidates(final String word) {
        final List<String> attempt = edit(word);
        final List<String> result = filter(attempt);
        if (result.isEmpty()) {
            for (int i = 0; i < attempt.size(); ++i) {
                result.addAll(edit(attempt.get(i)));
            }
        }
        return filter(result);
    }

    private List<String> filter(final List<String> list) {
        final List<String> temp = list.stream().distinct().filter(w -> check(w)).collect(Collectors.toList());
        return temp.subList(0, Math.min(REDUCE, temp.size()));
    }

    private List<String> edit(final String word) {
        List<String> result = new ArrayList<>();
        delete(word, result);
        insert(word, result);
        replace(word, result);
        transpose(word, result);
        return result;
    }

    private void delete(final String word, final List<String> result) {
        final StringBuilder builder = new StringBuilder(word);
        for (int i = 0; i < word.length(); ++i) {
            builder.deleteCharAt(i);
            result.add(builder.toString());
            builder.insert(i, word.charAt(i));
        }
    }

    private void replace(final String word, final List<String> result) {
        final StringBuilder builder = new StringBuilder(word);
        for (int i = 0; i < word.length(); ++i) {
            for (int j = 0; j < letters.length(); ++j) {
                builder.setCharAt(i, letters.charAt(j));
                result.add(builder.toString());
                builder.setCharAt(i, word.charAt(i));
            }
        }
    }

    private void insert(final String word, final List<String> result) {
        final StringBuilder builder = new StringBuilder(word);
        for (int i = 0; i < word.length() + 1; ++i) {
            for (int j = 0; j < letters.length(); ++j) {
                builder.insert(i, letters.charAt(j));
                result.add(builder.toString());
                builder.deleteCharAt(i);
            }
        }
    }

    private void transpose(final String word, final List<String> result) {
        final StringBuilder builder = new StringBuilder(word);
        for (int i = 0; i < word.length() - 1; ++i) {
            builder.setCharAt(i, word.charAt(i + 1));
            builder.setCharAt(i + 1, word.charAt(i));
            result.add(builder.toString());
            builder.setCharAt(i, word.charAt(i));
            builder.setCharAt(i + 1, word.charAt(i + 1));
        }
    }
}
