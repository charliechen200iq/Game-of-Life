/**
 * Create Game_of_Life using text format.
 *
 * @author Charlie Chen
 * @version 30/05/2023
 */
import java.util.Scanner;
public class Game_of_Life
{
    Scanner keyboard = new Scanner(System.in);

    //the number of cells of the lenth of the grid  
    int gridSize = 30;
    //2D array which store all the cells of the grid.
    String [][] grid = new String [gridSize][gridSize];
    //a sperate 2D array which saves all the changes from the original grid; used when advancing a generation
    String [][] savedGrid = new String [gridSize][gridSize];

    // saves the x coordinate of a cell when we want to do stuff with it
    int coordinateX = 0;
    // saves the y coordinate of a cell when we want to do stuff with it
    int coordinateY = 0;
    // how long each generation display for in milliseconds before moving to the next generation. 
    int intervalSpeed = 500;

    /**
     * Constructor for objects of class Game_of_Life
     */
    public Game_of_Life()
    {
        startGame();
    }

    //starting screen of the game and when enter key pressed the game starts
    void startGame(){
        initialGrid();
        System.out.println("\u000c");
        for (int y = 0; y < gridSize; y++){
            for(int x = 0; x < gridSize; x++){
                System.out.print(grid[x][y]);
            }
            System.out.println(" ");
        }
        System.out.println("Welcome to Conway's Game of Life, press enter start the game.");
        String input = keyboard.nextLine().toLowerCase();

        displayScreen();
        menu();
    }

    //This is the grid will all the cells turned to dead
    void initialGrid()
    {
        for (int y = 0; y < gridSize; y++){
            for(int x = 0; x < gridSize; x++){
                grid[x][y] = " - ";
            }
        }
    }

    //This function display the grid and instruction
    void displayScreen()
    {
        System.out.println("\u000c"); //clear the screen
        for (int y = 0; y < gridSize; y++){
            for(int x = 0; x < gridSize; x++){
                System.out.print(grid[x][y]);
            }
            System.out.println(" ");
        }
        instruction();
    }

    //This is the instruction of the menu
    void instruction()
    {
        System.out.println("t - turning cells on:' o ' or off:' - '");
        System.out.println("a - advancing a generation");
        System.out.println("m - advancing a provided number of generations");
        System.out.println("r - resetting all the cells to off");
        System.out.println("q - quitting");
    }

    //This is the menu which asks for input and turns the corresponding functions. 
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

    //commands 1: turns the cell on/off and display it
    void onOff()
    {
        System.out.println("");
        System.out.println("you have selected t");

        getCoordinates("turn the cells on or off by enter its coordinate in the form - x,y");
        switchCells();
        displayScreen();

        menu();
    }

    //get the valid coordinates
    void getCoordinates(String prompt)
    {
        System.out.println(prompt);
        String[] numbers = keyboard.nextLine().split(",");
        //make sure that the coordinates are in the form of x,y and the numbers are valid
        while (numbers.length !=2 || checkNumbers(numbers) != true){
            System.out.println("incorrect input :( " + prompt);
            numbers = keyboard.nextLine().split(",");
        }
        coordinateX = Integer.parseInt(numbers[0]);
        coordinateY = Integer.parseInt(numbers[1]);
    }

    //checks that the coordinates are valid numbers
    boolean checkNumbers(String[] integers)
    {
        //checks if the input coordinates are actully numbers
        for (int i=0; i<integers.length;i++){
            for (int j=0; j<integers[i].length();j++){
                if(integers[i].charAt(j) > '9' || integers[i].charAt(j) < '0'){
                    return false;
                }
            }
        }
        //cheks if the coordinates are within the limit
        for (int i=0; i<integers.length;i++){
            if(Integer.parseInt(integers[i]) > (gridSize-1) || Integer.parseInt(integers[i]) < 0){
                return false;
            }
        }
        return true;
    }

    //change the cells to on/off
    void switchCells(){
        if (grid[coordinateX][coordinateY] == " - "){
            grid[coordinateX][coordinateY] = " O ";
        } else{
            grid[coordinateX][coordinateY] = " - ";
        }
    }

    //commands 2: advance to the next generation and display it
    void oneGeneration()
    {
        nextGeneration();
        System.out.println("");
        System.out.println("You have selected a");
        System.out.println("This is the next generation");
        menu();
    }

    //go to the next generation
    void nextGeneration(){
        nextGenerationCells();
        for (int y = 0; y < gridSize; y++){
            for(int x = 0; x < gridSize; x++){
                grid[x][y] = savedGrid[x][y];
            }
        }
        displayScreen();
    }

    //gets all the cells for the next generations
    void nextGenerationCells()
    {
        for (int y = 0; y < gridSize; y++){
            for(int x = 0; x < gridSize; x++){
                if(grid[x][y].equals(" O ")){                    
                    coordinateX = x;
                    coordinateY = y;
                    
                    // check if the live cell has <2 or 2/3 or >3 live adjacent cells
                    checkLiveCells();
                } else {                    
                    coordinateX = x;
                    coordinateY = y;
                    
                    // check if the dead cell has 3 live adjacent cells
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
        } else if(coordinateX == (gridSize -1) && coordinateY == 0){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else if(coordinateX == 0 && coordinateY == (gridSize -1)){
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else if(coordinateX == (gridSize -1) && coordinateY == (gridSize -1)){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else if(coordinateY == 0){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else if(coordinateY == (gridSize -1)){
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else if(coordinateX == 0){
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX + 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else if(coordinateX == (gridSize -1)){
            if(grid[coordinateX][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY - 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY].equals(" O ")) numberOfAdjacentLiveCells += 1;
            if(grid[coordinateX - 1][coordinateY + 1].equals(" O ")) numberOfAdjacentLiveCells += 1;
        } else {
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

    //commands 3: advance mutiple generations and display it
    void multipleGenertaion()
    {
        System.out.println("");
        System.out.println("you have selected m");
        System.out.println("enter the number of generations you want to run");

        int number = numberOfGenerations();
        for (int i=0; i<number; i+=1){
            nextGeneration();
            try {
                Thread.sleep(intervalSpeed);
            } catch(Exception e) {
                System.out.println("Looks like something went wrong");
            }
        }

        menu();
    }

    //get the number of generation to advance
    int numberOfGenerations(){
        while (!keyboard.hasNextInt()){
            keyboard.nextLine();
            System.out.print("Invalid input. Please enter number");
        }
        int number = keyboard.nextInt();
        keyboard.nextLine();
        return number;
    }

    //commands 4: reset all the cells to dead
    void reset()
    {
        initialGrid();
        displayScreen();
        System.out.println("");
        System.out.println("you have selected r");
        System.out.println("the grid has been reset");
        menu();
    }

    //commands 5: quit the game and go back to starting screen
    void quit()
    {
        startGame();
    }
}