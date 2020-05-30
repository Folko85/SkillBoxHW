import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class FolderSizeVisitor implements FileVisitor<Path> {

    private long pathSize;
    private Path startDir;
    private int exceptionCounter;

    public FolderSizeVisitor(Path startDir) {
        this.startDir = startDir;
        this.pathSize = 0;
        this.exceptionCounter = 0;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        this.pathSize += basicFileAttributes.size();
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
        this.pathSize += basicFileAttributes.size();
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
        System.out.println("Failed to access file: " + file.toString());
        exceptionCounter++;
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
        boolean finishedSearch = Files.isSameFile(path, startDir);
        if (finishedSearch) {
            return TERMINATE;
        }
        return CONTINUE;
    }

    public long getPathSize() {  // ну нам ведь нужно как-то выводить размер
        return this.pathSize;
    }

    public int getExceptionCounter() {   // сюда будем выводить количество непрочитанных файлов/папок
        return this.exceptionCounter;
    }
}
