import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class GUI extends JPanel {
    private final int[][] maze;
    private final int cellSize;
    private final Color[] COLORS = {Color.WHITE, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK};
    private final int EMPTY = 0;
    private final int WALL = 1;
    private final int START = 2;
    private final int END = 3;
    private final int VISITED = 4;
    private final int SEARCHED = 5;
    private static LinkedList<int[]> path = new LinkedList<int[]>();
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
            {0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
            {0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0},
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

//    public void changeCellColor(int[][] maze, int row, int col, Color color, GUI gui) {
//        try {
//            Thread.sleep(500);  // Delay to visualize the color change
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        maze[row][col] = getColorCode(color);
//        gui.repaint();
//        try {
//            Thread.sleep(500);  // Delay to visualize the color change
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }

//    private int getColorCode(Color color) {
//        if (color.equals(Color.WHITE)) {
//            return EMPTY;
//        } else if (color.equals(Color.BLACK)) {
//            return WALL;
//        } else if (color.equals(Color.BLUE)) {
//            return START;
//        } else if (color.equals(Color.GREEN)) {
//            return END;
//        } else if (color.equals(Color.YELLOW)) {
//            return VISITED;
//        } else if (color.equals(Color.ORANGE)) {
//            return SEARCHED;
//        } else {
//            return -1;  // Unknown color
//        }
//    }


    public boolean dfs(int[][] maze, int startRow, int startCol, int endRow, int endCol, GUI gui) {
        int rows = maze.length;
        int cols = maze[0].length;

        boolean[][] visited = new boolean[rows][cols];
        visited[startRow][startCol] = true;

        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};  // Up, Left, Down, Right

        Stack<Integer> stack = new Stack<>();
        stack.push(startRow * cols + startCol);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            int row = current / cols;
            int col = current % cols;

            if (row == endRow && col == endCol) {
                return true;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && maze[newRow][newCol] != 1 && !visited[newRow][newCol]) {
                    stack.push(newRow * cols + newCol);
                    visited[newRow][newCol] = true;
                    maze[newRow][newCol] = SEARCHED;
                    gui.repaint();

                    try {
                        Thread.sleep(150);  // Delay to visualize the search process
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }


    public boolean bfs(int[][] maze, int startRow, int startCol, int endRow, int endCol, GUI gui) {
        int rows = maze.length;
        int cols = maze[0].length;

        boolean[][] visited = new boolean[rows][cols];
        visited[startRow][startCol] = true;

        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};  // Up, Left, Down, Right

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startRow * cols + startCol);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / cols;
            int col = current % cols;

            if (row == endRow && col == endCol) {
                return true;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && maze[newRow][newCol] != 1 && !visited[newRow][newCol]) {
                    queue.offer(newRow * cols + newCol);
                    visited[newRow][newCol] = true;
                    maze[newRow][newCol] = SEARCHED;
                    gui.repaint();

                    try {
                        Thread.sleep(150);  // Delay to visualize the search process
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
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
        panel.dfs(maze2, 0, 0, 10, 11, panel);

    }
}