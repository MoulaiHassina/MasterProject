package BSO.MultiBSO;

import BSO.MonoBSO.nBSO;
import Environnement.Dataset;
import Environnement.FileData;
import GA.GA;
import GA.Solution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class TestMultiBSO {

    public static String read(String path) throws Exception {
        /**read a file**/
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

        int MaxIter = 1000;
        int localSize = 10;
        int nn=1;//5


        /*** pour la portée ***/
//        generateByPortee(nn, MaxIter,  localSize);

        /*** pour le size de l'env ***/
     //   generateBySize(nn, MaxIter, localSize) ;

        /*** pour le nbr de targets de l'env ***/
//        generateByNbTarget(nn, MaxIter, localSize);



    }


    /**********************************************************************************/

    public static void generateByPortee(int nn, int MaxIter, int localSize) throws Exception {

        int nbrTarget = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "", nbrTarg = "";
        ArrayList<Double> resultat ;
        int PORTEE;
        ArrayList<Integer> para;
        int nbrBees,MaxChances,flip,nbrSwarm;

        write("Resultat/MBSO/"+nbrTarget+"Target/TestPortee/testIter.txt");
        write("Resultat/MBSO/"+nbrTarget+"Target/TestPortee/testTime.txt");
        write("Resultat/MBSO/"+nbrTarget+"Target/TestPortee/testTarg.txt");


        for (int i = 1; i <= 10; i++) {
            //boucle pour la portée

            PORTEE = i*10;
            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset
                Dataset envi = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/Portee/"+nbrTarget+"targets/portee" + PORTEE + "/mat" + n + "/target" + nbrTarget + ".txt");
                    envi = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 3, GaMuta);
                para = genetic.GA(nbrTarget, envi, 3).getParameters();

                flip=para.get(0);
                MaxChances=para.get(1);
                nbrBees=para.get(2);
                nbrSwarm=para.get(3);

                System.out.println("bso: flip: "+flip+" ,MaxChance: "+MaxChances+" ,nbrBees: "+nbrBees+" ,nbrSwarm: "+nbrSwarm);

                for (int j = 1; j <= nbrRepete; j++) {

                    MultiBSO mbso = new MultiBSO(MaxIter);
                    Solution nsol = new Solution(MaxIter, flip, MaxChances, nbrBees, nbrSwarm);
                    mbso.SetParametersvector(nsol.getParameters());
                    resultat = mbso.exe2(envi, nbrTarget);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);
                }
            }

            write("Resultat/MBSO/"+nbrTarget+"Target/TestPortee/testIter.txt", nbnrIter.substring(1));
            write("Resultat/MBSO/"+nbrTarget+"Target/TestPortee/testTime.txt", time.substring(1));
            write("Resultat/MBSO/"+nbrTarget+"Target/TestPortee/testTarg.txt", nbrTarg.substring(1));
        }
    }

    public static void generateBySize(int nn,int MaxIter,int localSize) throws Exception {

        int nbrTarget = nn;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {1000,1500,3000,5000};//50,100,200,400,600,800,1000,1500,
        int s = 0;
        ArrayList<Integer> para;
        int nbrBees,MaxChances,flip,nbrSwarm;

        write("Resultat/MBSO/"+nbrTarget+"Target/TestSize/testIter.txt");
        write("Resultat/MBSO/"+nbrTarget+"Target/TestSize/testTime.txt");
        write("Resultat/MBSO/"+nbrTarget+"Target/TestSize/testTarg.txt" );


        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour la portée
            s = envSize[i-1];

            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset

                Dataset envi = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/SIZE/"+nbrTarget+"targets/size" + s + "/mat" + n + "/target" + nbrTarget + ".txt");
                    envi = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 3, GaMuta);
                para = genetic.GA(nbrTarget, envi, 3).getParameters();

                flip=para.get(0);
                MaxChances=para.get(1);
                nbrBees=para.get(2);
                nbrSwarm=para.get(3);

                System.out.println("bso: flip: "+flip+" ,MaxChance: "+MaxChances+" ,nbrBees: "+nbrBees+" ,nbrSwarm: "+nbrSwarm);

                for (int j = 1; j <= nbrRepete; j++) {

                    MultiBSO mbso = new MultiBSO(MaxIter);
                    Solution nsol = new Solution(MaxIter, flip, MaxChances, nbrBees, nbrSwarm);
                    mbso.SetParametersvector(nsol.getParameters());
                    resultat = mbso.exe2(envi, nbrTarget);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);
                }
            }

            write("Resultat/MBSO/"+nbrTarget+"Target/TestSize/testIter.txt", nbnrIter.substring(1));
            write("Resultat/MBSO/"+nbrTarget+"Target/TestSize/testTime.txt", time.substring(1));
            write("Resultat/MBSO/"+nbrTarget+"Target/TestSize/testTarg.txt", nbrTarg.substring(1));

        }
    }

    public static void generateByNbTarget(int nn, int MaxIter,int localSize) throws Exception {
        /*** pour le size de l'env ***/
        int nb = nn; // = 5;
        int nbrRepete = 10; //nbr de répétition d'exe sur 1 meme dataset
        String nbnrIter = "", time = "",  nbrTarg = "";
        ArrayList<Double> resultat ;
        int[] envSize = {1,3,5,7,9,11,13,15};
        int nbrTarget = 0;
        ArrayList<Integer> para;
        int nbrBees,MaxChances,flip,nbrSwarm;

        write("Resultat/MBSO/"+nb+"Target/TestNbTarget/testIter.txt");
        write("Resultat/MBSO/"+nb+"Target/TestNbTarget/testTime.txt");
        write("Resultat/MBSO/"+nb+"Target/TestNbTarget/testTarg.txt");

        for (int i = 1; i <= envSize.length; i++) {
            //boucle pour le nombre de cibles
            nbrTarget = envSize[i-1];

            nbnrIter = "";time = "";nbrTarg = "";

            for(int n=1; n<5; n++) {
                //boucle pour les dataset

                Dataset envi = new Dataset();
                FileData ps = new FileData();
                try {
                    ps.parsing("../Simulator/"+type+"/nbTarget/nbTargets" + nbrTarget + "/mat" + n + "/target" + nbrTarget + ".txt");
                    envi = new Dataset(ps);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /** GA **/
                GA genetic = new GA(GaIter, GaPopu, 3, GaMuta);
                para = genetic.GA(nbrTarget, envi, 3).getParameters();

                flip=para.get(0);
                MaxChances=para.get(1);
                nbrBees=para.get(2);
                nbrSwarm=para.get(3);

                System.out.println("bso: flip: "+flip+" ,MaxChance: "+MaxChances+" ,nbrBees: "+nbrBees+" ,nbrSwarm: "+nbrSwarm);

                for (int j = 1; j <= nbrRepete; j++) {

                    MultiBSO mbso = new MultiBSO(MaxIter);
                    Solution nsol = new Solution(MaxIter, flip, MaxChances, nbrBees, nbrSwarm);
                    mbso.SetParametersvector(nsol.getParameters());
                    resultat = mbso.exe2(envi, nbrTarget);

                    nbnrIter = nbnrIter + "," + resultat.get(0);
                    time = time + "," + resultat.get(1);
                    nbrTarg = nbrTarg + "," + (resultat.get(2)/nbrTarget);
                }
            }

            write("Resultat/MBSO/"+nb+"Target/TestNbTarget/testIter.txt", nbnrIter.substring(1));
            write("Resultat/MBSO/"+nb+"Target/TestNbTarget/testTime.txt", time.substring(1));
            write("Resultat/MBSO/"+nb+"Target/TestNbTarget/testTarg.txt", nbrTarg.substring(1));

        }
    }



}
