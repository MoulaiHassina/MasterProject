package GA;

import Environnement.Dataset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by masterubunto on 20/02/19.
 */
public class Population
{

    public TreeSet<Solution> individus ;

    private int size;



    public void setIndividus(TreeSet<Solution> individus) {
        this.individus = individus;
    }

    public int getSize() {
        return size;
    }

    public TreeSet<Solution> getIndividus() {
        return individus;
    }

    public void setSize(int size) {
        this.size = size;
    }


    /*constructors*/

    public Population ()
    {
        size=0;
        this.individus=new TreeSet<Solution> ();
    }


    /*methods*/

    public void Evaluate_Population(int nbrCibles, Dataset env,int mode)
    {
        TreeSet<Solution> aftereval= new TreeSet<Solution> (  );

        for (Solution s:this.individus)
        {
            s.setFobjective (s.Evaluate( nbrCibles,  env,mode));
            aftereval.add ( s );

        }
        this.setIndividus (  aftereval);
    }


    public Solution[] BestParents (int mode,int bestmax){

        /*TODO: remove the index and replace by remove function with object parameters*/
        /*TODO: check if remove is actually working or not*/

        Solution bestparents[] = new Solution[2];
        Solution sbestmale=new Solution(Operation.getLimits ().size ());
        Solution sbestfemale=new Solution(Operation.getLimits ().size ());
        sbestfemale.setiteration ( 2000 );
        sbestmale.setiteration ( 2000 );

        bestparents[0]=this.individus.pollFirst ();
        bestparents[1]=this.individus.pollFirst ();

        return bestparents;
    }


    public void GenerateRandompopulation (int size,int mode)
    {

        for (int i=0;i<size;i++)
        {
            this.individus.add ( Operation.getrandomsolution ( Operation.getLimits ().size () , mode) );
        }

    }

    public void add(Solution s)
    { /** check if it doesnt exist yet**/
        this.individus.add ( s );
        this.size=this.individus.size ();
    }

    public void display(int nbtarget,int mode)
    {
        for(Solution s:this.individus)
            if( s.getFobjective ()==nbtarget)
                System.out.println (s.toString (mode) );
    }

    public void display(int mode)
    {
        for(Solution s:this.individus)
            System.out.println (s.toString (mode) );
    }





}
