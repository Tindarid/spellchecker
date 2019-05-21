# Spellchecker
Spellchecker (English and Russian) based on Damerau–Levenshtein distance

Compile: make all

Run tests: make test

Russian spellchecking: make russian

English spellchecking: make english

Or you can use it through java Main (--help for usage)

English books are from https://www.gutenberg.org/

Test cases are from http://ota.ox.ac.uk/headers/0643.xml (adapted)

Inspired by http://norvig.com/spell-correct.html

=============TEST 1=============
Count: 44
First is true: 56.82%
True is among: 75.00%
Not known words: 13.64%
Time: 0.61s
=============TEST 2=============
Count: 170
First is true: 48.82%
True is among: 71.76%
Not known words: 7.06%
Time: 1.06s
=============TEST 3=============
Count: 447
First is true: 48.32%
True is among: 63.09%
Not known words: 2.68%
Time: 5.10s
=============TEST 4=============
Count: 343
First is true: 51.90%
True is among: 69.68%
Not known words: 14.58%
Time: 2.96s
=============TEST 5=============
Count: 493
First is true: 39.35%
True is among: 59.43%
Not known words: 4.06%
Time: 3.13s
=============TEST 6=============
Count: 788
First is true: 67.64%
True is among: 75.13%
Not known words: 22.84%
Time: 10.66s
=============TEST 7=============
Count: 543
First is true: 66.11%
True is among: 76.43%
Not known words: 15.84%
Time: 6.73s
=============TEST 8=============
Count: 881
First is true: 66.06%
True is among: 77.19%
Not known words: 18.50%
Time: 9.86s
=============TEST 9=============
Count: 382
First is true: 69.37%
True is among: 76.44%
Not known words: 22.77%
Time: 5.18s
=============TEST 10=============
Count: 4951
First is true: 59.83%
True is among: 73.14%
Not known words: 11.01%
Time: 48.09s
=============TESTS=============
Count: 9042
First is true: 59.69%
True is among: 72.65%
Not known words: 12.84%
Time: 93.40s

