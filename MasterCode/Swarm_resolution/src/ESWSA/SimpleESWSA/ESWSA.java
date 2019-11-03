package ESWSA.SimpleESWSA;

import ESWSA.SubswarmEswsa.Elephant;
import Environnement.Position;
import Environnement.Dataset;

import java.awt.*;
import java.util.*;
//import EHO_0.Elephant;

/**
 * Created by masterubunto on 25/03/19.
 */
public class ESWSA
{
    int N;
    int Tmax;
    int iteration;
    long timelasped,start;
    int size;
    static  Position Xmax;
    Position Xmin;
    Position Gbest;
    double randomP;
    int localsize=10;
    static Dataset env;
    double wt;
    int nbtargets=0;
    Elephant bestelephant;
    TreeMap<Double,ArrayList<Elephant>> elephants;
    HashMap<Position,Double> solutioons;
    int target=0;
    int i=1;


    public ESWSA(int N, int Iteration, Position xmax, Position xmin, double randomP, Dataset data, double wt)
    {
        this.N=N;
        this.Xmax=xmax;
        this.Xmin=xmin;
        this.randomP=randomP;
        this.env= new Dataset(data);
        this.size=env.size;
        this.wt=wt;
        this.Tmax=Iteration;
        this.elephants=new TreeMap<> (  );

       // ImageSettings();

    }




    /** displaying particles**/
    public void Show_Elephants()
    { /**displaying all the elephants**/

        for(Double fitness:elephants.keySet ())
        {
            for(Elephant el:elephants.get ( fitness ))
            {
                //if(i==0){bestelephant=el; Gbest=new Position ( el.X.X,el.X.Y );i=-1;}

               System.out.println (el.toString ());
            }
        }
    }

    /********************************************************************************/
    /********************************************************************************/
    public int getIteration() {
        return iteration;
    }

    public long getTimelasped() {
        return timelasped;
    }

    public int getNbtarget (){return nbtargets;}

    public void setNbtargets(int nbtargets) {
        this.nbtargets = nbtargets;
    }
    public int getI() {
        return i;
    }

    public int getTarget() {
        return target;
    }

    public static Dataset getEnv() {
        return env;
    }

