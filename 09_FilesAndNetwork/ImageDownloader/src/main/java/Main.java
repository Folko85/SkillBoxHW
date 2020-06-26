import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static String address = "https://lenta.ru/";
    private static String inputPath = "images/";

    public static void main(String[] args) {

        Document document;
        try {
            document = Jsoup.connect(address).userAgent("Mozilla").get();   //userAgent добавляем, чтоб не считали ботом

            Elements images = document.select("img");  // получаем элементы страницы
            List<String> imageUrls = images.stream().map(el -> el.attr("abs:src"))  //переведём в привычный список строк
                    .filter(a -> a.contains(".jpg") || a.contains(".png") || a.contains(".gif")) // отфильтруем счётчики
                    .collect(Collectors.toList());
            if (!Files.exists(Paths.get(inputPath)) || !Files.isDirectory(Paths.get(inputPath))) {
                if (new File(inputPath).mkdir()) {   //если папка, куда копируем не существет, то создаём её
                    logger.info("Создана папка: " + Paths.get(inputPath).toAbsolutePath());
                }
            }
            imageUrls.forEach(k -> downloadImage(k, inputPath));    //копируем файлы по списку адресов в указанную папку
        } catch (Exception e) {
            logger.error("Возникла ошибка: " + e);       // тут фиксируем все ошибки
        }
    }

    private static void downloadImage(String urlStr, String inputPlace) {
        try {
            URL url = new URL(urlStr);
            String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);  //это имя копируемого файла
            String filePlace = inputPlace + fileName;  // а это полный адрес назначения
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            FileOutputStream outputStream = new FileOutputStream(filePlace);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = inputStream.read(buffer, 0, 1024)) != -1) { //читаем, пока не закончится входной поток
                outputStream.write(buffer, 0, count);                // потом записываем
            }
            outputStream.close();        // закрываем потоки
            inputStream.close();
            logger.info("Файл " + fileName + " успешно скопирован");
        } catch (IOException ex) {
            throw new RuntimeException(ex);   // пробрасываем необрабатываемое исключение, чтоб не пробрасывать его в лямбда-выражении
        }
    }
}
