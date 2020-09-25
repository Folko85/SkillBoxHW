import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class SiteMapper extends RecursiveTask<CopyOnWriteArraySet<Node>> {
    private final Node parent;
    private static CopyOnWriteArraySet<Node> allLinks = new CopyOnWriteArraySet<>();
    private static final String SITE_URL = "https://skillbox.ru/";
    private static final Logger logger = LogManager.getLogger(SiteMapper.class);

    public SiteMapper(Node parent) {
        this.parent = parent;
    }


    @Override
    protected CopyOnWriteArraySet<Node> compute() {
        allLinks.add(parent);
        CopyOnWriteArraySet<Node> childrenLinks = this.getChildrenLinks(parent);
        Set<SiteMapper> taskList = new HashSet<>();
        for (Node child : childrenLinks) {
            SiteMapper task = new SiteMapper(child);
            task.fork();
            taskList.add(task);
        }
        for (SiteMapper task : taskList) {
            allLinks.addAll(task.join());
        }
        return allLinks;
    }

    private CopyOnWriteArraySet<Node> getChildrenLinks(Node parent) {
        try {
            Document doc = Jsoup.connect(parent.getUrl())
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .timeout(10000).get();
            Elements links = doc.select("a[href]");
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))      // фильтруем ссылку на саму себя
                    .filter(y -> y.startsWith(SITE_URL))      // оставляем только ссылки внутри домена
                    .filter(v -> !v.contains("#") && !v.contains("?") && !v.contains("'"))  // убираем решётки и вопросы
                    .filter(w -> !w.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf))$)"))  //убираем картинки и файлы
                    .collect(Collectors.toSet());  // убираем дубли, собирая в сет
            for (String link : absUrls) {
                Node node = new Node(link, parent);
                if (!allLinks.contains(node)){
                    parent.addSubLinks(node);
                }
            }
            sleep(500);
        } catch (InterruptedException | IOException e) {
            logger.error("Возникла ошибка: " + e);
        }
        return parent.getSubLinks();
    }
}
