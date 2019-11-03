package UI.Code;

import javafx.application.Platform;

/**
 * Created by masterubunto on 22/05/19.
 */
public class UIUpdaterThread
{
    public static long pace=1200;

    public static long getPace() {
        return pace;
    }

    public static void updateInformations(Controller controller)
    {
      /*static imagelist iterator to update*/

        Thread T= new Thread(() -> {
            while (true)
            {
                /* update Ui image only if one methode (swarm approach)was selected we can thought that through the Method boolean variable*/
                   int method=controller.methode;
                  //  int globalchoice=controller.globalchoice;
                    if(method!=-1)
                    {
                        Platform.setImplicitExit(false);
                      //  System.out.println ("before" );
                        switch (method)
                        {   case 0: Platform.runLater ( controller::Bso );break;
                            case 3:Platform.runLater ( controller::Mbso );break;
                            case 1:Platform.runLater ( controller::Eho );break;
                            case 2:Platform.runLater ( controller::Eswsa);break;
                        default:
                        }
                        Platform.runLater ( controller::UpdateInfo);
                       // System.out.println ( "after" );
                        Platform.runLater ( controller::UpdateImage);
                       // System.out.println ( "after" );

                    }
            try
                {
                    Thread.sleep(pace);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        T.setDaemon(true);
        T.start ();
    }
}