    /********************************************************************************/
     public void Initeswsa(ArrayList<Position> elephantss)
     {
         env.create ( "eswsa" );
         /*initialisation*/
         //long start=System.currentTimeMillis ();
         env.prepare_editing_env ( "elephant2" );
         start=System.currentTimeMillis ();
         for(int i=0;i<N;i++)
         {
             //System.out.println (Xmax.X+":"+Xmin.X );
             Elephant eli=new Elephant ( i,Xmax.X,Xmin.X,env);


             if(elephants.get ( eli.Evaluate ( env,localsize ) ) ==null)
             {
                 elephants.put (eli.Evaluate (env,localsize),new ArrayList<Elephant> (  ) );
                 elephants.get ( eli.Fojbetive ).add ( eli );
             }
             else
                 elephants.get ( eli.Fojbetive ).add ( eli );

             eli.Pbestfitness=eli.Fojbetive;
             if(elephantss!=null && elephantss.size ()>0)
               eli.X=elephantss.get ( i );

             env.drawanimal ( eli.X );
         }
         env.writeimage ( 0,"eswsa" );

        /* debut de EHO_0*/

         solutioons = new HashMap<> (  );
         /** globalbest**/



         bestelephant=elephants.get ( (double )(elephants.firstKey ())).get ( 0 );
         Gbest=new Position (  bestelephant.Pbest.X,bestelephant.Pbest.Y);
     }
     public boolean Runui()
     { env.prepare_editing_env ( "elephant2" );
         start=System.currentTimeMillis ();
         if(i<=Tmax && target <this.nbtargets)
         {
           //  System.out.println ("ineswa" );
             for(Double fitness:elephants.keySet ())
             {
                 for(Elephant el:elephants.get ( fitness )) {

                     //env.drawanimal ( el.X );
                     // el.Gbest=Gbest;

                     if (el.Pbestfitness == 1) {
                         //System.out.println ( "solution:" + el.toString ( ) );
                         solutioons.put ( new Position ( el.Pbest.X, el.Pbest.Y ), el.Pbestfitness );
                         target = solutioons.size ( );


                        /* AMELIORATION*/
                         Position coypy=new Position ( Gbest.X,Gbest.Y );


//                         Gbest = el.shutDownarea ( el.Pbest, env.getPortee(), Xmax, env, Gbest, elephants );


                         bestelephant=elephants.get ( (double )(elephants.firstKey ())).get ( 0 );
                         env.shutdown(Xmax,el.X);
                         Gbest=new Position (  bestelephant.Pbest.X,bestelephant.Pbest.Y);

                         if(!coypy.identic ( Gbest ))
                         {
                             bestelephant.X = Gbest;
                             // bestelephant.Pbest=Gbest;
                             bestelephant.Fojbetive = env.getValue ( Gbest.X, Gbest.Y );
                         }


                         el.Pbest=new Position ( (el.X.X)%Xmax.X,(el.X.Y)%Xmax.X );
                         el.Pbestfitness=env.getValue ( el.Pbest.X,el.Pbest.Y );

                         // el.X=el.Pbest;*/
                     }


                     el.updatevelocity ( randomP, Math.abs (  Xmax.X-Xmin.X), wt, Gbest );

                     // g = image.getGraphics().create();

                     // g.setColor ( new Color ( (10 * (el.num)) % 255, (30 * (el.num)) % 255, (50 * (el.num)) % 255 ) );


                     el.updateposition ( Xmax, env ,Xmin);

                     el.Evaluate ( env, 10 );
                     env.drawanimal ( el.X );
                     /** updating the best global elephant**/
                     /*set the gbest for all elephant*/
                     //     if(i%2==0)

                     if (el.Pbestfitness > bestelephant.Fojbetive) {

                         Gbest = new Position ( el.Pbest.X, el.Pbest.Y );

                         bestelephant = new Elephant ( 5,Xmax.X,Xmin
                                 .X,env);
                         bestelephant.X=new Position ( el.Pbest.Y,el.Pbest.Y );
                         bestelephant.Fojbetive=el.Fojbetive;
                         bestelephant.Pbest=new Position ( el.Pbest.X,el.Pbest.Y );
                         bestelephant.Pbestfitness=el.Pbestfitness;
                     }

                 /*update weight inertia*/
                     this.updateWt ( i );


                 }
             }

             i++;
             env.writeimage ( i,"eswsa" );
             env.writeimage ( elephants.get ( elephants.firstKey () ).get ( 0 ).X);
             env.drawround ( elephants.get ( elephants.firstKey () ).get ( 0 ).X, Color.BLUE);
             timelasped+=System.currentTimeMillis ()-start;
             return true;

         }else return false;

     }



