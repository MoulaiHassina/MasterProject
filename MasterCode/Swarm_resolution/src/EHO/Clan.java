package EHO;

import Environnement.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import Environnement.Position;
import EvitementObs.Utility;

public class Clan{

    public ArrayList<Elephant> clan;

    Clan(){
        clan = new ArrayList();
    }

    Clan(ArrayList c){
        clan = c;
    }

    Clan(Dataset env, int nbrElephant,Position p ){
        clan = new ArrayList();
        Position position = p;

        for(int i =0 ; i< nbrElephant ; i++){

            Elephant elephant = new Elephant(env,position);
            elephant.evaluate(env);
            clan.add(elephant);


        }
    }
    Clan(Dataset env,int size, int nbrElephant ){
        clan = new ArrayList();
        Position position = new Position ( env,size );

        for(int i =0 ; i< nbrElephant ; i++){

            Elephant elephant = new Elephant(env,position);
            elephant.evaluate(env);
            clan.add(elephant);


        }
    }


    /***********************************************************/

    public void sorte(){
        Collections.sort(clan);
    }

    public Elephant getBest(){
        return clan.get(0);
    }

    public Elephant getWorst(){
        return clan.get(clan.size()-1);
    }

    /****************************************************/

    public void update( Dataset env, double alpha, double beta){


        for(int i = 1 ; i < clan.size(); i++) {
            //Update X c,i and generate X new,c,i by Eq. (2.1).
            clan.get(i).Equation1(env, this.getBest(), alpha);
            clan.get(i).evaluate(env);
        }

        Position CG = this.CenterGravity();

        //Update X c, i and generate X new, c, i by Eq.(2.2).

        this.getBest().Equation2(env,CG , beta);
        this.getBest().evaluate(env);

    }


    public Position CenterGravity(){
        int x=0, y=0;
        for(int i = 0; i<= this.clan.size()-1 ; i++){
            x = x + clan.get(i).position.X;
            y = y + clan.get(i).position.Y;
        }

        //if(clan.size ()==1) clan.add ( new Elephant(  ) );

        x = (int) x / (clan.size());
        y = (int) y / (clan.size());

        //System.out.println(new Position(x,y).toString());
        return new Position(x,y);
    }


    public void separate( Dataset env,  int size) {
        //Elephant e = this.getWorst().copy();

        double rand;
        int xx,yy;

        do {
            rand = Math.random();
            xx = (int) ((size) * rand);

            rand = Math.random();
            yy = (int) ((size) * rand);

        }while(!env.valide(xx,yy));

        //env.drawanimal ( this.getWorst ().position );

        //TODO : call step by step walk
        Position p= Utility.checkNextArea( env, this.getWorst().position,new Position(xx,yy), 10,0,new TreeSet());

        this.getWorst().position.X = p.X;
        this.getWorst().position.Y = p.Y;
        Elephant e=this.getWorst ();
        e.position=p;
        clan.set ( clan.size ()-1,e );


        this.getWorst().evaluate(env);
    }

    /*************************************************************/

    @Override
    public String toString() {
        String s = "";
        for(int i=0 ; i<this.clan.size(); i++){
            s = s + clan.get(i).toString();
        }
        return s;
    }
}
