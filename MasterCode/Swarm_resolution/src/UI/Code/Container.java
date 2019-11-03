package UI.Code;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by masterubunto on 23/05/19.
 */
public class Container
{
    /*TODO: change all <String> By a class having range to all the :Small,big,medium..*/
    static String path="./src/UI/icons";

    public static void initgraphics(JFXButton metastart,JFXButton stopbutton,JFXButton exit,Label robotlabel,Label envilabel,Label envlabelpref,Label paramlabel,Tab tabenv,Tab tabswarm,Tab histo)
    {        metastart.setText ( "" );
        stopbutton.setText ( "" );
        try {
            metastart.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/startblack.png") ,25,25,true,true)));
            stopbutton.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/pauseblack.png") ,25,25,true,true) ) );
            exit.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,25,25,true,true) ) );
            robotlabel.setText ( "Recherche de cibles" );
            robotlabel.setContentDisplay(ContentDisplay.TOP);
            robotlabel.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,40,50,false,true) ) );
            envilabel.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/info.png") ,25,25,true,true)));
            envlabelpref.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/world.png") ,25,25,true,true)));
            paramlabel.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,25,25,true,true)));
            tabenv.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/world.png") ,25,25,true,true) ) );
            tabswarm.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/swarm2.png") ,25,25,true,true) ) );
            histo.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/history.png") ,25,25,true,true) ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
    }


    public static void initlistpopmenu( JFXListView <Label> listmenu )
    {
        Label l=new Label ( "Auto-paramétrage:GA" );
        try {
            l.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/genetic.png") ,20,20,true,true) ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        listmenu.getItems ().add ( l);
        listmenu.getItems ().add ( new Label ( "Paramétrage manuel" ) );
        listmenu.setDepth ( 1 );
        listmenu.setExpanded ( true );
        listmenu.getStyleClass ().add ( "mylistview" );
    }
    public static void initparametragelist(JFXListView <Label> listmenu,String contenu)
    {
        System.out.println ("removeitems!" );
        listmenu.getItems ().removeAll ( listmenu.getItems () );
        String s[]=contenu.split ( "\n" );
        for(String line:s)
            listmenu.getItems ().add(new Label ( line ));
        listmenu.setDepth ( 1 );
        listmenu.setExpanded ( true );
        listmenu.getStyleClass ().add ( "mylistview" );

    }
    public static JFXListView initparametragelist(JFXListView <Label> listmenu,String [] contenu)
    {
        System.out.println ("removeitems" );
        listmenu.getItems ().removeAll ( listmenu.getItems () );
        for(String line:contenu)
            listmenu.getItems ().add(new Label ( line ));
        listmenu.setDepth ( 1 );
        listmenu.setExpanded ( true );
        listmenu.getStyleClass ().add ( "mylistview" );
        return listmenu;
    }
    public void InitcomboboxSize(JFXComboBox<String> sizecombo)
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add ("100");
        strings.add("400");
        strings.add ( "600" );
        strings.add("1000");
        strings.add ("1500" );
        sizecombo.getItems ().addAll ( strings );
    }
    public void Initcomboboxtarget(JFXComboBox<String> targetcombo)
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        //  strings.add();
        strings.add ("5" );
        // new FilteredComboBoxDecorator<>(sizecombo, FilteredComboBoxDecorator.STARTSWITH_IGNORE_SPACES);
        targetcombo.getItems ().addAll ( strings );
        //sizecombo.validate ();
    }
    public void InitcomboboxTypeenv(JFXComboBox<String> envcombo)
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("simple");
        strings.add ("obstacle" );
        strings.add("complexe");
        // new FilteredComboBoxDecorator<>(sizecombo, FilteredComboBoxDecorator.STARTSWITH_IGNORE_SPACES);
        envcombo.getItems ().addAll ( strings );

    }
    public void Inittargetrange(JFXComboBox<String> rangecombo)
    {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Petite");
        strings.add("Moyenne");
        strings.add("Large");
        rangecombo.getItems ().addAll ( strings );
    }

    public void InitAllComboBox(JFXComboBox<String> envcombo,JFXComboBox<String> targetcombo,JFXComboBox<String> sizecombo,JFXComboBox<String> rangecombo)
    {
        InitcomboboxSize ( sizecombo );
        Initcomboboxtarget ( targetcombo );
        InitcomboboxTypeenv ( envcombo );
        Inittargetrange ( rangecombo );

    }

    public void InitSelection(JFXListView<Label> selection, int methode) {
        /*listview try containing all meta methods*/
        javafx.scene.control.Label lbl;
        try {

            if(methode==1){
                lbl = new javafx.scene.control.Label("BSO");
                lbl.setGraphic(new ImageView(new javafx.scene.image.Image(new FileInputStream("./image/bee.png"), 20, 20, false, true)));

                selection.getItems().add(lbl);


            }
            if(methode==2){
                lbl=new javafx.scene.control.Label ( "EHO");
                lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/elephant.png") ,20,20,true,true) ) );
                selection.getItems().add(lbl);
            }
            if(methode==3){
                lbl=new javafx.scene.control.Label ( "ESWSA");
                lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/elephant2.png") ,20,20,true,true) ) );
                selection.getItems().add(lbl);
            }
            if(methode==4){
                lbl=new javafx.scene.control.Label ( "Multi-BSO");
                lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/bee.png") ,20,20,false,true) ) );
                selection.getItems().add(lbl);
            }

        }catch (FileNotFoundException e) {
             e.printStackTrace ( );
        }

        /*style for listview*/
        selection.getStyleClass().add("Css/selectedListView");
        selection.setExpanded ( true );
        selection.depthProperty ().setValue ( 1 );
    }

    public void InitListview(JFXListView<Label> listinfo)
    {
             /*listview try containing all meta methods*/
             try {  javafx.scene.control.Label lbl=new javafx.scene.control.Label ( "BSO");
                 lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/bee.png") ,20,20,false,true) ) );
                 listinfo.getItems().add(lbl);
                 lbl=new javafx.scene.control.Label ( "EHO");
                 lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/elephant.png") ,20,20,true,true) ) );
                 listinfo.getItems().add(lbl);
                 lbl=new javafx.scene.control.Label ( "ESWSA");
                 lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/elephant2.png") ,20,20,true,true) ) );
                 listinfo.getItems().add(lbl);
                 lbl=new javafx.scene.control.Label ( "Multi-BSO");
                 lbl.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  "./image/bee.png") ,20,20,false,true) ) );
                 listinfo.getItems().add(lbl);
             } catch (FileNotFoundException e) {
                 e.printStackTrace ( );
             }
        /*style for listview*/
        listinfo.getStyleClass().add("Css/mylistview");
        listinfo.setExpanded ( true );
        listinfo.depthProperty ().setValue ( 1 );

    }
    public void InitLabel(Label swarmtechnique,String name,String image)
    {swarmtechnique.setText ( name );
        try {
            swarmtechnique.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/"+image+".png") ,22,22,true,true)   ));
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }

    }
    public void InitFrameSlider(JFXSlider MetaSLider, int max , int min , int pace)
    {

        MetaSLider.setMax(max);
        MetaSLider.setMin(min);
        MetaSLider.setId("hello");
        //MetaSLider.setShowTickLabels(true);
        // MetaSLider.setMajorTickUnit(pace);
       //MetaSLider.setMinorTickCount(0);
        MetaSLider.showTickLabelsProperty();
        MetaSLider.setValue ( 0 );
        //  frameSlider.setOrientation(Orientation.HORIZONTAL);

    }
    public void InitListviewstat(JFXListView<Itemcell> stat)
    {
        stat.getItems ().add ( new Itemcell ( "Iterations:  ",0 ) );
        stat.getItems ().add ( new Itemcell ( "Cibles:  ",0 ) );
        stat.getItems ().add ( new Itemcell ( "Temps:  ",0 ,"s") );
        stat.getStyleClass().add("Css/mylistview");
        stat.setExpanded ( true );
        stat.depthProperty ().setValue ( 1 );

    }
    /* the GA init helper*/
    public static String GA(Label paramlabel,ArrayList para,int index)
    {
        String contenu="!";
        switch (index)
        {
            case 0:contenu=GAbso(paramlabel,para);break;
            case 1:contenu=GAeho (paramlabel,para);break;
            case 2:contenu=Gaeswsa (paramlabel,para);break;
            case 3:contenu=GAmbso (paramlabel,para);break;
        }
        return contenu;
    }

    public static  String GAbso(Label paramlabel,ArrayList <Integer> para)
    { String contenu="";
        try {
            paramlabel.setGraphic (new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/bee.png") ,25,25,true,true) ) );
            contenu+="Flip: "+para.get ( 0 )+"\n";
            contenu+="MaxChance: "+para.get ( 1 )+"\n";
            contenu+="nbrBees: "+para.get ( 2 )+"\n";
            contenu+="Maxiter: "+para.get ( 3 );
            Popupmenus.robots=para.get ( 2 );

        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        return contenu;}
    public static String GAmbso(Label paramlabel,ArrayList <Integer> para)
    {   String contenu="";
        try {  paramlabel.setGraphic (new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/bee.png") ,25,25,true,true) ) );
        contenu+="Flip: "+para.get ( 0 )+"\n";
        contenu+="MaxChance: "+para.get ( 1 )+"\n";
        contenu+="nbrBees: "+para.get ( 2 )+"\n";
        contenu+="nbSwarm: "+para.get ( 3 )+"\n";
            contenu+="Maxiter :"+para.get ( 4 );
            Popupmenus.robots=para.get ( 3 );

    } catch (FileNotFoundException e) {e.printStackTrace ( );}
        return contenu;

    }
    public static String GAeho(Label paramlabel,ArrayList<Integer> para)
    {  double a= para.get ( 2 )/10.0,b=  para.get ( 3 )/10.0;
        String contenu="";
        try {
        paramlabel.setGraphic (new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/elephant.png") ,25,25,true,true) ) );
        contenu+="nbClan: "+para.get ( 0 )+"\n";
        contenu+="nbElephant: "+para.get ( 1 )+"\n";
        contenu+="Alpha: "+a+"\n";
        contenu+="Beta: "+b+"\n";
        contenu+="Maxiter: "+para.get ( 4 );
            Popupmenus.robots=para.get ( 1 );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        return contenu;
    }
    public static String Gaeswsa(Label paramlabel,ArrayList<Integer> para)
    {
        double a= para.get ( 1 )/10.0,b=  para.get ( 2 )/10.0;
        String contenu="";
        try {
            paramlabel.setGraphic (new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/elephant2.png") ,25,25,true,true) ) );
            contenu+="nbElephant: "+para.get ( 0 )+"\n";
            contenu+="RamdonP: "+a+"\n";
            contenu+="Weight inertia: "+b+"\n";
            contenu+="Maxiter: "+para.get ( 3 );
            Popupmenus.robots=para.get ( 0 );
        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
        return contenu;
    }
    /* the error handler helper*/
    public static void GAparam(StackPane stackp, String contents, String image) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "Algorithme génetique" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/"+image+".png") ,40,40,true,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        exit.setGraphic (        (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,20,20,false,true) ) ));
        content.setBody ( new Label ( contents ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
        stackp.setVisible ( true );
//        dialog.show (stackp);
        //screenshot ( stackp,"Gaparametragebso" );
    }

    public static void errornullenvrionnement(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "Envrionnement non initialisé" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,20,20,false,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        content.setBody ( new Label ( "Environnement non choisi! allez dans l'onglet ''Envrionnement'' pour l'initialiser" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
    }
    public static void errormethodenotselected(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "Aucune méta-heuristique n'a été selectionnée" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,20,20,false,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        content.setBody ( new Label ( "Veuillez choisir une approche parmi celles proposées en haut à gauche!" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
    }
    /*button stop errors*/
    public static void StopMessage(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "STOP" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,40,40,true,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        exit.setGraphic (        (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,20,20,false,true) ) ));
        content.setBody ( new Label ( "Vous avez arreté l'exécution de l'approche!\nCliquez une seconde fois pour réinitialiser l'environnement!" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
        stackp.setVisible ( true );

    }
    public static void ResetMessage(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "STOP" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,40,40,true,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        exit.setGraphic (        (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,20,20,false,true) ) ));
        content.setBody ( new Label ( "L'environnement a été réinitialisé" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
        stackp.setVisible ( true );

    }
    /*error's messages for robots' positions*/
    public static void RobotsInitManuel(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "Positionnement initial manuel des robots" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,40,40,true,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        exit.setGraphic (        (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,20,20,false,true) ) ));
        content.setBody ( new Label ( "Veuillez placer le curseur (souris) sur l'environnement!\n" +
                "Placez les robots en cliquant sur les positions souhaitées.\n" +
                "Le compteur à gauche indique combien de robots il vous reste à positionner.\n" +
                "Une fois le compteur à ''0'', lancez l'exécution de l'approche choisie!" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
        stackp.setVisible ( true );

    }
    public static void RobotsInitRandom(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "Positionnement initial aléatoire des robots" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,40,40,true,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        exit.setGraphic (        (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,20,20,false,true) ) ));
        content.setBody ( new Label ( "Vous avez choisi le mode aléatoire, les positions initiales des robots seront générées aléatoirement.\n" +
                "Vous pouvez désormais cliquer sur le button ''start'' pour lancer la recherche" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
        stackp.setVisible ( true );

    }
    public static void RobotsInitNull(StackPane stackp) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        Label head= new Label ( "Positionnement initial des robots" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/robotsearch.png") ,40,40,true,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        exit.setGraphic (        (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  path+"/exit.png") ,20,20,false,true) ) ));
        content.setBody ( new Label ( "Vous n'avez pas encore initialisé le nombre de robots\nVeuillez d'abord choisir une approche et fixer ses paramètres." ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
        stackp.setVisible ( true );

    }

    public static void screenshot(Node p, String name)
    {
        try
        {

            Scene s = p.getScene ();

            WritableImage wi = new WritableImage((int)s.getWidth (),(int)s.getHeight ());
            WritableImage snapshot = s.snapshot (wi)
                    ;            File output = new File (name+".png");
            ImageIO.write( SwingFXUtils.fromFXImage(snapshot, null), "png", output);
        } catch (IOException ex) {
            ex.printStackTrace();
    }


}
    public static void screenshot(Scene p, String name)
    {
        try
        {

            Scene s = p;

            WritableImage wi = new WritableImage((int)s.getWidth (),(int)s.getHeight ());
            WritableImage snapshot = s.snapshot (wi)
                    ;            File output = new File (name+".png");
            ImageIO.write( SwingFXUtils.fromFXImage(snapshot, null), "png", output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void updateSlider(JFXSlider MetaSlider,JFXListView<Itemcell> liststatmeta,int iter,int nbtarget,double time)
    {
        MetaSlider.setValue ( iter );
        Itemcell change= liststatmeta.getItems ().get ( 0 );
        change.setValue ( iter );
        liststatmeta.getItems ().set ( 0, change);
        change= liststatmeta.getItems ().get ( 1 );
        change.setValue ( nbtarget );
        liststatmeta.getItems ().set ( 1, change);
        change= liststatmeta.getItems ().get ( 2 );
        change.setValue ( time );
        liststatmeta.getItems ().set ( 2, change);
    }

}
