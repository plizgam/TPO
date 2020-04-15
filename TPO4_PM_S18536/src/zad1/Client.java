/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    public SocketChannel channel;
    public StringBuilder sb = new StringBuilder();
    public String host, id;
    public int port;
    public boolean showOutput, isClientTask = false;

    public Client(String host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    public void connect(){
        try {
            sb.append("=== " + id + " log start ===\n" );
            channel = SocketChannel.open();
            //nieblokujace wyjscie - wejscie
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(host, port));

            while (!channel.finishConnect()) {
                try { Thread.sleep(200); } catch(Exception exc) { return; }
           }


           } catch (IOException e) {
            e.printStackTrace();
        }
    }




    ByteBuffer inBuf = ByteBuffer.allocate(1024);
    Charset charset  = Charset.forName("ISO-8859-2");
    StringBuffer reqString = new StringBuffer();


    public String send(String req) {
        StringBuffer result = new StringBuffer();
            try {

                //wysyłanie danych

                reqString.setLength(0);
                if(!req.contains(id))
                    reqString.append(id + " ");

                reqString.append(req);
                reqString.append('\n');

                ByteBuffer buf = charset.encode(CharBuffer.wrap(reqString));
                channel.write(buf);

                if(req.contains("-"))
                    sb.append("\nRequest: " + req + "\n");

                //odbiór danych
                while (true) {
                    inBuf.clear();
                    int readBytes = channel.read(inBuf);
                    if (readBytes == 0) {
                        Thread.sleep(200);
                        continue;
                    }
                    else if (readBytes == -1) {
                        channel.close();
                        break;
                    }
                    else {

                        inBuf.flip();

                        CharBuffer responseBuf = charset.decode(inBuf);
                        while(responseBuf.hasRemaining()) {
                            char c = responseBuf.get();
                            if (c == '\r') break;
                            result.append(c);
                        }
                        break;
                    }
                }

            } catch(Exception e) {
                e.printStackTrace();
            }


                displayOutput(result.toString());

            sb.append(result.toString());

            if(req.contains("bye") && (showOutput || !isClientTask)){
                sb.append("\nlogged out \n=== " + id + " log end ===\r");
                System.out.println(sb.toString());
            }


        return result.toString();
    }


    public void displayOutput(String response){

        if(response.contains("(")){
            sb.append("Result:\n");
        }


    }
}