package spell.lang;

public class Language {
    public static String getLetters(String language) {
        if (language.equals("EN")) {
            return "abcdefghijklmnopqrstuvwxyz";
        } else if (language.equals("RU")) {
            return "абвгдежзийклмнопрстуфхцчшщъыьэюя";
        } else {
            return null;
        }
    }
}
