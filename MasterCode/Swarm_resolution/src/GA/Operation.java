package GA;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by masterubunto on 20/02/19.
 */
public class Operation
{

    static private ArrayList limits;
    static private ArrayList MiniLimits;


    /** limits are constraint on each variable of solution (max value)**/
    /** miniLimits are min value constraint for each variable of solution**/
    /*constructors*/
    public static ArrayList getLimits() {
        return limits;
    }

    public static ArrayList getMiniLimits() {
        return MiniLimits;
    }

    /*METHODS*/

    public static void setLimits(ArrayList limits) {
        Operation.limits = limits;
    }

    public static void setMiniLimits(ArrayList miniLimits) {
        MiniLimits = miniLimits;
    }

    public static Solution Crossover  (Solution s1, Solution s2)
    {
        Solution child =new Solution(Operation.getLimits ().size ());

        /** split solution**/
        Random r = new Random (  );
        int split= r.nextInt(s1.getParameters ().size () ) ;
        child.Copy(s1,0,split);
        child.Copy ( s2,split,s2.size () );

        return child;
    }

    public static Solution Mutation (Solution s, int nbmutation,int size,int mode )
    {   /*TODO: check if the random is not duplicated*/
        Random r = new Random (  );
        int ranindex;

        for (int i=0;i<nbmutation;i++)
        {
            ranindex =r.nextInt( s.size () );
            s.set(ranindex  , Getrandomfromeho(s.getParameters().get( i ),ranindex) );
        }

        return s;

    }



    public static int Getrandomfromeho(int ancienvaleur,int indexofparameters)
    {
        Random r = new Random();
        int newvaleur;

        newvaleur = (r.nextInt ( (int) Operation.limits.get ( indexofparameters ) -
                (int) Operation.MiniLimits.get ( indexofparameters ) +1))+
                (int) Operation.MiniLimits.get ( indexofparameters );


        if(newvaleur == ancienvaleur) newvaleur+=1;
        if(newvaleur < (int)Operation.getMiniLimits().get ( indexofparameters ))
            newvaleur = (int)Operation.getMiniLimits().get ( indexofparameters );
        if(newvaleur > (int)Operation.limits.get ( indexofparameters )) newvaleur--;

        return newvaleur;
    }




    public static Solution getrandomsolution (int size,int mode)
    {

        Solution s1 = new Solution(Operation.getLimits ().size ());
        System.out.println ("solution:"+ s1.size ()+"sizelimits:"+Operation.getLimits ());

        Random r = new Random (  );

        int newvaleur;
        for (int i = 0; i< Operation.getLimits ().size (); i++)
        {
            newvaleur = (r.nextInt ( (int) Operation.limits.get ( i ) -
                    (int) Operation.MiniLimits.get ( i ) +1)) +
                    (int) Operation.MiniLimits.get ( i );


            if(newvaleur==0) newvaleur= (int)Operation.MiniLimits.get ( i );
                s1.set( i,newvaleur );

        }

        return s1;
    }


}
