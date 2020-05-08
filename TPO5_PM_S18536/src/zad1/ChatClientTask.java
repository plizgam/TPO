/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ChatClientTask extends FutureTask<ChatClient> {
    public ChatClientTask(Callable<ChatClient> callable) {
        super(callable);
    }

    public static ChatClientTask create(ChatClient c, List<String> msgs, int wait) {
        return new ChatClientTask(() ->{
            c.login();
            if(wait != 0)
               Thread.sleep(wait);

            for (String message : msgs)
            {
                c.send(c.id + " " + message);

                if(wait != 0)
                    Thread.sleep(wait);
            }

            c.logout();

            if(wait != 0)
                Thread.sleep(wait);

            return c;
        });
    }


    public ChatClient getClient() {
        try {
            return this.get();
        } catch (Exception e) {
            return null;
        }
    }
}
