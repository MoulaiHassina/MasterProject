package ESWSA.SubswarmEswsa;

import ESWSA.SimpleESWSA.ESWSA;
import Environnement.Position;
import Environnement.Dataset;
import EvitementObs.Utility;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by masterubunto on 25/03/19.
 */
public class Elephant implements Comparable<Elephant>
{ /** each x and v has dimension that is 2 (2D)**/
    public Position X;
    int Minvelocity;
    public Position V;
    public Position Pbest;
    public double Pbestfitness;

    public double Fojbetive;

    public  int num;


    /**constructor**/
    public Elephant()
    {
        Random r = new Random(  );
        // System.out.println (size+minsize );
        X= new Position ();
        V= new Position ();

        this.Pbest=new Position ( X.X,X.Y );
        this.Pbestfitness=0;
    }
    public Elephant(int i,int size,int minsize,Dataset env)
    { /** i is related to the i th elephant we just want to avoid that all elephants at first stand on the same position**/
        //X=new Position ( i*3,i*4);
        V=new Position ( 0,0 );
        num=i;
        Minvelocity=8;
      /*TODO: init at random position and 0,0 position*/

        Random r = new Random(  );
       // System.out.println (size+minsize );
      //  do {
        X= new Position (  r.nextInt ( size )+minsize,r.nextInt (size)+minsize);
        //} while (env.getValue ( X.X,X.Y )==-1);
      //  V= new Position ( r.nextInt ( size )+minsize,r.nextInt ( size )+minsize);

        this.Pbest=new Position ( X.X,X.Y );
        this.Pbestfitness=0;
    }

    /*** methods**/
    public double Evaluate(Dataset env, int localSize) {
        double b=0, localBest = 0;
        Position p =new Position ( 0,0 );
        /** insert evaluation of solution Code here**/
        for (int i = -localSize; i <= localSize; i++) {
            for (int j = -localSize; j <= localSize; j++) {

                if (env.valide ( X.X + i, X.Y + j )) {
                    b = env.getValue ( this.X.X + i, this.X.Y + j );

                    if (localBest < b ){
                        localBest = b;
                        p.X = X.X + i;
                        p.Y = X.Y + j;
                    }
                }
            }
   /*checking Environnement*/

        }
        if(p.X!=0 && p.Y!=0) {

//          env.editposition ( this.X,p, new Color ( 0,105,255 ));

            this.X = p;

        }

        this.Fojbetive=localBest;
        /** updating pbest if the new local is better than personalbest**/

        if(this.Fojbetive > this.Pbestfitness) {
            this.Pbest = new Position ( this.X.X,this.X.Y);
            this.Pbestfitness = this.Fojbetive;
        }

        return localBest;
    }



    public void updatevelocity(double p, int size, double Wt, Position Gbest)
    {
        Random r = new Random (  );
        double rand= r.nextDouble ();

        if( rand> p)
        {
            /** global best update**/
            V.X= (int) (V.X*Wt+((Gbest.X-this.X.X)*(rand))%size)%size;
            V.Y= (int) (V.Y*Wt+((Gbest.Y-this.X.Y)*(rand))%size)%size;
        }
        else
        {
            /** personnal best update**/
            V.X= (int) (V.X*Wt+((this.Pbest.X-this.X.X)*(rand))%size)%size;
            V.Y= (int) (V.Y*Wt+((this.Pbest.Y-this.X.Y)*(rand))%size)%size;
        }

//        if(Math.abs (  V.X)<Minvelocity && Math.abs (  V.Y)<Minvelocity)
//        {
//            V= new Position ( r.nextInt ( 2*Math.abs (  size+1)/3 ),r.nextInt ( 2*Math.abs (  size+1)/3 ));
//
//        }

    }
    public void updatevelocity2(double p,int sizey, int size, double Wt, Position Gbest) {
        Random r = new Random ( );
        double rand = r.nextDouble ( );

        if (rand > p) {
            /** global best update**/
            V.X = (int) (V.X * Wt + (Gbest.X - this.X.X) * (rand)) % size;
            V.Y = (int) (V.Y * Wt + (Gbest.Y - this.X.Y) * (rand)) % sizey;
        } else {
            /** personnal best update**/
            V.X = (int) (V.X * Wt + (this.Pbest.X - this.X.X) * (rand)) % size;
            V.Y = (int) (V.Y * Wt + (this.Pbest.Y - this.X.Y) * (rand)) % sizey;
        }

        if (Math.abs ( V.X ) < Minvelocity && Math.abs ( V.Y ) < Minvelocity) {
            V = new Position ( r.nextInt ( 2 * Math.abs ( size + 1 ) / 3 ), r.nextInt ( 2 * Math.abs ( size + 1 ) / 3 ) );

        }
    }

