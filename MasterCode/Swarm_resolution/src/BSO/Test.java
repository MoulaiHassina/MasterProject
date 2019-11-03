package BSO;

import BSO.MultiBSO.MultiBSO;
import GA.Solution;
import Environnement.Dataset;
import Environnement.FileData;

import java.io.IOException;


public class Test {

    public static void main(String[] args) throws Exception {

        /***ampirical parameters***/

        int MaxIter = 10;
        int nbrTarget = 1;
        int flip = 15;
        int MaxChances = 1;
        int nbrBees = 3;
        int nbrSwarm = 5;


        Dataset  env=new Dataset ( 800 );
        //env.partition(9);
        env.Inittarget ( 30 );
        env.Inittarget ( 30 );
        nbrTarget = 2;

//        nBSO nbso = new nBSO(MaxIter);
//        Solution nsol = new Solution(flip, MaxChances, nbrBees);
//        nbso.SetParametersvector(nsol.getParameters());
//        System.out.println(nbso.exe( env,nbrTarget ));

        MultiBSO nbso = new MultiBSO(MaxIter);
        Solution nsol = new Solution(flip, MaxChances, nbrBees,nbrSwarm);
        nbso.SetParametersvector(nsol.getParameters());
        System.out.println(nbso.exe2( env,nbrTarget ));


        /** GA **/
//        GA genetic = new GA(10 , 10, 0, 2);
//        System.out.println(genetic.GA(nbrTarget, env, 0).toString(0));

        // genetic = new GA( 1, 3, 0, 2);
        // genetic.GA(nbrTarget, env, 0);

        /**************************************************************************/
        /*env = new Dataset();
        Environnement.Dataset envi = new Environnement.Dataset(env.size, env.matrix);
        // env.display();

        String s;
        for (int i = 0; i < 0; i++) {

            nbso = new nBSO();
            nsol = new Solution(flip, MaxChances, nbrBees);
            nbso.localSize = localSize;
            nbso.MaxIter = MaxIter;
            nbso.SetParametersvector(nsol.getParameters());
            nbso.exe(envi, nbrTarget);

            s = i + ", ";
            //write("test.txt",s);
        }*/


    }



}