    public int Run(int nbrTarget)
     {
         env.create ( "eswsa" );
         /*initialisation*/
         long start=System.currentTimeMillis ();

         for(int i=0;i<N;i++)
         {
             //System.out.println (Xmax.X+":"+Xmin.X );
             Elephant eli=new Elephant ( i,Xmax.X,Xmin.X,env);


             if(elephants.get ( eli.Evaluate ( env,localsize ) ) ==null)
             {
                 elephants.put (eli.Evaluate (env,localsize),new ArrayList<Elephant> (  ) );
                 elephants.get ( eli.Fojbetive ).add ( eli );
             }
             else
                 elephants.get ( eli.Fojbetive ).add ( eli );

             eli.Pbestfitness=eli.Fojbetive;


         }

        /* debut de EHO_0*/

         HashMap<Position,Double> solutioons = new HashMap<> (  );
         /** globalbest**/



         bestelephant=elephants.get ( (double )(elephants.firstKey ())).get ( 0 );
         Gbest=new Position (  bestelephant.Pbest.X,bestelephant.Pbest.Y);
         Elephant l=bestelephant;

         while(i<=Tmax && target <nbrTarget)
         {   env.prepare_editing_env ( "elephant2" );
             for(Double fitness:elephants.keySet ())
             {
                 for(Elephant el:elephants.get ( fitness )) {
                     // el.Gbest=Gbest;

                     if (el.Pbestfitness == 1) {
                        //System.out.println ( "solution:" + el.toString ( ) );
                         solutioons.put ( new Position ( el.Pbest.X, el.Pbest.Y ), el.Pbestfitness );
                         target = solutioons.size ( );


                        /* AMELIORATION*/
                         Position coypy=new Position ( Gbest.X,Gbest.Y );


//                         Gbest = el.shutDownarea ( el.Pbest, env.getPortee(), Xmax, env, Gbest, elephants );


                         bestelephant=elephants.get ( (double )(elephants.firstKey ())).get ( 0 );
                         env.shutdown(Xmax,el.X);
                         Gbest=new Position (  bestelephant.Pbest.X,bestelephant.Pbest.Y);

                         if(!coypy.identic ( Gbest ))
                        {
                            bestelephant.X = Gbest;
                           // bestelephant.Pbest=Gbest;
                            bestelephant.Fojbetive = env.getValue ( Gbest.X, Gbest.Y );
                        }


                         el.Pbest=new Position ( (el.X.X)%Xmax.X,(el.X.Y)%Xmax.X );
                         el.Pbestfitness=env.getValue ( el.Pbest.X,el.Pbest.Y );

                         // el.X=el.Pbest;*/
                     }


                     if( target<nbrTarget ){
                     el.updatevelocity ( randomP, Math.abs (  Xmax.X-Xmin.X), wt, Gbest );

                    // g = image.getGraphics().create();

                    // g.setColor ( new Color ( (10 * (el.num)) % 255, (30 * (el.num)) % 255, (50 * (el.num)) % 255 ) );
                     el.updateposition ( Xmax, env ,Xmin);
                     el.Evaluate ( env, 10 );
                     env.drawanimal ( el.X );
                     l=el;

                     /** updating the best global elephant**/
                     /*set the gbest for all elephant*/
              //     if(i%2==0)

                     if (el.Pbestfitness > bestelephant.Fojbetive) {
                         Gbest = new Position ( el.Pbest.X, el.Pbest.Y );
                         bestelephant = new Elephant ( 5,Xmax.X,Xmin
                         .X,env);
                         bestelephant.X=new Position ( el.Pbest.Y,el.Pbest.Y );
                         bestelephant.Fojbetive=el.Fojbetive;
                         bestelephant.Pbest=new Position ( el.Pbest.X,el.Pbest.Y );
                         bestelephant.Pbestfitness=el.Pbestfitness;
                     }

                 /*update weight inertia*/
                     this.updateWt ( i );}

                 }
                 env.drawround ( l.X,Color.BLUE );
                 env.writeimage ( i,"eswsa" );
             }
             i++;

         }

          showsolutions ( solutioons );
          long finish=System.currentTimeMillis ();
          System.out.println ("iter:"+i+"time:"+(finish-start)+"nbTarget:"+target );
          iteration=i;
          timelasped=finish-start;


          env.writeall (i,"ewswa");




         return target;
     }


     public void showsolutions(HashMap<Position,Double> solutioons)
     {
         for (Position p :solutioons.keySet ())
             System.out.println ("solutions:"+ p.toString () );

     }


     /* improvement update inertia weight*/
        public void updateWt()
        {
            this.wt=0.5+(new Random (  ).nextDouble ()/2);
        }

        public void updateWt(int currentiteratio)
        {
            this.wt=(1)-((1-0.1)/Tmax)*currentiteratio;
        }


     public static TreeMap checkelephant(TreeMap<Double,ArrayList<Elephant>> elephants,Position x)
     {
         for(Double fitness:elephants.keySet ())
         {
             for(Elephant el:elephants.get ( fitness ))
             {
                if(el.X.identic ( x ))
                { /*init*/
                   // System.out.println (el.toString () );
                    el.X=new Position ( (el.X.X+20)%env.size,(el.X.Y+30)%env.size  );

    ;

                }
                if(el.Pbest.identic ( x ))
                {
                    el.Pbest=new Position ( (el.Pbest.X+30)%env.size,(el.Pbest.Y+20)%env.size  );;
                    el.Pbestfitness=env.getValue ( el.Pbest.X,el.Pbest.Y );
                }

                 //System.out.println ("fitness:"+el.Fojbetive+":elephant:"+el.toString ());
             }
         }
         return elephants;

     }


    /********************************************************************************/

