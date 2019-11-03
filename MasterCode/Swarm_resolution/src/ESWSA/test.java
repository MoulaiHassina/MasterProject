package ESWSA;

import ESWSA.SimpleESWSA.ESWSA;
import ESWSA.SubswarmEswsa.SwarmESWSA;
import Environnement.*;
import GA.GA;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by masterubunto on 04/05/19.
 */
public class test {
    public static void main(String[] args) throws Exception {

        Dataset env=new Dataset ( 1000);
        //env.partition(9);
        int MaxIter=500,nbrClan=3,nbrTarget=2;

        env.Inittarget ( 60);


        double P=0.6,wt=0.2,average=0;
       // for(int i=0;i<20;i++){
//        SwarmESWSA swarmESWSA = new SwarmESWSA ( 1, 5, env );
//        swarmESWSA.Setparemetres ( MaxIter, P, wt);
//        swarmESWSA.run ( nbrTarget );
//       // average+=swarmESWSA.getIteration ();
    //}//}
      //  System.out.println ("moy:"+average/20 );
//        GA genetic = new GA(20, 30, 2, 2);
//        genetic.GA(nbrTarget, env, 4);


        // generateBySize ( 1,1000 );
         // generateByNbTarget ( 5,1000 );
        //   generateByPortee ( 1,1000 );
         ESWSA el = new ESWSA ( 3, 50, new Position( env.size - 1, env.size - 1 ), new Position(0,0), 0, env, 0.5 );
         el.Run (1);


    } public static void generateBySize(int nn, int MaxIter) throws Exception {

        int nbrClan=7;
        double P=0.6, wt=0.3;
        String type="SansObs";

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {50,100,200,400,600,800,1000,1500,3000,5000};
        int s = 0;
        ArrayList<Integer> para;


        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour la taille
            s = envSize[i-1];
            nbnrIter = "";time = "";nbrTarg = "";
            System.out.println ("env:"+s);
            for(int n=1; n<5; n++) {

                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset
                    Dataset env = new Dataset();
                    FileData ps = new FileData();
                    try {
                        ps.parsing("../Simulator/"+type+"/SIZE/"+nbrTarget+"targets/size" + s + "/mat" + n + "/target" + nbrTarget + ".txt");
                        env = new Dataset(ps);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /*simulator fo eswsa: since one swarm and has elephants*/
                    SwarmESWSA swarmESWSA = new SwarmESWSA ( 1, nbrClan, env );
                    swarmESWSA.Setparemetres ( MaxIter, P, wt);
                    swarmESWSA.run ( nbrTarget );

                }

            }




        }
    }
    public static void generateByNbTarget(int nn, int MaxIter) throws Exception {

        /*** pour le size de l'env ***/

        int nbrClan=7;
        double P=0.6, wt=0.2;
        String type="SansObs";

        int nb = nn;//5
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {1,3,5,7,9,11,13,15};
        int nbrTarget = 0;
        ArrayList<Integer> para;



        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour le nombre de cibles
            nbrTarget = envSize[i-1];

            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset
                Dataset env = new Dataset();
                FileData ps = new FileData();
                System.out.println ("nbtargets:"+nbrTarget);

                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset

                    try {
                        ps.parsing("../Simulator/"+type+"/nbTarget/nbTargets" + nbrTarget + "/mat" + n + "/target" + nbrTarget + ".txt");
                        env = new Dataset(ps);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

              /*simulator fo eswsa: since one swarm and has elephants*/
                    SwarmESWSA swarmESWSA = new SwarmESWSA ( 1, nbrClan, env );
                    swarmESWSA.Setparemetres ( MaxIter, P, wt);
                    swarmESWSA.run ( nbrTarget );


                }
                System.out.println ("env:"+env.size );
            }


        }
    }
    public static void generateByPortee(int nn, int MaxIter) throws Exception {

        int nbrClan=7;
        double P=0.6, wt=0.2;

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "",
                time = "",
                nbrTarg = "",type="SansObs";
        ArrayList<Double> resultat ;
        int PORTEE;
        ArrayList<Integer> para;



        for (int i = 1; i <= 10; i++) {
            //boucle pour la portée

            PORTEE = i*10;
            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset
                System.out.println("eswsa: nbclan: "+nbrClan+" ,wt: "+wt+" ,p: "+P);
                System.out.println ("portéé:"+PORTEE );

                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset

                    Dataset env = new Dataset();
                    FileData ps = new FileData();
                    try {
                        ps.parsing("../Simulator/"+type+"/Portee/"+nbrTarget+"targets/portee" + PORTEE + "/mat" + n + "/target" + nbrTarget + ".txt");
                        env = new Dataset(ps);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /*simulator fo eswsa: since one swarm and has elephants*/
                    SwarmESWSA swarmESWSA = new SwarmESWSA ( 1, nbrClan, env );
                    swarmESWSA.Setparemetres ( MaxIter, P, wt);
                    swarmESWSA.run ( nbrTarget );


                }
            }
        }
    }
    }
