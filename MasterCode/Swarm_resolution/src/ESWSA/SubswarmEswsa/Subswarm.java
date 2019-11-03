package ESWSA.SubswarmEswsa;

import Environnement.Dataset;
import Environnement.Position;
import EvitementObs.Utility;

import java.awt.*;
import java.util.*;


/**
 * Created by masterubunto on 04/05/19.
 */
public class Subswarm {

    public ArrayList<Elephant> swarm;
    public int size;
    public int count;
    public Position Xmax;
    public Position Xmin;
    public Position Gbest;
    public double Gbestvalue;
    HashSet<Position> solutioons ;
    public int maxchances=35;
    public int color;
   //TODO: maxchances as parametre for GA

    public Subswarm(int taille)
    {
        swarm= new ArrayList<> (  );
        for (int i=0;i<taille;i++)
        {
            swarm.add ( new Elephant (  ) );
            // System.out.println ("i"+i );
        }

        Gbest= new Position (  );
        solutioons = new HashSet<> (  );

    }

    public void setXmax(Position xmax) {
        Xmax = xmax;
    }

    public void setXmin(Position xmin) {
        Xmin = xmin;
    }

    public Position getGbest() {
        return Gbest;
    }

    public void setGbest(Position gbest) {
        Gbest = gbest;
    }

    public void InitPositionsofswarm(Dataset env)
    {
        /** choosing positions for elephants  of a swarm according to their zones**/
         /*TODO: add do while e.x value in environnement is -1*/

        for (Elephant e:swarm) {


            Random r= new Random (  );


         /*   if(e.X.Y==e.X.X && e.X.X==0)
                e.X=new Position ((r.nextInt(Xmax.X-Xmin.X)+Xmin.X),r.nextInt(Xmax.Y-Xmin.Y)+Xmin.Y  );
            else
                e.X= Utility.checkNextArea ( env, e.X,new Position (r.nextInt(Xmax.X-Xmin.X)+Xmin.X,r.nextInt(Xmax.Y-Xmin.Y)+Xmin.Y  ),10,0,new TreeSet());
//*/

                e.X= Utility.checkNextArea ( env, e.X,new Position (r.nextInt(Xmin.Y-Xmin.X)+Xmin.X,r.nextInt(Xmax.Y-Xmax.X)+Xmax.X  ),10,0,new TreeSet());


            e.V=new Position ( r.nextInt(Math.abs (Xmin.Y-Xmin.X)),r.nextInt(Math.abs (  Xmax.Y-+Xmax.X ) ));

//            e.V=new Position ( r.nextInt(Math.abs (Xmax.X-Xmin.X)),r.nextInt(Math.abs (  Xmax.Y-+Xmin.Y ) ));

            e.Pbest=new Position ( e.X.X,e.X.Y );
            e.Pbestfitness=env.getValue ( e.Pbest.X,e.Pbest.Y );
            env.editposition ( e.X,e.X, new Color ( color,color,120),10 );
           // System.out.println ("equipe:"+color+"elephant:"+e.X.toString ());

        }


    }
    public void Updatevelocity(double p,double wt,Position gbest)
    { /*TODO:check the zone for updating velocity*/
        for(Elephant e:swarm)
        {
            //double size=Math.sqrt ( Math.pow ( Xmax.X-Xmin.X ,2) +Math.pow ( Xmax.Y-Xmin.Y,2 ));
            double size=Math.sqrt ( Math.pow ( Xmax.Y-Xmax.X ,2) +Math.pow ( Xmin.X-Xmin.Y,2 ));
            //System.out.println ((int)(size ));
            e.updatevelocity ( p,(int)(size ),wt,gbest );

        }
    }
    public void Updateposition(Dataset env)
    {
        for(Elephant e:swarm)
    {
        e.updateposition ( Xmax,env,Xmin );
        env.editposition ( e.X,e.X, new Color ( color,(color*2)%255,120 ),10 );


    }

    }
    public void Evaluate(Dataset d,int localzine)
    {   /*     int currentsize=solutioons.size ();
        if(currentsize==size)
        {
            count++;

        }
        if(count>maxchances)
        {
            count=0;
            this.InitPositionsofswarm (d);

        }
        */
        double fitness=0;
        for(Elephant e:swarm)
        {
            e.Evaluate ( d,localzine );
            fitness+=e.getPbestfitness ();
            if(Gbestvalue< e.getPbestfitness())
            {Gbest=e.getPbest();
                Gbestvalue=e.getPbestfitness();}
            if(e.getPbestfitness()==1)
            { /*checking */
                solutioons.add ( e.getPbest() );
                //  System.out.println (e.Pbest.toString () );
                size=solutioons.size ();
                d.shutdown ( Xmax,e.Pbest );
               // e.Pbest=new Position ( (e.X.X)%Xmax.X,(e.X.Y)%Xmax.Y );
                e.Pbestfitness=d.getValue ( e.Pbest.X,e.Pbest.Y );
                Gbestvalue=d.getValue ( Gbest );
//                //e.Pbestfitness=0;
//                Gbest=new Position (  );
//                Gbestvalue=0;


            }

        }
        if(fitness ==0) count++;
        if(count>maxchances) {InitPositionsofswarm ( d ); count=0; }
    }
    public void distancebetweenmembers()
    {
        System.out.println ("start:"+Xmin+",end:"+Xmax );
        for(int i=0;i<swarm.size ()-1;i++)
            for(int j=i+1;j<swarm.size ();j++)
                System.out.println ("Distanceelephant:("+i+","+j+"):"+Math.sqrt ( Math.pow ( swarm.get ( i ).X.X-swarm.get ( j ).X.X ,2) +Math.pow ( swarm.get ( i ).X.Y-swarm.get ( j ).X.Y,2 )  ) );

    }
}