package zad1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        try {
            Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("File visited " + file.toString());
                    return super.visitFile(file, attrs);
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}