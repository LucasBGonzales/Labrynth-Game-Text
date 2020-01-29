package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Descriptor imports all the descriptions from descriptors.txt, then provides a useful method to
 * retrieve a random descriptor.
 * @author Lucas Gonzales
 * @version 23.11.2017
 * @version 26.11.2017
 * • Added generateDescription so that I could generate a completely unique description
 *      with no overlap (such as saying the walls are wood, then saying the walls are stone.)
 *      • This renders getRandomDescriptor obsolete to the game.
 */
public class Descriptor {
    private static ArrayList<String[]> m_descriptors;
    
    /**
     * Constructor
     * Calls importDescriptors()
     */
    public Descriptor()
    {
        m_descriptors = new ArrayList<String[]>();
        importDescriptors();
    }
    
    /**
     * Imports descriptors from descriptors.txt
     */
    private void importDescriptors()
    {
        //Set up file with directory.
        File file = new File("resources/descriptors.txt");
        Scanner s;
        
        //Open file, handle error if it doesn't exist.
        try {
            s = new Scanner(file);
        } catch (FileNotFoundException e) {
            game.Game.logger.error("descriptors.txt Not Found."
                    + "\nMake sure descriptors.txt exists within the resources folder.");
            e.printStackTrace();
            return;
        }
        
        //Import descriptors
        while(s.hasNextLine())
        {
            //Process next line
            String nextLine;
            String[] descriptors;
            nextLine = s.nextLine();
            //Split like descriptors and add them to the list.
            descriptors = nextLine.split("/");
            m_descriptors.add(descriptors);
        }
        s.close();
    }
    
    /**
     * Generates a completely unique description of a room.
     * @param arg0 The base size of the description. (How many descriptors used.)
     * @param arg1 The desviation from the size of the description, so 
     *              that some rooms may have more details than others.
     * @return ArrayList<String> A description of a room.
     */
    public ArrayList<String> generateDescription(int arg0, int arg1)
    {
        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String[]> tempDescriptors = new ArrayList<String[]>(m_descriptors);
        //Base random generation
        int randNum = (int) (Math.random() * arg0) + 1;
        //Deviation random generation
        int randDev = (int) ((Math.random() * (arg1 * 2)) - arg1) + 1;
        randNum += randDev;
        //Avoid asking for more descriptors than there are to give.
        if(randNum >= tempDescriptors.size())
            randNum = tempDescriptors.size() - 1;
        
        //Grab random descriptors.
        for(int i=0; i < randNum; i++)
        {
            //Get a random index
            int descIndex = (int) ( Math.random() * (tempDescriptors.size()) );
            //Add descriptor to the description.
            int randLength = (int) (Math.random() * tempDescriptors.get(descIndex).length);
            description.add(tempDescriptors.get(descIndex)[randLength]);
            //Remove from tempDescriptors
            tempDescriptors.remove(descIndex);
        }
        return description;
    }
    
    /**
     * OBSOLETE
     * Get a random descriptor from this Descriptor's archive.
     * @return A random descriptor.
     */
    public String getRandomDescriptor()
    {
        int rand1, rand2;
        rand1 = (int) (Math.random() * m_descriptors.size());
        rand2 = (int) (Math.random() * m_descriptors.get(rand1).length);
        return m_descriptors.get(rand1)[rand2];
    }
}
