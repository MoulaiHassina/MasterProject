package Multi_Regions.Agent;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by masterubunto on 21/04/19.
 */
public class TestAgent {
    public static void main(String[] args) throws Exception {

        /***agent test**/

        Runtime rt = Runtime.instance();
        ProfileImpl p = new ProfileImpl("localhost", 1099, "RSHP", false);
        ContainerController mc = rt.createMainContainer(p);

        AgentController ag1 = mc.createNewAgent("Robot1",
                "Multi_Regions.Agent.Agent1",new Object[1] );
        ag1.start ();
        AgentController ag2 = mc.createNewAgent("Robot2",
                "Multi_Regions.Agent.Agent1",new Object[1] );
        ag1.start ();
        ag2.start ();

    }
}
