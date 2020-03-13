package zad1;



import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;



public class Futil {



     static FileChannel outputChannel;

    public static void processDir(String dirName, String resultFileName) {

        try {

            outputChannel = FileChannel.open(Paths.get(resultFileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            FileChannel.open(Paths.get(resultFileName), StandardOpenOption.WRITE).truncate(0).close();

            Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    readFile(file, attrs.size());
                    return super.visitFile(file, attrs);
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void readFile(Path path, long size) throws IOException {

        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)size +1);

        channel.read(byteBuffer);
        byteBuffer.flip();

        Charset charsetIn = Charset.forName("Cp1250");
        Charset charsetOut = Charset.forName("UTF-8");

        CharBuffer charBuffer = charsetIn.decode(byteBuffer);
        ByteBuffer bufferAdds = charsetOut.encode(charBuffer);

        while(bufferAdds.hasRemaining()){
            outputChannel.write(bufferAdds);
       }


       channel.close();

    }
}
