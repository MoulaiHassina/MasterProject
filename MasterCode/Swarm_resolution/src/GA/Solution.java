package GA;

import Environnement.Dataset;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by masterubunto on 20/02/19.
 */
public class Solution extends ArrayList<Integer> implements Comparable<Solution>{

    private ArrayList<Integer> Parameters;
    private long Fobjective;
    long timelapsed=0;
    private int iteration;
    private  int sizesolution;
    public boolean choosed=false;


    public void setTimelapsed(long timelapsed) {
        this.timelapsed = timelapsed;
    }

    public void setParameters(ArrayList<Integer> parameters) {
        Parameters = parameters;
    }

    public ArrayList<Integer> getParameters() {
        return Parameters;

    }

    public long getFobjective() {
        return Fobjective;
    }

    public void setFobjective(long fobjective) {
        Fobjective = fobjective;
    }

    /* constructers*/

    public Solution (int p1, int p2 , int p3, int p4, int p5)
    {
        this.Parameters=new ArrayList (  );
        this.Parameters.add ( p1 );
        this.Parameters.add(p2);
        this.Parameters.add ( p3 );
        this.Parameters.add(p4);
        this.Parameters.add(p5);
        Fobjective=0;

    }


    public Solution (int p1, int p2 , int p3, int p4)
    {
        this.Parameters=new ArrayList (  );
        this.Parameters.add ( p1 );
        this.Parameters.add(p2);
        this.Parameters.add ( p3 );
        this.Parameters.add(p4);

        Fobjective=0;

    }


    public Solution(int mode,int x){
        this.Parameters = new ArrayList<>();
        if(mode == 0 || mode == 2){
            for(int i =0 ; i<3; i++){
                Parameters.add(0);
            }
        }

        if(mode == 1 || mode == 3 || mode ==4){
            for(int i =0 ; i<4; i++){
                Parameters.add(0);
            }
        }

    }
    public Solution(int size)
    {
        this.setParameters ( new ArrayList<> (  ) );
        for(int i=0;i<size;i++)
            Parameters.add ( 0 );
    }

    public Solution (int p1, int p2 , int p3)
    {
        this.Parameters=new ArrayList (  );
        this.Parameters.add ( p1 );
        this.Parameters.add(p2);
        this.Parameters.add ( p3 );

        Fobjective=0;

    }
    public  void setiteration(int iteration)
    {
        this.iteration=iteration;
    }
    public int getiteration()
    {
        return iteration;
    }
    public void setchoosed(boolean value)
    {
        this.choosed=value;
    }

    public Solution (ArrayList parameters)
    {
        this.Parameters=parameters;
        Fobjective=0;
    }

//    public Solution (int sizesolution)
//    {
//        this.sizesolution=sizesolution;
//        this.setParameters ( new ArrayList<> (  ) );
//        this.init ();
//    }

    /*evaluation of solution*/

    public long Evaluate (int nbrCibles, Dataset env,int mode)
    {
        Solution s=this;
        this.Fobjective=Evaluation.Evalute ( s, nbrCibles,env,mode);
        return this.Fobjective;
    }

    public void Copy(Solution s, int d, int size)

    {   if( this.size ()==0)
        for(int i=0;i<s.size ();i++)
        {
            this.Parameters.add ( 0 );
        }
        System.out.println ("d:"+d+"size:"+size );
        for (int i=d;i<size;i++)
        {
            this.getParameters ().set ( i,s.getParameters ().get ( i ) );
        }
    }


    public void set (int index, int element)
    {
        this.Parameters.set ( index, element);
    }


    public Integer get (int index)
    {
        return this.Parameters.get(index);
    }


    public void init()
    {
        if(this.getParameters ()==null) this.setParameters ( new ArrayList<> (  ) );
        int newvaleur=0;
        //  this.add ( 1500 );
        Random r = new Random (  );
        for (int i = 0; i< Operation.getLimits ().size (); i++)
        {
            newvaleur = (r.nextInt ( (int) Operation.getLimits ().get ( i ) -
                    (int) Operation.getMiniLimits ().get ( i ) +1))+(int) Operation.getMiniLimits ().get ( i );

            this.add (newvaleur );
        }

    }

