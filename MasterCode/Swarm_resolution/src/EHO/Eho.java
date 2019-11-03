package EHO;

import Environnement.Dataset;

import java.util.ArrayList;

public class Eho {

    int MaxGen;
    ArrayList<Clan> clans;


    void exe(Dataset env, int nbrClan, int nbrElephant, int MaxGen, double alpha, double beta, int size){


        int t = 0;
        Elephant solution = new Elephant();

        /** init clans **/
        clans = new ArrayList();
        for(int i=0 ; i<nbrClan ; i++){
            Clan clan = new Clan(env, size,nbrElephant);
            clans.add(clan);
        }

        /** Search **/
        while(t<MaxGen && solution.fitness!=1){

            clans = sorte(clans);
            clans = update(clans, env,alpha,beta);



            clans = sorte(clans);
            clans = separate(clans, env,size);


            clans = sorte(clans);
            if(solution.fitness < getBest(clans).fitness)
                solution = getBest(clans).copy();


            t++;
            //System.out.println("Itération : "+t+"elephant tooked : "+solution.toString());

        }



        //System.out.println(t+" - final solution : "+solution.toString());

    }


    /***********************************************************************************/

    public static ArrayList<Clan> sorte(ArrayList<Clan> clans){
        for(int c=0 ; c<clans.size() ; c++){
            clans.get(c).sorte();
        }
        return clans;
    }


    public static ArrayList<Clan> separate(ArrayList<Clan> clans, Dataset env,int size ){
        for(int c=0 ; c<clans.size() ; c++){
            clans.get(c).separate(env,size);
        }
        return clans;
    }


    public static ArrayList<Clan> update(ArrayList<Clan> clans,
                                         Dataset env,double alpha,double beta ){
        for(int c=0 ; c<clans.size() ; c++){
            clans.get(c).update(env,alpha,beta);
        }
        return clans;
    }

    public static Elephant getBest(ArrayList<Clan> clans){
        Elephant elephantBest = clans.get(0).getBest();
        for(int c=0 ; c<clans.size() ; c++){
            if(elephantBest.fitness < clans.get(c).getBest().fitness)
                elephantBest = clans.get(c).getBest().copy();
        }

        return elephantBest;
    }

}
