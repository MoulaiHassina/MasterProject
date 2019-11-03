package Multi_Regions.Agent;

import BSO.MonoBSO.nBSO;
import Environnement.Dataset;
import Environnement.FileData;
import GA.Solution;
import jade.core.Agent;

import java.io.IOException;

/**
 * Created by masterubunto on 25/04/19.
 */
public class Agent2 extends Agent
{

    protected void setup()
    {
        int nbrTarget = 1;
        int flip = 10;
        int MaxChances = 1;
        int nbrBees = 1;


        Dataset env = new Dataset();
        FileData ps = new FileData();
        try {
            ps.parsing("../Simulator/SansObs/Portee/1targets/portee10/mat1/target" + 1 + ".txt");
            env = new Dataset(ps);
        } catch (IOException e) {
            e.printStackTrace();
        }

        nBSO nbso = new nBSO(1000);
        Solution nsol = new Solution(flip, MaxChances, nbrBees);
        nbso.SetParametersvector(nsol.getParameters());
        System.out.println(nbso.exe( env,nbrTarget ));
        System.out.println("Hello World. ");
        System.out.println("My name is "+ getLocalName());
        doDelete();
    }
}
