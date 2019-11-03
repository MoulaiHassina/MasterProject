package UI.Code;
import BSO.MonoBSO.nBSO;
import BSO.MultiBSO.MultiBSO;
import EHO.nEho;
import ESWSA.SimpleESWSA.ESWSA;
import Environnement.Dataset;
import Environnement.FileData;
import Environnement.Position;
import GA.GA;
import GA.Solution;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javax.swing.event.AncestorEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class Controller
{

    protected Dataset environnement;
    protected ArrayList<Image> images;
    protected String path,pathgraphics;
    protected int methode=-1,methodeselected=-1;
    /* target is the number of target in environnement the user choose it from the combobox*/
    private int target =0,stop=0;
    /*GA==>0 manuel==>1 from the popup menu*/
    private int GAorManuel,NBrobots=0;
    /*all methods of meta*/
    /*method=0 ==> bso , method=1 ==> eho, method=2 ==> eswsa, method==>3 mbso*/
    private MultiBSO nbso;
    private nEho eho;
    private nBSO bso;
    private ESWSA eswsa;
    /* genetic algorithme is used to get parameters automatically for any meta ,
    the parameters optimize a fonction which is the number of target found and  the number of iteration*/
    private GA genetic;
    /* GAparameters: parameters is the parameters returned by the execution of GA*/
    private ArrayList <Integer> GAparameters;
    /*robots starting points*/
    private ArrayList <Position> Initrobotsp;

    /*UI components*/
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private BorderPane metaanchor;

    @FXML private JFXComboBox<String> rangecombo;
    @FXML private JFXComboBox<String> targetcombo;
    @FXML private JFXComboBox<String> envcombo;
    @FXML private JFXComboBox<String> sizecombo;
    @FXML private ImageView env;
    @FXML private JFXSlider MetaSLider;
    @FXML private ImageView metaImage;
    @FXML private ImageView robotview;
    @FXML private JFXButton metastart;
    @FXML private JFXPopup parammeta;
    @FXML private JFXButton stopbutton;
    @FXML private JFXListView<Itemcell> liststatmeta;
    @FXML private JFXListView<javafx.scene.control.Label> listinfo;
    @FXML private JFXListView<javafx.scene.control.Label> selection;
    @FXML private javafx.scene.control.Label swarmtechnique;
    @FXML private StackPane stackp ;
    @FXML private StackPane stackenv;
    @FXML private Label informations;
    @FXML private  JFXButton exitbutton;
    @FXML private Tab tabswarm;
    @FXML private Tab tabenv;
    @FXML private JFXListView <Label> paramswarm;
    @FXML private Label envilabel;
    @FXML private Label envlabelpref;
    @FXML private Label paramlabel;
    @FXML private Label robotlabel;
    @FXML private HBox hb;
    @FXML private Label space;
    @FXML private Label ss;
    @FXML private Label ls;
    @FXML private Label pp;
    @FXML private Label nbstartingrobots;
    @FXML private JFXListView<Label> robotspositions;
    @FXML private JFXListView<JFXListView<Label>> historic;
    @FXML private Label localview;
    @FXML private Label robotposition;
    @FXML private Tab tabhisto;
          private Popupmenus popupmenus;
    @FXML
    void initialize() throws FileNotFoundException {
        /*initcombobox parametres*/
        Container container=new Container ();

        container.InitAllComboBox ( envcombo,targetcombo,sizecombo ,rangecombo);
        container.InitListview ( listinfo );

        container.InitLabel ( swarmtechnique ,"Les Approches","robotsearch");
        container.InitLabel ( informations,"Informations", "stat");
        container.InitListviewstat ( liststatmeta );
        container.InitFrameSlider (MetaSLider, 400,0,1 );
        Container.initgraphics(metastart,stopbutton,exitbutton,robotlabel,envilabel,envlabelpref,paramlabel,tabenv,tabswarm,tabhisto);
        Label lb =new Label ( "Aléatoire" );
        robotspositions.getItems ().add ( lb );
        lb.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./src/UI/icons/position.png") ,20,20,true,true) ) );
        lb =new Label ( "Manuel" );
        lb.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./src/UI/icons/position.png") ,20,20,true,true) ) );
        localview.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./src/UI/icons/zoom-len.png") ,20,20,true,true) ) );
        localview.setVisible(false);
        localview.setPrefHeight(1);
        robotspositions.getItems ().add ( lb );

        robotspositions.setOnMouseClicked ( event -> {
            selection.getItems().removeAll();
            if(robotspositions.getSelectionModel().getSelectedIndex()==0) RandomPositions ();
            else ManuelPositions ();
        } );


        pathgraphics=Container.path;
        parammeta=new JFXPopup (  );
        Initrobotsp=new ArrayList<> (  );

        /*setting env listener: to get position*/

       // parammeta.setAutoHide ( false );

        /*init popmenus for all meta:swarm approches*/
        InitAllPopup ();
        genetic = new GA(5, 5, 0, 2);
        /*setting buttons env*/
        this.metastart.setOnAction( event -> {
            try {
                ChoiceMeta ( );
            } catch (FileNotFoundException e) {
                e.printStackTrace ( );
            }
        } );

        /*slider button making it appears while dragging*/
        MetaSLider.setOnMouseReleased ( event -> paramswarm.setOpacity ( 0.9 ));
        MetaSLider.setOnMouseDragged ( event ->{   paramswarm.setOpacity ( 0.23 ); IncrementSlider (); }
        );

        /*set orientation for listview containing parameterages to horizontal to gain space*/
        paramswarm.setOrientation ( Orientation.HORIZONTAL );
        /*init images array list which will contain all slider images depending on the swarm approch*/
        images=new ArrayList<> (  );
        listinfo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label> () {
            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                methodeselected=listinfo.getSelectionModel ().getSelectedIndex ();
                //container.InitSelection(selection,methodeselected);
                if(methodeselected!=methode)
                    images=new ArrayList<> (  );
            }

        });
        UIUpdaterThread.updateInformations ( this );


    }


    public void InitAllPopup()
    {   /*TODO: changing  combobox <string> to combobox of combobox and each combo combx would have integer or float*/
        /*each meta has a popmenu describing her*/
        /*TODO:handling null combobox choice for all comboviews of each meta*/
        popupmenus=new Popupmenus ();
        popupmenus.InitComboviews ();
        popupmenus.listmenu.setOnMouseClicked ( event -> {

            GAorManuel=popupmenus.listmenu.getSelectionModel ().getSelectedIndex ();
            if(GAorManuel==1) {
                 //Container.screenshot(allpane,"popmenu"+methodeselected+"methode");
                /* if parametrage statique alors selon l'indice de item( chaque item est une meta) on affiche un popmenu*/
                switch (methodeselected){
                    case 0:popupmenus.values.show (popupmenus.listmenu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX ( ), event.getY ( ) );
                    //Container.screenshot(allpane,"popmenu"+methodeselected);
                    break;
                    case 3:popupmenus.mbso.show ( popupmenus.listmenu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX ( ), event.getY ( ) );
                    //Container.screenshot(allpane,"popmenu"+methodeselected);
                    break;
                    case 1:popupmenus.eho.show ( popupmenus.listmenu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX ( ), event.getY ( ) );
                    //Container.screenshot(allpane,"popmenu"+methodeselected);
                    break;
                    case 2:popupmenus.eswsa.show ( popupmenus.listmenu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX ( ), event.getY ( ) );
                    //Container.screenshot(allpane,"popmenu"+methodeselected);
                    break;

                }
            } else
                try {
                        if(environnement==null)
                        Container.errornullenvrionnement ( stackp );
                        else {
                            int index=methodeselected;
                           /* if multibso decrease the number or iteration and population because it takes a lot of time*/
                            if(index==3){genetic= new GA(2, 3, 0, 2);}
                            ChoiceEnvi ();

                            genetic=new GA ( 5,5,0,2 );
                            GAparameters = genetic.GA( target, environnement, index ).getParameters ( );
                            String image="elephant";
                            if(index==0 || index==3 ) image="bee";
                            if(index==2) image="elephant2";
                            String contenu= Container.GA(paramlabel,GAparameters,index);
                            Container.initparametragelist (paramswarm,contenu);
                            Container.GAparam ( stackp,contenu,image);

                }
                }catch (FileNotFoundException e) {
                    e.printStackTrace ( );
                }
        }
        );
         parammeta.setPopupContent (popupmenus.listmenu);
         //parammeta.setOnAutoHide (event ->{ System.out.println (  "hiding ga") ;});
         listinfo.setOnMouseClicked (  event -> {
             methodeselected=listinfo.getSelectionModel ().getSelectedIndex ();
                parammeta.show ( listinfo,JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX ( ), event.getY ( ) ) ;});
      //  System.out.println ("nbrobots:"+listinfo.getItems ().get ( 0 ) );
    }

    public void ChoiceEnvi() throws FileNotFoundException {
      /*TOdO:CHANGE THE STRING TO A CLASS*/

        String sizechoice=sizecombo.getValue ();
        String envchoice=envcombo.getValue ();
        String targetchoice=targetcombo.getValue ();
        String portee=rangecombo.getValue();
        String porteechoice="5";
        /*taking a screen shot before any settings of env*/
        //Container.screenshot(stackenv,"env");
        if(sizechoice==null || envchoice==null || targetchoice==null ||portee==null)
            Container.errornullenvrionnement (stackenv);
        else{
            if(portee.equalsIgnoreCase("moyenne"))
                porteechoice="10";
            if(portee.equalsIgnoreCase("Large"))
                porteechoice="25";
            target=Integer.parseInt ( targetchoice );
            //out.println (sizechoice+","+envchoice+","+targetchoice );
           // metaImage.setCache ( true );

            /*setting path and image of env*/
            path="../UI/"+envchoice+"/"+sizechoice+"/p"+porteechoice+"/target"+targetchoice;




            /*taking a screen shot after envsiting*/
//            Container.screenshot(stackenv,"envsetting");
            FileData ps = new FileData();
            try {
            ps.parsing ( path+".txt" );
            environnement = new Dataset(ps);
            out.println ("size"+environnement.size );
                metaImage.fitHeightProperty().bind(metaanchor.heightProperty());
                metaImage.fitWidthProperty().bind(metaanchor.widthProperty());
                metaImage.setPreserveRatio (true);

                            /*resize to fit ui*/

                Image im3;
                im3 = SwingFXUtils.toFXImage ( environnement.image, null );



                metaImage.setImage ( im3 );


//                env.setFitHeight ( environnement.size);
//                env.setFitHeight ( environnement.size );
//                env.setPreserveRatio ( false);

                env.setImage ( im3 );
            } catch (IOException e) {e.printStackTrace();}

        //System.out.println (environnement.matrix );
        }
    }
    public void UpdateImage() {
        Image im=null;

        BufferedImage i=null,im2=null;
        switch (methode)
        {
            case 0:if(bso.getEnv ().copyimage!=null)
                i = bso.getEnv ().copyimage;
                im2=bso.getEnv ().view;break;
            case 1:if(eho.getEnv ().copyimage!=null)
                i = eho.getEnv ().copyimage;
                im2=eho.getEnv ().view;break;
            case 2:if(eswsa.getEnv ().copyimage!=null)
                i = eswsa.getEnv ().copyimage;
                im2=eswsa.getEnv ().view;
                break;
            case 3: if(nbso.getEnv ().copyimage!=null)
                i = nbso.getEnv ().copyimage;
                im2=nbso.getEnv ().view;break;
            default:
        }

        if(i!=null)
        {Image im3;
            im = SwingFXUtils.toFXImage ( i, null );
            if(im2!=null)
            {im3=SwingFXUtils.toFXImage ( im2, null );
                robotview.setFitHeight(200);
                robotview.setFitWidth(200);

                listinfo.setPrefWidth(1);
                listinfo.setPrefHeight(1);
                listinfo.setVisible(false);

                ss.setPrefHeight(2);
               // sss.setPrefHeight(0);

                pp.setText("");
                pp.setVisible(false);
                pp.setPrefHeight(0);

                space.setPrefHeight(0);
                space.resize(0,0);
                space.setVisible(false);

                robotspositions.setPrefHeight(0);
                robotspositions.setPrefWidth(1);
                robotspositions.setVisible(false);

                ls.setPrefHeight(0);

                nbstartingrobots.setVisible(false);
                nbstartingrobots.setPrefHeight(0);

                hb.setPrefHeight(0);
                hb.setMaxHeight(0);
                hb.setVisible(false);

                localview.setVisible(true);
                localview.setPrefHeight(16);
                localview.setPrefWidth(205);

                selection.setExpanded(true);
                selection.setVisible(true);
                selection.setPrefHeight(100);
                selection.setPrefWidth(170);

                robotposition.setVisible(false);
                robotposition.setPrefHeight(0);

                nbstartingrobots.setText("");
                nbstartingrobots.setVisible(false);
                nbstartingrobots.setPrefHeight(0);

                //swarmtechnique.setText("Vue locale");
            robotview.setImage (im3 );}
            /*list of images of the evaluation of execution of meta*/
            metaImage.setImage ( im );

            images.add ( im );
        }


    }

    public void UpdateInfo()
    {   if(methode!=-1)
        {
            int iter=0,nbtarget = 0;
            double time=0;
            switch (methode){
            case 3:
            {   iter= nbso.getT ();
                nbtarget= nbso.getNbrT ();
                time=nbso.getTimeMultiBSO ();
                break;
            }
            case 1:

            {   iter=eho.getT ();
                nbtarget=eho.getNbrT ();
                time=eho.getTimelapsed ();
                break;
            }
            case 0:
            {   iter=bso.getIter ();
                nbtarget=bso.getNbrT ();
                time=bso.getTimeNBSO ();
                break;
            }
            case 2:
            {  iter=eswsa.getI ();
                nbtarget=eswsa.getTarget ();
                time=eswsa.getTimelasped ();
                break;}
            }
            time=time/1000;
            Container.updateSlider(MetaSLider,liststatmeta,iter,nbtarget,time);
        }
    }
    /* swarm approches methodes*/
    public void InitMbso() {
        Container container=new Container ();
        container.InitSelection(selection,4);

        int MaxIter = 400, flip, MaxChances, nbrBees, nbrSwarm;
        try {
            paramlabel.setText("Paramétrage Multi-BSO");
            paramlabel.setGraphic (new ImageView ( new Image ( new FileInputStream (  pathgraphics+"/bee.png") ,25,25,true,true) ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        if (GAorManuel == 1) {
            Container.initparametragelist ( paramswarm,popupmenus.parammbso.showparam () );
            MaxIter = (int) popupmenus.parammbso.get ( 0 );
            flip = (int) popupmenus.parammbso.get ( 3 );
            MaxChances = (int) popupmenus.parammbso.get ( 4 );
            nbrBees = (int) popupmenus.parammbso.get ( 1 );
            nbrSwarm = (int) popupmenus.parammbso.get ( 2 );
        } else {
            flip = (int) GAparameters.get ( 0 );
            MaxChances = (int) GAparameters.get ( 1 );
            nbrBees = (int) GAparameters.get ( 2 );
            nbrSwarm=(int) GAparameters.get ( 3 );
            MaxIter= GAparameters.get ( 4 );
        }


        // int target = Integer.parseInt ( targetcombo.getValue ( ) );

        nbso = new MultiBSO ( MaxIter );

        Solution nsol = new Solution ( flip, MaxChances, nbrBees, nbrSwarm );
        nbso.SetParametersvector ( nsol.getParameters ( ) );
        nbso.Init ( environnement,Initrobotsp );
        methode=3;
    }
    public void Mbso(){
        // target=Integer.parseInt ( targetcombo.getValue ( ) );
        if(nbso!=null)
        if (nbso.getT ( ) < nbso.getMaxIter () && nbso.getNbrT ( ) < target) {
            nbso.run ( target );
        }
        if(nbso.getT ()==nbso.getMaxIter () || nbso.getNbrT ()==target)
        {methode=-1;
            Itemcell change=liststatmeta.getItems ().get ( 1 );
            change.setValue ( nbso.getNbrT () );
            liststatmeta.getItems ().set ( 1, change);
            JFXListView <Label> item =new JFXListView<> ();
            item.getItems ().add (  new Label ( "Multi-BSO: " )  );
            String [] items=nbso.toString ().split ( "\t" );

            for(String s:items)
                item.getItems ().add ( new Label ( s ) );

            historic.getItems ().add (  item);
        }

    }


    public void InitBSO() throws FileNotFoundException
    {
        Container container=new Container ();

        container.InitSelection(selection,1);

        try {
        paramlabel.setText("Paramétrage BSO");
        paramlabel.setGraphic (new ImageView ( new Image ( new FileInputStream (  pathgraphics+"/bee.png") ,25,25,true,true) ) );
    } catch (FileNotFoundException e) {
        e.printStackTrace ( );
    }

        int MaxIter=400,flip,MaxChances,nbrBees;
        if(GAorManuel==1)
        {
           // if(parambso==null) System.out.println ("init parambso problems" );
          //  if(paramswarm ==null) System.out.println ("init param swarm problems" );
            Container.initparametragelist ( paramswarm,popupmenus.parambso.showparam () );
        MaxIter = (int) popupmenus.parambso.get ( 0 );
        flip = (int) popupmenus.parambso.get ( 2 );
        MaxChances = (int) popupmenus.parambso.get ( 3);
        nbrBees = (int)popupmenus.parambso.get ( 1 );
        }
        else {
            flip = (int) GAparameters.get ( 0 );
            MaxChances = (int) GAparameters.get ( 1);
            nbrBees = GAparameters.get ( 2 );
            MaxIter= GAparameters.get ( 3 );
        }
        bso = new nBSO(MaxIter);
        bso.setNbtargets ( target );
        Solution nsoll = new Solution(MaxIter, flip, MaxChances, nbrBees, 10);
        bso.SetParametersvector ( nsoll.getParameters ());
        bso.InitBso ( environnement ,Initrobotsp);
        methode=0;

    }
    public void Bso()
    {
        if(bso!=null)
        if(!bso.RunUI ()){
          methode=-1;
          Itemcell change=liststatmeta.getItems ().get ( 1 );
          change.setValue (bso.getNbrT () );
          liststatmeta.getItems ().set ( 1, change);


          JFXListView <Label> item =new JFXListView<> ();
          item.getItems ().add (  new Label ( "BSO: " )  );
          String [] items=bso.toString ().split ( "\t" );

          for(String s:items)
              item.getItems ().add ( new Label ( s ) );

          historic.getItems ().add (  item);
        //  MetaSLider.setMax ( bso.getIter () );
      }
    }
    public void InitEHO()
    {
        Container container=new Container ();

        container.InitSelection(selection,2);
        //paramswarm.getItems ().removeAll ( paramswarm.getItems () );
        try {
            paramlabel.setText("Paramétrage EHO");
            paramlabel.setGraphic (new ImageView ( new Image ( new FileInputStream (  pathgraphics+"/elephant.png") ,25,25,true,true) ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        int nbrClan,nbrElephant,MaxGen=400;
        double alpha,beta;
        if(GAorManuel==1){

            Container.initparametragelist ( paramswarm,popupmenus.parameho.showparam () );
            nbrClan =(int) popupmenus.parameho.get ( 1 );
            nbrElephant = (int) popupmenus.parameho.get ( 2 );
            MaxGen = (int) popupmenus.parameho.get ( 0 );
            alpha =(double) popupmenus.parameho.get ( 3 );
            beta = (double) popupmenus.parameho.get ( 4 );
        }
        else {
            nbrClan =(int) GAparameters.get ( 0 );
            nbrElephant = (int) GAparameters.get ( 1 );
            alpha =(double)(GAparameters.get ( 2) )/10.0;
            beta=(double)(GAparameters.get ( 3) )/10.0;
            MaxGen= GAparameters.get ( 4 );

        }
        eho= new nEho (environnement.size,MaxGen);
        methode=1;
        eho.Init ( environnement,Initrobotsp,target,nbrClan,nbrElephant,alpha,beta);
    }
    public void Eho()
    {
       if(eho!=null)
        if(eho.runUI ()==false) {
            methode=-1;
            Itemcell change=liststatmeta.getItems ().get ( 1 );
            change.setValue ( eho.getNbrT () );
            liststatmeta.getItems ().set ( 1, change);
            JFXListView <Label> item =new JFXListView<> ();
            item.getItems ().add (  new Label ( "EHO: "));
            String [] items=eho.toString ().split ( "\t" );

            for(String s:items)
                item.getItems ().add ( new Label ( s ) );
            historic.getItems ().add (  item);


        }

    }
    public void Initeswsa()
    {
        Container container=new Container ();

        container.InitSelection(selection,3);

        try {
            paramlabel.setText("Paramétrage ESWSA");
            paramlabel.setGraphic (new ImageView ( new Image ( new FileInputStream (  pathgraphics+"/elephant2.png") ,25,25,true,true) ) );


        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        int nbelephant,maxiter;
        double p,wt;
        if(GAorManuel==1)
        {
            Container.initparametragelist ( paramswarm,popupmenus.parameswsa.showparam () );
         p=(double) popupmenus.parameswsa.get ( 2 );
         wt=(double)popupmenus.parameswsa.get ( 3 );
         nbelephant=(int) popupmenus.parameswsa.get ( 1 );
         maxiter=(int)popupmenus.parameswsa.get ( 0 );
        }
         else {
            nbelephant=GAparameters.get ( 0 );
        p=(double) GAparameters.get ( 1 )/10.0;
        wt=(double)GAparameters.get ( 2 )/10.0;
        maxiter=GAparameters.get ( 3 );
        }
        eswsa= new ESWSA ( nbelephant,maxiter , new Position ( environnement.size - 1, environnement.size - 1 ), new Position(0,0), p, environnement,wt );

        eswsa.Initeswsa (Initrobotsp);
        eswsa.setNbtargets ( target );
        methode=2;
       // el.Run (2);
    }
    public void Eswsa()
    {
        if(!eswsa.Runui ())
        {methode=-1;
            Itemcell change=liststatmeta.getItems ().get ( 1 );
            change.setValue ( eswsa.getTarget () );
            liststatmeta.getItems ().set ( 1, change);
            JFXListView <Label> item =new JFXListView<> ();
            String [] items=eswsa.toString ().split ( "\t" );
            item.getItems ().add (  new Label ( "ESWSA" )  );
            for(String s:items)
            item.getItems ().add ( new Label ( s ) );
            historic.getItems ().add (  item);
            //   MetaSLider.setMax ( eswsa.getI () );
            }

    }
    public void ChoiceMeta() throws FileNotFoundException
    {   //Container.screenshot(stackp,"metaheuristiques");
        if(environnement==null) Container.errornullenvrionnement ( stackp );
        else {
            if(methodeselected==-1)Container.errormethodenotselected ( stackp );
            else
              if(GAparameters==null && !popupmenus.selected ()) popupmenus.parametragenull ( stackp,methodeselected );
          else  {
                //  ChoiceEnvi ();
                //  images=new ArrayList<> (  );

            methode=methodeselected;

            switch(methode) {
                case 3:InitMbso();break;
                case 1:InitEHO();break;
                case 0:try {
                    InitBSO();
                } catch (FileNotFoundException e) {
                    e.printStackTrace ( );
                }break;
                case 2:Initeswsa();break;
                default:
            }
          }
        }
    }
    public void Stop() throws FileNotFoundException {


        if(stop==0) {
            Container.StopMessage ( stackp );
       switch (methode)
        {
            case 3:methode=-1; {JFXListView <Label> item =new JFXListView<> ();
                item.getItems ().add (  new Label ( "Multi-BSO: " )  );
                String [] items=nbso.toString ().split ( "\t" );

                for(String s:items)
                    item.getItems ().add ( new Label ( s ) );

                historic.getItems ().add (  item);}nbso=null;break;

            case 1:methode=-1;{JFXListView <Label> item =new JFXListView<> ();
                item.getItems ().add (  new Label ( "EHO: " )  );
                String [] items=eho.toString ().split ( "\t" );

                for(String s:items)
                    item.getItems ().add ( new Label ( s ) );

                historic.getItems ().add (  item);}eho=null;break;

            case 0:methode=-1;{JFXListView <Label> item =new JFXListView<> ();
                item.getItems ().add (  new Label ( "BSO: " )  );
                String [] items=bso.toString ().split ( "\t" );

                for(String s:items)
                    item.getItems ().add ( new Label ( s ) );

                historic.getItems ().add (  item);}bso=null;break;

            case 2:methode=-1;{JFXListView <Label> item =new JFXListView<> ();
                item.getItems ().add (  new Label ( "ESWSA: " )  );
                String [] items=eswsa.toString ().split ( "\t" );

                for(String s:items)
                    item.getItems ().add ( new Label ( s ) );

                historic.getItems ().add (  item);}eswsa=null; break;

        }
            stop++;

    }else{ Container.ResetMessage ( stackp );
            selection.getItems().remove(0);
        try {ChoiceEnvi ();
            images=new ArrayList<> (  );
            Initrobotsp=new ArrayList<> (  );
            robotview.setImage ( null );
            robotview.setFitHeight(1);
            robotview.setFitWidth(1);
           // swarmtechnique.setText("Métaheuristiques");
            listinfo.setPrefWidth(170);
            listinfo.setPrefHeight(180);
            listinfo.setVisible(true);
            pp.setText("Positions des robots");
            pp.setVisible(true);
            pp.setPrefHeight(22);


            hb.setVisible(true);
            //sss.setPrefHeight(5);
            robotspositions.setVisible(true);
            robotspositions.setPrefHeight(115);
            robotspositions.setPrefWidth(170);
            ls.setPrefHeight(10);
            nbstartingrobots.setVisible(true);
            nbstartingrobots.setPrefHeight(18);
            hb.setPrefHeight(18);

            hb.setMaxHeight(18);
            ss.setPrefHeight(0);

            space.setPrefHeight(5);
            space.resize(20,5);
            space.setVisible(true);

            localview.setPrefHeight(1);
            localview.setPrefWidth(1);
            localview.setVisible(false);

            robotposition.setPrefHeight(18);
            robotposition.setVisible(true);

            selection.setVisible(false);
            selection.setPrefHeight(1);
            MetaSLider.setValue ( 0 );
            MetaSLider.setMax (400);
            stop=0;}
            catch (FileNotFoundException e) {e.printStackTrace ( );}
    }


    }

    public void exit()
    {
       Platform.exit ();
    }

    /*slider*/
    public void IncrementSlider()
    {
        int i =(int)(MetaSLider.getValue ());
        if(i>0) i=i-1;
        out.println ("slide:"+i+"selected" );
        if(i< images.size ())
            this.metaImage.setImage ( images.get ( i ) );
    }

    public void RandomPositions()
    {  robotposition.setText ( "" );
       nbstartingrobots.setText ( "" );
        try {
            Container.RobotsInitRandom ( stackp );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        switch (methodeselected)
        {
            case 0:{environnement.create ( "bso" );
                environnement.prepare_editing_env ( "bee" ); break;}
            case 1:{environnement.create ( "eho" );
                environnement.prepare_editing_env ( "elephant" );}
            case 2:{
                environnement.create ( "eswsa" );
                environnement.prepare_editing_env ( "elephant2" );break;
            }
            case 3:{environnement.create ( "bso" );
                environnement.prepare_editing_env ( "bee" ); break;}

        }

    }

    public void ManuelPositions()
    {
        try {
            Container.RobotsInitManuel ( stackp );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        /*bso case: one position and all the others positions deduced by the flip operation*/
        if(methodeselected==0)Popupmenus.robots=1;
        if(Popupmenus.robots == null) {
            try {
                Container.RobotsInitNull(stackp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            robotposition.setText ( "Robots à positionner: " );
        NBrobots=(int)Popupmenus.robots;

        nbstartingrobots.setText ( String.valueOf (NBrobots) );
        metaImage.setPickOnBounds(true);
        switch (methodeselected)
        {
            case 0:{environnement.create ( "bso" );
                environnement.prepare_editing_env ( "bee" ); break;}
            case 1:{environnement.create ( "eho" );
                environnement.prepare_editing_env ( "elephant" );}
            case 2:{
                environnement.create ( "eswsa" );
                environnement.prepare_editing_env ( "elephant2" );break;
            }
            case 3:{environnement.create ( "bso" );
                environnement.prepare_editing_env ( "bee" ); break;}

        }

        /*listener  to manuel positions of robots*/
        metaImage.setOnMouseClicked(e -> {

            if((NBrobots)!=0){
               System.out.println ("size:"+metaImage.getFitHeight ()+","+metaImage.getFitWidth () );
               System.out.println ("x:"+e.getX ()+" y:"+e.getY () );
                int x =(int)((e.getX() * environnement.size)/(double)(metaImage.getBoundsInLocal ().getWidth ()));
                int y =(int)((e.getY() * environnement.size)/(double)(metaImage.getBoundsInLocal ().getHeight ()));
                if(environnement.getValue ( x,y )!=-1){
                     // System.out.println ("x2:"+x+" y2:"+y );
                    environnement.drawanimal ( new Position ( (int)(x),(int)(y)));

                    BufferedImage i=environnement.copyimage;
                    Image im;
                    if(i!=null)
                    {
                        im = SwingFXUtils.toFXImage ( i, null );
                    /*list of images of the evaluation of execution of meta*/
                        metaImage.setImage ( im );
                        images.add ( im );

                    }

                    Initrobotsp.add ( new Position ( x,y ) );
                    NBrobots--;

                    nbstartingrobots.setText ( String.valueOf ( NBrobots ) );
                }
            }
            else
            {
                NBrobots=(int)(Popupmenus.robots);
            }

        });}

    }
}
