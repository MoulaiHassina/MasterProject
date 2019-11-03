package Environnement;



import ESWSA.SubswarmEswsa.Region;
import javafx.scene.paint.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.*;

import java.util.List;

public class Dataset {

    public int size;
  //  java.util.List<BufferedImage> listimage;
    public double[][] matrix;
    public ArrayList<Position> targets;
    public Graphics g;
    public File f ;
    public BufferedImage image;
    int ObstacleColor =  new Color(0,0,0).getRGB();
    int EnvColor =  new Color(240,240,240).getRGB();
    int PorteeColor = new Color(50,255,50).getRGB ();
    int TargetColor = new Color ( 164,0,199 ).getRGB ()
            ;

    Color DrawLineColor = new Color(25,50,20);
    int portee;
    BufferedImage animal= null;
    Image newImage;
    public BufferedImage view;
    public BufferedImage copyimage;
    public Graphics gcopyimage;

    public BufferedImage getCopyimage() {
        return copyimage;
    }

    public Graphics getGcopyimage() {
        return gcopyimage;
    }

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    /**********************************************/



    public Dataset(FileData ps){
        this.size = ps.size;
        this.matrix = ps.matrix;
        this.portee=ps.portee;
        this.create ( "start" );
       // listimage=new ArrayList<> (  );
    }

    public Dataset(int s, double[][] m){
        this.size = s;
        this.matrix = m;
      //  listimage=new ArrayList<> (  );

    }

    public Dataset(){
        this.size = 0;
        this.matrix = new double[0][0];
      //  listimage=new ArrayList<> (  );


    }
    public Dataset(int size){
        this.size = size;
        this.matrix = new double[size][size];
        //listimage=new ArrayList<> (  );

    }

    public Dataset(Dataset env){
        this.size = env.size;
        this.matrix = new double[size][size];
        this.portee = env.portee;
        System.out.println ("size:"+size );

        for(int i=0 ; i<env.matrix.length ; i++)
            for(int j=0 ; j<env.matrix.length ; j++)
                this.matrix[i][j] = env.matrix[i][j];
//        System.out.println(this.matrix.toString() +" : "+env.matrix.toString());
       // listimage=new ArrayList<> (  );

    }


    public ArrayList<Region>  partition(int nbzone)
    {
        ArrayList<Region> zones = new ArrayList<> (  );
        ArrayList<Region> finalzones=new ArrayList<> (  );
        Position position= new Position (  this.size,size);
        //System.out.println ("zone voulu:"+nbzone );
        int i=1,j=0;
        while(i<nbzone)
        {  if(j==0) { position.X/=2;
            j=1;
        }
        else {
            position.Y/=2;
            j=0;
        }
           i=i*2;
        }
      //  System.out.println (position );

        int sizeenv= this.size, region=0,nbregions=0,k=0, rangesideX=position.X,rangesideY=position.Y;
        //System.out.println ("zone voulu:"+nbzone );
        //System.out.println ("taille of a zone:"+rangesize );


        if(rangesideX>sizeenv) rangesideX=sizeenv;


        while(k < sizeenv)
        {

            //   System.out.println ("debut:"+ k +","+region +"fin:"+(k+rangesize-1)+","+(region+rangesize-1) );


            Position d = new Position ( k,region ),f=new Position ( k+rangesideX,region+rangesideY-1);
            if(k+rangesideX-1>sizeenv) f.X=sizeenv;
            if(region+rangesideY-1>sizeenv) f.Y=sizeenv;


           // System.out.println (d.toString ()+":"+f.toString () );
            zones.add ( new Region ( d, f ));

            region=region+rangesideY;

            if(region>= sizeenv)
            {
                k=k+rangesideX;
                region=0;
            }
            nbregions++;




        }

        //System.out.println ("regions:"+nbregions );
        int zone=nbzone;
        j=(nbregions)%nbzone;
        if(nbregions/nbzone!=1)
        System.out.println ("nbfusion:"+nbregions/nbzone+"mode:"+(nbregions)%nbzone );

        k=0;
        //   if(j==0 && ((nbregions+1)/nbzone)>1) j=(nbregions+1)/nbzone;
        while(zone>0 && k<nbregions)
        {
            if(j>0)
            {
                int index=k;
                if(zones.get ( k ).isStatue ()==false) {
                    zone-=1;
                    j-=1;
                    // System.out.println ( "fusion1:" + zones.get ( k ).getDebut ( ) + "," + zones.get ( k ).getFin ( ) );
                    zones.get ( k ).setStatue ( true );
                   /*fusion only if neighbors*/

                    do {k++; }
                    while (zones.get ( k ).getDebut ( ).X != zones.get ( index ).getDebut ( ).X && zones.get ( k ).getDebut ( ).Y != zones.get ( index ).getDebut ( ).Y);

                    if(zones.get ( k ).isStatue ()==false)
                    {
                        //System.out.println ( "choosen2:" + zones.get ( k ).getDebut ( ) + "," + zones.get ( k ).getFin ( ) );
                        finalzones.add ( new Region ( zones.get ( index ).getDebut (),zones.get ( k ).getFin () ) );
                        zones.get ( k ).setStatue ( true );
                      //  System.out.println ( zones.get ( index ).getDebut ()+","+zones.get ( k ).getFin ());
                    }
                }
                k=index+1;

            }
            else
            {
                if(zones.get ( k ).isStatue ()==false)
                { zone--;
                    zones.get ( k ).setStatue ( true );
                    //System.out.println ("giving 1 zone" );
                    finalzones.add ( zones.get ( k ) );
                   // System.out.println ("normal:"+zones.get ( k ).getDebut ()+","+zones.get ( k ).getFin () );
                }
                k++;
            }

        }
        return finalzones;


    }




