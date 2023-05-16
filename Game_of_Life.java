/**
 * Create Game_of_Life using text format.
 *
 * @author Charlie Chen
 * @version 15/05/2023
 */
import java.util.Scanner;
public class Game_of_Life
{
    Scanner keyboard = new Scanner(System.in);

    int gridSize = 30;
    String [][] grid = new String [gridSize][gridSize];
    String [][] savedGrid = new String [gridSize][gridSize];

    int coordinateX = 0;
    int coordinateY = 0;

    
    /**
     * Constructor for objects of class Game_of_Life
     */
    public Game_of_Life()
    {
        initialGrid();
        displayGrid();
        menu();
    }

    void initialGrid()
    {
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                grid[x][y] = " - ";
            }
        }
    }

    void displayGrid()
    {
        System.out.println("\u000c");
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                System.out.print(grid[x][y]);
            }
            System.out.println(" ");
        }
        instruction();
    }

    void instruction()
    {
        System.out.println("t - turning cells on:' o ' or off:' - '");
        System.out.println("a - advancing a generation");
        System.out.println("m - advancing a provided number of generations");
        System.out.println("r - resetting all the cells to off");
        System.out.println("q - quitting");
    }

    void menu()
    {
        System.out.println("select from menu: ");
        String input = keyboard.nextLine().toLowerCase();
        switch (input){
            case "t": onOff();
            break;
            case "a": oneGeneration();
            break;
            case "m": multipleGenertaion();
            break;
            case "r": reset();
            break;
            case "q": quit();
            break;
            default: 
            System.out.println("");
            System.out.println("Wrong input:(");
            menu();
            break;
        }
    }

    void onOff()
    {
        System.out.println("");
        System.out.println("you have selected t");

        getCoordinates("turn the cells on or off by enter its coordinate in the form - x,y");
        newGrid();
        displayGrid();

        menu();
    }

    void getCoordinates(String prompt)
    {
        System.out.println(prompt);
        String[] numbers = keyboard.nextLine().split(",");
        while (numbers.length !=2 || checkNumbers(numbers) != true){
            System.out.println("incorrect input :( " + prompt);
            numbers = keyboard.nextLine().split(",");
        }
        coordinateX = Integer.parseInt(numbers[0]);
        coordinateY = Integer.parseInt(numbers[1]);
    }

    boolean checkNumbers(String[] integers)
    {
        for (int i=0; i<integers.length;i++){
            for (int j=0; j<integers[i].length();j++){
                if(integers[i].charAt(j) > '9' || integers[i].charAt(j) < '0'){
                    return false;
                }
            }
        }
        return true;
    }

    void newGrid(){
        if (grid[coordinateX][coordinateY] == " - "){
            grid[coordinateX][coordinateY] = " O ";
        } else{
            grid[coordinateX][coordinateY] = " - ";
        }
    }

    
    
    
    
    
    void oneGeneration()
    {
        System.out.println("");
        System.out.println("You have selected a");
        
        savedGrid = grid;
        checkLiveCells();
    }

    void checkLiveCells()
    {
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                if(grid[x][y].equals(" O ")){                    
                    coordinateX = x;
                    coordinateY = y;
                    
                    checkUnderpopulation();
                    rule2();
                    rule3();
                    rule4();
                }
            }
        }
    }

    void checkUnderpopulation(){
        int adjecentLiveCells = 0;
        if(grid[coordinateX - 1][coordinateY].equals(" O ")){
            adjecentLiveCells += 1;
        }
        if(grid[coordinateX + 1][coordinateY].equals(" O ")){
            adjecentLiveCells += 1;
        }
        if(grid[coordinateX][coordinateY - 1].equals(" O ")){
            adjecentLiveCells += 1;
        }
        if(grid[coordinateX][coordinateY + 1].equals(" O ")){
            adjecentLiveCells += 1;
        }
        if(adjecentLiveCells < 2){
            savedGrid[coordinateX][coordinateY] = " - ";
        }
    }

    void rule2(){
    }

    void rule3(){
    }

    void rule4(){
    }

    
    
    
    
    
    void multipleGenertaion()
    {
        System.out.println("m - advancing a provided number of generations");
    }

    void reset()
    {
        initialGrid();
        displayGrid();
        System.out.println("");
        System.out.println("you have selected r");
        System.out.println("the grid has been reset");
        menu();
    }

    void quit()
    {
        System.out.println("q - quitting");
    }
}
