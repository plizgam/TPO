package zad1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Futil {

    public static File file;

    public static void processDir(String dirName, String resultFileName) {

        try {
            file = new File(resultFileName);
            file.delete();
            file.createNewFile();

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
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        Charset charset = Charset.forName("Cp1250");

        while(channel.read(byteBuffer) > 0){
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()){
                writeToFile(byteBuffer);
            }
            byteBuffer.clear();
        }

        channel.close();
        file.close();

    }


    public static void writeToFile(ByteBuffer byteBuffer) throws IOException {
        FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        channel.write(byteBuffer);
        channel.close();

    }
}
