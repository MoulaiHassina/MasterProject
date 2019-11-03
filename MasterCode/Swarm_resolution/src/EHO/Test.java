package EHO;

import Environnement.Dataset;
import Environnement.FileData;

import java.io.IOException;

public class Test {


    public static void main(String[] args){



        int nbrClan = 1;
        int nbrElephant = 3;
        int MaxGen = 10;
        double alpha = 0.8;
        double beta = 0.9;
        int size = 300;
        int nbrTarget = 1;
        int PORTEE = 10;


        /*** dataset ***/
        Dataset env = new Dataset ( );
        FileData ps = new FileData ( );
        try {
            ps.parsing("../Simulator/RangeTarget/1/mat1/10/target" + 1 + ".txt");
            env = new Dataset ( ps );
        } catch (IOException e) {
            e.printStackTrace ( );
        }


        /*** eho ***/
        //Eho eho = new Eho();
        //eho.exe(env,nbrClan,nbrElephant,MaxGen,alpha,beta,size);


        nEho neho = new nEho(env.size,100);
        neho.exe(env,nbrTarget,nbrClan,nbrElephant,alpha,beta);

//        neho = new nEho(env.size,1550);
//        neho.exe2(env,nbrTarget,nbrClan,nbrElephant,alpha,beta);


        }
}
