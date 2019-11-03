package BSO.MonoBSO;

import Environnement.Dataset;
import Environnement.Position;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BSO {

    //DatasetOld env;
    int size;
    Dataset env ;
    int MaxIter;
    int flip;
    int MaxChances;
    int nbrBees;
    int localSize;

    /**************************************************************************************/

    public void SetParametersvector(ArrayList<Integer> parameters)
    {
        this.MaxIter= (int)( parameters.get ( 0 ));
        this.flip = (int) (parameters.get ( 1 ));
        this.MaxChances=(int) (parameters.get ( 2 ));
        this.nbrBees = (int) (parameters.get(3));
        this.localSize = parameters.get(4);
    }

    public int getNbtargets() {
        return 1;
    }

    /**************************************************************************************/
    /*public Position exe(Dataset envi,int nbrTarget) {

        size=envi.size;
        env = envi;


        File f = null;
        image = null;
        try {
            f = new File("tt1.png"); //image file path
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);

        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        //drawing the env and obstacles
        for(int x=0;x<size;x++)
            for(int y=0;y<size;y++)
                if(env.getValue(x,y) == -1){
                    image.setRGB(y,x,ObstacleColor);

                }else{
                    if(env.getValue(x,y)>0 && env.getValue ( x,y )<1.0)
                        image.setRGB(y,x,PorteeColor);

                    else
                        image.setRGB(y,x,EnvColor);
                }

        Graphics g = image.getGraphics().create();
        g.setColor(DrawLineColor);

        


        *//***other parameters**//*
        int t = 0;
        int maxX=size,maxY=size,minX=0,minY=0;
        Bee Solution = new Bee();
        ArrayList<Bee> Bees = new ArrayList();
        ArrayList<Bee> TabooList = new ArrayList();
        ArrayList<Bee> Dance = new ArrayList();


        *//***algorithm***//*
        //init Sref randomly
        Bee Sref = new Bee(maxX, minX, maxY, minY);

        *//*******************************Search******************************//*
        *//*******************************************************************//*

        while (t < MaxIter && Sref.fitness != 1) {
            //insert Sref in a taboo list;
            TabooList.add(Sref.copy());

            //determine SearchPoints from Sref
            Bees = globalSearch(env,Sref,flip,nbrBees);

            for (Bee b : Bees) {
                
                *//***************************//*
                //local search
                Bee localBee = b.localSearch(env,localSize).copy();

                //store the result in Dance list
                Dance.add(localBee.copy());
            }

            //Assign the best solution to Sref
            Bee Sref1 = Sref.copy();
            Sref = selectBestSref(Sref, Dance, MaxChances,TabooList);

            //TODO: dinamic draw with obstacles avoidance

            g.drawLine(Sref1.Y(),Sref1.X(),Sref.Y(),Sref.X());


            t++;
            System.out.println(t+" iteration");

            if(Solution.fitness < Sref.fitness){
                Solution = Sref.copy();

            }

        }



        ColorTarget(env,image,Solution,TargetColor);

        //write image
        try{
            ImageIO.write(image, "png", f);
            System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }


        return Solution.position;
    }*/

    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/


    public Bee selectBestSref(Bee Sref, ArrayList<Bee> Dance, int MaxChances, ArrayList<Bee> Taboo) {

        //select best bee solution in quality from Dance list
        Bee newB = getBestQuality(Dance,Taboo);
        Bee newSref = new Bee();

        //calculate Δf = f(Sbest(t+1)) – f(Sref(t));
        double deltaF = newB.fitness - Sref.fitness;

        if (deltaF >= 0) {

            //Sref = the best solution in quality;
            newSref = newB.copy();
            newSref.nbrChance = 0;

        } else {

            Sref.nbrChance = Sref.nbrChance + 1;
            if (Sref.nbrChance >= MaxChances) {

                //newSref = the best solution in diversity;
                newSref = getBestDiversity(Dance,Taboo);
                newSref.nbrChance = 0;
            }else{
                newSref = Sref.copy();
            }

        }

        return newSref;
    }

    public Bee getBestQuality(ArrayList<Bee> Dance, ArrayList<Bee> Taboo){
        Bee bee = new Bee();
        boolean allInTaboo = true;

        for(int i = 0 ; i<Dance.size() ; i++){

            if(Dance.get(i).existIn(Taboo) == false){
                bee = Dance.get(i);
                //if in dance and not in taboo list
                allInTaboo = false;
                if(bee.fitness < Dance.get(i).fitness){
                    bee = Dance.get(i).copy();
                }
            }
        }

        if(allInTaboo == true){

            do{
                bee = new Bee(env, size,  0,  size,  0);
            }while(bee.existIn(Taboo));

            bee.evaluate(env);
        }
        return bee;
    }

    public Bee getBestDiversity(ArrayList<Bee> Dance2, ArrayList<Bee> Taboo){
        Bee bee = new Bee();
        double d,minDistance ;
        boolean allInTaboo = true;
        ArrayList<Bee> Dance = new ArrayList();

        for(int i = 0 ; i<Dance2.size() ; i++) {
            if (Dance2.get(i).existIn(Taboo) == false) {
                allInTaboo = false;
                Dance.add(Dance2.get(i).copy());
            }
        }

        if(allInTaboo == true){

            do{
                bee = new Bee(env, size,  0,  size,  0);
            }while(bee.existIn(Taboo));
            bee.evaluate(env);
        }
        else {
            for (int i = 0; i < Dance.size() - 1; i++) {

                minDistance = Dance.get(i).distance(Dance.get(i + 1));
                for (int j = i + 1; j < Dance.size(); j++) {
                    d = Dance.get(i).distance(Dance.get(j));
                    if (d < minDistance && d != 0) {
                        minDistance = d;
                    }
                }
                Dance.get(i).minDistance = minDistance;
            }

            for (int i = 0; i < Dance.size(); i++) {
                if (bee.minDistance < Dance.get(i).minDistance) {
                    bee = Dance.get(i).copy();
                }
            }
        }
        //System.out.println("best in diversityy");
        return bee;
    }

/********************************************************************************/

    public ArrayList<Bee> globalSearch(Dataset env, Bee Sref, int flip, int nbrBees){
        ArrayList<Bee> Bees = new ArrayList();
        Position pos = new Position();
        int rand;

        //generate solutions
        ArrayList<Position> circlePos = Sref.position.getCiclePositions(env,flip);
        if(nbrBees > circlePos.size()) nbrBees=circlePos.size();
        //System.out.println("circle: "+circlePos);

        for (int i = 0; i < nbrBees; i++) {
            //creat a bee
            Bee bee = new Bee();

            //put the solution in the bee
            Random random = new Random();
            rand = random.nextInt(circlePos.size() );
            bee.putSolution(circlePos.get(rand));
            circlePos.remove(rand);

            //evaluate solution
            bee.evaluate(env);

            //insert it in Bees list
            Bees.add(bee);
        }

        return Bees;
    }


    /*************************************************/
    public static void ColorTarget(Dataset env, BufferedImage image, Bee Solution, int TargetColor){

        image.setRGB(Solution.X(),Solution.Y(),TargetColor);
        if(env.valide(Solution.X()+1,Solution.Y()+1))
            image.setRGB(Solution.X()+1,Solution.Y()+1,TargetColor);
        if(env.valide(Solution.X()-1,Solution.Y()-1))
            image.setRGB(Solution.X()-1,Solution.Y()-1,TargetColor);
        if(env.valide(Solution.X()-1,Solution.Y()+1))
            image.setRGB(Solution.X()-1,Solution.Y()+1,TargetColor);
        if(env.valide(Solution.X()+1,Solution.Y()-1))
            image.setRGB(Solution.X()+1,Solution.Y()-1,TargetColor);
        if(env.valide(Solution.X()+1,Solution.Y()))
            image.setRGB(Solution.X()+1,Solution.Y(),TargetColor);
        if(env.valide(Solution.X()-1,Solution.Y()))
            image.setRGB(Solution.X()-1,Solution.Y(),TargetColor);
        if(env.valide(Solution.X(),Solution.Y()+1))
            image.setRGB(Solution.X(),Solution.Y()+1,TargetColor);
        if(env.valide(Solution.X(),Solution.Y()-1))
            image.setRGB(Solution.X(),Solution.Y()-1,TargetColor);
    }



}
