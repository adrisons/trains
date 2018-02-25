
/******************************************************************************
 *
 *  Compilation:  javac Main.java
 *  Execution:    java Main <input_file>
 *
 ******************************************************************************/

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    // force Unicode UTF-8 encoding; otherwise it's system dependent
    private static final String CHARSET_NAME = "UTF-8";

    // send output here
    private static PrintWriter out;

    // this is called before invoking any methods
    static {
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }

    /**
     * Processes the input to get a list with the graph definition
     *
     * @param input
     * @return A list of strings representing the vertices, edges and cost of the graph
     */
    private static List<String> getDataFromInput(String input) {
        Scanner sc = new Scanner(input);
        Pattern filerRegx = Pattern.compile("([a-zA-Z]{2}[0-9]{1})\\s*,?");
        List<String> results = sc.findAll(filerRegx)
                .map(mr -> mr.group(1))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return results;
    }

    /**
     * Calculates the distance of a route and prints it on console
     *
     * @param m
     * @param list
     * @param name
     */
    private static void executeProblem(AdjMatrix m, ArrayList<String> list, String name) {
        Integer dist = m.calculateDistance(list);
        if (dist == -1) {
            out.println("Output #" + name + ": NO SUCH ROUTE");
        } else {
            out.println("Output #" + name + ": " + dist);
        }
        list.clear();
    }

    /**
     * Main
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // Read the input file
        String input = new String(Files.readAllBytes(Paths.get(args[0])));
        out.println("Raw input: \'" + input + "\'");

        // Extract data from the input
        List<String> results = getDataFromInput(input);

        // Create the matrix
        AdjMatrix M = new AdjMatrix(results);
        out.println("Adjacency Matrix: " + M);

        // Tests
        ArrayList<String> list = new ArrayList<String>();
        ;
        Integer dist;
        // Problem 1
        list.add("A");
        list.add("B");
        list.add("C");
        executeProblem(M, list, "1");
        // Problem 2
        list.add("A");
        list.add("D");
        executeProblem(M, list, "2");
        // Problem 3
        list.add("A");
        list.add("D");
        list.add("C");
        executeProblem(M, list, "3");
        // Problem 4
        list.add("A");
        list.add("E");
        list.add("B");
        list.add("C");
        list.add("D");
        executeProblem(M, list, "4");
        // Problem 5
        list.add("A");
        list.add("E");
        list.add("D");
        executeProblem(M, list, "5");
    }


}
