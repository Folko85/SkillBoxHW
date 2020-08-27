import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImageResizer implements Runnable {
    private static final Logger logger = LogManager.getLogger(ImageResizer.class);
    private List<File> files;
    private String dstFolder;
    private int size;

    public ImageResizer(List<File> files, String dstFolder, int size) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.size = size;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            for (File file : files) {
                BufferedImage srcImage = ImageIO.read(file);
                BufferedImage scaledImage = Scalr.resize(srcImage, Scalr.Method.SPEED, 300);
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(scaledImage, getFileExtension(newFile), newFile);
                logger.info("Файл: " + newFile.getName() + " уменьшен до размера " + size);
            }
            System.out.println("Файлов скопировано: " + files.size() + " Заняло времени: " + (System.currentTimeMillis() - start));
        } catch (Exception ex) {
            logger.error("Возникла ошибка: " + ex);
        }
    }

    //метод определения расширения файла
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".") + 1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }
}
