package UI.Code;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by masterubunto on 25/05/19.
 */
public class Itemcell extends Label
{
    public int index;
    public long value;
    public String name;
    public String unit=".";



    public Itemcell(int i,int v)
    {
        index=i;
        value=v;
    }

    public Itemcell(String name,int v,String unit)
    {
        index=0;
        value=v;
        this.unit=unit;
        this.name=name;
        initGraphics ();
        this.setText ( name+value+" "+unit );
    }
    public Itemcell(String name,int v)
    {
        index=0;
        value=v;
      //  this.unit=unit;
        this.name=name;
        initGraphics ();
        this.setText ( name+value );
    }
    public Itemcell(String name,double v)
    {
        index=0;
        value=(long) (v);
        this.name=name;
        initGraphics ();
        this.setText ( name+value );

    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setValue(double value) {
        this.value = (long)value;
        this.setText ( name+value+" "+unit );
    }

    public void setName(String name) {
        this.name = name;
        this.setText ( name+value );
    }

    public void initGraphics()
    {
        try {
            this.setGraphic ( new ImageView ( new javafx.scene.image.Image ( new FileInputStream (  Container.path+"/info.png") ,25,25,true,true) ) );        } catch (FileNotFoundException e) {
            e.printStackTrace ( );
        }
    }



    @Override
    public String toString() {

        return name+value+" "+unit;
    }
}
