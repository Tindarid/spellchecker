import java.io.File;

import spell.dict.Dictionary;
import spell.dict.DictionaryException;

import spell.lang.Language;

import spell.check.Checker;
import spell.check.SpellChecker;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void createDictionary(String letters, String booksDir, String dest) {
        try {
            int books = Dictionary.create(new File(booksDir), new File(dest), letters);
            System.out.println(books + " books resolved succesfully");
        } catch (DictionaryException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void spellcheck(String letters, String dictionary) {
        try (Scanner in = new Scanner(System.in)) {
            Checker checker = new SpellChecker(new File(dictionary), letters);
            System.out.println("Type word and it would be checked (EOF to exit)");
            System.out.println("----------------------------------------");
            while (in.hasNext()) {
                String word = in.next().toLowerCase();
                if (checker.check(word)) {
                    System.out.println("All is good");
                } else {
                    List<String> candidates = checker.formatSorted(word);
                    if (candidates.isEmpty()) {
                        System.out.println("No candidates found");
                    }
                    System.out.println("Probably you mean:");
                    for (String candidate : candidates) {
                        System.out.println(candidate);
                    }
                }
                System.out.println("----------------------------------------");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void printUsage() {
        System.out.println("Usage: java Main [language] [dict or check] [dict] [books dir (if dict mode)]");
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0 || "--help".equals(args[0]) || args.length < 3) {
            printUsage();
            return;
        }
        String language = args[0];
        String letters = Language.getLetters(language.toUpperCase());
        if (letters == null) {
            System.out.println("Language is not supported");
            return;
        }
        String mode = args[1];
        String dictDir = args[2];
        if (args.length == 3 && "check".equals(mode) && dictDir != null) {
            spellcheck(letters, dictDir);
        } else if (args.length == 4 && "dict".equals(mode) && dictDir != null && args[3] != null) {
            createDictionary(letters, args[3], dictDir);
        } else {
            printUsage();
        }
    }
}
