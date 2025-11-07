# Problem
Solve 8-puzzle problem, it is a sliding puzzle that if played on a 3x3 grid with 8 square tiles labeled 1 through 8, plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as few moves as possible. You are permitted to slide tiles either horizontally or vertically into the blank square.

# Solution
A* algorithm is used here to reach the goalboard. All the various board combination are created from a starting board and then pushed on to a MinPQ(Priority Queue data structure). Each board is assigned a priority. To create priority we use manhattan or hamming heuristic function. The functions tells are the min distance to reach the goal and the board with min value is on the top of MinPQ. It becomes our path to solve the problem in an efficient manner.

