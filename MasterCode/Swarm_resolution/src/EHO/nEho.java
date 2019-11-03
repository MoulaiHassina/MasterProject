package EHO;

import Environnement.Dataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import Environnement.Position;

public class nEho {

    int MaxGen;
    int iteration;
    ArrayList<Clan> clans;
    int size;
    int nbtarget = 0;
    long timelapsed;
    Dataset env;
    int t = 0, nbrT = 0;
    Position solution;
    HashSet<Position> AllSolution;

    int nbrClan;
    int nbrElephant;
    double alpha;
    double beta;
    long start;

    public nEho(int size, int Maxgen) {
        this.size = size;
        this.MaxGen = Maxgen;
        AllSolution = new HashSet<> ( );
    }

    public nEho() {
        AllSolution = new HashSet<> ( );
    }


    public int getNbtarget() {
        return nbtarget;
    }

    public long getTimelapsed() {
        return timelapsed;
    }


    /******************************************************************/
    /******************************************************************/
    public void Init(Dataset envi, ArrayList<Position> elephants, int nbrTarget, int nbrClan, int nbrElephant, double alpha, double beta) {
        size = envi.size;
        env = envi;

        /** setting the image**/
        env.create ( "eho" );
        this.nbrClan = nbrClan;
        this.nbrElephant = nbrElephant;
        this.alpha = alpha;
        this.beta = beta;
        this.nbtarget = nbrTarget;

        /** init clans **/

        clans = new ArrayList ( );

        if (!elephants.isEmpty ( )) {
            for (int i = 0; i < nbrClan; i++) {
                Clan clan = new Clan ( env, nbrElephant, elephants.get ( i ) );
                //System.out.println(elephants.get ( i ).toString()+"****************"+clan.toString());
                clans.add ( clan );
            }
        } else {
            for (int i = 0; i < nbrClan; i++) {
                Clan clan = new Clan ( env, size, nbrElephant );
                clans.add ( clan );
            }
        }

    }

    public int getT() {
        return t;
    }

    public Dataset getEnv() {
        return env;
    }

    public int getNbrT() {
        return nbrT;
    }

    public boolean runUI() {
        start = System.currentTimeMillis ( );

//        System.out.println ("t:"+t+"nbt:"+nbtarget +"max:"+MaxGen+"nnbtrcrrent");
        if (t < MaxGen && nbrT < nbtarget) {

            env.prepare_editing_env ( "elephant" );
            clans = sorte ( clans );

            clans = update ( clans, env, alpha, beta );


            clans = separate ( clans, env, size );
            Elephant tracking = null;
            Elephant e;


            //clans = sorte(clans);

            for (int c = 0; c < clans.size ( ); c++) {
                for (int j=0;j<nbrElephant;j++) {
                    e = clans.get ( c ).clan.get ( j );
                    System.out.println ("e:"+j+" :p:"+e.position );
                    env.drawanimal ( e.position );
                    tracking = e;


                    if (e.fitness == 1) {
                        solution = e.position.copy ( );

                        AllSolution.add ( solution );
                        nbrT = AllSolution.size ( );

                        env.matrix = env.shutdown ( new Position ( env.size, env.size ), e.position );

                    }
                }
            }

            //TODO : Draw
            env.drawround ( tracking.position, Color.BLUE );
            env.writeimage ( tracking.position );
            env.writeimage ( t, "eho" );


            t++;
            timelapsed += System.currentTimeMillis ( ) - start;

            return true;

        } else return false;

    }

    public void exe(Dataset envi, int nbrTarget, int nbrClan, int nbrElephant, double alpha, double beta) {

        size = envi.size;
        env = envi;

        /** setting the image**/
        env.create ( "eho" );

        /***************************************************************/


        /************************************************************/

        /** init clans **/
        clans = new ArrayList ( );

        for (int i = 0; i < nbrClan; i++) {
            Clan clan = new Clan ( env, size, nbrElephant );
            clans.add ( clan );
        }


        /** Search **/
        long start = System.currentTimeMillis ( );

        while (t < MaxGen && nbrT < nbrTarget) {

            clans = sorte ( clans );


            clans = update ( clans, env, alpha, beta );
            clans = separate ( clans, env, size );

            //clans = sorte(clans);

            for (int c = 0; c < clans.size ( ); c++) {
                for (Elephant e : clans.get ( c ).clan) {

                    if (e.fitness == 1) {
                        solution = e.position.copy ( );

                        AllSolution.add ( solution );
                        nbrT = AllSolution.size ( );

                        env.matrix = env.shutdown ( new Position ( env.size, env.size ), e.position );

                    }
                }
            }

            //TODO : Draw
            env.writeimage ( t, "eho" );

            t++;

        }


        long end = System.currentTimeMillis ( );

        this.nbtarget = AllSolution.size ( );
        this.iteration = t;
        this.timelapsed = (end - start);


        //System.out.println ( t + " final solution : " + AllSolution.toString ( ) );

    }


