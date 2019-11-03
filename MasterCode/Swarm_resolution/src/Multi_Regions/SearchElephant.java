package Multi_Regions;

import Environnement.Dataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by masterubunto on 12/04/19.
 */
public class SearchElephant
{

    public ArrayList<Robot> Elephants;
    public int nbtargets;
    public int itertions;
    public Dataset dataset;


    public SearchElephant(ArrayList<Robot> elephants,Dataset data,int nbtargets) {
        Elephants = elephants;
       this.nbtargets=nbtargets;
       itertions=5500;
       dataset=data;
    }


    public void Search(int portee)
    { // initImage ();
        int target=0,i=0, j=0,maxsizeregions=Elephants.get ( Elephants.size ()-1 ).regions.size ();
        Robot.portee=portee;

//      g= image.createGraphics ();



        while(target<nbtargets && i < itertions)
        {
             if(j<maxsizeregions){
              for (Robot elephant: Elephants)
             {
                 System.out.println ("elephant:"+Elephants.indexOf ( elephant ) );

                 elephant.search_region ( j ,dataset);

                 target=Robot.nbtargetfound;


             } j++;

             }
             i++;

        }

        System.out.println ("targets:"+target+"iter:"+i );

      //  writeimage ();
    }
}
