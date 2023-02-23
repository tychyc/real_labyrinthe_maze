import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class MazeDriver {
    public static void main(String[] args) throws Exception {
        Scanner clavier = new Scanner(System.in);
        System.out.println("Entrez le nom du fichier : ");
        String str = clavier.nextLine();
        System.out.println("\n");
        File maze1 = new File(str);
        try
        {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(str);
            Scanner scanner = new Scanner(file);

            //renvoie true s'il y a une autre ligne à lire
            while(scanner.hasNextLine())
            {
                System.out.println(scanner.nextLine());
            }


            scanner.close();
        }
        catch(
                IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("\n");
        System.out.println("Un fichier comprenant la solution du labyrinthe s'est créée dans votre dossier");
        execute(maze1);


    }





    private static void execute(File file) throws Exception {
        Maze maze = new Maze(file);
        dfs(maze);

    }


    private static void dfs(Maze maze) {
        MazeSolver dfs = new MazeSolver();
        List<Coordinate> path = dfs.solve(maze);
        maze.printPath(path);
        maze.reset();

    }

}