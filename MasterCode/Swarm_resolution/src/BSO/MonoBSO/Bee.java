package BSO.MonoBSO;

import Environnement.Dataset;
import Environnement.Position;

import java.util.ArrayList;
public class Bee {

    public Position position;
    public double fitness;
    public int nbrChance;
    public double minDistance;


    public Bee(Dataset env, int maxX, int minX, int maxY, int minY){
        position = new Position(env,maxX, minX, maxY, minY);
        fitness = 0.;
        nbrChance = 0;
        minDistance = 0;

    }


    public Bee(){
        position = new Position();
        fitness=0;
        nbrChance = 0;
        minDistance = 0;

    }

    public Bee(Position p){
        position = new Position(p.X,p.Y);
        fitness=0;
        nbrChance = 0;
        minDistance = 0;

    }

    public int X(){return position.X;}
    public int Y(){return position.Y;}

    public void putSolution(Position pos){
        this.position.X = pos.X;
        this.position.Y = pos.Y;
    }

    public void evaluate(Dataset env){

        this.fitness = env.getValue(this.position.X,this.position.Y);
    }



    public Bee copy(){
        Bee beeCopy = new Bee();
        beeCopy.fitness = this.fitness;
        beeCopy.position.X = this.position.X;
        beeCopy.position.Y = this.position.Y;
        beeCopy.nbrChance = this.nbrChance;

        return beeCopy;
    }

    public double distance(Bee bee){
        return Math.sqrt(Math.pow(bee.position.X-this.position.X,2) +
                Math.pow(bee.position.Y-this.position.Y,2));
    }


    public Position localSearch(Dataset env, int localSize){
        double localBest = this.fitness, b;
        Boolean improoved = false;
        Bee bee =  this.copy();
        localSize = localSize*2;

        //TODO : improve local search

        for (int i = -localSize; i <= localSize; i++) {
            for (int j = -localSize; j <= localSize; j++) {

                if (env.valide(position.X + i, position.Y + j)){
//                        && Math.pow( i, 2) + Math.pow(j, 2) < Math.pow(localSize , 2)) {

                    b = env.getValue(this.position.X + i, this.position.Y + j);

                    if (localBest < b) {

                        localBest = b;
                        bee.putSolution(
                                new Position(this.position.X + i, this.position.Y + j)
                        );
                        bee.fitness = localBest;
                        improoved = true;
                    }
                }
            }
        }


        if(improoved == true) bee.putSolution(bee.localSearch(env,localSize));
        return bee.position;
    }


    public boolean existIn(ArrayList<Bee> Taboo){
        for(int i=0 ; i<Taboo.size(); i++){
            if(this.position.identic(Taboo.get(i).position)){
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return nbrChance+", Bee: " + position.toString() + "fitness: " + fitness + "\n";
    }
}
