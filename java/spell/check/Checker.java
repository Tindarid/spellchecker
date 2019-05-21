package spell.check;

import java.util.List;

public interface Checker {
    boolean check(String word);
    List<String> candidates(String word);
    List<String> formatSorted(String word);
    List<String> sorted(String word);
    String probably(String word);
}
