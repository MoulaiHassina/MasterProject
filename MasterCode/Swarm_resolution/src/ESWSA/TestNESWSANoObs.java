package ESWSA;


import ESWSA.SimpleESWSA.ESWSA;
import Environnement.Position;
import Environnement.Dataset;
import Environnement.FileData;
import GA.GA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TestNESWSANoObs {

    static int GaIter=20,
            GaPopu=20,
            GaMuta=2;
    static String type = "SansObs";//"AvecObs" //"Complex" //"SansObs"

    public static void main(String[] args) throws Exception {

        /***ampirical parameters***/
        int MaxIter = 1000;

        int nn=1;//5

        /*** pour la portée ***/
//        generateByPortee(nn, MaxIter);

        /*** pour le size de l'env ***/
        generateBySize(nn, MaxIter) ;

        /*** pour le nbr de targets de l'env ***/
//        generateByNbTarget(nn, MaxIter);

         nn=5;//5
        generateBySize(nn, MaxIter) ;



//        Dataset env = new Dataset(300);
//        env.Inittarget(30);
//        env.Inittarget(30);
//
//        ESWSA el = new ESWSA ( 5, MaxIter, new Position( env.size - 1, env.size - 1 ), new Position(0,0), 0.5, env, 0.5 );
//        el.exe2(2);

    }



    /**********************************************************/

    public static void generateByPortee(int nn, int MaxIter) throws Exception {

        int nbrClan;
        double P, wt;

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "",
                time = "",
                nbrTarg = "";
        ArrayList<Double> resultat ;
        int PORTEE;
        ArrayList<Integer> para;


        write("Resultat/ESWSA/"+nbrTarget+"Target/TestPortee/testIter.txt");
        write("Resultat/ESWSA/"+nbrTarget+"Target/TestPortee/testTime.txt");
        write("Resultat/ESWSA/"+nbrTarget+"Target/TestPortee/testTarg.txt");



        for (int i = 1; i <= 10; i++) {
            //boucle pour la portée

            PORTEE = i*10;
            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset

                Dataset env = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/Portee/"+nbrTarget+"targets/portee" + PORTEE + "/mat" + n + "/target" + nbrTarget + ".txt");
                    env = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 2, GaMuta);
                para = genetic.GA(nbrTarget, env, 2).getParameters();

                nbrClan=para.get(0);
                wt=(double)(para.get(1))/10.0;
                P =(double)(para.get(2))/10.0;

                System.out.println("eswsa: nbclan: "+nbrClan+" ,wt: "+wt+" ,p: "+P);

                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset


                    ESWSA el = new ESWSA ( nbrClan, MaxIter, new Position( env.size - 1, env.size - 1 ), new Position(0,0), P, env, wt );
                    resultat = el.exe2(nbrTarget);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);

                }
            }
            write("Resultat/ESWSA/"+nbrTarget+"Target/TestPortee/testIter.txt", nbnrIter.substring(1));
            write("Resultat/ESWSA/"+nbrTarget+"Target/TestPortee/testTime.txt", time.substring(1));
            write("Resultat/ESWSA/"+nbrTarget+"Target/TestPortee/testTarg.txt", nbrTarg.substring(1));
        }
    }

    public static void generateBySize(int nn, int MaxIter) throws Exception {

        int nbrClan;
        double P, wt;

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {50,100,200,400,600,800,1000,1500,3000,5000};
        int s = 0;
        ArrayList<Integer> para;

        write("Resultat/ESWSA/"+nbrTarget+"Target/TestSize/testIter.txt");
        write("Resultat/ESWSA/"+nbrTarget+"Target/TestSize/testTime.txt");
        write("Resultat/ESWSA/"+nbrTarget+"Target/TestSize/testTarg.txt" );


        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour la taille
            s = envSize[i-1];

            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset

                Dataset env = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/SIZE/"+nbrTarget+"targets/size" + s + "/mat" + n + "/target" + nbrTarget + ".txt");
                    env = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 2, GaMuta);
                para = genetic.GA(nbrTarget, env, 2).getParameters();

                nbrClan=para.get(0);
                wt=(double)(para.get(1))/10.0;
                P =(double)(para.get(2))/10.0;


                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset


                    ESWSA el = new ESWSA ( nbrClan, MaxIter, new Position( env.size - 1, env.size - 1 ), new Position(0,0), P, env, wt );
                    resultat=el.exe2 (nbrTarget);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);

                }

            }

            write("Resultat/ESWSA/"+nbrTarget+"Target/TestSize/testIter.txt", nbnrIter.substring(1));
            write("Resultat/ESWSA/"+nbrTarget+"Target/TestSize/testTime.txt", time.substring(1));
            write("Resultat/ESWSA/"+nbrTarget+"Target/TestSize/testTarg.txt", nbrTarg.substring(1));

        }
    }

    public static void generateByNbTarget(int nn, int MaxIter) throws Exception {

        /*** pour le size de l'env ***/

        int nbrClan;
        double P, wt;

        int nb = nn;//5
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {1,3,5,7,9,11,13,15};
        int nbrTarget = 0;
        ArrayList<Integer> para;

        write("Resultat/ESWSA/"+nb+"Target/TestNbTarget/testIter.txt");
        write("Resultat/ESWSA/"+nb+"Target/TestNbTarget/testTime.txt");
        write("Resultat/ESWSA/"+nb+"Target/TestNbTarget/testTarg.txt");


        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour le nombre de cibles
            nbrTarget = envSize[i-1];

            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset

                Dataset env = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/nbTarget/nbTargets" + nbrTarget + "/mat" + n + "/target" + nbrTarget + ".txt");
                    env = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }



                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 2, GaMuta);
                para = genetic.GA(nbrTarget, env, 2).getParameters();

                nbrClan=para.get(0);
                wt=(double)(para.get(1))/10.0;
                P =(double)(para.get(2))/10.0;


                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset


                    ESWSA el = new ESWSA( nbrClan, MaxIter, new Position( env.size - 1, env.size - 1 ), new Position(0,0), P, env, wt );
                    resultat=el.exe2(nbrTarget);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);

                }
            }

            write("Resultat/ESWSA/"+nb+"Target/TestNbTarget/testIter.txt", nbnrIter.substring(1));
            write("Resultat/ESWSA/"+nb+"Target/TestNbTarget/testTime.txt", time.substring(1));
            write("Resultat/ESWSA/"+nb+"Target/TestNbTarget/testTarg.txt", nbrTarg.substring(1));

        }
    }



    /*********************************************************************************/
    public static String read(String path) throws Exception {
        /**read a file**/
        // pass the path to the file as a parameter
        File file =
                new File(path);
        Scanner sc = new Scanner(file);
        String output = "";


        while (sc.hasNextLine())
            output = output + sc.nextLine() + "\n";

        return output;
    }

    public static void write(String path, String text) throws IOException, Exception {
        /** write in a file**/
        String fileContent = read(path);

        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(fileContent);
        printWriter.printf(text);
        printWriter.close();
    }

    public static void write(String path) throws IOException, Exception {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.close();
    }


}