    /***********************************************************************************/

    public static ArrayList<Clan> sorte(ArrayList<Clan> clans) {
        for (int c = 0; c < clans.size ( ); c++) {
            clans.get ( c ).sorte ( );
        }
        return clans;
    }


    public static ArrayList<Clan> separate(ArrayList<Clan> clans, Dataset env, int size) {
        for (int c = 0; c < clans.size ( ); c++) {
            clans.get ( c ).separate ( env, size );
        }
        return clans;
    }


    public static ArrayList<Clan> update(ArrayList<Clan> clans, Dataset env, double alpha, double beta) {
        for (int c = 0; c < clans.size ( ); c++) {
            clans.get ( c ).update ( env, alpha, beta );
        }
        return clans;
    }

    public static Elephant getBest(ArrayList<Clan> clans) {
        Elephant elephantBest = clans.get ( 0 ).getBest ( );
        for (int c = 0; c < clans.size ( ); c++) {
            if (elephantBest.fitness < clans.get ( c ).getBest ( ).fitness)
                elephantBest = clans.get ( c ).getBest ( ).copy ( );
        }

        return elephantBest;
    }


    public int getIteration() {
        return iteration;
    }


    /***************************************************************************************/

    public ArrayList<Double> exe2(Dataset envi, int nbrTarget, int nbrClan, int nbrElephant, double alpha, double beta) {

        int size = envi.size;
        env = new Dataset ( envi );

        /*********************************************************/

        int t = 0, nbrT = 0;
        Position solution;
        HashSet<Position> AllSolution = new HashSet<> ( );
        ArrayList<Double> results = new ArrayList ( );

        /************************************************************/

        long startTime = System.currentTimeMillis ( );

        /** init clans **/
        clans = new ArrayList ( );
        for (int i = 0; i < nbrClan; i++) {

            Clan clan = new Clan ( env, size, nbrElephant );

            clans.add ( clan );
        }

        /** Search **/
        while (t < MaxGen && nbrT < nbrTarget) {

            clans = sorte ( clans );


            clans = update ( clans, env, alpha, beta );
            clans = separate ( clans, env, size );


            //clans = sorte(clans);

            for (int c = 0; c < clans.size ( ); c++) {
                for (Elephant e : clans.get ( c ).clan) {

                    if (e.fitness == 1) {
                        solution = e.position.copy ( );

                        AllSolution.add ( solution );
                        nbrT = AllSolution.size ( );

                        env.matrix = env.shutdown ( new Position ( env.size, env.size ), e.position );

                    }
                }
            }

            t++;

        }

        long stopTime = System.currentTimeMillis ( );
        long elapsedTime = stopTime - startTime;

        /**FOR : GA**/
        this.nbtarget = AllSolution.size ( );
        this.iteration = t;
        this.timelapsed = (elapsedTime);

//        System.out.println(t+" iteration, nbrarget "+nbrT);

        /**FOR : Expérimentation**/
        results.add ( t * 1.0 );
        results.add ( (elapsedTime * 1.0) / 1000.0 );
        results.add ( nbrT * 1.0 );

        System.out.println ( AllSolution );

        return results;
    }

    public String toString() {
        return "Iterations:" + t + "\tNbElephant:" + nbrElephant + "\tNbClan:" + nbrClan + "\tAlpha:" + alpha + "\tBeta:" + beta + "\tTime:" + timelapsed + " sec\tNombre de cibles trouvées: "+getNbrT ()+"\tNombre de cibles manquées:"+(nbtarget-nbrT);
    }
}