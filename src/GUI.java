import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class GUI extends JPanel {
    private boolean isSearching = false;
    private static int[][] maze;
    private final int cellSize;
    private static final Color[] COLORS = {Color.WHITE, Color.BLACK, Color.GREEN, Color.RED, Color.ORANGE, Color.PINK};
    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int END = 3;
    private static final int PATH = 4;
    private static final int SEARCHED = 5;
    private static int[] startpoint;
    private static int[] endpoint;

    private static int[][] maze1 = {
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 1, 1, 0, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0}
    };
    private static int[][] maze2 = {
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
    private static int[][] maze3 = {
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
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}
    };
    private static int[][] maze4 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private static int[][] maze5 = {
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0},
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1},
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            {0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0}
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


    public void dfsThread() {
        isSearching = true;

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                dfs(maze, startpoint[0], startpoint[1], endpoint[0], endpoint[1], GUI.this);
                isSearching = false;
            }

        }, 0, TimeUnit.MILLISECONDS);

        executor.shutdown();
    }

    public void bfsThread() {
        isSearching = true;

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                bfs(maze, startpoint[0], startpoint[1], endpoint[0], endpoint[1], GUI.this);
                isSearching = false;
            }

        }, 0, TimeUnit.MILLISECONDS);

        executor.shutdown();
    }






    public static void main(String[] args) {


        int cellSize = 40;
        GUI panel = new GUI(maze5, cellSize);

//        int[][] randomMaze = generateMaze(20, 20);
//        GUI panel = new GUI(randomMaze, cellSize);

        startpoint = new int[]{0, 0};
        endpoint = new int[]{10, 11};

        JFrame frame = new JFrame("PATHFINDER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.WEST);

        JTextField startXField = new JTextField("0", 5);
        JTextField startYField = new JTextField("0", 5);

        JTextField endXField = new JTextField("10", 5);
        JTextField endYField = new JTextField("11", 5);


        JButton dfsButton = new JButton("DFS");
        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!panel.isSearching) {
                    panel.dfsThread();
                }
            }
        });


        JButton bfsButton = new JButton("BFS");
        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!panel.isSearching) {
                    panel.bfsThread();
                }
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        if (maze[i][j] == SEARCHED || maze[i][j] == PATH) {
                            maze[i][j] = EMPTY;
                        }
                    }
                }
                panel.repaint();
            }
        });

//        JButton maze1Button = new JButton("Maze 1");
//        maze1Button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GUI panel = new GUI(maze1, cellSize);
//                contentPanel = new JPanel();
//                contentPanel.setLayout(new BorderLayout());
//                contentPanel.add(panel, BorderLayout.WEST);
//            }
//        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(clearButton);

        JPanel inputXPanel = new JPanel();
        inputXPanel.add(new JLabel("Start Row:"));
        inputXPanel.add(startXField);

        JPanel inputYPanel = new JPanel();
        inputYPanel.add(new JLabel("Start Col:"));
        inputYPanel.add(startYField);

        JPanel inputEXPanel = new JPanel();
        inputEXPanel.add(new JLabel("End Row:"));
        inputEXPanel.add(endXField);

        JPanel inputEYPanel = new JPanel();
        inputEYPanel.add(new JLabel("End Col:"));
        inputEYPanel.add(endYField);

        JButton setSEButton = new JButton("Set SE");
        setSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        if (maze[i][j] == START || maze[i][j] == END) {
                            maze[i][j] = EMPTY;
                        }
                    }
                }

                panel.startpoint[0] = Integer.parseInt(startXField.getText());
                panel.startpoint[1] = Integer.parseInt(startYField.getText());
                maze[startpoint[0]][startpoint[1]] = START;

                panel.endpoint[0] = Integer.parseInt(endXField.getText());
                panel.endpoint[1] = Integer.parseInt(endYField.getText());
                maze[endpoint[0]][endpoint[1]] = END;

                panel.repaint();
            }
        });

        JPanel setButtonPanel = new JPanel();
        setButtonPanel.add(setSEButton);

        buttonPanel.add(inputXPanel);
        buttonPanel.add(inputYPanel);
        buttonPanel.add(inputEXPanel);
        buttonPanel.add(inputEYPanel);
        buttonPanel.add(setSEButton);

        contentPanel.add(buttonPanel, BorderLayout.EAST);


        frame.add(contentPanel);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    
}