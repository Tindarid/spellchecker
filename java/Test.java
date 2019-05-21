import spell.lang.Language;

import spell.check.Checker;
import spell.check.SpellChecker;

import java.util.List;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

public class Test {
    private static Integer TEST_COUNT = 10;

    public static void main(String[] args) throws IOException {
        String letters = Language.getLetters("EN");
        Checker checker = new SpellChecker(new File("../dict/en.txt"), letters);
        long testsStart = System.currentTimeMillis();
        int allCount = 0;
        int allFirst = 0;
        int allHas = 0;
        int allNot = 0;
        for (int i = 0; i < TEST_COUNT; ++i) {
            long testStart = System.currentTimeMillis();
            File file = new File("../tests/test" + i + ".txt");
            System.out.println("=============TEST " + (i + 1) + "=============");
            int count = 0;
            int first = 0;
            int has = 0;
            int not = 0;
            try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))))) {
                while (in.hasNext()) {
                    final String is = in.next().toLowerCase();
                    final String shouldBe = in.next().toLowerCase();
                    if (checker.check(is) || is.equals(shouldBe)) {
                        continue;
                    }
                    if (!checker.check(shouldBe)) {
                        not++;
                    }
                    count++;
                    final List<String> candidates = checker.sorted(is);
                    if (!candidates.isEmpty()) {
                        if (candidates.get(0).equals(shouldBe)) {
                            first++;
                            has++;
                            continue;
                        }
                        for (String candidate : candidates) {
                            if (candidate.equals(shouldBe)) {
                                has++;
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            print(count, first, has, not, testStart);
            allCount += count;
            allFirst += first;
            allHas += has;
            allNot += not;
        }
        System.out.println("=============TESTS=============");
        print(allCount, allFirst, allHas, allNot, testsStart);
    }

    private static void print(int count, int first, int has, int not, long startTime) {
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Count: %d", count));
        System.out.println(String.format("First is true: %.2f%%", 100.0 * first / count));
        System.out.println(String.format("True is among: %.2f%%", 100.0 * has / count));
        System.out.println(String.format("Not known words: %.2f%%", 100.0 * not / count));
        System.out.println(String.format("Time: %.2fs", (endTime - startTime) / 1000.0));
        System.out.println(String.format("Words / sec: %.2f", count / ((endTime - startTime) / 1000.0)));
    }
}
