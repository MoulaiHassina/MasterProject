package Environnement;

import java.util.ArrayList;
import java.util.Random;

public class Position implements Comparable<Position>{

    public int X;
    public int Y;
    public double minDistance;
    /*constructeurs*/
    public Position(Dataset env, int maxX, int minX, int maxY, int minY){
        do {
            Random random = new Random();
            X = random.nextInt(maxX);
            Y = random.nextInt(maxY);
        }while(env.getValue(X,Y)==-1);
        minDistance=0;

    }

    public Position(){
        X = 0;
        Y = 0;
        minDistance=0;
    }

    public Position(int x, int y){
        X = x;
        Y = y;
        minDistance=0;
    }
    public Position(Dataset env, int size){
        do{
            Random random = new Random();
            X = random.nextInt(size) ;
            Y = random.nextInt(size);
        }while(env.getValue(X,Y)==-1);
        minDistance=0;
    }

    /*methods*/

    public Position copy(){
        return new Position(this.X,this.Y);
    }


    public boolean identic(Position position){
        if(position.Y == this.Y && position.X == this.X)
            return true;
        else
            return false;
    }




    public ArrayList<Position> getCiclePositions(Dataset env,int flip){
        ArrayList<Position> circlePos = new ArrayList();
        Position pos;
        int d = env.size/flip;


        for (int i = -d; i <= d; i++) {
            for (int j = -d; j <= d; j++) {
                if ((Math.pow(i, 2) + Math.pow(j, 2) == Math.pow(d, 2))
                        && env.valide(X + i, Y + j)) {
                    pos = new Position(X + i, Y + j);
                    i++;
                    j++;
                    circlePos.add(pos);

                }
            }
        }

        return circlePos;
    }


    @Override
    public String toString() {
        return "Position("+X+" , "+Y+")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + 7;
        result = prime * result +6;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Position other = (Position) obj;

        return  this.identic(other);

    }


    @Override
    public int compareTo(Position p) {
        return ((this.X-p.X)+(this.Y-p.Y));
    }

    public Position nearPosition( Dataset env){
        int var = 0,X=-1,Y=-1;
        int taux = 10;

        do{
            var = (int)(Math.random()*100) % 4;
            switch (var) {
                case 3:
                    X = (int) (Math.random() * taux) + this.X;
                    Y = (int) (Math.random() * taux) + this.Y;
                    break;
                case 1:
                    X = this.X - (int) (Math.random() * taux);
                    Y = this.Y + (int) (Math.random() * taux );
                    break;
                case 2:
                    X = this.X + (int) (Math.random() * taux);
                    Y = this.Y - (int) (Math.random() * taux );
                    break;
                case 0:
                    X = this.X - (int) (Math.random() * taux);
                    Y = this.Y - (int) (Math.random() * taux);
                    break;
            }
        }while (env.valide(X,Y) == false);

        //System.out.println("ran***"+(int) (Math.random() * taux));
        return new Position(X,Y);
    }

    public double distance(Position p1){
        return Math.sqrt(Math.pow(p1.X-this.X,2)+Math.pow(p1.Y-this.Y,2));
    }

    public  Position addto(Position p1)
    {
        return new Position ( this.X
        +p1.X,Y+p1.Y);
    }

}
