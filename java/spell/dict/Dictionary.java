package spell.dict;

import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

public class Dictionary {
    public static int create(File bookDir, File dest, String langLetters) throws DictionaryException {
        Map<String, Integer> dict = new TreeMap<>();
        File[] files = bookDir.listFiles();
        if (files == null) {
            throw new DictionaryException(bookDir.toString() + " is not a directory");
        }
        if (files.length == 0) {
            throw new DictionaryException("No books found in " + bookDir.toString());
        }
        int books = 0;
        for (File file : files) {
            try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))))) {
                while (in.hasNext()) {
                    String next = in.next().toLowerCase();
                    if (next.endsWith(",") || next.endsWith(".") || next.endsWith(":")) {
                        next = next.substring(0, next.length() - 1);
                    }
                    if (next.isEmpty()) {
                        continue;
                    }
                    if (next.chars().allMatch(Character::isLetter)) {
                        Integer count = dict.get(next);
                        if (count != null) {
                            dict.put(next, count + 1);
                        } else {
                            if (next.chars().allMatch(c -> langLetters.indexOf(c) != -1)) {
                                dict.put(next, 1);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new DictionaryException(e);
            }
            books++;
        }
        if (books == 0) {
            throw new DictionaryException("None books have been loaded from " + bookDir.toString());
        }
        try (PrintWriter out = new PrintWriter(dest)) {
            for (Map.Entry<String, Integer> entry : dict.entrySet()) {
                out.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            throw new DictionaryException(e);
        }
        return books;
    }
}
