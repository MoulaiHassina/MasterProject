package Multi_Regions;

import Environnement.Position;
import Environnement.Dataset;

import java.util.ArrayList;

/**
 * Created by masterubunto on 06/04/19.
 */
public class Region {

    public int nbcase = 0;
    public long time;
    public int rbregions;
    ArrayList<Position> all;




    public long search(Dataset env, int nbtarget, long time, Position debut,Position fin) {
        double b = -1;
        double localbest = 0;
        Position local_best = new Position ( );

         System.out.println ("deb:"+debut.toString ()+":fin:"+fin.toString () );
        for (int i = debut.X; i < (fin.X); i++) {
            for (int j = debut.Y; j < (fin.Y); j++) {
                //nbcase++;


                //  System.out.println ("case:"+nbcase+ "i:"+i+":j:"+j );

                b = env.getValue ( i, j );
                //System.out.println ( "b:"+b+":i"+i+":j:"+j);
                if (localbest < b) {

                    localbest = b;

                    //   local_best=new Position ( i,j );
                    //  this.debut=local_best;
                    if (localbest == 1) {
                        nbcase++;

                        if (nbtarget == nbcase) ;

                        {
                            this.time = System.currentTimeMillis ( );
                            return 0;
                        }
                    }


                    //  System.out.println ("solution:"+local_best.toString () );
                }


            }
        }
        return time;

    }


    public ArrayList<Position> defineallregions(int range, int sizeenv) {
        ArrayList<Position> positions = new ArrayList<> ( );

        int rangesize = ((range * 2) + 1);

        int region = 0;

        if (rangesize > sizeenv) rangesize = sizeenv;

        int k = 0;
        Position p = new Position ( k, region );
        positions.add ( p );

        while (k <=sizeenv) {

         System.out.println ("debut:"+ k +","+region );

            region = region + rangesize;

            if (region >= sizeenv) {
                k = k + rangesize;
                region = 0;
            }


            p = new Position ( k, region );
            positions.add ( p );



        }

//        if(! p.identic ( new Position (  sizeenv-1,sizeenv-1 )))

        rbregions = positions.size ( );
        this.all = positions;

        return positions;


    }


    public void lookfortargets(ArrayList<Position> p, Dataset env, int portee, int nbtarget, long end) {
        int i = 0;
        Position debut ;
        Position fin = new Position ( 0,0 );

        while (i < rbregions && nbcase < nbtarget) {
            debut = p.get ( i );
            if (debut.X + (portee * 2) + 1 > env.size)

                fin.X = env.size;
            else fin.X = debut.X + (portee * 2) + 1;

            if (debut.Y + (portee * 2) + 1 > env.size)

                fin.Y = env.size;

            else fin.Y = debut.Y + (portee * 2) + 1;

            //   System.out.println ("debut:"+pi.toString ()+"fin:"+fin.toString ());

            search ( env, nbtarget, end ,debut,fin);

            i++;
        }

        System.out.println ( "regions seen:" + i );


    }

    public ArrayList<Robot> nb_robots_regions(int nbrobots) {
        int nbregions_per_robots = (int) (this.all.size ( ) / (double) nbrobots);
        System.out.println ( "regions:"+this.rbregions+
                ":each robots got:" + nbregions_per_robots
        +"le reste:"+(this.rbregions-(nbregions_per_robots*nbrobots)));

        int i,j=0,k=0;
        ArrayList<Robot> robots= new ArrayList<> (  );
        for(i=0;i<nbrobots;i++)
        {
            robots.add ( new Robot ( new ArrayList<> (  ),nbregions_per_robots ) );

            while(j<this.rbregions && k < nbregions_per_robots)
            {
                System.out.println ("debut:"+all.get ( j ) );
                robots.get ( i ).addregion ( all.get ( j ) );
                k++;
                j++;
            }
            k=0;

        }
        while (j<this.rbregions)
        {
            robots.get ( nbrobots-1 ).addregion ( all.get ( j) );
            j++;
        }




       /* System.out.println ("robots" +robots.size () );
        /**display**/
    /*    for( Robot robot: robots)
        {
            robot.display ();

*/

         return robots;

    }

    public static void main(String[] args) throws Exception {


        Region region = new Region ();
        int size=1000;
        int range=250;

        region.defineallregions ( range,size );

        Dataset d = new Dataset ( size );

        d.Inittarget ( 10 );

        d.Inittarget ( 10);

        SearchElephant Eho= new SearchElephant (     region.nb_robots_regions ( 2),d,2);
        Eho.Search (range);



}
}