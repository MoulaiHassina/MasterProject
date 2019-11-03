package EHO;


import Environnement.Dataset;
import Environnement.FileData;
import GA.GA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class TestNEHONoObs {

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

    static int GaIter=20,
            GaPopu=20,
            GaMuta=2;
    static String type = "SansObs";//"AvecObs" //"Complex" //"SansObs"

    public static void main(String[] args) throws Exception {

        /***ampirical parameters***/
        int MaxGen = 1000;
        int nn=5;//5

        /*** pour la portée ***/
//        generateByPortee(nn, MaxGen);

        /*** pour le size de l'env ***/
        generateBySize(nn, MaxGen) ;

        /*** pour le nbr de targets de l'env ***/
//        generateByNbTarget(nn,MaxGen);


//        Dataset env = new Dataset(300);
//        env.Inittarget(30);
//        env.Inittarget(30);
//
//        nEho neho = new nEho(env.size,MaxGen);
//        System.out.println(neho.exe2(env,2,1,5,0.5,0.5));

    }

    /**********************************************************************************/

    public static void generateByPortee(int nn, int MaxGen) throws Exception {

        int nbrClan , nbrElephant;
        double alpha, beta ;

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "",
                time = "",
                nbrTarg = "";
        ArrayList<Double> resultat ;
        int PORTEE;
        ArrayList<Integer> para;

        write("Resultat/EHO/"+nbrTarget+"Target/TestPortee/testIter.txt");
        write("Resultat/EHO/"+nbrTarget+"Target/TestPortee/testTime.txt");
        write("Resultat/EHO/"+nbrTarget+"Target/TestPortee/testTarg.txt");



        for (int i = 1; i <= 10; i++) {
            //boucle pour la portée

            PORTEE = i*10;
            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {

                Dataset env = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/Portee/"+nbrTarget+"targets/portee" + PORTEE + "/mat" + n + "/target" + nbrTarget + ".txt");
                    env = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 1, GaMuta);
                para = genetic.GA(nbrTarget, env, 1).getParameters();

                nbrClan=para.get(0);
                nbrElephant=para.get(1);
                alpha=(double)(para.get(2))/10.0;
                beta =(double)(para.get(3))/10.0;

//                System.out.println("EHO: nbrClan: "+nbrClan+" ,nbrElephant: "+nbrElephant+" ,alpha: "+alpha+" ,beta: "+beta);

                for (int j = 1; j <= nbrRepete; j++) {

                    nEho neho = new nEho(env.size,MaxGen);
                    resultat=neho.exe2(env,nbrTarget,nbrClan,nbrElephant,alpha,beta);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);
                }
            }
            write("Resultat/EHO/"+nbrTarget+"Target/TestPortee/testIter.txt", nbnrIter.substring(1));
            write("Resultat/EHO/"+nbrTarget+"Target/TestPortee/testTime.txt", time.substring(1));
            write("Resultat/EHO/"+nbrTarget+"Target/TestPortee/testTarg.txt", nbrTarg.substring(1));
        }
    }

    /**********************************************************************************/

    public static void generateBySize(int nn ,int MaxGen) throws Exception {

        int nbrClan , nbrElephant;
        double alpha, beta ;

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {50,100,200,400,600,800,1000,1500,3000,5000};
        int s = 0;
        ArrayList<Integer> para;

        write("Resultat/EHO/"+nbrTarget+"Target/TestSize/testIter.txt");
        write("Resultat/EHO/"+nbrTarget+"Target/TestSize/testTime.txt");
        write("Resultat/EHO/"+nbrTarget+"Target/TestSize/testTarg.txt" );


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
                GA genetic = new GA(GaIter, GaPopu, 1, GaMuta);
                para = genetic.GA(nbrTarget, env, 1).getParameters();

                nbrClan=para.get(0);
                nbrElephant=para.get(1);
                alpha=(double)(para.get(2))/10.0;
                beta =(double)(para.get(3))/10.0;


                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset

                    nEho neho = new nEho(env.size,MaxGen);
                    resultat = neho.exe2(env,nbrTarget,nbrClan,nbrElephant,alpha,beta);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);
                }
            }

            write("Resultat/EHO/"+nbrTarget+"Target/TestSize/testIter.txt", nbnrIter.substring(1));
            write("Resultat/EHO/"+nbrTarget+"Target/TestSize/testTime.txt", time.substring(1));
            write("Resultat/EHO/"+nbrTarget+"Target/TestSize/testTarg.txt", nbrTarg.substring(1));

        }
    }

    /*********************************************************************************/

    public static void generateByNbTarget(int nn,int MaxGen) throws Exception {

        /*** pour le size de l'env ***/
        int nbrClan , nbrElephant;
        double alpha, beta ;

        int nb = nn;//5
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {1,3,5,7,9,11,13,15};
        int nbrTarget = 0;
        ArrayList<Integer> para;

        write("Resultat/EHO/"+nb+"Target/TestNbTarget/testIter.txt");
        write("Resultat/EHO/"+nb+"Target/TestNbTarget/testTime.txt");
        write("Resultat/EHO/"+nb+"Target/TestNbTarget/testTarg.txt");


        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour le nombre de cibles
            nbrTarget = envSize[i-1];

            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset

                Dataset env = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/SansObs/nbTarget/nbTargets" + nbrTarget + "/mat" + n + "/target" + nbrTarget + ".txt");
                    env = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 1, GaMuta);
                para = genetic.GA(nbrTarget, env, 1).getParameters();

                nbrClan=para.get(0);
                nbrElephant=para.get(1);
                alpha=(double)(para.get(2))/10.0;
                beta =(double)(para.get(3))/10.0;

                for (int j = 1; j <= nbrRepete; j++) {
                    //boucle pour tester plusieurs fois le même dataset

                    nEho neho = new nEho(env.size,MaxGen);
                    resultat = neho.exe2(env,nbrTarget,nbrClan,nbrElephant,alpha,beta);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);
                }
            }

            write("Resultat/EHO/"+nb+"Target/TestNbTarget/testIter.txt", nbnrIter.substring(1));
            write("Resultat/EHO/"+nb+"Target/TestNbTarget/testTime.txt", time.substring(1));
            write("Resultat/EHO/"+nb+"Target/TestNbTarget/testTarg.txt", nbrTarg.substring(1));

        }
    }



}
