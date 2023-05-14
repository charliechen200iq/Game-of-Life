/**
 * Create Game_of_Life using text format.
 *
 * @author Charlie Chen
 * @version 15/05/2023
 */
import java.util.Scanner;
public class Game_of_Life
{
    int gridSize = 30;
    String [][] grid = new String [gridSize][gridSize];
    
    /**
     * Constructor for objects of class Game_of_Life
     */
    public Game_of_Life()
    {
        initialGrid();
        displayGrid();
        instruction();
        menu();
    }

    void initialGrid()
    {
        for (int x = 0; x < gridSize; x++)
            for(int y = 0; y < gridSize; y++)
                grid[x][y] = " - ";
    }
    
    void displayGrid()
    {
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++)
                System.out.print(grid[x][y]);
            System.out.println(" ");
        }
    }
    
    void instruction()
    {
        System.out.println("T - turning cells on:' o ' or off:' - '");
        System.out.println("A - advancing a generation");
        System.out.println("M - advancing a provided number of generations");
        System.out.println("R - resetting all the cells to off");
        System.out.println("Q - quitting");
    }
    
    void menu()
    {

    }
}
