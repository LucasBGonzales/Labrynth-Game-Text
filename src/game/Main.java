package game;

import utility.Log;

/**
 * Main class. Runs Labyrinth game.
 * @author Lucas Gonzales
 * @version 23.11.2017
 * @version 27.11.2017
 * • Modified the main method accept some parameters
 *      • Log Level, Debug Enabled, Size of Labyrinth
 */
public class Main {

    /**
     * Argument order:
     * Labyrinth Size, Log Level, DebugEnabled
     * Minumum Labyrinth Size = 7
     * Log Levels:
     * LEVEL_DISABLED = 0;
     * LEVEL_ERROR = 1;
     * LEVEL_WARNING = 2;
     * LEVEL_INFO = 3;
     */
    public static void main(String[] args) {
        Game game;
        int logLevel = Log.LEVEL_DISABLED;
        int size=0;
        boolean debug = true;
        //Handle Labyrinth Size
        if(args.length >= 1)
        {
            size = Integer.parseInt(args[0]);
        }
        //Handle logging level
        if(args.length >= 2)
        {
            logLevel = Integer.parseInt(args[1]);
        }
        //Handle debug enabled
        if(args.length >= 3)
        {
            if(args[2].equals("false"))
                debug = false;
        }
        
		game = new Game(logLevel, debug, size);
		game.play();
	}

}
