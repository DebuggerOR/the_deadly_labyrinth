
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Maze Loader, loads a maze from a text file
 */
public class MazeLoader {
    /**
     * Static function to load a maze from the contents of a file.
     * The maze is loaded from a text file which stores the maze like
     * so:
     * 5
     * 9
     * 10
     * 9
     * -s#-h#h-#
     * k##-##-##
     * -#d----d-
     * -###-#-#k
     * ---d---##
     * --##h#--#
     * -##-##-##
     * -d--#---d
     * ###-##h#-
     * #h-d--##e
     * The first two lines are the dimensions of the maze. Then follows
     * the layout of the maze. Each cell in the maze is represented as
     * a character. An '-' represents an open space in the maze, an '#'
     * represents an occupied space (walls) and 's' and 'e' are the
     * start and end of the maze respectively.
     *
     * @param filename The name of the file to load
     * @return A list of Cell objects that make up the maze
     */
    static public ArrayList<Cell> MakeMaze(String filename) {
        // variables to store the data in the file
        int[] _dimensions = {0, 0};
        String[] _maze_string = {};

        // read the data from the file
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            try {
                // read the dimensions
                _dimensions[0] = Integer.parseInt(input.readLine());
                _dimensions[1] = Integer.parseInt(input.readLine());
                // read the maze data
                _maze_string = new String[_dimensions[0]];
                for (int i = 0; i < _dimensions[0]; i++) {
                    // read in lines in reverse order otherwise the grid layout gets reflected
                    _maze_string[_dimensions[0] - i - 1] = input.readLine();
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            System.out.println("Failed to open maze file\n" + ex.toString());
            System.exit(1);
        }

        // Go through the data and create the maze
        ArrayList<Cell> maze = new ArrayList<Cell>();
        // Iterate through each cell in the grid
        for (int i = 0; i < _dimensions[0]; ++i) {
            for (int j = 0; j < _dimensions[1]; ++j) {
                // If that space in the grid is not occupied...
                if (_maze_string[i].charAt(j) != '#') {
                    // Start with a cell with four walls
                    boolean[] walls = {true, true, true, true};
                    // Look at the neighbouring cells. If they are not occupied remove the wall
                    if (i > 0 && _maze_string[i - 1].charAt(j) != '#') {
                        walls[0] = false;
                    }
                    if (i < (_dimensions[0] - 1) && _maze_string[i + 1].charAt(j) != '#') {
                        walls[1] = false;
                    }
                    if (j > 0 && _maze_string[i].charAt(j - 1) != '#') {
                        walls[2] = false;
                    }
                    if (j < (_dimensions[1] - 1) && _maze_string[i].charAt(j + 1) != '#') {
                        walls[3] = false;
                    }

                    // If this cell is the start or end point, add the appropriate marker
                    Cell new_cell = new Cell(walls, i, j);
                    if (_maze_string[i].charAt(j) == 's') {
                        new_cell.addItem(new GameObj(GameObj.ObjType.START));
                    }
                    //create end stage object
                    if (_maze_string[i].charAt(j) == 'e') {
                        new_cell.addItem(new GameObj(GameObj.ObjType.END));
                    }
                    //create heal object
                    if (_maze_string[i].charAt(j) == 'h') {
                        new_cell.addItem(new GameObj(GameObj.ObjType.HEAL));
                    }
                    //create decrease live object
                    if (_maze_string[i].charAt(j) == 'd') {
                        new_cell.addItem(new GameObj(GameObj.ObjType.DAMAGE));
                    }
                    //create key object for this stage
                    if (_maze_string[i].charAt(j) == 'k') {
                        new_cell.addItem(new GameObj(GameObj.ObjType.ENDKEY));
                    }
                    // Add this cell to the maze
                    maze.add(new_cell);
                }
            }
        }

        // Return the completed maze
        return maze;
    }
}
