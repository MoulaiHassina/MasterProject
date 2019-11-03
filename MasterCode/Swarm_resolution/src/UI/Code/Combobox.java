package UI.Code;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;
import javafx.scene.paint.Color;

/**
 * Created by masterubunto on 26/05/19.
 */
public class Combobox<type> extends JFXComboBox<type> {

    String textprom;
    type selecteditem;

    public Combobox(String text)
    {
        super();
        this.setPromptText ( text );
        textprom=text;
        this.setLabelFloat ( true );
        this.setOnAction ( event -> {
          if(textprom.contains ( "Nb" ))
          {
              Popupmenus.robots=this.getSelectionModel().getSelectedItem ();
          }
           // System.out.println ("value:"+this.getSelectionModel ().getSelectedItem () );
            selecteditem=this.getSelectionModel ().getSelectedItem ();
        } );
        this.setFocusColor (Color.valueOf("#4B0082") );
    }
    public void add( type val)
    {     super.getItems ().add ( val );
    }


    @Override
    public String toString() {
        return textprom;
    }

}
