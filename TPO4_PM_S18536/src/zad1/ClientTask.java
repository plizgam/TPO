/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ClientTask extends FutureTask<String> {


    public ClientTask(Callable<String> callable) {
        super(callable);
    }

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes){
        return new ClientTask(() -> {
            c.connect();
            c.isMulti = true;
            c.send("login " + c.id);

            for (String req : reqs) {
                String response = c.send(req);
                if(showSendRes)
                    System.out.println(response);
            }

            c.send("bye and log tranfer");
            return c.sb.toString() + "\n";
        });
    }
}