    public ArrayList<Double> exe2(int nbrTarget)
    {
        /*initialisation*/
        ArrayList<Double> results = new ArrayList();
        long start=System.currentTimeMillis ();

        for(int i=0;i<N;i++)
        {
            Elephant eli=new Elephant ( i,env.size,Xmin.X,env);

            if(elephants.get ( eli.Evaluate ( env,localsize) ) ==null)
            {
                elephants.put (eli.Evaluate (env,localsize),new ArrayList<Elephant> (  ) );
                elephants.get ( eli.Fojbetive ).add ( eli );
            }
            else
                elephants.get ( eli.Fojbetive ).add ( eli );

            eli.Pbestfitness=eli.Fojbetive;
        }



        HashMap<Position,Double> solutioons = new HashMap<> (  );
        /** globalbest**/

        int target=0;
        int i=1;

        bestelephant=elephants.get ( (double )(elephants.firstKey ())).get ( 0 );
        Gbest=new Position (  bestelephant.Pbest.X,bestelephant.Pbest.Y);

        while(i<=Tmax && target <nbrTarget)
        {
            for(Double fitness:elephants.keySet ())
            {
                for(Elephant el:elephants.get ( fitness )) {
                    // el.Gbest=Gbest;

                    if (el.Pbestfitness == 1) {
                        //System.out.println ( "solution:" + el.toString ( ) );
                        solutioons.put ( new Position ( el.Pbest.X, el.Pbest.Y ), el.Pbestfitness );
                        target = solutioons.size ( );

                        //     if (solutioons.keySet ( ).size ( ) == 3) System.out.println ( "nb iteration:" + i );

                        /* AMELIORATION*/
                        Position coypy=new Position ( Gbest.X,Gbest.Y );


                         Gbest = el.shutDownarea ( el.Pbest, env.getPortee(), Xmax, env, Gbest, elephants );

//                        bestelephant=elephants.get ( (double )(elephants.firstKey ())).get ( 0 );
//                        env.shutdown(Xmax,el.X);
//                        Gbest=new Position (  bestelephant.Pbest.X,bestelephant.Pbest.Y);

//                        env.shutdown(Xmax,el.X);
//                        Gbest = new Position(env,env.size);

                        if(!coypy.identic ( Gbest ))
                        {
                            bestelephant.X = Gbest;
                            // bestelephant.Pbest=Gbest;
                            bestelephant.Fojbetive = env.getValue ( Gbest.X, Gbest.Y );
                        }

                        el.Pbest=new Position ( (el.X.X)%env.size,(el.X.Y)%env.size );

                        el.Pbestfitness=env.getValue ( el.Pbest.X,el.Pbest.Y );

                    }


                    if( target<nbrTarget ){


                    el.updatevelocity ( randomP, env.size, wt, Gbest );

                    el.updateposition ( Xmax, env,Xmin );
                    el.Evaluate ( env, localsize);

                    /** updating the best global elephant**/
                    /*set the gbest for all elephant*/

                    if (el.Pbestfitness > bestelephant.Fojbetive) {

                        Gbest = new Position ( el.Pbest.X, el.Pbest.Y );

                        bestelephant = new Elephant ( 5,env.size,Xmin.X ,env);
                        bestelephant.X=new Position ( el.Pbest.Y,el.Pbest.Y );
                        bestelephant.Fojbetive=el.Fojbetive;
                        bestelephant.Pbest=new Position ( el.Pbest.X,el.Pbest.Y );
                        bestelephant.Pbestfitness=el.Pbestfitness;
                    }

                    /*update weight inertia*/
                    this.updateWt ( i );}


                }
            }
            i++;
        }


        long finish=System.currentTimeMillis ();

        long elapsedTime=finish-start;
//        for (Position p :solutioons.keySet ())
//            System.out.println ("solutions:"+ p.toString () );


        i--;
//        System.out.println(i+" iteration, nbrarget "+target+",time:"+elapsedTime);
        results.add(i*1.0);
        results.add((elapsedTime * 1.0)/1000.0);
        results.add(target * 1.0);

        iteration=i;
        timelasped=elapsedTime;
        this.nbtargets=target;

        return results;
    }

public String toString()
{
    return "Iterations:"+i+"\tNbElephant:"+N+"\tP:"+randomP+"\tWt:"+wt+"\tTime:"+timelasped+ " sec\tNombre de cibles trouvées:"+target+"\tNombre de cibles manquées:"+(nbtargets-target);
}
}

