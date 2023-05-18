import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GUI extends JPanel {
    private final int[][] maze;
    private final int cellSize;
    private final Color[] COLORS = {Color.WHITE, Color.BLACK, Color.GREEN, Color.RED, Color.ORANGE, Color.PINK};
    private final int EMPTY = 0;
    private final int WALL = 1;
    private final int START = 2;
    private final int END = 3;
    private final int PATH = 4;
    private final int SEARCHED = 5;
    private static int[] startpoint;
    private static int[] endpoint;

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

/*
    public void changeCellColor(int[][] maze, int row, int col, Color color, GUI gui) {
        try {
            Thread.sleep(500);  // Delay to visualize the color change
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        maze[row][col] = getColorCode(color);
        gui.repaint();
        try {
            Thread.sleep(500);  // Delay to visualize the color change
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private int getColorCode(Color color) {
        if (color.equals(Color.WHITE)) {
            return EMPTY;
        } else if (color.equals(Color.BLACK)) {
            return WALL;
        } else if (color.equals(Color.BLUE)) {
            return START;
        } else if (color.equals(Color.GREEN)) {
            return END;
        } else if (color.equals(Color.YELLOW)) {
            return VISITED;
        } else if (color.equals(Color.ORANGE)) {
            return SEARCHED;
        } else {
            return -1;  // Unknown color
        }
    }
*/

    private void showPath(LinkedList<int[]> path, GUI gui) {
        for (int[] cell : path) {
            if ((cell[0] == startpoint[0] && cell[1] == startpoint[1]) || (cell[0] == endpoint[0] && cell[1] == endpoint[1])) {
                continue;
            }
//            System.out.print("(" + cell[0] + ", " + cell[1] + ") ");  // Print the path
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            maze[cell[0]][cell[1]] = PATH;
            gui.repaint();

        }
    }




    public boolean dfs(int[][] maze, int startRow, int startCol, int endRow, int endCol, GUI gui) {
        int rows = maze.length;
        int cols = maze[0].length;

        boolean[][] visited = new boolean[rows][cols];
        visited[startRow][startCol] = true;

        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};  // Up, Left, Down, Right

        Stack<Integer> stack = new Stack<>();
        stack.push(startRow * cols + startCol);

        LinkedList<int[]> path = new LinkedList<int[]>();
        path.add(new int[]{startRow, startCol});

        while (!stack.isEmpty()) {
            int current = stack.pop();
            int row = current / cols;
            int col = current % cols;

            if (row == endRow && col == endCol) {
                showPath(path, gui);
                return true;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && maze[newRow][newCol] != 1 && !visited[newRow][newCol]) {
                    stack.push(newRow * cols + newCol);
                    visited[newRow][newCol] = true;
                    if ((newRow == startpoint[0] && newCol == startpoint[1]) || (newRow == endpoint[0] && newCol == endpoint[1])) {
                        continue;
                    }
                    maze[newRow][newCol] = SEARCHED;
                    gui.repaint();

                    try {
                        Thread.sleep(100);  // Delay to visualize the search process
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    path.add(new int[]{newRow, newCol});
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

        LinkedList<int[]> path = new LinkedList<>();
        path.add(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / cols;
            int col = current % cols;

            if (row == endRow && col == endCol) {
                showPath(path, gui);
                return true;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && maze[newRow][newCol] != 1 && !visited[newRow][newCol]) {
                    queue.offer(newRow * cols + newCol);
                    visited[newRow][newCol] = true;
                    if ((newRow == startpoint[0] && newCol == startpoint[1]) || (newRow == endpoint[0] && newCol == endpoint[1])) {
                        continue;
                    }
                    maze[newRow][newCol] = SEARCHED;
                    gui.repaint();

                    try {
                        Thread.sleep(100);  // Delay to visualize the search process
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    path.add(new int[]{newRow, newCol});
                }
            }
        }

        return false;
    }




    public static void main(String[] args) {


        int cellSize = 40;
        GUI panel = new GUI(maze2, cellSize);
        startpoint = new int[]{0, 0};
        endpoint = new int[]{10, 11};

        JFrame frame = new JFrame("PATHFINDER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //TODO: fix the lines below

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.WEST);


//        panel.dfs(maze2, startpoint[0], startpoint[1], endpoint[0], endpoint[1], panel);
//        panel.bfs(maze2, startpoint[0], startpoint[1], endpoint[0], endpoint[1], panel);


        JButton dfsButton = new JButton("DFS");
        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.dfs(maze2, startpoint[0], startpoint[1], endpoint[0], endpoint[1], panel);
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dfsButton);

        JButton bfsButton = new JButton("BFS");
        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.bfs(maze2, startpoint[0], startpoint[1], endpoint[0], endpoint[1], panel);
            }
        });
        buttonPanel.add(bfsButton);

        contentPanel.add(buttonPanel, BorderLayout.EAST);


        frame.add(contentPanel);

        //TODO: fix the lines above

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}