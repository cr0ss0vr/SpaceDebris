package client;

/*
 * This is a very simple class that encapsulates the loading
 * of images only.
 * 
 * Access from other classes: 
 *     Image myImg = GameImage.loadImage("C://MyImage.gif");
 */

import java.awt.*;              // Graphics stuff from the AWT library, here: Image
import java.io.File;            // File I/O functionality (for loading an image)
import javax.swing.ImageIcon;   // All images are used as "icons"



public class GameImage
{
    public static Image loadImage(String imagePathName) {
        
        // All images are loades as "icons"
        ImageIcon i = null;
        
        // Try to load the image
        File f = new File(imagePathName);
        
        
        if(f.exists()) {  // Success. Assign the image to the "icon"
            i = new ImageIcon(imagePathName);
        }
        else {           // Oops! Something is wrong.
            System.out.println("\nCould not find this image: "+imagePathName+"\nAre file name and/or path to the file correct?");            
            System.exit(0);
        }
        
        // Done. Either return the image or "null"
        return i.getImage();
        
    } // End of loadImages method

}
