package BSO.MultiBSO;

import BSO.MonoBSO.Bee;
import Environnement.Dataset;

import java.util.ArrayList;

public class BeeSwarm {

    public ArrayList<Bee> swarm;

    BeeSwarm(){
        swarm = new ArrayList();
    }

    BeeSwarm(ArrayList s){
        swarm = s;
    }

    BeeSwarm(int nbrBees ){
        swarm = new ArrayList();

        for(int i =0 ; i< nbrBees ; i++){
            Bee bee = new Bee();
            swarm.add(bee);
        }
    }

    BeeSwarm(Dataset env,int nbrSwarm){
        swarm = new ArrayList();

        for(int i =0 ; i< nbrSwarm ; i++) {
            Bee Sref = new Bee(env, env.size, 0, env.size, 0);
            Sref.evaluate(env);

            swarm.add(Sref);
        }
    }



}
