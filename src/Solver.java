import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solver {
    private int minMoves = -1;
    private Iterable<Board> solutionPath;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solve(initial);
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return solvable ? minMoves : -1;
    }

    public Iterable<Board> solution() {
        return solvable ? solutionPath : null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private void solve(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null)); // main board.
        twinPq.insert(new SearchNode(initial.twin(), 0, null)); // twin board to check for solvability.
        SearchNode goalNode = null;
        while (true) { // run search on both node to check if the board is solvable or not.
            goalNode = step(pq);
            if (goalNode != null) {
                solvable = true;
                minMoves = goalNode.moves;
                solutionPath = buildPath(goalNode);
                return;
            }
            SearchNode twinGoal = step(twinPq);
            if (twinGoal != null) {
                solvable = false;
                minMoves = -1;
                solutionPath = null;
                return;
            }
        }
    }

    private SearchNode step(MinPQ<SearchNode> pq) {
        if (pq == null)
            return null;

        // get the node with smallest priority.
        SearchNode node = pq.delMin();
        if (node.board.isGoal()) { // check if goal is reached.
            return node;
        }

        // if goal not reached then get the neighbors and insert them in the PQ.
        for (Board nb : node.board.neighbors()) {
            if (node.prev != null && nb.equals(node.prev.board)) {
                continue;
            }
            pq.insert(new SearchNode(nb, node.moves + 1, node));
        }
        return null; // goal is not reached.
    }

    private Iterable<Board> buildPath(SearchNode goal) {
        Deque<Board> stack = new ArrayDeque<>();
        SearchNode x = goal;
        while (x != null) {
            stack.push(x.board);
            x = x.prev;
        }
        return stack;
    }

    private static final class SearchNode implements Comparable<SearchNode> {
        final Board board;
        final int moves;
        final int priority;
        final SearchNode prev;

        SearchNode(Board b, int m, SearchNode p) {
            this.board = b;
            this.moves = m;
            this.priority = m + b.manhattan();
            this.prev = p;
        }

        @Override
        public int compareTo(SearchNode n) {
            int comp = Integer.compare(this.priority, n.priority);
            if (comp != 0)
                return comp;
            return Integer.compare(this.board.hamming(), n.board.hamming());
        }
    }

}