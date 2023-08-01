/**
 * Create Game_of_Life using text format.
 *
 * @author Charlie Chen
 * @version 01/08/2023
 */
import java.util.Scanner;
public class Game_of_Life
{
    Scanner keyboard = new Scanner(System.in);

    //the number of cells of that make up the lenth of the grid  
    final int GRID_SIZE = 30;
    //2D array with all the cells of the grid to display
    String [][] grid = new String [GRID_SIZE][GRID_SIZE];
    //a sperate 2D array which saves all the changes from the original grid; used when advancing a generation
    String [][] savedGrid = new String [GRID_SIZE][GRID_SIZE];
    //dead cell
    final String DEAD_CELL = "⬜ ";
    //live cell
    final String LIVE_CELL = "⬛ ";

    // saves the x coordinate of a cell when we want to do stuff with it
    int coordinateX;
    // saves the y coordinate of a cell when we want to do stuff with it
    int coordinateY;
    // how long each generation display for in milliseconds before moving to the next generation. 
    final int INTERVAL_SPEED = 500;
    // number of generations to run
    int numberOfGenerations;

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
        System.out.print("\u000c");
        for (int y = 0; y < GRID_SIZE; y++){
            for(int x = 0; x < GRID_SIZE; x++){
                System.out.print(grid[x][y]);
            }
            System.out.println(" ");
        }
        System.out.println("Welcome to Conway's Game of Life, enter anything to start the game.");
        String input = keyboard.nextLine().toLowerCase();

        displayScreen();
        menu();
    }

    //This is the grid will all the cells turned to dead
    void initialGrid()
    {
        for (int y = 0; y < GRID_SIZE; y++){
            for(int x = 0; x < GRID_SIZE; x++){
                grid[x][y] = DEAD_CELL;
            }
        }
    }

    //This function display the grid and instruction
    void displayScreen()
    {
        System.out.print("\u000c"); //clear the screen
        for (int y = 0; y < GRID_SIZE; y++){
            for(int x = 0; x < GRID_SIZE; x++){
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
            if(Integer.parseInt(integers[i]) > (GRID_SIZE-1) || Integer.parseInt(integers[i]) < 0){
                return false;
            }
        }
        return true;
    }

    //change the cells to on/off
    void switchCells(){
        if (grid[coordinateX][coordinateY] == DEAD_CELL){
            grid[coordinateX][coordinateY] = LIVE_CELL;
        } else{
            grid[coordinateX][coordinateY] = DEAD_CELL;
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
        for (int y = 0; y < GRID_SIZE; y++){
            for(int x = 0; x < GRID_SIZE; x++){
                grid[x][y] = savedGrid[x][y];
            }
        }
        displayScreen();
    }

    //gets all the cells for the next generations
    void nextGenerationCells()
    {
        for (int y = 0; y < GRID_SIZE; y++){
            for(int x = 0; x < GRID_SIZE; x++){
                if(grid[x][y].equals(LIVE_CELL)){                    
                    coordinateX = x;
                    coordinateY = y;
                    
                    // check if the live cell has <2 or 2/3 or >3 live adjacent cells
                    checkLIVE_CELLs();
                } else {                    
                    coordinateX = x;
                    coordinateY = y;
                    
                    // check if the dead cell has 3 live adjacent cells
                    checkDEAD_CELLs();
                }
            }
        }
    }

    // check if the live cell has <2 or 2/3 or >3 live adjacent cells
    void checkLIVE_CELLs(){
        if(numberOfAdjacentLIVE_CELLs() < 2){
            savedGrid[coordinateX][coordinateY] = DEAD_CELL;
        } else if(numberOfAdjacentLIVE_CELLs() > 3){
            savedGrid[coordinateX][coordinateY] = DEAD_CELL;
        } else {
            savedGrid[coordinateX][coordinateY] = LIVE_CELL;
        }
    }

    // check if the dead cell has 3 live adjacent cells
    void checkDEAD_CELLs(){
        if(numberOfAdjacentLIVE_CELLs() == 3){
            savedGrid[coordinateX][coordinateY] = LIVE_CELL;
        } else {
            savedGrid[coordinateX][coordinateY] = DEAD_CELL;
        }
    }

    //returns the number of adjacent live cells
    int numberOfAdjacentLIVE_CELLs(){
        int numberOfAdjacentLIVE_CELLs = 0;
        for(int dy = (coordinateY-1); dy <= (coordinateY+1); dy++){
            for(int dx = (coordinateX-1); dx <= (coordinateX+1); dx++){
                if(dx == coordinateX && dy == coordinateY){ //do nothing
                } else if (dx < 0 || dx >= GRID_SIZE || dy < 0 || dy >= GRID_SIZE){//do nothing
                } else if (grid[dx][dy].equals(LIVE_CELL)){ 
                    numberOfAdjacentLIVE_CELLs += 1;
                }
            }
        }

        return numberOfAdjacentLIVE_CELLs;
    }

    //commands 3: advance mutiple generations and display it
    void multipleGenertaion()
    {
        System.out.println("");
        System.out.println("you have selected m");
        System.out.println("enter the number of generations (>1 and <100) you want to run");
        
        numberOfGenerations(); //run this to the number of generation to advance
        System.out.println(numberOfGenerations);
        for(int i=0; i<numberOfGenerations; i+=1){
            nextGeneration();
            System.out.println("don't enter stuff until see 'select from menu:'");
            try {
                Thread.sleep(INTERVAL_SPEED);
            } catch(Exception e) {
                System.out.println("Looks like something went wrong");
            }
        }
        menu();
    }

    //get the number of generation to advance
    void numberOfGenerations(){
        while(!keyboard.hasNextInt()){
            keyboard.nextLine();
            System.out.println("Invalid input. Please enter a number");
        }
        numberOfGenerations = keyboard.nextInt();
        keyboard.nextLine();
        
        if(numberOfGenerations < 1 || numberOfGenerations > 100){
            System.out.println("Invalid input. Please enter a number that's >1 and <100");
            numberOfGenerations();
        }
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