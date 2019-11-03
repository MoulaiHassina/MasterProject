package GA;

import BSO.MultiBSO.MultiBSO;
import ESWSA.SimpleESWSA.ESWSA;
import ESWSA.SubswarmEswsa.SwarmESWSA;
import Environnement.Position;
import BSO.MonoBSO.nBSO;
import EHO.nEho;
import Environnement.Dataset;

import java.util.ArrayList;

/**
 * Created by masterubunto on 20/02/19.
 */
public class Evaluation
{


/*class containing all kind of evaluation for solution ,
checking constraints on any member of solutions*/

    public static long Evalute(Solution solution, int nbrCibles, Dataset env,int mode){


        if (mode == 0) {
            nBSO bso = new nBSO(1000 );
            bso.SetParametersvector ( (ArrayList<Integer>) solution.getParameters ( ) );

            //TODO : uncomment
            bso.exe2( env, nbrCibles );

            solution.setFobjective ( bso.getNbtargets ( ) );
            solution.setiteration ( bso.getIter ( ) );
            solution.setTimelapsed ( bso.getTimeNBSO ( ) );

            return bso.getNbtargets ( );
        }

        if (mode == 1) {
            /*** eho ***/

            nEho neho = new nEho(env.size,1000);

            double beta=(double)(solution.getParameters ().get ( 3 ))/10;
            double alpha=(double)(solution.getParameters ().get ( 2 ))/10;

            neho.exe2(env,nbrCibles,solution.getParameters().get( 0 ),solution.getParameters().get(1),alpha,beta);

            solution.setiteration ( neho.getIteration () );
            solution.setFobjective ( neho.getNbtarget () );
            solution.setTimelapsed ( neho.getTimelapsed () );

            return neho.getNbtarget ();
        }

        if(mode==2)
        {   double wt,p;
            wt=solution.getParameters ().get ( 1 )/10.0;
            p=solution.getParameters ().get ( 2 )/10.0;

            ESWSA eswsa = new ESWSA (solution.getParameters ().get ( 0 ),1000,new Position ( env.size-1,env.size-1),new Position ( 0,0 ),p,env,wt  );

            eswsa.exe2( nbrCibles );

            solution.setFobjective ( eswsa.getNbtarget ()  );
            solution.setiteration ( eswsa.getIteration ());
            solution.setTimelapsed ( eswsa.getTimelasped () );


            return solution.getFobjective ();
        }

        if (mode == 3) {
            MultiBSO mbso = new MultiBSO(1);
            mbso.SetParametersvector ( (ArrayList<Integer>) solution.getParameters ( ) );

            //TODO : uncomment
            mbso.exe2( env, nbrCibles );
            solution.setFobjective( mbso.getNbtargets( ) );
            solution.setiteration( mbso.getIter( ) );
            solution.setTimelapsed( mbso.getTimeMultiBSO() );

            return mbso.getNbtargets ( );
        }
        if (mode == 4)
        {
            SwarmESWSA swarmESWSA = new SwarmESWSA ( solution.getParameters ().get ( 0 ),solution.getParameters ().get ( 1 ) , env );
            swarmESWSA.Setparemetres ( 1000, solution.getParameters ().get ( 2 ),solution.getParameters ().get ( 3 ) );
            swarmESWSA.run ( nbrCibles);
            solution.setFobjective(swarmESWSA.getNbtargets( ) );
            solution.setiteration( swarmESWSA.getIteration () );
            solution.setTimelapsed( swarmESWSA.getTimelasped () );


            return swarmESWSA.getNbtargets ();
        }

        return 0;

    }


}
