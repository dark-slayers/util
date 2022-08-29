package person.liuxx.util.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadLogFiles {
    public static void main(String[] args) {
        String path = "D:/shell/lf/logs/DEBUG.log";
        int defalutLineNumber = 15;
        String todatString = Pattern.compile("")
                .splitAsStream(LocalDate.now().toString())
                .skip(2)
                .collect(Collectors.joining());
        read(path, defalutLineNumber, todatString + " at ", "updateState", "", "");
    }

    static void read(String path, int lineNumber, String... word) {
        try {
            List<String> logList = search(Files.lines(Paths.get(path)), lineNumber, word);
            Files.write(Paths.get(path + "_all"), logList);
            System.out.println("文件写入完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<String> search(Stream<String> lines, int lineNumber, String... word) {
        AtomicInteger index = new AtomicInteger(0);
        return lines.filter(t -> {
            if (Stream.of(word).allMatch(w -> t.contains(w))) {
                index.set(lineNumber);
            } else {
                index.getAndDecrement();
            }
            return index.intValue() > 0;
        }).collect(Collectors.toList());
    }
}
