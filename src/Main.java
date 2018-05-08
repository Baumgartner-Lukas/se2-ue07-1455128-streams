import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        final String filePath = "sampletext.txt";
        final String outPath = "outputtext.txt";


        Optional<String> line = findLine(filePath, "five");
        Optional<String> line2 = findLine(filePath, "not in text");
        System.out.println("Find first line with: five");
        System.out.println(line);
        System.out.println("Find first line with: not in text");
        System.out.println(line2);

        List<String> lines = findLines(filePath, "is");
        List<String> lines2 = findLines(filePath, "isn't");
        System.out.println("Find lines with: is");
        System.out.println(lines);
        System.out.println("Find lines with: isn't");
        System.out.println(lines2);

        writeNoEmptyLines(filePath, outPath);
        System.out.println(words(filePath));

        System.out.println(averageLineLength(filePath));

    }
    //-------------------------------- a --------------------------------
    static Optional<String> findLine(String inFile, String text) throws IOException {
        return Files.lines(Paths.get(inFile))
                //regex to find the exact word not a substring as with contains
                .filter(w -> w.matches("(?s).*\\b" + text + "\\b.*"))
                .findFirst();
    }

    static List<String> findLines(String inFile, String text) throws IOException {
        return Files.lines(Paths.get(inFile))
                //regex to find the exact word not a substring as with contains
                .filter(w -> w.matches("(?s).*\\b" + text + "\\b.*"))
                .collect(Collectors.toList());
    }

    //-------------------------------- b --------------------------------
    static void writeNoEmptyLines(String inFile, String outFile) throws IOException {
        Files.write(Paths.get(outFile),
                Files.lines(Paths.get(inFile))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toList()));
    }

    //-------------------------------- c --------------------------------
    static Stream<String> wordStream(String inFile) throws IOException{
        return Files.lines(Paths.get(inFile))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .filter(word -> !word.matches("[^A-Za-z]"))
                //.map(String::toLowerCase)
                .map(line -> line.split("[ .,;?!:()-]"))
                .flatMap(Arrays::stream)
                .distinct();
    }

    static List<String> words(String inFile) throws IOException{
        return wordStream(inFile)
                .collect(Collectors.toList());
    }

    //-------------------------------- d --------------------------------
    static double averageLineLength(String inFile) throws IOException{
        return 0;
    }

}
