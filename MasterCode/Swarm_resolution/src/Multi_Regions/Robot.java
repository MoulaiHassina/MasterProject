package Multi_Regions;

import Environnement.Position;
import ESWSA.SimpleESWSA.ESWSA;
import Environnement.Dataset;

import java.util.ArrayList;


public class Robot
{
    public ArrayList<Position> regions;
    public static int nbtargetfound=0;
    public static int portee;
    public static int nbtargets;
    public int sizeregions;


    public Robot(ArrayList<Position> regions, int sizeregions)
    {
        this.regions = regions;
        this.sizeregions = sizeregions;
    }


    public static void setNbtargetfound(int nbtargetfound) {
        Robot.nbtargetfound = nbtargetfound;
    }

    public static void setPortee(int portee) {
        Robot.portee = portee;
    }

    public static void setNbtargets(int nbtargets) {
        Robot.nbtargets = nbtargets;
    }

    public void search_region(int i, Dataset env)
    {
        {
            // System.out.println (i );
            if (i < regions.size ( )) {
              //  System.out.println ( regions.get ( i ).toString ( ) );

                Position debut = regions.get ( i );

                Position fin = new Position ( );

                if (debut.X + (portee * 2) + 1 > env.size)

                    fin.X = env.size;
                else fin.X = debut.X + (portee * 2) + 1;

                if (debut.Y + (portee * 2) + 1 > env.size)

                    fin.Y = env.size;

                else fin.Y = debut.Y + (portee * 2) + 1;

                double b = -1;
                double localbest = 0;
                Position local_best = new Position ( );

                fin.X-=1;
                fin.Y-=1;
                System.out.println ("deb:"+debut.toString ()+":fin:"+fin.toString () );

                ESWSA el = new ESWSA ( 2, 1500, fin, debut, 0.6, env, 0.4 );


                nbtargetfound+=el.Run(1);
                /*

                // System.out.println ("deb:"+debut.toString ()+":fin:"+fin.toString () );
                for (i = debut.X; i < (fin.X); i++) {
                    for (int j = debut.Y; j < (fin.Y); j++) {

                        b = env.getValue ( i, j );
                   //   System.out.println ("i,j="+i+","+j );
                        if (localbest < b) {

                            localbest = b;
                            if (localbest == 1) {
                             //   System.out.println ( "found" );
                                nbtargetfound++;

                                if (nbtargetfound == nbtargets) ;

                                {
                                    System.out.println ("end search" );
                                    //this.time=System.currentTimeMillis ();
                                }
                            }
                        }


                        //  System.out.println ("solution:"+local_best.toString () );
                    }


                }*/
            }

        }



    }

    public void addregion(Position p)
    {
        if(this.regions==null) this.regions= new ArrayList<> (  );
        this.regions.add ( p );
    }
    public void display ()
    {
        System.out.println ("nbregions:"+regions.size () );
    }







}
