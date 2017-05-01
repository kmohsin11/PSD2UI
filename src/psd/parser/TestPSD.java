/**
 * 
 */
package psd.parser;

import java.io.File;
import java.io.IOException;
import psd.model.Psd;



public class TestPSD {

    

   public static int android = 0;
   public static String dir= System.getProperty("user.dir");
    
    public static void main(String[] args) throws IOException {
         Psd psd = new Psd(new File(dir+"/src/psd/parser/t9_72_bl.psd"));
        if(android!=1){
            
            CreateStoryboard.create(psd);
        }
        else
        {
            CreateAndroid.create(psd);
        }
 
}
}