    public void updateposition(Position Xmax,Dataset env,Position Xmin)
    {


        if(this.X.X<Xmin.X) this.X.X+=Xmin.X;
        if(this.X.Y<Xmin.Y) this.X.Y+=Xmin.Y;
         int xx =Math.abs((this.X.X+V.X)%Xmax.X);
         int yy=Math.abs((this.X.Y+V.Y)%Xmax.Y);
        this.X.X=Math.abs ( this.X.X )%Xmax.X;
        this.X.Y=Math.abs ( this.X.Y )%Xmax.Y;
//        int xx =Math.abs((this.X.X+V.X));
//        int yy=Math.abs((this.X.Y+V.Y));

//        if(this.X.X<Xmin.X) this.X.X+=Xmin.X;
//        if(this.X.X>Xmin.Y) this.X.X=X.X%Xmin.Y;
//        if(this.X.Y<Xmax.X) this.X.Y+=Xmax.X;
//        if(this.X.Y>Xmax.Y) this.X.Y=X.Y%Xmax.Y;

       //env.getG ().drawLine ( this.X.X,this.X.Y,xx,yy );
        Position pos = Utility.checkNextArea( env, this.X, new Position(xx,yy),10,0,new TreeSet());

        if(env.getValue ( pos.X,pos.Y)>=this.Pbestfitness)
        {
            this.Pbest=pos;
            this.Pbestfitness=env.getValue ( pos.X,pos.Y);
        }

        this.X.X=pos.X;
        this.X.Y=pos.Y;

//
//        if(this.X.X<Xmin.X) this.X.X+=Xmin.X;
//        if(this.X.Y<Xmin.Y) this.X.Y+=Xmin.Y;
    }

    public void updateposition2(Position Xmax,Dataset env,Position Xmin)
    {


//        if(this.X.X<Xmin.X) this.X.X+=Xmin.X;
//        if(this.X.Y<Xmin.Y) this.X.Y+=Xmin.Y;
//        int xx =Math.abs((this.X.X+V.X)%Xmax.X);
//        int yy=Math.abs((this.X.Y+V.Y)%Xmax.Y);
//        this.X.X=Math.abs ( this.X.X )%Xmax.X;
//        this.X.Y=Math.abs ( this.X.Y )%Xmax.Y;
        int xx =Math.abs((this.X.X+V.X));
        int yy=Math.abs((this.X.Y+V.Y));

        if(this.X.X<Xmin.X) this.X.X+=Xmin.X;
        if(this.X.X>Xmin.Y) this.X.X=X.X%Xmin.Y;
        if(this.X.Y<Xmax.X) this.X.Y+=Xmax.X;
        if(this.X.Y>Xmax.Y) this.X.Y=X.Y%Xmax.Y;

        //env.getG ().dr awLine ( this.X.X,this.X.Y,xx,yy );
        Position pos = Utility.checkNextArea( env, this.X, new Position(xx,yy),10,0,new TreeSet());

        if(env.getValue ( pos.X,pos.Y)>=this.Pbestfitness)
        {
            this.Pbest=pos;
            this.Pbestfitness=env.getValue ( pos.X,pos.Y);
        }

        this.X.X=pos.X;
        this.X.Y=pos.Y;

//
//        if(this.X.X<Xmin.X) this.X.X+=Xmin.X;
//        if(this.X.Y<Xmin.Y) this.X.Y+=Xmin.Y;
    }


    public String toString()
    {
        return "elephant:"+num+" fitness:"+Fojbetive+" position:"+this.X.toString ()+" :pbeset"+this.Pbest.toString ();
    }


    @Override
    public int compareTo(Elephant o) {

     /* int k;
      if((this.X.identic ( o.X )) )
        k=0;
      else k=1;*/
        if(this.Fojbetive-o.Fojbetive==0)
            return this.num-o.num;
        else return (int) (this.Fojbetive-o.Fojbetive) ;
    }


    public  Position shutDownarea(Position p, int portee, Position Xmax, Dataset env, Position Gbest, TreeMap <Double,ArrayList<Elephant>> elephant)
    {

        /* position p is the postion where fitness ==1
        * */
        int maxline=Math.abs ( p.X )+portee;
        int maxcolumn=Math.abs ( p.Y )+portee;
        if(maxline>Xmax.X)
            maxline=Xmax.X;
        if(maxcolumn>Xmax.Y)
            maxcolumn=Xmax.Y;
        for (int k = 0; k <portee; k++) {
            for(int i=p.X-portee;i<maxline;i++)
                for(int j=p.Y-portee;j<maxcolumn;j++)

                    if(Math.pow (  i-p.X,2)+Math.pow (  j-p.Y,2)<Math.pow (portee-k,2))
                    {

                        double b = ((double)(k+1)/(double)(portee));
                        if(env.valide( i,j))
                            if(env.getValue ( i,j ) <= b )

                                env.setValue ( i,j,0.0 );


                        elephant= ESWSA.checkelephant ( elephant,new Position ( i,j ) );

                        if(Gbest.identic ( new Position ( i,j ) ))
                        { Random r =new Random (  );
                            do
                            {
                                Gbest = new Position (r.nextInt ( Xmax.X ), r.nextInt ( Xmax.Y ) );
                            }
                            while(env.getValue ( Gbest.X,Gbest.Y )<0);
                            // /  Pbestfitness=env.getValue ( Pbest.X,Pbest.Y);
                        }


                    }}


        return Gbest;
    }

    public double getPbestfitness() {
        return Pbestfitness;
    }

    public Position getPbest() {
        return Pbest;
    }
}