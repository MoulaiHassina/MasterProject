package UI.Code;

import com.jfoenix.controls.JFXListView;
import java.util.ArrayList;


/**
 * Created by masterubunto on 26/05/19.
 */
public class Comboview extends JFXListView<Combobox> {

    String name;
    ArrayList values;
    ArrayList names;

 public Comboview(String name)
 {   //this.name=name;
     super();
     values=new ArrayList (  );
     names=new ArrayList (  );
     this.name=name;



 }
//
//    public void setP(Node p) {
//
//        this.setOnMouseClicked(event -> {
//            System.out.println ("selected itembso"+this.getSelectionModel ().getSelectedIndex ());
//            Container.screenshot(p,"bso"+this.getSelectionModel ().getSelectedIndex ());
//
//        });
//    }

    public ArrayList getValues() {
        return values;
    }
    public Object get(int index){
     return values.get ( index );
    }

    public String [] showparam()
    {
        values=new ArrayList (  );
        names=new ArrayList (  );
        for(Combobox combobox:this.getItems ())
        {
            values.add ( combobox.selecteditem );
            names.add ( combobox.textprom );
        }
        String [] param = new String[values.size ()];
        for(int i=0;i<values.size ();i++)
            param[i]=names.get ( i )+":"+String.valueOf ( values.get ( i ) );

        return param;
    }
    public  boolean checkallselected()
    {
        int count=0;
     for(Combobox combobox:this.getItems ())
     {
         if(combobox.selecteditem!=null)
             count++;
     }
     if(count==this.getItems ().size ())
         return true;
     else return false;
    }
    @Override
    public String toString() {
       return "hi";
    //    return super.toString ( );
    }
    public  void initEHO()
    {
        Combobox nbclan= new Combobox ( "NbClan" );
        Combobox nbelephant= new Combobox ( "nbElephant" );
        Combobox Maxgeneration= new Combobox ( "MaxIteration" );
        Combobox alpha= new Combobox ( "alpha" );
        Combobox beta= new Combobox ( "beta" );
        for(int i=1; i<10; i++) {

            beta.add ( i/10.0 );
            alpha.add ( i/10.0 );

        }
        for(int i=1; i<6; i++)
        {
            Maxgeneration.add ( i*100 );
            nbelephant.add ( i);
            nbclan.add ( i);
        }
        this.getItems ().addAll (Maxgeneration,nbclan,nbelephant,alpha,beta );
    }
    public void initESWSA()
    {
        /* double p=0.5,wt=0.5;
        int nbelephant=7;*/
        Combobox p = new Combobox ( "P" );
        Combobox wt = new Combobox ( "Weight inertia" );
        Combobox nbelephant=new Combobox ( "NbElephants" );
        Combobox maxiteration=new Combobox ( "MaxIterations" );
        for(int i=1; i<10; i++) {

            p.add ( i/10.0 );
            wt.add ( i/10.0 );
            if(i<6)
                maxiteration.add ( i*100 );
        }

        for(int i=1; i<26 ; i=i+2)
            nbelephant.add( i );

        this.getItems ().addAll (maxiteration ,nbelephant, p,wt);
    }
    public void initBSO()
    {
        Combobox flip = new Combobox ( "Flip" );
        Combobox maxiter=new Combobox ( "MaxIteration" );
        Combobox maxchances= new Combobox ( "MaxChance" );
        Combobox abeilles=new Combobox ( "nbrBees" );
        flip.add (1);
        flip.add (2);
        flip.add (3);
        flip.add (4);
        for(int i=5; i<=100; i=i+5) {
            flip.add ( i );
        }

        for(int i=1; i<26; i=i+2) {
            abeilles.add ( i );
        }

        for(int i=1; i<6; i++) {
            maxiter.add ( i*100 );
        }

        for(int i=1; i<5; i++) {
            maxchances.add ( i );
        }

        this.getItems ().addAll ( maxiter,abeilles,flip,maxchances);
    }
    public void InitMbso()
    {
        Combobox flip = new Combobox ( "Flip" );
        Combobox maxiter=new Combobox ( "MaxIteration" );
        Combobox maxchances= new Combobox ( "MaxChance" );
        Combobox abeilles=new Combobox ( "nbrBees" );
        Combobox swarm=new Combobox ( "NbSwarm" );

        flip.add (1);
        flip.add (2);
        flip.add (3);
        flip.add (4);
        for(int i=5; i<=50; i=i+5) {
            flip.add ( i );
        }
        for(int i=1; i<6; i++) {
            abeilles.add ( i );
            maxiter.add ( i*100 );
            swarm.add ( i );
        }
        for(int i=1; i<5; i++) {
            maxchances.add ( i );
        }
        this.getItems ().addAll ( maxiter,abeilles,swarm,flip,maxchances);

    }
}
