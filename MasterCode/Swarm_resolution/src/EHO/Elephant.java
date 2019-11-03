package EHO;

import Environnement.Dataset;

import java.util.ArrayList;
import java.util.TreeSet;

import Environnement.Position;
import EvitementObs.Utility;

public class Elephant  implements Comparable{

    public Position position;
    public double fitness;

    Elephant(){
        position = new Position();
        fitness=0;
    }

    Elephant(Dataset env , int size){
        position = new Position(env,size);
        this.evaluate(env);
    }

    Elephant(Dataset env , Position pos){

        this.position = pos.nearPosition(env);
        this.evaluate(env);
    }

    Elephant(Position position, double fitness){
        this.position = position;
        this.fitness=fitness;
    }

    /***********************************************************/

    public int X(){return position.X;}
    public int Y(){return position.Y;}

    public void putSolution(Position pos){
        this.position.X = pos.X;
        this.position.Y = pos.Y;
    }


    public void evaluate(Dataset env){
        this.fitness = env.getValue(this.X(), this.Y());
    }


    public Elephant copy(){
        Elephant elephant = new Elephant();
        elephant.fitness = this.fitness;
        elephant.position.X = this.position.X;
        elephant.position.Y = this.position.Y;

        return elephant;
    }




    public void Equation1(Dataset env, Elephant Best, double alpha){
        double r = Math.random();
        int xx,yy,nbrChances=0,maxChance=10;

        do {
            xx = this.X() + (int) (alpha * (Best.X() - this.X()) * r)+nbrChances;
            yy = this.Y() + (int) (alpha * (Best.Y() - this.Y()) * r)+nbrChances;

            if(env.valide(xx,yy) == false) {
                xx = this.X() + (int) (alpha * (Best.X() - this.X()) * r) - nbrChances;
                yy = this.Y() + (int) (alpha * (Best.Y() - this.Y()) * r) + nbrChances;
            }

            if(env.valide(xx,yy) == false) {
                xx = this.X() + (int) (alpha * (Best.X() - this.X()) * r) + nbrChances;
                yy = this.Y() + (int) (alpha * (Best.Y() - this.Y()) * r) - nbrChances;
            }

            if(env.valide(xx,yy) == false) {
                xx = this.X() + (int) (alpha * (Best.X() - this.X()) * r) - nbrChances;
                yy = this.Y() + (int) (alpha * (Best.Y() - this.Y()) * r) - nbrChances;
            }


            nbrChances++;
        }while(env.valide(xx,yy) == false && nbrChances < maxChance);



        if(env.valide(xx,yy)) {

            //TODO : call step by step walk
            this.position=Utility.checkNextArea( env, this.position,new Position(xx,yy), 10,0,new TreeSet());

        }
    }

    public void Equation2(Dataset env, Position CG, double beta){
        int xx = (int)(beta * CG.X);
        int yy = (int)(beta * CG.Y);

        if(env.valide(xx,yy)) {
            //TODO : call step by step walk
            this.position=Utility.checkNextArea( env, this.position,new Position(xx,yy), 10,0,new TreeSet());

        }
    }
    /****************************************************/

    public boolean existIn(ArrayList<Elephant> Taboo){
        for(int i=0 ; i<Taboo.size(); i++){
            if(this.position.identic(Taboo.get(i).position)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        //du plus grand au plus petit
        Elephant elephant = (Elephant) o;
        if(this.fitness< elephant.fitness)
            return 1;
        else if(this.fitness > elephant.fitness)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "Elephant: " + position.toString() + " : fitness: " + fitness + "\n";
    }



}