    public double getValue(int x, int y){
        return matrix[y][x];
    }

    public Graphics getG() {
        return g;
    }

    public void setValue(int x, int y, double val){
        matrix[y][x]=val;
    }

    public int getX(int x){
        int x2 ;
        if(x<0) x2 = x + size;
        else     x2 = x % size;
        return x2;
    }

    public int getY(int y){
        int y2 ;
        if(y<0) y2 = y + size;
        else     y2 = y % size;
        return y2;
    }

    public boolean InBorder(int x, int y){
        if(x<size && x>=0 && y<size && y>=0 )
            return true;
        else
            return false;
    }

    public boolean valide(int x, int y){
        if(x<size && x>=0 && y<size && y>=0 && getValue(x,y)!=-1)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        String s = "";
        //print matrix
        for(int i=0;i<this.size;i++){
            s=s+"\n";
            for(int j=0;j<this.size;j++){
                s= s + matrix[i][j] + "\t,";
            }
        }
        return s;
    }


    public void Inittarget(int portee) {
        this.portee=portee;
        Position p = new Position( new Random ( ).nextInt ( size ), new Random ( ).nextInt ( size ) );
        int maxline = Math.abs( p.X ) + portee;
        int maxcolumn = Math.abs ( p.Y ) + portee;
        if (maxline > size) maxline = size;
        if (maxcolumn > size) maxcolumn = size;
        for (int k = 0; k <portee; k++) {
            for (int i = p.X - portee; i < maxline; i++)
                for (int j = p.Y - portee; j < maxcolumn; j++)

                    if (this.valide(i,j) && Math.pow ( i - p.X, 2 ) + Math.pow ( j - p.Y, 2 ) < Math.pow ( portee - k, 2 )) {
                        double b = ((double)(k+1)/(double)(portee));

                        if(this.getValue(i,j)< b)
                           this.setValue(i,j,b);
                    }


        }
        if(this.getValue(p.X,p.Y)==1) System.out.println ("Position target:"+p.toString () );
        if(targets==null) targets=new ArrayList<> (  );
        targets.add ( p );
    }


    public double [][] Inittargets(int portee,Position p) {
        this.portee=portee;
       // Position p = new Position( new Random ( ).nextInt ( size ), new Random ( ).nextInt ( size ) );
        int maxline = Math.abs( p.X ) + portee;
        int maxcolumn = Math.abs ( p.Y ) + portee;
        if (maxline > size) maxline = size;
        if (maxcolumn > size) maxcolumn = size;
        for (int k = 0; k <portee; k++) {
            for (int i = p.X - portee; i < maxline; i++)
                for (int j = p.Y - portee; j < maxcolumn; j++)

                    if (this.valide(i,j) && Math.pow ( i - p.X, 2 ) + Math.pow ( j - p.Y, 2 ) < Math.pow ( portee - k, 2 )) {
                        double b = ((double)(k+1)/(double)(portee));

                        if(this.getValue(i,j)< b)
                            this.setValue(i,j,b);
                    }


        }
       // if(this.getValue(p.X,p.Y)==1) System.out.println ("Position target:"+p.toString () );
        if(targets==null) targets=new ArrayList<> (  );
        targets.add ( p );
        return this.matrix;
    }


    public void display(){
        System.out.println(this.toString());
    }

    public double getValue(Position position){
        return getValue(position.X,position.Y);
    }

