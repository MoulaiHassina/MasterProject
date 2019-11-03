package UI.Code;

import com.jfoenix.controls.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by masterubunto on 29/05/19.
 */
public class Popupmenus
{
    static Object robots;
    public JFXPopup eswsa,values,eho,mbso;
    protected Comboview parambso,parammbso,parameho,parameswsa;
    protected JFXListView <Label>listmenu;
    boolean seho,sbso,smbso,seswsa;


    public Popupmenus()
    {   listmenu = new JFXListView<> ();
        Container.initlistpopmenu(listmenu);
        mbso=new JFXPopup (  );
        values = new JFXPopup (  );
        eho = new JFXPopup (  );
        eswsa=new JFXPopup (  );
        parambso=new Comboview ( "bso" );
        parammbso=new Comboview ( "mbso" );
        parameho= new Comboview ( "eho" );
        parameswsa=new Comboview ( "eswsa" );
    }
    public void InitComboviews()
    {
        parambso.initBSO ();
        parameho.initEHO ();
        parammbso.InitMbso ();
        parameswsa.initESWSA ();


        values.setPopupContent ( parambso );

        mbso.setPopupContent ( parammbso );
        eho.setPopupContent ( parameho );
        eswsa.setPopupContent ( parameswsa );


    }

    public boolean selected()
    {
        sbso=parambso.checkallselected ();
        seho=parameho.checkallselected ();
        seswsa=parameswsa.checkallselected ();
        smbso=parammbso.checkallselected ();
        return (smbso||seho||seswsa||sbso);
    }
    public int robots(int index)
    {  int robot=0;
        switch (index)
        {
            case 0: robot=(int)parambso.values.get ( 0 ); break;
            case 1: robot=(int)parameho.values.get ( 0 );break;
            case 2: robot=(int)parameswsa.values.get ( 0 );break;
            case 3: robot=(int) parammbso.values.get ( 0 ); break;
            default: break;
        }


        return robot;
    }
    public String whichmeta(int index)
    {

        if(index==0) return "BSO:BEE SWARM OPTIMIZATION";
        if(index==3) return "Multi-BSO:Multi swarm bee swarm optimization";
        if(index==1) return "EHO:Elephant Herding Optimization";
        if (index==2) return "ESWSA:Elephant Swarm Water Search Algorithm";
        return "none";
    }
    public void parametragenull(StackPane stackp,int index) throws FileNotFoundException {

        JFXDialogLayout content=new JFXDialogLayout ();
        javafx.scene.control.Label head= new javafx.scene.control.Label ( "Parametrages non initialisé" );
        head.setGraphic (  new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  Container.path+"/robotsearch.png") ,20,20,false,true) ) );
        content.setHeading ( head);
        JFXDialog dialog=new JFXDialog( stackp,content,JFXDialog.DialogTransition.CENTER );
        JFXButton exit = new JFXButton ( "Quitter" );
        content.setBody ( new javafx.scene.control.Label ( "Vous n'avez pas initilisé les parametres de : "+whichmeta (index)+" Veuillez choisir un mode en cliquant sur la méthode: AutoParametrage avec GA ou Manuel mode.\n Si vous avez déjà choisi le paramétrage manuel, il se peut que vous ayez oublié d'initialiser tous les champs.\n" +
                "Veuillez verifier!" +
                "" ) );
        exit.setOnAction ( event -> dialog.close ());
        content.setActions ( exit );
        dialog.show ();
        content.setVisible ( true );
    }


}