    public long getIteration() {
        return iteration;
    }

    public int size()
    {
        return this.getParameters().size ();
    }
    public void add( int i)
    {
        this.getParameters ().add ( i );
    }




    public String toString(int mode) {
        String s = "";
        if(mode==0)
        {  s="nbrTargets:"+this.Fobjective+" maxiter:"+this.getiteration ()
                    +" flip:"+this.getParameters ().get (  0)
                    +" maxchances:"+this.getParameters ().get ( 1)
                    +" nbrBees:"+this.getParameters().get(2)

                    +" time:"+timelapsed+" miliS";}



        if(mode==1)
        {
            double beta=(double)(getParameters ().get ( 3 ))/10;
            double alpha=(double)(getParameters ().get ( 2 ))/10;
            s="nbrTargets:"+this.Fobjective+" maxiter:"+this.getiteration ()
                    +" nbclan:"+this.getParameters ().get (  0)
                    +" nbelephant:"+this.getParameters ().get ( 1)
                    +" alpha:"+alpha
                    + "beta:"+beta

                    +" time:"+timelapsed+" miliS";}

        if(mode==2)
            s="nbrTargets:"+this.Fobjective+" maxiter:"+this.getiteration ()

                    +" nbelephant:"+this.getParameters ().get ( 0)
                    +" wt:"+this.getParameters ().get ( 1 )/10.0
                    + "p:"+this.getParameters ().get ( 2 )/10.0

                    +" time:"+timelapsed+" miliS";

        if(mode==3)
        {

            s="nbrTargets:"+this.Fobjective+" maxiter:"+this.getiteration ()
                    +" flip:"+this.getParameters ().get (  0)
                    +" maxchance:"+this.getParameters ().get ( 1)
                    +" nbrBees:"+this.getParameters().get(2)
                    + "nbrSwarms:"+this.getParameters().get(3)

                    +" time:"+timelapsed+" miliS";}
        if(mode==4)
        {   s="nbrTargets:"+this.Fobjective+" maxiter:"+this.getiteration ()+"nbswarm:"+this.getParameters ().get ( 0 )

                +" nbelephant:"+this.getParameters ().get ( 1)
                +" wt:"+this.getParameters ().get ( 2 )/10.0
                + "p:"+this.getParameters ().get ( 3 )/10.0

                +" time:"+timelapsed+" miliS";
          }




        return s;
    }


    @Override
    public int compareTo(Solution user) {


        if(user.getFobjective ()!=this.getFobjective ())
            return (int)(+user.getFobjective ()-this.getFobjective ());

        if(user.getiteration ()!=this.getiteration ()) return -user.getiteration ()+this.getiteration ();

        for(int i=0;i<user.Parameters.size ();i++)
        {
            if(user.Parameters.get ( i ) != this.Parameters.get ( i ) )
                return (-user.Parameters.get ( i ) +this.Parameters.get ( i )) ;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass ( ) != o.getClass ( )) return false;
        if (!super.equals ( o )) return false;

        Solution solution = (Solution) o;

        if (Fobjective != solution.Fobjective) return false;
        if (iteration != solution.iteration) return false;
        //  if (sizesolution != solution.sizesolution) return false;
        // if (choosed != solution.choosed) return false;
        return Parameters != null ? Parameters.equals ( solution.Parameters ) : solution.Parameters == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode ( );
        result = 31 * result + (Parameters != null ? Parameters.hashCode ( ) : 0);
        result = 31 * result + (int) (Fobjective ^ (Fobjective >>> 32));
        result = 31 * result + iteration;
        result = 31 * result + sizesolution;
        result = 31 * result + (choosed ? 1 : 0);
        return result;
    }

}
