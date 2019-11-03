package EvitementObs;

import Environnement.Position;
import Environnement.Dataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Utility {
    public  static  int sref=0;
    public static Color c=Color.RED;


    public static void color (int index)
    {
        switch (index)
        {
            case 0: c=Color.RED; break;
            case 1:c=Color.cyan; break;
            case 2:c=Color.BLUE; break;
            case 3: c=Color.darkGray; break;
            case 4:c=Color.green; break;
            case 5:c=Color.ORANGE;break;
            case 6:c=Color.magenta;break;
            case 7:c=Color.PINK;break;
            case 8:c=Color.YELLOW;break;
            case 9:c=new Color ( 127,0,255 );
            default:
        }


    }

    public static ArrayList<Position> getS(Position position,int portee,
                                           Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getSEhelp(position,portee,env,last));
        list.addAll(getSWhelp(position,portee,env,last));

        return list;
    }

    public static ArrayList<Position> getN(Position position,int portee,
                                           Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getNWhelp(position,portee,env,last));
        list.addAll(getNEhelp(position,portee,env,last));

        return list;
    }

    public static ArrayList<Position> getE(Position position,int portee,
                                           Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getNEhelp(position,portee,env,last));
        list.addAll(getSEhelp(position,portee,env,last));

        return list;
    }


    public static ArrayList<Position> getW(Position position,int portee,
                                           Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getNWhelp(position,portee,env,last));
        list.addAll(getSWhelp(position,portee,env,last));

        return list;
    }

    /***********************************************************************/

    public static ArrayList<Position> getSE(Position position,int portee,
                                            Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getSEhelp(position,portee,env,last));
        if(portee > 100) {
            list.addAll(getSWhelp(position, portee, env, last));
            list.addAll(getNEhelp(position, portee, env, last));
        }

        return list;
    }

    public static ArrayList<Position> getNE(Position position,int portee,
                                            Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getNEhelp(position,portee,env,last));
        if(portee > 100) {
            list.addAll(getNWhelp(position, portee, env, last));
            list.addAll(getSEhelp(position, portee, env, last));
        }

        return list;
    }

    public static ArrayList<Position> getSW(Position position,int portee,
                                            Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getSWhelp(position,portee,env,last));
        if(portee > 100) {
            list.addAll(getNWhelp(position, portee, env, last));
            list.addAll(getSEhelp(position, portee, env, last));
        }

        return list;
    }


    public static ArrayList<Position> getNW(Position position,int portee,
                                            Dataset env, TreeSet<Position> last){
        ArrayList<Position> list = new ArrayList<>();

        list.addAll(getNWhelp(position,portee,env,last));
        if(portee > 100) {
            list.addAll(getSWhelp(position, portee, env, last));
            list.addAll(getNEhelp(position, portee, env, last));
        }

        return list;
    }


    /*****************************help******************************/

    public static ArrayList<Position> getNEhelp(Position position,int portee,
                                                Dataset env, TreeSet<Position> last){

        ArrayList<Position> list = new ArrayList<>();
        Position pos;

        for (int k = -portee/2; k <= portee/2; k++) {
            for(int i = 0; i<=portee ; i++) {
                for (int j = -portee; j <= 0; j++) {
                    if ((Math.pow(i, 2) + Math.pow(j, 2) == Math.pow(portee,2) +k) &&//+k
                            env.valide(position.X + i, position.Y + j)) {
                        pos = new Position(position.X + i, position.Y + j);
//                        image.setRGB(position.X + i, position.Y + j,Color.PINK.getRGB());
                        if(!last.contains(pos))
                            list.add(pos);
                    }
                }
            }
        }


        return list;
    }

    public static ArrayList<Position> getSEhelp(Position position,int portee,
                                                Dataset env, TreeSet<Position> last){

        ArrayList<Position> list = new ArrayList<>();
        Position pos;

        for (int k = -portee/2; k <= portee/2; k++) {
            for(int i = 0; i<=portee ; i++) {
                for (int j = 0; j <= portee; j++) {
                    if ((Math.pow(i, 2) + Math.pow(j, 2) == Math.pow(portee,2)+k ) && //+k
                            env.valide(position.X + i, position.Y + j)) {
                        pos = new Position(position.X + i, position.Y + j);
//                        image.setRGB(position.X + i, position.Y + j,Color.PINK.getRGB());
                        if(!last.contains(pos))
                            list.add(pos);
                    }
                }
            }
        }

        return list;
    }

    public static ArrayList<Position> getNWhelp(Position position,int portee,
                                                Dataset env, TreeSet<Position> last){

        ArrayList<Position> list = new ArrayList<>();
        Position pos;

        for (int k = -portee/2; k <= portee/2; k++) {
            for(int i = -portee; i<= 0 ; i++) {
                for (int j = -portee; j <= 0; j++) {
                    if ((Math.pow(i, 2) + Math.pow(j, 2) == Math.pow(portee,2) +k) &&//+k
                            env.valide(position.X + i, position.Y + j)) {
                        pos = new Position(position.X + i, position.Y + j);
                        //image.setRGB(position.X + i, position.Y + j,Color.PINK.getRGB());
                        if(!last.contains(pos))
                            list.add(pos);
                    }
                }
            }
        }

        return list;
    }

    public static ArrayList<Position> getSWhelp(Position position,int portee,
                                                Dataset env,TreeSet<Position> last){

        Position pos;
        ArrayList<Position> list = new ArrayList<>();

        for (int k = -portee/2; k <= portee/2; k++) {
            for (int i = -portee; i <= 0; i++) {
                for (int j = 0; j <= portee; j++) {
                    if ((Math.pow(i, 2) + Math.pow(j, 2) == Math.pow(portee,2)+k ) &&
                            env.valide(position.X + i, position.Y + j) ) {
                        pos = new Position(position.X + i, position.Y + j);
//                        image.setRGB(position.X + i, position.Y + j,Color.PINK.getRGB());
                        if(!last.contains(pos))
                            list.add(pos);
                    }
                }
            }
        }

        return list;
    }

    /******************************************************************************/

    public static ArrayList<Position> getEnd(Position start, Position end,
                                             Dataset env,TreeSet<Position> last){

        int diffX = end.X-start.X;
        int diffY = end.Y-start.Y;

        int portee = (int) Math.sqrt(diffX*diffX + diffY*diffY);

        if (diffX == 0)
            if (diffY > 0)
                return getS(start, portee, env,last);
            else
                return getN(start, portee, env,last);
        else if (diffX < 0)
            if (diffY == 0)
                return getW(start, portee, env,last);
            else if (diffY < 0)
                return getNW(start, portee, env,last);
            else
                return getSW(start, portee, env,last);
        else if (diffY == 0)
            return getE(start, portee, env,last);
        else if (diffY < 0)
            return getNE(start, portee, env,last);
        else
            return getSE(start, portee, env,last);


    }

    public static ArrayList<Position> whichSide(Position start, Position end,int portee,
                                                Dataset env, TreeSet<Position> last){

        int diffX = end.X-start.X;
        int diffY = end.Y-start.Y;

        if( Math.abs(diffX) <= portee &&  Math.abs(diffY) <= portee)
            return getEnd(start,end,env,last);
        else {
            if (diffX == 0)
                if (diffY > 0)
                    return getS(start, portee, env,last);
                else
                    return getN(start, portee, env,last);
            else if (diffX < 0)
                if (diffY == 0)
                    return getW(start, portee, env,last);
                else if (diffY < 0)
                    return getNW(start, portee, env,last);
                else
                    return getSW(start, portee, env,last);
            else if (diffY == 0)
                return getE(start, portee, env,last);
            else if (diffY < 0)
                return getNE(start, portee, env,last);
            else
                return getSE(start, portee, env,last);
        }

    }


    public static Position checkNextArea(Dataset env, Position start, Position end
            , int portee, int step, TreeSet<Position> last) {

        double minDistance;
        boolean obs = true; int indiceNext=0, i;
        Position next = new Position(start.X,start.Y);
        ArrayList<Position> NextPosList = whichSide(start,end,portee,env,last);

        last.add(start);

        while(obs == true) {
            i=-1;minDistance=-1;

//            System.out.println("liste: "+NextPosList);

            for (Position position : NextPosList) {

                i++;

                if (minDistance == -1) {
                    minDistance = distance(end, position);
                    next.X = position.X;
                    next.Y = position.Y;
                    indiceNext=i;

                } else {
                    if (minDistance > distance(end, position)) {
                        minDistance = distance(end, position);
                        next.X = position.X;
                        next.Y = position.Y;

                        indiceNext=i;
                    }
                }
            }

            if(NextPosList.size()>0) {
                NextPosList.remove(indiceNext);
            }

            obs = containeObs(start.X,start.Y,next.X,next.Y,env);

            if(NextPosList.isEmpty())
                break;
        }


//        //TODO : draw
        if(!start.identic(next))
        {

            if(sref==0)
            env.editposition(start, next, c,3);
//            else
//                System.out.println ("Not drawing" );
       }




        //TODO : supp le if when it's with obstacles
        if(step%2 == 0) {
            next = localView(env, next, portee);
        }


        step++;
        if(next.identic(end)==false && env.getValue(next.X,next.Y)!=1 && !NextPosList.isEmpty())
            next = checkNextArea(env,next,end,portee,step,last);
        return next;
    }







    public static Position localView(Dataset env, Position position,int portee) {

        double val, localBest = env.getValue(position.X,position.Y) ;
        Position next = new Position(position.X,position.Y);
        boolean improoved=false;


        for (int i = -portee; i <= portee; i++) {
            for (int j = -portee; j <= portee; j++) {

                if(env.valide(position.X + i, position.Y + j)//){
                        && Math.pow(i, 2) + Math.pow(j, 2) <= Math.pow(portee , 2)) {


                    val = env.getValue(position.X + i, position.Y + j);

                    if (localBest < val) {

                        localBest = val;
                        next.X=position.X + i; next.Y= position.Y + j;
                        improoved = true;
                    }
                }
            }
        }


//        //TODO : draw
        if(!position.identic(next))
        {   if(sref==0)
            env.editposition(position, next, c,3);
//        else
//            System.out.println ("not drawing hello" );
        }


        if(improoved == true) next = localView(env,next,portee);

        return next;
    }

    public static double distance(Position p1,Position p2){
        return Math.sqrt(Math.pow(p1.X-p2.X,2)+Math.pow(p1.Y-p2.Y,2));
    }



    public static boolean containeObs(int x0,int y0,int x1, int y1, Dataset env){
        boolean obs = false;

        int dx = Math.abs(x0-x1), sx = x0<x1? 1 : -1;
        int dy = -Math.abs(y0-y1), sy = y0<y1 ? 1 : -1;
        int err = dx+dy, e2;

        while(x0 != x1 || y0 != y1){
            if(env.getValue(x0,y0) == -1)
                return true;
            e2=2*err;
            if(e2 >= dy) { err += dy; x0 += sx; }
            if(e2 <= dx) { err += dx; y0 += sy; }
        }

        return obs;
    }

    public static void Line(int x0,int y0,int x1, int y1,BufferedImage image){

        int dx = Math.abs(x0-x1), sx = x0<x1? 1 : -1;
        int dy = -Math.abs(y0-y1), sy = y0<y1 ? 1 : -1;
        int err = dx+dy, e2;

        while(x0 != x1 || y0 != y1){

            //image.setRGB(x0, y0, DrawLineColor.getRGB());

            e2=2*err;
            if(e2 >= dy) { err += dy; x0 += sx; }
            if(e2 <= dx) { err += dx; y0 += sy; }
        }

    }

    /**********************************************************************************/
    /**********************************************************************************/



   /* public static void main(String[] args){
       // Position start = new Position(100,0);
        //Position end = new Position(100,200);
//        Dataset env = new Dataset(size);
//        FileData ps = new FileData();
//        try {
//            ps.parsing("../Simulator/RangeTarget/1/mat1/10/target" + 1 + ".txt");
//            env = new Dataset(ps);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for(int i =90 ; i<120; i++) {
//            for(int j=5 ; j<20 ; j++)
//                env.setValue(i, j, -1);
//        }
//        openImage(env);
//        env.writeimage(0,"test");
//        Line(0,0,299,299,image);
//        env.create("test");
//        Position pos = checkNextArea( env,  start,  end
//                ,  10,0,new TreeSet());
//
//        env.writeimage(0,"test");
//        closeImage();
        TreeSet<Position> treeSet = new TreeSet();
        treeSet.add(start);
        System.out.println(treeSet.contains(end));
        System.out.println(treeSet.contains(start));
    }*/

    /**************************************************************************/

    static Graphics g;
    static File f ;
    static BufferedImage image;
    static int size=300;

    static int ObstacleColor =  new Color(0,0,0).getRGB ();
    static int EnvColor =  new Color(255,255,255).getRGB();
    //int PorteeColor = new Color(255,50,20).getRGB ();
    static int TargetColor = new Color(255,105,180).getRGB();
    static Color DrawLineColor = new Color(255,50,20);

    public static void openImage(Dataset env){
        f = null;
        image = null;
        try {
            f = new File("image/test.png"); //image file path
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);

        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        for(int x=0;x<size;x++)
            for(int y=0;y<size;y++)
                if(env.getValue(x,y) ==-1){
                    image.setRGB(x,y,ObstacleColor);

                }else

                {
                    if(env.getValue ( x,y )>0.0)
                        image.setRGB ( x,y,TargetColor);
                    else
                        image.setRGB(x,y,EnvColor);
                }


        g = image.getGraphics().create();
        g.setColor(DrawLineColor);
    }

    public static void closeImage(){
        //write image
        try{
            ImageIO.write(image, "png", f);
            //System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
    }

}