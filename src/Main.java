import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws IOException {
        final String filePath = "sampletext.txt";
        final String faustPath = "faust_1.txt";
        final String outPath = "outputtext.txt";
        final String faustOutPath = "outputfaus.txt";

        System.out.println("Find first line with: five");
        System.out.println(findLine(filePath, "five"));
        System.out.println("Find first line with: not in text");
        System.out.println(findLine(filePath, "not in text"));

        System.out.println("Find first line with: Vorspiel");
        System.out.println(findLine(faustPath, "Vorspiel"));
        System.out.println("Find first line with: Spaß");
        System.out.println(findLine(faustPath, "Spaß"));

        System.out.println("Find lines with: is");
        findLines(filePath, "is").forEach(System.out::println);
        System.out.println("Find lines with: Herz");
        findLines(faustPath, "Herz").forEach(System.out::println);
        System.out.println("Find lines with: isn't");
        findLines(filePath, "isn't").forEach(System.out::println);

        writeNoEmptyLines(filePath, outPath);
        writeNoEmptyLines(faustPath, faustOutPath);

        System.out.println("Get all words used in the text");
        words(filePath).forEach(System.out::println);
        words(faustPath).forEach(System.out::println);

        System.out.println("Get avg line length");
        System.out.println(averageLineLength(filePath));
        System.out.println(averageLineLength(faustPath));

        System.out.println("Get average nr of words in line");
        System.out.println(averageWordsInLine(filePath));
        System.out.println(averageWordsInLine(faustPath));
        System.out.println("group words by their first character");
        System.out.println(alphaGrouping(filePath));
        System.out.println(alphaGrouping(faustPath));

    }

    //-------------------------------- a --------------------------------
    private static Optional<String> findLine(String inFile, String text) throws IOException {
        return Files.lines(Paths.get(inFile))
                //the regex beneath is used to find the exact word in the text
                //and not a substring as with contains
                //source: http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
                .filter(w -> w.matches("(?s).*\\b" + text + "\\b.*"))
                .findFirst();
    }

    private static List<String> findLines(String inFile, String text) throws IOException {
        return Files.lines(Paths.get(inFile))
                //the regex beneath is used to find the exact word in the text
                //and not a substring as with contains
                //source: http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
                .filter(w -> w.matches("(?s).*\\b" + text + "\\b.*"))
                .collect(Collectors.toList());
    }

    //-------------------------------- b --------------------------------
    private static void writeNoEmptyLines(String inFile, String outFile) throws IOException {
        Files.write(Paths.get(outFile),
                Files.lines(Paths.get(inFile))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toList()));
    }

    //-------------------------------- c --------------------------------
    private static Stream<String> wordStream(String inFile) throws IOException {
        return Files.lines(Paths.get(inFile))
                .flatMap(line -> Stream.of(line.split("[ .,;?!:()-]")))
                .filter(word -> word.length() > 0)
                .map(String::toLowerCase)
                .sorted(String::compareTo)
                .distinct();
    }

    private static List<String> words(String inFile) throws IOException {
        return wordStream(inFile)
                .collect(Collectors.toList());
    }

    //-------------------------------- d --------------------------------
   private static double averageLineLength(String inFile) throws IOException {
        return Files.lines(Paths.get(inFile))
                //disclaimer: does not count empty lines as a line
                //i.e. sampletext.txt has 7 lines (2 empty) => 5 lines with avg. 29.4 chars
                .filter(line -> line.trim().length() > 0)
                .collect(Collectors.averagingDouble(String::length));
    }

    private static double  averageWordsInLine(String inFile) throws IOException {
        return Files.lines(Paths.get(inFile))
                .map(line -> line.split("\\s"))
                .map(array -> array.length)
                .collect(Collectors.averagingDouble(Double::new));
    }

    //-------------------------------- e --------------------------------
    private static Map<Character, List<String>> alphaGrouping(String inFile) throws IOException {
        return wordStream(inFile)
                .filter(w -> w.length() > 0)
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
    }

}
