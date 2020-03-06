package zad1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Futil {

    //plik do zapisu
    public static Path path = Paths.get("TPO1res.txt");

    public static void processDir(String dirName, String resultFileName) {
        try {
            Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    readFile(file.toString());
                    return super.visitFile(file, attrs);
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void readFile(String path) throws IOException {

        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        Charset charset = Charset.forName("Cp1250");

        while(channel.read(byteBuffer) > 0){
            byteBuffer.rewind();
            writeToFile(byteBuffer);
            byteBuffer.flip();
        }

        channel.close();
        file.close();

    }


    public static void writeToFile(ByteBuffer byteBuffer) throws IOException {

        FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        channel.write(byteBuffer);
        channel.close();

    }
}
