package BSO.MonoBSO;


import Environnement.Dataset;
import Environnement.Position;
import EvitementObs.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class nBSO {

    int size;
    Dataset env ;
    int MaxIter=500;
    int iter;
    int flip;
    int MaxChances;
    int nbtargets;
    int nbrBees;
    int localSize=10;
    long timeNBSO=0,start;
    int maxX=size,maxY=size,minX=0,minY=0;
    int t = 1, nbrT = 0,Tcourant=-1;

    HashSet<Position> Solution ;
    ArrayList<Bee> Bees ;
    HashSet<Position> TabooList ;
    ArrayList<Position> Dance ;
    Position nextPos;
    Bee lol;
    Bee Sref;




    /**************************************************************************************/
    public void SetParametersvector(ArrayList<Integer> parameters)
    {   this.MaxIter=parameters.get ( 0 );
        this.flip = (int) (parameters.get ( 1));
        this.MaxChances=(int) (parameters.get ( 2 ));
        this.nbrBees = (int) (parameters.get(3));
    }

    public nBSO(int MaxIter){
        this.MaxIter=MaxIter;
        Solution = new HashSet<>();
        Bees = new ArrayList<>();
        TabooList = new HashSet<>();
         Dance = new ArrayList<>();
    }

    public void setNbtargets(int nbtargets) {
        this.nbtargets = nbtargets;
    }

    public int getNbtargets() {
        return nbtargets;
    }

    public int getNbrT() {
        return nbrT;
    }

    public int getIter() {
        return t;
    }

    public Dataset getEnv() {
        return env;
    }

    public long getTimeNBSO() {
        return timeNBSO;
    }


    public void InitBso(Dataset envi,ArrayList<Position> beez)
    {  size=envi.size;
        env = envi;
        maxX=size;
        maxY=size;

        /** setting the image**/
       // if(beez==null)
        env.create( "bso" );
        System.out.println ("nbbezz:"+nbrBees );
        System.out.println ( "flip:"+flip );
        System.out.println ("maxchances"+MaxChances );


        /***other parameters**/
     //   if(beez==null)
       // env.prepare_editing_env ( "bee" );

        for(int i=0; i<nbrBees; i++){
            Bee bee = new Bee();
//            if(beez.size ()==nbrBees)
//            bee.position=beez.get ( i );
            Bees.add(bee);
//            env.drawanimal ( bee.position );

        }
        t=0;
     //   env.writeimage ( 0,"bso" );


        /***algorithm***/
        //init Sref randomly
        Sref = new Bee(env,maxX, minX, maxY, minY);
        if(beez.size ()>0)
        Sref.position=beez.get ( 0 );
       // start=System.currentTimeMillis ();
        Sref.evaluate(env);

    }
    public boolean RunUI()
    {

         start=System.currentTimeMillis ();


        if (t < MaxIter && nbrT < nbtargets) {
            if(nbrT != Tcourant){
                Tcourant = nbrT;
            }

            //insert Sref in a taboo list;
            TabooList.add(Sref.position.copy());

            //TODO : Draw
//            g.setColor(Color.CYAN);
//            g.drawOval( Sref.X()-(size/(flip)),Sref.Y()-(size/(flip)),2*size/(flip),2*size/(flip));

            globalSearch(env,Sref,flip,Bees.size(),Bees,t);

            env.prepare_editing_env ( "bee" );

            for(int i=0 ; i<Bees.size(); i++) {
                env.drawanimal ( Bees.get ( i ).position );

            //    System.out.println ("i:"+i+"beez:"+Bees.get ( i ).position);
                //local search
                nextPos = Bees.get(i).localSearch(env,localSize);

               // Utility.color ( i );
              //  System.out.println ("nbbezz:"+i+","+Utility.c.toString () );
                if(!Bees.get(i).position.identic(nextPos))
                    Bees.get(i).putSolution(Utility.checkNextArea( env, Bees.get(i).position, nextPos, localSize,0,new TreeSet()));


                Bees.get(i).evaluate(env);

                //store the result in Dance list
                Dance.add(Bees.get(i).position.copy());
                env.drawanimal ( Bees.get ( i ).position );

            }


            if(Sref.fitness == 1 ){
                Sref.nbrChance=MaxChances;
                Solution.add(Sref.position.copy());
                nbrT=Solution.size();

                //TODO : use shutdown
                env.matrix=env.shutdown(new Position( env.size,env.size ),Sref.position );
            }


            lol = selectBestSref(Sref, Dance, MaxChances,TabooList).copy();


            //TODO : Draw
            env.drawanimal ( Bees.get ( 0 ).position );
            env.drawround ( Bees.get ( 0 ).position,Color.BLUE );
            env.writeimage ( Bees.get( 0 ).position );





            Sref.putSolution( lol.position);
            Sref.nbrChance=lol.nbrChance;
            Sref.evaluate(env);
            env.writeimage ( t,"bso" );
            t++;
            timeNBSO+=System.currentTimeMillis ()-start;

            return true;
        }
        else{

            timeNBSO+=System.currentTimeMillis ()-start;

        return false;}
    }


    /**************************************************************************************/
    public HashSet<Position> exe(Dataset envi, int nbrTarget) {

        size=envi.size;
        env = envi;

        /** setting the image**/
        env.create( "bso" );


        /***other parameters**/
        int maxX=size,maxY=size,minX=0,minY=0;
        int t = 0, nbrT = 0,Tcourant=-1;

        HashSet<Position> Solution = new HashSet<>();
        ArrayList<Bee> Bees = new ArrayList<>();
        HashSet<Position> TabooList = new HashSet<>();
        ArrayList<Position> Dance = new ArrayList<>();
        Position nextPos; Bee lol;

        for(int i=0; i<nbrBees; i++){
            Bee bee = new Bee();
            Bees.add(bee);
        }


        /***algorithm***/
        //init Sref randomly
        Bee Sref = new Bee(env,maxX, minX, maxY, minY);
        Sref.evaluate(env);


        /*************************Search**************************/
        /*********************************************************/

        long start=System.currentTimeMillis ();

        while (t < MaxIter && nbrT < nbrTarget) {
            if(nbrT != Tcourant){
                Tcourant = nbrT;
            }

            //insert Sref in a taboo list;
            TabooList.add(Sref.position.copy());

            //TODO : Draw
//            g.setColor(Color.CYAN);
//            g.drawOval( Sref.X()-(size/(flip)),Sref.Y()-(size/(flip)),2*size/(flip),2*size/(flip));

            globalSearch(env,Sref,flip,Bees.size(),Bees,t);


            for(int i=0 ; i<Bees.size(); i++) {

                //local search
                nextPos = Bees.get(i).localSearch(env,localSize);


                if(!Bees.get(i).position.identic(nextPos))
                    Bees.get(i).putSolution(Utility.checkNextArea( env, Bees.get(i).position, nextPos, localSize,0,new TreeSet()));


                Bees.get(i).evaluate(env);

                //store the result in Dance list
                Dance.add(Bees.get(i).position.copy());

            }


            if(Sref.fitness == 1 ){
                Sref.nbrChance=MaxChances;
                Solution.add(Sref.position.copy());
                nbrT=Solution.size();

                //TODO : use shutdown
                env.matrix=env.shutdown(new Position( env.size,env.size ),Sref.position );
            }


            lol = selectBestSref(Sref, Dance, MaxChances,TabooList).copy();


            //TODO : Draw
            //env.writeimage ( t,"bso" );

            Sref.putSolution( lol.position);
            Sref.nbrChance=lol.nbrChance;
            Sref.evaluate(env);

            t++;
        }

        long end=System.currentTimeMillis ();
        this.nbtargets=Solution.size ();
        this.iter=t;
        this.timeNBSO=(end-start);

        System.out.println(t+" iteration, nbrarget "+nbrT);
        System.out.println(Solution);


        return Solution;
    }


    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/

    public void globalSearch(Dataset env, Bee Sref, int flip, int nbrBees,
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

    public Bee selectBestSref(Bee Sref, ArrayList<Position> Dance, int MaxChances, HashSet<Position> Taboo) {

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

    public Position getBestDiversity(ArrayList<Position> Dance2, HashSet<Position> Taboo){
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




    /*************************************************/
    public static void ColorTarget(Dataset env, BufferedImage image,
                                   ArrayList<Position> Solution, int TargetColor){



        for(int c=0; c<Solution.size();c++) {
            image.setRGB(Solution.get(c).Y, Solution.get(c).X,TargetColor);

            if (env.valide(Solution.get(c).X+1, Solution.get(c).Y+1))
                image.setRGB(Solution.get(c).X+1, Solution.get(c).Y+1, TargetColor);
            if (env.valide(Solution.get(c).X-1, Solution.get(c).Y-1))
                image.setRGB(Solution.get(c).X-1, Solution.get(c).Y-1, TargetColor);
            if (env.valide(Solution.get(c).X+1, Solution.get(c).Y-1))
                image.setRGB(Solution.get(c).X+1, Solution.get(c).Y-1, TargetColor);
            if (env.valide(Solution.get(c).X-1, Solution.get(c).Y+1))
                image.setRGB(Solution.get(c).X-1, Solution.get(c).Y+1, TargetColor);
            if (env.valide(Solution.get(c).X, Solution.get(c).Y+1))
                image.setRGB(Solution.get(c).X, Solution.get(c).Y + 1, TargetColor);
            if (env.valide(Solution.get(c).X, Solution.get(c).Y - 1))
                image.setRGB(Solution.get(c).X, Solution.get(c).Y - 1, TargetColor);
            if (env.valide(Solution.get(c).X-1, Solution.get(c).Y))
                image.setRGB(Solution.get(c).X-1, Solution.get(c).Y, TargetColor);
            if (env.valide(Solution.get(c).X+1, Solution.get(c).Y))
                image.setRGB(Solution.get(c).X+1, Solution.get(c).Y, TargetColor);
        }



    }
    public String toString()
    {
        return "Iterations:"+getIter ()+"\tFlip:"+flip+"\tMaxChance:"+MaxChances+"\tNbrBees:"+this.nbrBees+"\tTime:"+timeNBSO+ " sec\tNombre de cibles trouvées:"+nbrT+"\tNombre de cibles manquées:"+(nbtargets-nbrT);
    }


    /**************************************************************************************/
    /**************************************************************************************/
    public ArrayList<Double> exe2(Dataset envi,int nbrTarget) {

        /***other parameters**/
        size=envi.size;
        env = new Dataset(envi);

        int maxX=size,maxY=size,minX=0,minY=0;
        int t = 0, nbrT = 0,Tcourant=-1;

        HashSet<Position> Solution = new HashSet<>();
        ArrayList<Bee> Bees = new ArrayList<>();
        HashSet<Position> TabooList = new HashSet<>();
        ArrayList<Position> Dance = new ArrayList<>();
        Position nextPos; Bee lol;

        for(int i=0; i<nbrBees; i++){
            Bee bee = new Bee();
            Bees.add(bee);
        }


        /***algorithm***/
        //init Sref randomly
        Bee Sref = new Bee(env,maxX, minX, maxY, minY);

        Sref.evaluate(env);


        /*************************Search**************************/
        /*********************************************************/
        ArrayList<Double> results = new ArrayList();
        long startTime=System.currentTimeMillis ();

        while (t < MaxIter && nbrT < nbrTarget) {
//            System.out.println(Sref.toString());
            if(nbrT != Tcourant){
                Tcourant = nbrT;
            }

            //insert Sref in a taboo list;
            TabooList.add(Sref.position.copy());

            globalSearch(env,Sref,flip,Bees.size(),Bees,t);

            for(int i=0 ; i<Bees.size(); i++) {
                //local search
                nextPos = Bees.get(i).localSearch(env,localSize);

                if(!Bees.get(i).position.identic(nextPos))
                    Bees.get(i).putSolution(Utility.checkNextArea( env, Bees.get(i).position, nextPos, localSize,0,new TreeSet()));


                Bees.get(i).evaluate(env);

                //store the result in Dance list
                Dance.add(Bees.get(i).position.copy());
            }


            if(Sref.fitness == 1 ){
                Sref.nbrChance=MaxChances;

                Solution.add(Sref.position.copy());
                nbrT=Solution.size();

                env.matrix=env.shutdown(new Position( env.size,env.size ),Sref.position );

            }

            lol = selectBestSref(Sref, Dance, MaxChances,TabooList).copy();


            //Assign the best solution to Sref
            Sref.putSolution( lol.position);
            Sref.nbrChance=lol.nbrChance;
            Sref.evaluate(env);

            t++;
        }


        long stopTime=System.currentTimeMillis ();
        long elapsedTime=stopTime-startTime;

        /**FOR : GA**/
        this.nbtargets=Solution.size ();
        this.iter=t;
        this.timeNBSO=(elapsedTime);

//        System.out.println(t+" iteration, nbrarget "+nbrT);

        /**FOR : Expérimentation**/
        results.add(t*1.0);
        results.add((elapsedTime * 1.0)/1000.0);
        results.add(nbrT * 1.0);


        System.out.println(Solution);
        return results;
    }



}