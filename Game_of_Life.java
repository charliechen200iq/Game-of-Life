/**
 * Create Game_of_Life using text format.
 *
 * @author Charlie Chen
 * @version 25/05/2023
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
    int intervalSpeed = 500;

    /**
     * Constructor for objects of class Game_of_Life
     */
    public Game_of_Life()
    {
        startGame();
    }

    void startGame(){
        initialGrid();
        System.out.println("\u000c");
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                System.out.print(grid[x][y]);
            }
            System.out.println(" ");
        }
        System.out.println("Welcome to Conway's Game of Life, press enter start the game.");
        String input = keyboard.nextLine().toLowerCase();

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

    //commands 1: turns the cell on/off and display it 
    void onOff()
    {
        System.out.println("");
        System.out.println("you have selected t");

        getCoordinates("turn the cells on or off by enter its coordinate in the form - x,y");
        switchCells();
        displayGrid();

        menu();
    }

    //get the valid coordinates
    void getCoordinates(String prompt)
    {
        System.out.println(prompt);
        String[] numbers = keyboard.nextLine().split(",");
        while (numbers.length !=2 || checkNumbers(numbers) != true){
            System.out.println("incorrect input :( " + prompt);
            numbers = keyboard.nextLine().split(",");
        }
        coordinateX = Integer.parseInt(numbers[1]);
        coordinateY = Integer.parseInt(numbers[0]);
    }

    //checks that the coordinates are valid numbers
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
        for (int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                grid[x][y] = savedGrid[x][y];
            }
        }
        displayGrid();
    }

    //gets all the cells for the next generations
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
        displayGrid();
        System.out.println("");
        System.out.println("you have selected r");
        System.out.println("the grid has been reset");
        menu();
    }

    //commands 5: quite the game and go back to starting screen
    void quit()
    {
        startGame();
    }
}