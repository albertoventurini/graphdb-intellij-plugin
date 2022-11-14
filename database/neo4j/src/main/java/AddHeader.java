import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class AddHeader {

    public static void main(String[] args) throws IOException {

        var path = Paths.get("").toAbsolutePath();

//        System.out.println(path);

        Files.walk(path)
                .filter(Files::isRegularFile)
                .filter(p -> !p.toString().contains(".git"))
                .filter(p -> !p.toString().endsWith(".class"))
                .forEach(p -> {

                    String header = null;

//                    if (p.toString().endsWith(".java")) {
//                        header = """
//                                /**
//                                 * Copied and adapted from plugin
//                                 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
//                                 * by Neueda Technologies, Ltd.
//                                 * Modified by Alberto Venturini, 2022
//                                 */
//                                """;
//                    } else
                        if (p.toString().endsWith("build.gradle")) {
                        header = """
                                // Copied and adapted from plugin "Graph Database Support"
                                // by Neueda Technologies, Ltd.
                                // Modified by Alberto Venturini, 2022
                                """;
                    }

                    if (header != null) {
                        String fileContent;
                        try {
                            fileContent = Files.readString(p);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        String newContent = header + fileContent;

                        try {
                            Files.writeString(p, newContent);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
        });

    }
}
