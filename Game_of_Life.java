
/**
 * Create Game_of_Life using text format.
 *
 * @author Charlie Chen
 * @version 8/05/2023
 */
public class Game_of_Life
{
    int gridSize = 30;

    /**
     * Constructor for objects of class Game_of_Life
     */
    public Game_of_Life()
    {
        Grid();
    }

    void Grid()
    {
        String [][] grid = new String [gridSize][gridSize];
        for (int x = 0; x < gridSize; x++)
            for(int y = 0; y < gridSize; y++)
            grid [x][y] = " - ";
    }
}
