JUnidecode - A Unicode to ASCII Java Library
============================================

> If you already know what is Unicode and you are sure that you know that you want to convert it to 7-bit ASCII and of the downside that it has, you can skip the chit-chat and jump to [JUnidecode description](#junidecode) or [download section](#download).

> This page contains Unicode characters. If the font that you are using doesn't contain all the characters you may get a garbled output. In this case try to install and use a font with a good Unicode support like DejaVu. 

tl;dr
-----

Convert Unicode strings to somewhat reasonable ASCII7-only strings. [Download junidecode.jar](#download) or [Install it from Maven Central](https://search.maven.org/artifact/net.gcardone.junidecode/junidecode), then strip diacritics and convert strings:

```
import static net.gcardone.junidecode.Junidecode.*;

// ...

// s = "résumé"
String s = "r\u00E9sum\u00E9";
System.out.print(unidecode(s));
// prints "resume"
```

More details about the what, why, and limitations below.


What is Unicode
---------------

The characters that you are reading right now, i.e. the latin alphabet's characters, are not as common as one may think. Summing up the number of native speakers of the top 20 most spoken langueges of the world it comes up that almost 3100 million people ([source](https://en.wikipedia.org/wiki/List_of_languages_by_number_of_native_speakers)) use a language that doesn't contain even a single latin character; for example Chinese, Hindi, Arabic, Bengali, Russian and so on. Except for English all the languages that use the latin alphabet "enrich" it by using diacritic marks.

Unicode was invented to represent and manipulate all the different characters not included in the traditional 7-bit ASCII encoding. Unicode assigns to each character a unique so called "code point". For example the letter "a" has as code point U+0061, while "Я"'s code point is U+042F.

Unicode's code points are just a standardized way to say: "I mean that letter", but Unicode doesn't say how you should encode the code point. Result is, of course, that there are many different ways to encode Unicode like UTF-8, UTF-7 or UCS2, the most common being probably UTF-8.

For a nice article about what you should know about Unicode as programmer read this article by Joel Spolsky: [The Absolute Minimum Every Software Developer Absolutely, Positively Must Know About Unicode and Character Sets (No Excuses!)](http://www.joelonsoftware.com/articles/Unicode.html).

Java's String implementation internally use UTF-16, but we can get the encoding for many other charsets using the method [getBytes(String charsetName)](http://docs.oracle.com/javase/8/docs/api/java/lang/String.html#getBytes-java.lang.String-). Most important, we can ask: "what is the code point of the character at index x?" ([codePointAt(int index)](http://docs.oracle.com/javase/8/docs/api/java/lang/String.html#codePointAt-int-)). 


How to strip diacritic marks in Java
------------------------------------

Unicode is nice and good. Why should one ever want to strip diacritic marks? There are some situations where it's sensible to do so.

Let's assume you're writing a software for a multinational industry to manage its employees. Since it's a multinational it has employees from all around the world with exotic (invented) names like "Franco Lorè" or "Stjepan Bebić". The management will be very displeased when it will discover that if they look for "Bebic" they won't find Stjepan.

Another example: you're managing a travel agency web site. A customer logs in, looks for travel offers for "cote d'Azur" and then goes away because your web site knows nothing about "cote d'Azur", it just knows "côte d'Azur".

So: you should use Unicode whenever it's possible, but you should also know when "dumb it down".

If you use Java 6 or above you can use the java.text.Normalizer class:

```
String normalized = java.text.Normalizer.normalize(originalstring, java.text.Normalizer.Form.NFD)
String accentsgone = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
```

The first line applies the so called "canonical decomposition", which takes a string and recursively replaces composite characters using the Unicode canonical decomposition mappings. For example the character "à" is decomposed in a (a, &#96;). For more informations refer to [Unicode Standard Annex 15](http://www.unicode.org/unicode/reports/tr15/tr15-23.html#Decomposition).

The second line uses a regular expression that matches all the diacritic marks that can be combined to characters (like a grave accent that combined with "a" creates "à") and replaces them with an empty string. Being able to specify a Unicode block as pattern in a regular expression is a rarely used feature, but it can come pretty handy when working with i18n and l10n. You can find more details in Java's [java.util.regex.Pattern documentation](http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html). The list of Unicode blocks is available at the address [http://www.unicode.org/Public/UNIDATA/Blocks.txt]().

Applying this code to "Bebić" returns "Bebic".

Another option is to use IBM's [ICU library](http://site.icu-project.org/), which offers a larger support to Unicode functionality than Java. The downside is that the JAR of this library is about 6MiB, and can be quite cumbersome if one needs only some basic functionality. However if you are using this library you can remove diacritic marks in this way:

```
Transliterator accentsconverter = com.ibm.icu.text.Transliterator.getInstance("NFD; [:Nonspacing Mark:] Remove; NFC.");
String accentsgone = accentsconverter.transliterate(originalstring);
```

This code is more refined than the previous one. It first applies a canonical decomposition, then removes the diacritic marks and then applies a canonical composition.

Both techniques remove diacritic marks and transform, for example, "côté" in "cote" and "Ελληνικά" in "Ελληνικα". Wait, what's that? If you don't have a greek keyboard you should use a lot of alt+[numeric code] to write that word.


Unicode to ASCII: why and how
-----------------------------

Let's say that you've got an e-mail from Mr. まさゆき たけだ. Assuming that you do not speak japanese and you don't know what "hiragana" is, how are you going to add this person to your address book? And, even more important: how are you going to retrieve him?. Sometimes it's worse: a lot of software doesn't know how to handle characters that do not belong to the usual blocks, so they mangle the output and show just a bunch of question marks, so the e-mail is from Mr. ���� ���, which is even less useful than "weird-stuff-that-I-can't-read". Sometimes they fail gracefully and show something like "[307E][3055][3086][304D] [305F][3051][3060]".

Going back to our employees example: if you strip diacritic marks from Łukasiński you get "Łukasinski" which is slightly better, but still your boss won't be able to find your polish colleague.

If you are indexing text documents and you are from a western country, you'll hardly find the documents about China's capital 北亰.

It turns out that sometimes just stripping out characters is not enough: sometimes you need to transliterate words from an alphabet to another. When the destination alphabet is the latin one this process is called romanization. So in the first case the e-mail sender is Mr. Masayuki Takeda, your boss will find Mr. Łukasiński even if she looks for Lukasinski and your documents about Bei Jing will be easier to retrieve.

IBM's ICU library offers a great transliteration functionality ([ICU's transform documentation](http://userguide.icu-project.org/transforms/general)), but has some downsides:

* is a heavy library (although fast);
* it can be relatively complicated to use;
* it follows the rule "if it can't be done correctly better to do nothing at all".

For a little project I needed a simple and fast function to transliterate arbitrary Unicode strings to ASCII without hassle, and so JUnidecode was born. The philosophy it follows it's that for transliteration "something, even wrong, is better than nothing".


JUnidecode
----------

JUnidecode is a Java port of [Text::Unidecode](http://search.cpan.org/~sburke/Text-Unidecode-0.04/lib/Text/Unidecode.pm) perl module by Sean M. Burke, who deserves all the credit. Ça va sans dire that any bug in JUnicode is all my fault.

JUnidecode official web site is [https://github.com/gcardone/junidecode]()

JUnidecode has only one class, gcardone.junidecode.Junidecode, which, as of version 0.1, has only one method: public String unidecode(String s). It takes a String and transliterates it to a valid 7-bit ASCII String (obviously it also strips diacritic marks). For example:

```
import net.gcardone.junidecode;

// ...

// s = "résumé"
String s = "r\u00E9sum\u00E9";
System.out.print(Junidecode.unidecode(s));
// prints "resume"
```

This code can be made less verbose using the static import feature available on Java 5.0 and higher:

```
import static net.gcardone.junidecode.Junidecode.*;

// ...

// s = "résumé"
String s = "r\u00E9sum\u00E9";
System.out.print(unidecode(s));
// prints "resume"
```

This is a little example of JUnidecode capabilities: 

|     Unicode block      |                                 Original text                           | Transliteration  |                  Notes                   |
| ---------------------- | ----------------------------------------------------------------------- | ---------------- | ---------------------------------------- |
| Basic Latin            | JUnidecode.                                                             | JUnidecode.      |                                          |
| Latin-1 Supplement     | r&eacute;sum&eacute;                                                    | resume           | All diacritic marks are always stripped. |
| Latin Extended-A       | &#269;e&#353;tina                                                       | cestina          |                                          | 
| Armenian               | &#1344;&#1377;&#1397;&#1377;&#1405;&#1407;&#1377;&#1398;                | Hayastan         |                                          |
| Cyrillic               | &#1052;&#1086;&#1089;&#1082;&#1074;a                                    | Moskva           |                                          |
| Greek and Coptic       | &#917;&#955;&#955;&#951;&#957;&#953;&#954;&#940;                        | Ellenika         |                                          |
| Ethiopic               | &#4771;&#4850;&#4661; &#4771;&#4704;&#4707;                             | 'aadise 'aababaa | Expected: Addis Ababa                    |
| Arabic                 | &#1607;&#1575;&#1578;&#1601;                                            | htf              | Expected: hatif                          |
| Hebrew                 | &#1500;&#1513;&#1493;&#1503; &#1492;&#1511;&#1493;&#1491;&#1513;        | lshvn hqvdsh     | Expected: Leshon HaKodesh                |
| Syriac                 | &#1824;&#1835;&#1826;&#1808; &#1827;&#1816;&#1834;&#1821;&#1821;&#1808; | lshn' swryy'     | Expected: lessana suryaya                |
| Bengali                | &#2476;&#2494;&#2434;&#2482;&#2494;                                     | baaNlaa          | Expected: bangla                         |
| Devanagari             | &#2342;&#2375;&#2357;&#2344;&#2366;&#2327;&#2352;&#2368;                | devnaagrii       | Expected: devanagari                     |
| Gurmukhi               | &#2583;&#2625;&#2608;&#2606;&#2625;&#2582;&#2624;                       | gurmukhii        | Expected: gurmukhi                       |
| Malayalam              | &#3374;&#3378;&#3375;&#3390;&#3379;&#3330;                              | mlyaallN         | Expected: malayalam                      |
| Tamil                  | &#2980;&#2990;&#3007;&#2996;&#3021;                                     | tamilll          | Expected: tamil                          |
| Thai                   | &#3619;&#3634;&#3594;&#3629;&#3634;&#3603;&#3634;&#3592;&#3633;&#3585;&#3619;&#3652;&#3607;&#3618; | raach`aanaacchakraithy | Expected: ratcha anachak thai |
| CJK Unified Ideographs | &#21271;&#20144;                                                        | Bei Jing         |                                          |
| Unified Canadian Aboriginal Syllabics | &#5132;&#5144;&#5231;                                    | wewako           | Expected: Ewako (&#202;wako)             |
| Currency Symbols       | &#8356; &#8359; &#8360;                                                 | L Pts Rs         |                                          |
| Braille Patterns       | &#10255;&#10263;&#10257;&#10253;&#10250;&#10257;&#10263;                | premier          |                                          |
| Number Forms           | &#8532; &#8551;                                                         | 2/3 VIII         |                                          |
 

Unidecode mapping tables are largely based on Text::Unidecode's tables. However was released in 2001, and meanwhile Unicode was extended. Most notably JUnidecode supports the transliteration of most characters from Unicode's letterlike symbols block, which includes among the others &#8469;, &#8477;, &#8486; (which represents Ohm and is different from &#937;, which is the greek capital letter omega), &#8463; and other lovely nerdy characters.


Download
--------

Several download packages are available. JUnidecode is self-contained and doesn't need any additional library to work (except for the unit tests, which are based on JUnit).

[Please visit the download page of JUnidecode](https://github.com/gcardone/junidecode/releases).


License
-------

JUnidecode is licensed under the Apache License 2.0.

Credits
-------

JUnidecode is a Java port of [Text::Unidecode](http://search.cpan.org/~sburke/Text-Unidecode-0.04/lib/Text/Unidecode.pm) perl module by Sean M. Burke, who deserves all the credit (while I deserve all the blame for any error in JUnidecode).

There is a beautiful article by Burke himself that explains the design choices behind "unidecoding": http://interglacial.com/~sburke/tpj/as_html/tpj22.html. Basically there are two idea behind this software:

1. Writing a context aware transliteration library is an almost infeasible task. This is due to the complexity of many languages, for example Thai writing runs all togheter, in Arab a character should be transliterated in different ways based on the gender or other characteristics of the preceding word. So the first choice is: when a character can be transliterated in different ways, transliterate it to the way that is right most of the time.
2. The same character can be transliterated in different ways in different languages, for example the same ideogram has a different transliteration in Mandarin, Japanese and Korean. The choice in this case is: try to please most of the people (in the case of ideogram Chinese transliteration is always preferred since more than 1 billion people speak it and it is usually the root from which other languages derived).

