import java.util.LinkedList;
import java.util.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int manhattan = -1;
    private int hamming = -1;
    private final int[][] tiles;
    private final int n;

    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        int nLocal = tiles.length;
        // if the input is beyond the boundary conditions.
        if (nLocal < 2 || nLocal >= 128) {
            throw new IllegalArgumentException();
        }

        n = nLocal;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            this.tiles[i] = tiles[i].clone();
        }
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(n).append('\n');
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.append(String.format("%2d ", tiles[i][j]));
            }
            out.append('\n');
        }
        return out.toString();
    }

    public int dimension() {
        return n;
    }

    /**
     * This is one of the heuristic function for A* algo.
     * This functions tells us how far we are from goal board by counting how many
     * cells are in wrong place.
     * e.g : 3 x 3 board
     * 1 2 3
     * 4 5 6
     * 7 0 8
     * In this matrix the hamming count is 1 since only one cell is out of place.
     * 
     * @return
     */
    public int hamming() {
        if (hamming != -1) // if already computed for this board use the cache.
            return hamming;

        int count = 0;
        int n = dimension();
        for (int k = 0; k < n * n; k++) {
            int i = k / n, j = k % n;
            int val = tiles[i][j];
            if (val == 0)
                continue;
            int goal = i * n + j + 1;
            if (val != goal) {
                count++;
            }
        }
        return hamming = count;
    }

    /**
     * This is another heuristic function for A* algo.
     * This function tells us sum of the distance between board and goal board.
     * The cells can move horizontally or vertically only. 0 is used to denote empty
     * slot.
     * e.g : 3 x 3 board
     * 8 1 3
     * 4 0 2
     * 7 6 5
     * the manhattan for this matrix is 10.
     * calculation: 1[1] 2[2] 3[0] 4[0] 5[2] 6[2] 7[0] 8[3]; the values in the
     * bracket show how far the values are from their original place.
     * 
     * @return
     */
    public int manhattan() {
        if (manhattan != -1)
            return manhattan;
        int sum = 0;
        for (int k = 0; k < n * n; k++) {
            int i = k / n, j = k % n; // arithmetic trick to reduce the second for loop.
            int val = tiles[i][j];
            if (val == 0)
                continue;
            int correctRow = (val - 1) / n; // since the array is 0 based need to subtract to get correct position.
            int correctCol = (val - 1) % n;
            if (i != correctRow || j != correctCol) {
                sum += Math.abs(correctRow - i) + Math.abs(correctCol - j);
            }
        }
        return manhattan = sum;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null || y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (this.n != that.n)
            return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    /**
     * neighbors are created by moving empty slot to one slot in all 4 directions,
     * when not at the boundary or else the number of neighbor can be less.
     * 
     * @return
     */
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new LinkedList<>();
        int zi = -1, zj = -1; // for zero index.
        for (int k = 0; k < n * n; k++) {
            int i = k / n, j = k % n;
            if (tiles[i][j] == 0) {
                zi = i;
                zj = j;
                break;
            }
        }

        // candidate directions for the tiles to move to.
        int[][] dir = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } }; // up, left, down, right.
        for (int[] d : dir) {
            int ni = zi + d[0], nj = zj + d[1];
            if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                int[][] nTiles = new int[n][];
                for (int i = 0; i < tiles.length; i++) {
                    nTiles[i] = tiles[i].clone();
                }
                // swap the elements
                int temp = nTiles[zi][zj];
                nTiles[zi][zj] = nTiles[ni][nj];
                nTiles[ni][nj] = temp;
                neighbors.add(new Board(nTiles));
            }
        }
        return neighbors;
    }

    public Board twin() { 
        int[][] nTiles = new int[n][];
        for (int i = 0; i < tiles.length; i++) {
            nTiles[i] = tiles[i].clone();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (nTiles[i][j] != 0 && nTiles[i][j + 1] != 0) {
                    int t = nTiles[i][j];
                    nTiles[i][j] = nTiles[i][j + 1];
                    nTiles[i][j + 1] = t;
                    return new Board(nTiles);
                }
            }
        }
        return new Board(nTiles);
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();

        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // print the board to check correct construction.
        StdOut.print(initial);
    }
}