    public double [][] shutdown (Position Xmax,Position p)
    {  /** ToDo : Xmax , Xmin adaptation to zone modelisation**/

        /* position p is the postion where fitness ==1
        * */


        int maxline=Math.abs ( p.X )+portee;
        int maxcolumn=Math.abs ( p.Y )+portee;
        if(maxline>Xmax.X)
            maxline=Xmax.X;
        if(maxcolumn>Xmax.Y)
            maxcolumn=Xmax.Y;

        for (int k = 0; k <portee; k++) {
            for(int i=p.X-portee;i<maxline;i++)
                for(int j=p.Y-portee;j<maxcolumn;j++)

                    if(Math.pow (  i-p.X,2)+Math.pow (  j-p.Y,2)< Math.pow (portee-k,2))
                    {
                        double b = ((double)(k+1)/(double)(portee));
                        if(valide(i,j))

                           if(getValue ( i,j ) <= b && getValue ( i,j ) > -1 )
                            {
                                setValue ( i,j,0.0 );
                            }
                    }
        }

       return matrix;
    }


    /***** edit+create+update image for meta functions**/
    public void create(String meta)
    {f = null;
        image = null;


        try {
            f = new File("image/"+meta+".png"); //image file path
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);

        }catch(IOException e){
            System.out.println("Error: "+e);
        }


        //drawing the env and obstacles
        for(int x=0;x<size;x++)
            for(int y=0;y<size;y++)
                if(getValue(x,y) ==-1){
                    image.setRGB(x,y,ObstacleColor);

                }else

                {
                    if(getValue ( x,y )>0.0)
                        image.setRGB ( x,y,TargetColor);
                    else
                        image.setRGB(x,y,EnvColor);
                }


        g = image.getGraphics().create();
       g.setColor(DrawLineColor);



    }

    public void editposition(Position start,Position end,Color c,int taille)
    {
//       // System.out.println ("doing" );
        if(g==null)
        {  if(image!=null)
            {   g=image.getGraphics().create ();
                g.setColor(c);
                g.drawLine(start.X, start.Y, end.X, end.Y);
            }
        }
        else{
            g=image.getGraphics().create ();
            g.setColor(c);
            g.drawLine(start.X, start.Y, end.X, end.Y);}
         //g.drawRect ( end.X,end.Y,10,10 );
        if(copyimage!=null){
        if(gcopyimage==null )
            gcopyimage=copyimage.getGraphics().create ();
            gcopyimage.setColor(c);
            gcopyimage.drawLine(start.X, start.Y, end.X, end.Y);
        }
      //   gcopyimage.drawRect ( end.X,end.Y,10,10 );


//        Utility.Line(start.X, start.Y, end.X, end.Y,image);
        //g.drawRect ( start.X,start.Y,taille,taille);
    }


  /*  public List<BufferedImage> getListimage() {
        return listimage;
    }*/
  public static BufferedImage deepCopy(BufferedImage bi) {
      ColorModel cm = bi.getColorModel();
      boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
      WritableRaster raster = bi.copyData(null);
      return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }
    public void writeimage(int i, String s)
    {
//        File f2 = new File("image/"+s+i+".png");
//      try{
//     //     ImageIO.write(copyimage, "png", f2);
////            System.out.println("Writing complete.");
//        }
//
//        catch(IOException e)
//        {
//            System.out.println("Error: "+e);
//        }

    }
    public void writeimage(Position p)
    {   int sizex= (int) (size/10.0),x=0,y=0;
        x=p.X-35;
        y=p.Y-35;
        if((p.X-35+sizex)<size && (p.Y-35+sizex)<size)
       {
           if(p.X-35<0 )x=0;
           if(p.Y-35<0) y=0;
           view=copyimage.getSubimage(Math.abs ( x), Math.abs (  y) ,sizex , sizex);
       }


    }
    public void writeall(int i ,String s)
    {
        File f2 = new File("image/"+s+i+".png");
        BufferedImage g=image;
        try{
            ImageIO.write(g, "png", f2);
            //System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }


    }
    public void drawanimal(Position p){
        gcopyimage.drawImage ( newImage,Math.abs (  p.X-20),Math.abs (  p.Y-20),null);
        /*Graphics gcopyimage=copyimage.createGraphics ();
        gcopyimage.setColor ( Color.RED );
        gcopyimage.drawRect ( p.X,p.Y,10,10 );*/
    }

    public void drawround(Position p,Color c)
    {
        gcopyimage.setColor(c);
        int sizex= (int) (size/10.0);
      //  g.drawRect(p.X,p.Y,width,width);
        gcopyimage.drawOval ( p.X-35,p.Y-35,sizex,sizex );
    }

    public void prepare_editing_env (String nameofanimal)
    {
        try {
            animal = ImageIO.read(new File ("image/"+nameofanimal+".png"));
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        newImage = animal.getScaledInstance((int)(size*50.0/1000.0),(int)(size*50.0/1000.0) , Image.SCALE_DEFAULT);
        copyimage=Dataset.deepCopy ( image );
        gcopyimage=copyimage.createGraphics ();
        gcopyimage.setColor ( g.getColor () );
    }



}
