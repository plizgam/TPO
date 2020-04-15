/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ClientTask implements Runnable {

    public Client client;

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes){

        ClientTask task = new ClientTask(){
            @Override
            public void run() {
                client = c;
                c.showOutput = showSendRes;
                c.isClientTask = true;
                c.connect();
                c.send("login " + c.id);

                for (String req : reqs) {
                    c.send(req);
                }

                c.send("bye and log tranfer");
            }
        };

        return task;
    }

    @Override
    public void run() {
    }

    public String get() throws InterruptedException, ExecutionException {
        if(client.showOutput)
            return client.sb.toString();
        else
            return "";
    }
}