package ESWSA.SubswarmEswsa;

import Environnement.Dataset;
import Environnement.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by masterubunto on 04/05/19.
 */
public class SwarmESWSA {
    int NB_SWARM;
    ArrayList<Subswarm> teams;
    static Dataset env;
    int N;
    int Tmax;
    int iteration;
    long timelasped;
    int size;
    Position Gbest;
    double Gbestvalue;
    double randomP;
    int localsize = 10;
    double wt;
    int nbtargets = 0;
    HashSet<Position> solutions;


    public SwarmESWSA(int nbswarm, int nbelephant, Dataset env) {
        NB_SWARM = nbswarm;
        teams = new ArrayList<> ( );

        for (int i = 0; i < NB_SWARM; i++) {

            Subswarm s=new Subswarm ( nbelephant );
            s.color=new Random (  ).nextInt (255);
            teams.add ( s);

        }
        this.env = env;
        Gbest = new Position ( );
        solutions = new HashSet<> ( );

    }

    public SwarmESWSA(int nbswarm, int nbelephant, int size) {
        NB_SWARM = nbswarm;
        Subswarm s;
        teams = new ArrayList<> ( );
        for (int i = 0; i < NB_SWARM; i++) {
            s = new Subswarm ( nbelephant );

            teams.add ( s );
        }

        Gbest = new Position ( );
        this.env = new Dataset ( size );

    }

    public void Setparemetres(int maxiter, double p, double wt) {
        this.randomP = p;
        this.wt = wt;
        this.Tmax = maxiter;
    }

    /* affect each swarm a region*/

    public void Initteamsborders() {
       /* get a region for each swarm*/
        ArrayList<Region> zones = env.partition ( NB_SWARM );
        int i = 0;

       /*TODO:methods giving an number of regions it returns the locations of them*/
        for (Subswarm subswarm : teams) {
            // System.out.println ("swarm("+i+"):" );
            Position debut =zones.get ( i ).getDebut ( );
            Position fin =zones.get ( i ).getFin ();
//            System.out.println ("limit:"+debut+","+fin );
//            /* switch  the limit of x and limit of y*/
//            Position debut2= new Position ( debut.X,fin.X );
//            fin.X=debut.Y;
            subswarm.setXmin (debut );
            subswarm.setXmax ( fin );
         //   System.out.println ("boundx:"+debut2+",boundy:"+fin );

        //    env.editposition ( subswarm.Xmax,null, Color.RED,20 );
       //     env.editposition ( subswarm.Xmin,null, Color.RED,20 );
            i++;

        }
    }

    public void show() {
        int i = 0;
        for (Subswarm subswarm : teams) {
            System.out.println ( "swarm(" + i + "):" + i );
            for (Elephant e : subswarm.swarm)
                System.out.println ( "elephant:" + e.toString ( ) );
            subswarm.distancebetweenmembers ( );


            i++;

        }
    }

    /* improvement update inertia weight*/
    public void updateWt() {
        this.wt = 0.5 + (new Random ( ).nextDouble ( ) / 2);
    }

    public void updateWt(int currentiteratio) {
        this.wt = (1) - ((1 - 0.1) / Tmax) * currentiteratio;
    }

    public void Init() {
        // System.out.println (teams.size () );
        for (Subswarm sb : teams) {
            //  System.out.println ("initswarm" );
            sb.InitPositionsofswarm ( env );
        }
    }

    public void evaluateswarm(int localsize) { /*TODO:checking if we really need global Gbest of all swarms */
        for (Subswarm sb : teams) {
            sb.Evaluate ( env, localsize );
            if (sb.Gbestvalue < Gbestvalue) {
                Gbest = new Position ( sb.Gbest.X, sb.Gbest.Y );
                Gbestvalue = sb.Gbestvalue;
            }
            if (Gbestvalue == 1) {
                Gbest = new Position ( );
                Gbestvalue = 0;
            }

        }
    }

    public void Updateswarmsposition() {

        for (Subswarm sb : teams) {
            sb.Updateposition ( env );
        }
    }

    public void Updateswarmsvelocity() {

        for (Subswarm sb : teams) {
            sb.Updatevelocity ( randomP, wt, Gbest );
        }
    }

    public void nbtargetsfoundbyswarm() {
        nbtargets = 0;
        for (Subswarm sb : teams) {
            for (Position s : sb.solutioons)
                solutions.add ( s );
            // nbtargets+=sb.solutioons.size ();
            //  System.out.println ("teamsolutions:"+sb.solutioons.size () );
        }
        nbtargets = solutions.size ( );
    }

    public void run(int nbtargets) {
        env.create ( "swarmelephant" );

        iteration = 0;
        /*init positions & borders*/
        Initteamsborders ( );
        Init ( );

        //this.show ();
        while (iteration < Tmax && this.nbtargets < nbtargets) {
           /* evaluation swarms*/
          // show ();
            evaluateswarm ( 10 );
           /*update velocity for each swarm*/
            Updateswarmsvelocity ( );
           /*update position of each swarm*/
            Updateswarmsposition ( );
           /*update the number of targets communicated by swarm*/
            nbtargetsfoundbyswarm ( );
            /*weight inertia updating*/

            this.updateWt ( iteration );
            iteration++;
        }
        System.out.println ( "target:" + this.nbtargets + ":iter:" + iteration );
         showsolutions ();
       env.writeimage ( iteration, "swarmeswsa" );


    }

    public void showsolutions() {

        for (Position solution : solutions) {
          //  System.out.println ( solution );
            env.matrix=this.env.Inittargets ( env.getPortee (),solution );
        }

    }
/*getters*/

    public int getNbtargets() {
        return nbtargets;
    }
    public HashSet<Position> getSolutions() {
        return solutions;
    }

    public int getIteration() {
        return iteration;
    }

    public long getTimelasped() {
        return timelasped;
    }
}
