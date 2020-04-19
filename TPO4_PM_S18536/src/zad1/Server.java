/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    public ServerSocketChannel ssc = null;
    public ExecutorService ex = Executors.newCachedThreadPool();
    public Selector selector = null;
    public String host;
    public int port;

    public Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void startServer()  {

            try {
                ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);

                ssc.socket().bind(new InetSocketAddress(host, port));
                selector = Selector.open();
                ssc.register(selector, SelectionKey.OP_ACCEPT);

            } catch(Exception exc) {
                exc.printStackTrace();
                System.exit(1);
            }

            Runnable runnable = () -> serviceConnections();
            ex.execute(runnable);
    }


    public void stopServer() throws IOException {

        ex.shutdown();
        ssc.close();

    }


    boolean serverIsRunning = true;
    private void serviceConnections() {

            while (serverIsRunning) {
                try {
                    selector.select();
                    Set keys = selector.selectedKeys();

                    Iterator iter = keys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = (SelectionKey) iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {

                            SocketChannel cc = ssc.accept();

                            cc.configureBlocking(false);
                            cc.register(selector, SelectionKey.OP_READ);
                            continue;
                        }

                        if (key.isReadable()) {
                            SocketChannel cc = (SocketChannel) key.channel();
                            serviceRequest(cc);
                            continue;
                        }


                    }

                } catch (Exception exc) {
                    exc.printStackTrace();
                    continue;
                }
        }
    }





    private ByteBuffer bbuf = ByteBuffer.allocate(1024);
    private static Charset charset  = Charset.forName("ISO-8859-2");
    private StringBuffer reqString = new StringBuffer();

    private void serviceRequest(SocketChannel sc) {
        if (!sc.isOpen()) return;

        reqString.setLength(0);
        bbuf.clear();
        try {
            readLoop:
            while (true) {
                int n = sc.read(bbuf);
                if (n > 0) {
                    bbuf.flip();
                    CharBuffer cbuf = charset.decode(bbuf);
                    while(cbuf.hasRemaining()) {
                        char c = cbuf.get();
                        if (c == '\r' || c == '\n') break readLoop;
                        reqString.append(c);
                    }
                }
            }


            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
            String currentTime = timeFormatter.format(System.currentTimeMillis());


            if(reqString.toString().contains("login")) {
                writeResp(sc, "logged in\r");
                writeToLog(reqString.toString().split(" ")[1] + " logged in at " + currentTime);

            }else{
                if(reqString.toString().contains("bye")){
                    String[] data = reqString.toString().split(" ");
                    writeToLog(data[0] + " logged out at " + currentTime);
                    sc.close();
                }else{
                    if(reqString.toString().contains("-")){
                        String[] dates = reqString.toString().split(" ");
                        writeResp(sc, Time.passed(dates[1], dates[2]) + "\r");
                        writeToLog(dates[0] + " request at " + currentTime + ": \"" + dates[1] + " " + dates[2] + "\"");
                    }
                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    StringBuffer remsg = new StringBuffer();

    private void writeResp(SocketChannel sc, String addMsg) throws IOException {
        remsg.setLength(0);
        remsg.append(addMsg);
        remsg.append('\n');

        ByteBuffer buf = charset.encode(CharBuffer.wrap(remsg));
        sc.write(buf);
    }


    StringBuffer serverLog = new StringBuffer();

    private void writeToLog(String message){
        serverLog.append(message + "\n");
    }


    public String getServerLog() {
        return serverLog.toString();
    }
}
