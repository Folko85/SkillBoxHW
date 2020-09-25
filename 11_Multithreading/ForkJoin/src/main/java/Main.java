import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Paths;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String SITE_URL = "https://skillbox.ru/";
    private static final String SITEMAP_DOC = "data/sitemap.txt";      // сюда пишем
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Node rootUrl = new Node(SITE_URL);                 //чтобы потом корректно формировалось сделаем класс-узел
        CopyOnWriteArraySet<Node> result = new ForkJoinPool().invoke(new SiteMapper(rootUrl));   // и кинем его в пул
        TreeSet<Node> resultSet = new TreeSet<>(result);
        writeSitemapUrl(resultSet, SITEMAP_DOC);                            // а теперь уже пишем
    }

    public static void writeSitemapUrl(SortedSet<Node> set, String sitemapDoc) {
        if (new File(sitemapDoc.substring(0, sitemapDoc.indexOf("/"))).mkdir()) {
            logger.info("Создана папка: " + Paths.get(sitemapDoc).toAbsolutePath());
        }
        try (FileWriter saveSet = new FileWriter(sitemapDoc, false)) {
            for (Node urlNode : set) {
                saveSet.write(urlNode + "\r\n");
            }
            saveSet.flush();
            logger.info("Карта сайта сохранена в файл " + sitemapDoc);
        } catch (IOException e) {
            logger.error("Возникла ошибка: " + e);
        }

    }
}
