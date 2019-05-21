MAIN_SOURCES=java/Main.java java/Test.java 
CHECK_SOURCES=java/spell/check/Checker.java java/spell/check/SpellChecker.java
DICT_SOURCES=java/spell/dict/Dictionary.java java/spell/dict/DictionaryException.java
LANG_SOURCES=java/spell/lang/Language.java
SOURCES=$(MAIN_SOURCES) $(CHECK_SOURCES) $(DICT_SOURCES) $(LANG_SOURCES)
TEST=java/Test.class
MAIN=java/Main.class
RU_DICT_DIR=dict/ru.txt
EN_DICT_DIR=dict/en.txt
TESTS_DIR=tests/
RU_BOOKS_DIR=books/ru/
EN_BOOKS_DIR=books/en/


all: $(SOURCES)
	javac -cp java java/Main.java
	javac -cp java java/Test.java

test: $(TEST)
	java -cp java Test $(EN_DICT_DIR) $(TESTS_DIR)

russian: $(MAIN)
	java -cp java Main ru check $(RU_DICT_DIR)

russianDict: $(MAIN)
	java -cp java Main ru dict $(RU_DICT_DIR) $(RU_BOOKS_DIR)

english: $(MAIN)
	java -cp java Main en check $(EN_DICT_DIR)

englishDict: $(MAIN)
	java -cp java Main en dict $(EN_DICT_DIR) $(EN_BOOKS_DIR)

clean:
	find . -name '*.class' -delete
