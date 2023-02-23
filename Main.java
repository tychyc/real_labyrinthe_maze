import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Maze {
    private static final int ROAD = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int EXIT = 3;
    private static final int PATH = 4;

    private int[][] maze;
    private boolean[][] visited;
    private Coordinate start;
    private Coordinate end;

    public Maze(File maze) throws FileNotFoundException {
        String fileText = "";
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine() + "\n";
            }
        }
        initializeMaze(fileText);
    }

    private void initializeMaze(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");
        maze = new int[lines.length][lines[0].length()];
        visited = new boolean[lines.length][lines[0].length()];

        for (int row = 0; row < getHeight(); row++) {
            if (lines[row].length() != getWidth()) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length() + " but should be " + getWidth() + ")");
            }

            for (int col = 0; col < getWidth(); col++) {
                if (lines[row].charAt(col) == 'X')
                    maze[row][col] = WALL;
                else if (lines[row].charAt(col) == '1') {
                    maze[row][col] = START;
                    start = new Coordinate(row, col);
                } else if (lines[row].charAt(col) == '2') {
                    maze[row][col] = EXIT;
                    end = new Coordinate(row, col);
                } else
                    maze[row][col] = ROAD;
            }
        }
    }

    public int getHeight() {
        return maze.length;
    }

    public int getWidth() {
        return maze[0].length;
    }

    public Coordinate getEntry() {
        return start;
    }



    public boolean isExit(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    public boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        return maze[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            return true;
        }
        return false;
    }

    public void printPath(List<Coordinate> path) {
        int[][] tempMaze = Arrays.stream(maze)
                .map(int[]::clone)
                .toArray(int[][]::new);

        for (Coordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isExit(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
        }
        String filename = "labyrinthe_solutionV2.txt";
        String encoding = "UTF-8";

        try {
        PrintWriter writer = new PrintWriter(filename, encoding);
        PrintStream printStream = new PrintStream(filename);
        System.setOut(printStream);
            System.out.println("voici la solution du labyrinthe");
        writer.close();
        }catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(toString(tempMaze));

    }

    public String toString(int[][] maze) {
        StringWriter result = new StringWriter(getWidth() * (getHeight() + 1));
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (maze[row][col] == ROAD) {
                    result.append('0');
                } else if (maze[row][col] == WALL) {
                    result.append('X');
                } else if (maze[row][col] == START) {
                    result.append('1');
                } else if (maze[row][col] == EXIT) {
                    result.append('2');
                } else {
                    result.append('@');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public void reset() {
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }
}