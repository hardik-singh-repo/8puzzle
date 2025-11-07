
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Scanner;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class TestBoard {

    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE = Locale.US;
    private static final String testInPath = "test/in/";

    @Test
    void shouldCheckBoardConstruction() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle01.txt"));

        Board initial = new Board(tiles);
        assert (initial.dimension() == tiles.length);
    }

    @Test
    void shouldReturnCorrectHammingValueFor3x3() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle3x3-18.txt"));
        Board initial = new Board(tiles);
        assertEquals(6, initial.hamming());
    }

    @Test
    void shouldReturnCorrectHammingValueFor4x4() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle4x4-35.txt"));
        Board initial = new Board(tiles);
        assertEquals(15, initial.hamming());
    }

    @Test
    void shouldReturnCorrectManhattanValueFor3x3() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle3x3-18.txt"));
        Board initBoard = new Board(tiles);
        assertEquals(12, initBoard.manhattan());
    }

    @Test
    void shouldReturnCorrectManhattanValueFor4x4() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle4x4-35.txt"));
        Board initBoard = new Board(tiles);
        assertEquals(29, initBoard.manhattan());
    }

    @Test
    void shouldReturnNeighborsBasedOnEmptyTile() throws IOException {
        int[][] tiles = readTestData(testInPath.concat("puzzle3x3-08.txt"));
        Board initBoard = new Board(tiles);
        Iterable<Board> neighbors = initBoard.neighbors();
        int i = 0;
        for (Board board : neighbors)
            i++;
        assertEquals(2, i);
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
