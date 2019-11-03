package BSO.MultiBSO;

import BSO.MonoBSO.Bee;
import Environnement.Position;
import Environnement.Dataset;
import EvitementObs.Utility;
import UI.Code.UIUpdaterThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class MultiBSO {

    int size;
    Dataset env ;
    int MaxIter=1;
    int iter;
    int flip;
    int MaxChances;
    int nbtargets;
    int nbrBees;
    int nbrSwarm;
    int localSize=10;
    long timeNBSO=0,start=0;
    int t = 0;
    int  nbrT = 0,Tcourant=-1;
    HashSet<Position> Solution ;
    ArrayList<BeeSwarm> BeesSwarms ;
    ArrayList<Bee> Bees;
    HashSet<Position> TabooList ;
    ArrayList<ArrayList<Position>> Dances ;
    ArrayList<Position> Dance;
    Position nextPos; Bee lol,Sref;
    BeeSwarm SrefSwarm ;



    /**************************************************************************************/
    public void SetParametersvector(ArrayList<Integer> parameters)
    {
        this.flip = (int) (parameters.get ( 0 ));
        this.MaxChances=(int) (parameters.get ( 1 ));
        this.nbrBees = (int) (parameters.get(2));
        this.nbrSwarm = (int) (parameters.get(3));
        System.out.println ("flip:"+flip+"maxchances:"+MaxChances+"nbbees:"+nbrBees+"swarm:"+nbrSwarm );
    }

    public MultiBSO(int MaxIter){
        this.MaxIter=MaxIter;
        Solution = new HashSet<>();
        BeesSwarms = new ArrayList<>();
        TabooList = new HashSet<>();
        Dances = new ArrayList<>();
    }

    public int getMaxIter() {
        return MaxIter;
    }

    public int getNbtargets() {
        return nbtargets;
    }

    public int getIter() {
        return iter;
    }

    public long getTimeMultiBSO() {
        return timeNBSO;
    }

    public Dataset getEnv() {
        return env;
    }
    public void Init(Dataset envi, ArrayList<Position> robots)
    {
        /***other parameters**/
        size=envi.size;
        env = envi;
        env.create ( "bso" );

        for(int i=0; i<nbrSwarm; i++){
            BeeSwarm bee = new BeeSwarm( nbrBees);
            BeesSwarms.add(bee);



            ArrayList<Position> dance = new ArrayList<>();
            Dances.add(dance);
        }

        start=System.currentTimeMillis ();

        /***algorithm***/
        //init Sref randomly
         SrefSwarm = new BeeSwarm(env,nbrSwarm);
        for(int s=0 ; s<nbrSwarm ; s++) {
            if(robots.size ()==nbrSwarm)
            {  SrefSwarm.swarm.get ( s ).position=robots.get ( s );
                env.drawanimal (robots.get ( s ));}
        }

         env.writeimage ( 0,"bso" );
//         env.prepare_editing_env ( "bee" );


    }
    /**************************************************************************************/
    /**************************************************************************************/
    public void run (int nbrTarget)
    {
         env.prepare_editing_env ( "bee" );
         start=System.currentTimeMillis ();

            if(nbrT != Tcourant){
                Tcourant = nbrT;
            }

            //for each subswarm
            /*image editing*/


            for(int s=0 ; s<nbrSwarm ; s++) {

                Sref = SrefSwarm.swarm.get(s);


             //   env.drawanimal ( Sref.position );
                Bees = BeesSwarms.get(s).swarm;
                Dance = Dances.get(s);

                //insert Sref in a taboo list;
                TabooList.add(Sref.position.copy());

                globalSearch(env, Sref, flip, Bees.size(), Bees, t);

                for (int i = 0; i < Bees.size(); i++) {
                    //local seaenv.drawanimal ( "bee",Bees.get ( i ).position );
                    /**drawing of beez*/
                    //env.drawanimal (Bees.get ( i ).position);
                     nextPos = Bees.get(i).localSearch(env, localSize);
                     Utility.sref=1;
                     if (!Bees.get(i).position.identic(nextPos))
                        Bees.get(i).putSolution(Utility.checkNextArea(env, Bees.get(i).position, nextPos, localSize, 0, new TreeSet()));


                    Bees.get(i).evaluate(env);
                    env.drawanimal (Bees.get ( i ).position);

                    //store the result in Dance list
                    Dance.add(Bees.get(i).position.copy());
                }


                if (Sref.fitness == 1) {
//                    Sref.nbrChance = MaxChances;

                    Solution.add(Sref.position.copy());
                    nbrT = Solution.size();

                    env.matrix = env.shutdown(new Position(env.size, env.size), Sref.position);

                }

                if(nbrT == nbrTarget)
                    break;

                lol = selectBestSref(Sref, Dance, MaxChances, TabooList).copy();


                //Assign the best solution to Sref
                Utility.sref=0;
                Utility.checkNextArea(env,Sref.position,lol.position,localSize,0,new TreeSet (  ) );
                Utility.sref=1;
                //env.editposition ( Sref.position,lol.position, Color.RED,0 );
              //  env.drawanimal ( Sref.position );
                Sref.putSolution(lol.position);
                Sref.nbrChance = lol.nbrChance;
                Sref.evaluate(env);

            }
            env.drawround (Sref.position,Color.BLUE);
            env.drawanimal ( Sref.position );
            env.writeimage ( t,"bso" );
            env.writeimage ( Sref.position );

            t++;
            timeNBSO+=System.currentTimeMillis ()-start;
            Utility.sref=0;

    }

    public int getT() {
        return t;
    }

    public int getNbrT() {
        return nbrT;
    }

    public void setEnv(Dataset env) {
        this.env = env;
    }

    public ArrayList<Double> exe2(Dataset envi, int nbrTarget) {

        /***other parameters**/
        size=envi.size;
        env = new Dataset(envi);
        env.create ( "bso" );

        int t = 0, nbrT = 0,Tcourant=-1;

        HashSet<Position> Solution = new HashSet<>();

        ArrayList<BeeSwarm> BeesSwarms = new ArrayList<>();
        ArrayList<Bee> Bees;
        HashSet<Position> TabooList = new HashSet<>();
        ArrayList<ArrayList<Position>> Dances = new ArrayList<>();
        ArrayList<Position> Dance;
        Position nextPos; Bee lol,Sref;

        for(int i=0; i<nbrSwarm; i++){
            BeeSwarm bee = new BeeSwarm( nbrBees);
            BeesSwarms.add(bee);


            ArrayList<Position> dance = new ArrayList<>();
            Dances.add(dance);
        }


        /***algorithm***/
        //init Sref randomly
        BeeSwarm SrefSwarm = new BeeSwarm(env,nbrSwarm);


        /*************************Search**************************/
        /*********************************************************/
        ArrayList<Double> results = new ArrayList();
        long startTime=System.currentTimeMillis ();

        while (t < MaxIter && nbrT < nbrTarget) {
            if(nbrT != Tcourant){
                Tcourant = nbrT;
            }
            /*image editing*/
             env.prepare_editing_env ( "bee" );
            //for each subswarm
             for(int s=0 ; s<nbrSwarm ; s++) {
                Sref = SrefSwarm.swarm.get(s);
                Bees = BeesSwarms.get(s).swarm;
                Dance = Dances.get(s);

                //insert Sref in a taboo list;
                TabooList.add(Sref.position.copy());

                globalSearch(env, Sref, flip, Bees.size(), Bees, t);

                for (int i = 0; i < Bees.size(); i++) {
                    //local search
                    /**drawing of beez*/
                    env.drawanimal (Bees.get ( i ).position);
                    nextPos = Bees.get(i).localSearch(env, localSize);

                    if (!Bees.get(i).position.identic(nextPos))
                        Bees.get(i).putSolution(Utility.checkNextArea(env, Bees.get(i).position, nextPos, localSize, 0, new TreeSet()));
                    //env.drawanimal ( copyimage,"bee",Bees.get ( i ).position,t );

                    Bees.get(i).evaluate(env);

                    //store the result in Dance list
                    Dance.add(Bees.get(i).position.copy());


                }


                if (Sref.fitness == 1) {
//                    Sref.nbrChance = MaxChances;

                    Solution.add(Sref.position.copy());
                    nbrT = Solution.size();

                    env.matrix = env.shutdown(new Position(env.size, env.size), Sref.position);

                }

                if(nbrT == nbrTarget)
                    break;

                lol = selectBestSref(Sref, Dance, MaxChances, TabooList).copy();


                //Assign the best solution to Sref
                Sref.putSolution(lol.position);
                Sref.nbrChance = lol.nbrChance;
                Sref.evaluate(env);

            }
         //   env.writeimage ( t,"bso" );
            t++;
        }


        long stopTime=System.currentTimeMillis ();
        long elapsedTime=stopTime-startTime;

        /**FOR : GA**/
        this.nbtargets=Solution.size ();
        this.iter=t;
        this.timeNBSO=(elapsedTime);

        System.out.println(t+" iteration, nbrarget "+nbrT);

        /**FOR : Expérimentation**/
        results.add(t*1.0);
        results.add((elapsedTime * 1.0)/1000.0);
        results.add(nbrT * 1.0);


        System.out.println(Solution);
        return results;
    }














    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/

    public void globalSearch(Dataset env,Bee Sref, int flip, int nbrBees,
                             ArrayList<Bee> Bees, int t){

        int rand,start=0;

        //generate solutions
        ArrayList<Position> circlePos = Sref.position.getCiclePositions(env,flip);

        if(nbrBees > circlePos.size()) nbrBees=circlePos.size();

        if(t==0){
            start=1;
            Bees.get(0).putSolution(Sref.position);
            Bees.get(0).evaluate(env);
        }

        for (int i = start; i < nbrBees; i++) {

            //put the solution in the bee
            Random random = new Random();
            rand = random.nextInt(circlePos.size() );

            if(t==0){
                Bees.get(i).putSolution(circlePos.get(rand));
            }
            else{
                Bees.get(i).putSolution(Utility.checkNextArea(env, Bees.get(i).position, circlePos.get(rand), localSize,0,new TreeSet()));
            }

            circlePos.remove(rand);

            //evaluate solution
            Bees.get(i).evaluate(env);
        }
    }

    /********************************************************************************************************/

    public Bee selectBestSref(Bee Sref, ArrayList<Position> Dance, int MaxChances,HashSet<Position> Taboo) {

        //select best bee solution in quality from Dance list
        Bee newB =  new Bee(getBestQuality(Dance,Taboo));
        newB.evaluate(env);

        Bee newSref = new Bee();

        //calculate Δf = f(Sbest(t+1)) – f(Sref(t));
        double deltaF = newB.fitness - Sref.fitness;

        if (deltaF >= 0) {

            //Sref = the best solution in quality;
            newSref = newB.copy();
            newSref.nbrChance = 0;

        } else {

            Sref.nbrChance += 1;
            if (Sref.nbrChance > MaxChances) {

                //newSref = the best solution in diversity;
                newSref.putSolution(getBestDiversity(Dance,Taboo));
                newSref.evaluate(env);
                newSref.nbrChance = 0;
            }else{
                newSref = Sref.copy();
            }

        }

        return newSref;
    }

    public Position getBestQuality(ArrayList<Position> Dance, HashSet<Position> Taboo){
        Position bee = new Position();
        boolean allInTaboo = true;

        for(int i = 0 ; i<Dance.size() ; i++){

            if(Taboo.contains(Dance.get(i)) == false){
                //if in dance and not in taboo list
                allInTaboo = false;
                if(env.getValue(bee) <= env.getValue(Dance.get(i))){
                    bee = Dance.get(i).copy();
                }
            }
        }

        if(allInTaboo == true){
            do{
                bee = new Position(env, size,  0,  size,  0);
            }while(Taboo.contains(bee));
        }
        return bee;
    }

    public Position getBestDiversity(ArrayList<Position> Dance2,HashSet<Position> Taboo){
        Position bee = new Position();
        double d,minDistance ;
        boolean allInTaboo = true;
        ArrayList<Position> Dance = new ArrayList();


        for(int i = 0 ; i<Dance2.size() ; i++) {
            if (Taboo.contains(Dance2.get(i)) == false) {
                allInTaboo = false;
                Dance.add(Dance2.get(i).copy());
            }
        }

        if(allInTaboo == true){

            do{
                bee = new Position(env, size,  0,  size,  0);
            }while(Taboo.contains(bee));

            //bee.evaluate(env);
        }
        else {
            for (int i = 0; i < Dance.size() - 1; i++) {

                minDistance = Dance.get(i).distance(Dance.get(i + 1));
                for (int j = i + 1; j < Dance.size(); j++) {
                    d = Dance.get(i).distance(Dance.get(j));
                    if (d < minDistance && d != 0) {
                        minDistance = d;
                    }
                }
                Dance.get(i).minDistance = minDistance;
            }

            for (int i = 0; i < Dance.size(); i++) {
                if (bee.minDistance < Dance.get(i).minDistance) {
                    bee = Dance.get(i).copy();
                }
            }
        }

        return bee;
    }



    public String toString()
    {
        return "Iterations:"+getIter ()+"\tFlip:"+flip+"\tMaxChance:"+MaxChances+"\tNbrBees:"+this.nbrBees+"\tNbSwarm:"+nbrSwarm+"\tTime:"+timeNBSO+ " sec\tNombre de cibles trouvées:"+nbrT+"\tNomre de cibles manquées:"+(nbtargets-nbrT);
    }

    /*************************************************/

}
