import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.HashSet;
import java.util.Set;


public class GUI extends JPanel {
    private final int[][] maze;
    private final int cellSize;
    private final Color[] COLORS = {Color.WHITE, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
    private final int EMPTY = 0;
    private final int WALL = 1;
    private final int START = 2;
    private final int END = 3;
    private final int VISITED = 4;
    private final int SEARCHED = 5;
    private static int[][] maze1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private static int[][] maze2 = {
            {2, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
            {0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 3},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
    };

    public GUI(int[][] maze, int cellSize) {
        this.maze = maze;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
//                switch (maze[i][j]) {
//                    case 0:
//                        g.setColor(Color.WHITE);
//                        break;
//                    case 1:
//                        g.setColor(Color.BLACK);
//                        break;
//                    case 2:
//                        g.setColor(Color.GREEN);
//                        break;
//                    case 3:
//                        g.setColor(Color.RED);
//                        break;
//                    case 4:
//                        g.setColor(Color.ORANGE);
//                        break;
//                    default:
//                        g.setColor(Color.GRAY);
//                        break;
//                }
//                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(COLORS[maze[i][j]]);
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }


    public static List<int[]> DFS(int[][] maze, int startRow, int startCol, int endRow, int endCol) {
        Stack<int[]> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        stack.push(new int[] {startRow, startCol});
        visited.add(startRow + "," + startCol);

        while (!stack.isEmpty()) {
            int[] curr = stack.pop();
            int row = curr[0];
            int col = curr[1];

            if (row == endRow && col == endCol) {
                // Found the end point, backtrack to get the path
                List<int[]> path = new ArrayList<>();
                path.add(new int[] {endRow, endCol});
                while (row != startRow || col != startCol) {
                    int[] prev = stack.pop();
                    row = prev[0];
                    col = prev[1];
                    path.add(new int[] {row, col});
                }
                Collections.reverse(path);
                return path;
            }

            // Mark the current cell as visited
            visited.add(row + "," + col);

            // Check all neighboring cells
            for (int[] neighbor : getNeighbors(maze, row, col)) {
                int neighborRow = neighbor[0];
                int neighborCol = neighbor[1];
                if (!visited.contains(neighborRow + "," + neighborCol)) {
                    stack.push(new int[] {neighborRow, neighborCol});
                }
            }
        }

        // No path found
        return null;
    }

    private static List<int[]> getNeighbors(int[][] maze, int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        if (row > 0 && maze[row-1][col] == 0) {  // up
            neighbors.add(new int[] {row-1, col});
        }
        if (col > 0 && maze[row][col-1] == 0) {  // left
            neighbors.add(new int[] {row, col-1});
        }
        if (row < maze.length-1 && maze[row+1][col] == 0) {  // down
            neighbors.add(new int[] {row+1, col});
        }
        if (col < maze[0].length-1 && maze[row][col+1] == 0) {  // right
            neighbors.add(new int[] {row, col+1});
        }
        return neighbors;
    }




    public static void main(String[] args) {


        int cellSize = 40;
        GUI panel = new GUI(maze2, cellSize);

        JFrame frame = new JFrame("PATHFINDER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}