package Environnement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by masterubunto on 04/03/19.
 */
public class FileData {
    public int size;
    public double[][] matrix;
    int nbobstacle;
    public int nbtarget;
    public int portee;


    public void parsing(String path) throws IOException {
        BufferedReader scan = new BufferedReader (
                new InputStreamReader (
                        new FileInputStream ( path ), "UTF-8" ) );


        String line = "";
        int i=0,cpt=0,numline=0;
        boolean indicator=false;

            while (line != null) {
                cpt++;

                 if(cpt==2)
                 {
                     size=Integer.parseInt (  line.split ( ":" )[1].split ( "," )[0]) ;
                     matrix= new double[size][size];
                 }
                
		        if(cpt==3){
                     portee=Integer.valueOf(line);
                }

                 if (line.startsWith("cible:" )) nbtarget=Integer.parseInt (  line.split ( ":" )[1] );
                 if(line.startsWith("size" )) i++;
                 if(indicator){ read(numline,line.split ( " " )); numline++;}
                 if(line.startsWith ( "matrix::" ) ) indicator=true;

                 line=scan.readLine ();
            }
    }


    public double [][] read(int numline, String[] columnofline)
    {
          for (int i=0;i<columnofline.length;i++)
          {
              matrix[numline][i]=Double.parseDouble ( columnofline[i] );
             // System.out.print (matrix[numline][i] + "\t" );
          }
       // System.out.println ( );


        return matrix;
    }
}
