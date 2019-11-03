package ESWSA.SubswarmEswsa;

import Environnement.Position;
import Environnement.Dataset;

import java.util.ArrayList;

/**
 * Created by masterubunto on 06/04/19.
 */
public class Region
{
  Position debut;
  Position fin;
  boolean statue;
  public int nbcase=0;
  public long time;
  public int rbregions;


  public Region(Position d,Position f)
  {
      this.debut=d;
      this.fin=f;
      statue=false;
  }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    public Position getDebut() {
        return debut;
    }

    public void setDebut(Position debut) {
        this.debut = debut;
    }

    public Position getFin() {
        return fin;
    }

    public void setFin(Position fin) {
        this.fin = fin;
    }

    public long search(Dataset env,int nbtarget, long time)
  {  double b=-1;
     double localbest=0;
     Position local_best=new Position (  );

     // System.out.println ("deb:"+debut.toString ()+":fin:"+fin.toString () );
      for(int i=debut.X;i<(fin.X);i++) {
          for (int j = debut.Y; j < (fin.Y); j++)
          {
              //nbcase++;


           //  System.out.println ("case:"+nbcase+ "i:"+i+":j:"+j );

              b = env.getValue (i,j);
              //System.out.println ( "b:"+b+":i"+i+":j:"+j);
              if(localbest < b) {

                  localbest=b;

               //   local_best=new Position ( i,j );
                //  this.debut=local_best;
                  if(localbest== 1)
                  {
                      nbcase++;

                  if(nbtarget==nbcase);

                      {this.time=System.currentTimeMillis ();
                      return 0;
                      }
                  }


                //  System.out.println ("solution:"+local_best.toString () );
              }



              }
          }
      return time;

  }




  public ArrayList<Position> defineallregions(int range, int sizeenv)
  {
      ArrayList<Position> positions = new ArrayList<> (  );

      int rangesize=((range*2)+1);

      int region=0;

      if(rangesize>sizeenv) rangesize=sizeenv;

      int k=0;
      Position p = new Position ( k,region );
      positions.add ( p );

      while(k < sizeenv)
      {

//         System.out.println ("debut:"+ k +","+region );

          region=region+rangesize;

          if(region>= sizeenv)
          {
              k=k+rangesize;
              region=0;
          }


          p = new Position ( k,region );
          positions.add ( p );

      }

      rbregions=positions.size ();

      return positions;



  }





  public void lookfortargets(ArrayList<Position> p,Dataset env,int portee,int nbtarget,long end)
  {
     int i=0;

      while (i < rbregions && nbcase < nbtarget)
      {
          this.debut=p.get ( i );
         if(debut.X+(portee*2)+1>env.size)

           this.fin.X=env.size;
       else
           this.fin.X=debut.X+(portee*2)+1;

       if(debut.Y+(portee*2)+1>env.size)

              this.fin.Y=env.size;

       else
                this.fin.Y=debut.Y+(portee*2)+1;

       //   System.out.println ("debut:"+pi.toString ()+"fin:"+fin.toString ());

          search (env,nbtarget,end);

      i++;
      }

      System.out.println ("regions seen:"+i );




  }


}
