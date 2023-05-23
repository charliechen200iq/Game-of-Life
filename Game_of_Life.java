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
        for (int i=0; i<integers.length;i++){
            if(Integer.parseInt(integers[i]) > (gridSize-1) || Integer.parseInt(integers[i]) < 0){
                return false;
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
        nextGenerationCells();
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                grid[x][y] = savedGrid[x][y];
            }
        }
        displayGrid();

        System.out.println("");
        System.out.println("You have selected a");
        System.out.println("This is the next generation");
        menu();
    }

    void nextGenerationCells()
    {
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                if(grid[x][y].equals(" O ")){                    
                    // check if the live cell has <2 or 2/3 or >3 live adjacent cells
                    coordinateX = x;
                    coordinateY = y;

                    checkLiveCells();
                } else {                    
                    // check if the dead cell has 3 live adjacent cells
                    coordinateX = x;
                    coordinateY = y;

                    checkDeadCells();
                }
            }
        }
    }

    // check if the live cell has <2 or 2/3 or >3 live adjacent cells
    void checkLiveCells(){
        if(numberOfAdjacentLiveCells() < 2){
            savedGrid[coordinateX][coordinateY] = " - ";
        } else if(numberOfAdjacentLiveCells() > 3){
            savedGrid[coordinateX][coordinateY] = " - ";
        } else {
            savedGrid[coordinateX][coordinateY] = " O ";
        }
    }

    // check if the dead cell has 3 live adjacent cells
    void checkDeadCells(){
        if(numberOfAdjacentLiveCells() == 3){
            savedGrid[coordinateX][coordinateY] = " O ";
        } else {
            savedGrid[coordinateX][coordinateY] = " - ";
        }
    }

    //returns the number of adjacent live cells
    int numberOfAdjacentLiveCells(){
        int numberOfAdjacentLiveCells = 0;
        if(coordinateX == 0 && coordinateY == 0){
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX == (gridSize -1) && coordinateY == 0){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX == 0 && coordinateY == (gridSize -1)){
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX == (gridSize -1) && coordinateY == (gridSize -1)){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX != 0 && coordinateY == 0){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX != 0 && coordinateY == (gridSize -1)){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX == 0 && coordinateY != 0){
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX == (gridSize -1) && coordinateY != 0){
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        }
        
        if(coordinateX != 0 && coordinateY != 0){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY + 1].equals(" O "))numberOfAdjacentLiveCells += 1;
        }

        return numberOfAdjacentLiveCells;
    }

    /*  int adjecentLiveCells = 0;
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
    if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")){
    adjecentLiveCells += 1;
    }
    if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")){
    adjecentLiveCells += 1;
    }
    if(grid[coordinateX + 1][coordinateY + 1].equals(" O ")){
    adjecentLiveCells += 1;
    }
    if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")){
    adjecentLiveCells += 1;
    }
    if(adjecentLiveCells < 2){
    savedGrid[coordinateX][coordinateY] = " - ";
    }
     */

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
