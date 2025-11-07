
import static org.junit.Assert.assertTrue;

import java.io.IO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Scanner;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class TestSolver {

    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE = Locale.US;
    private static final String testInPath = "test/in/";

    @Test
    void shouldBeAbleToSolveASolvablePuzzle() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle01.txt"));
        Board initial = new Board(tiles);
        Solver sol = new Solver(initial);
        assertTrue(sol.isSolvable());
    }

    @Test
    void shouldReturnFalseForUnsolvableBoard() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle2x2-unsolvable1.txt"));
        Board initial = new Board(tiles);
        Solver sol = new Solver(initial);
        assertTrue(!sol.isSolvable());
    }

    private int[][] readTestData(String filepath) throws IOException {
        int[][] tiles = null;
        Path path = Path.of(filepath);
        try (InputStream in = Files.newInputStream(path); Scanner sc = new Scanner(in, CHARSET_NAME)) {
            sc.useLocale(LOCALE);
            int n = 0;
            if (sc.hasNext()) {
                n = sc.nextInt();
                tiles = new int[n][n];
                for (int k = 0; k < n * n; k++) {
                    int i = k / n, j = k % n;
                    tiles[i][j] = sc.nextInt();
                }
            }
        }
        return tiles;
    }
}
