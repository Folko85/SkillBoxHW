package core;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import ru.folko85.tableofcolor.TableOfColor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    private final static String mapUrl = "https://www.moscowmap.ru";
    private final static String colorFindUrl = "https://encycolorpedia.ru/";  //лучшее что нашёл в плане определения цветов
    private static final Logger logger = LogManager.getLogger(Parser.class);
    Document metropolitan;
    Elements objects;

    public Parser() throws IOException {
        this.metropolitan = Jsoup.connect(mapUrl + "/metro.html#lines").userAgent("Chrome").maxBodySize(0).get();
        this.objects = metropolitan.select("div#metrodata");
    }

    public List<Line> parseLines() {
        Elements lines = objects.select("span.js-metro-line.t-metrostation-list-header.t-icon-metroln");
        List<Line> listOfLines = new ArrayList<>();
        Map<String, String> namesOfLines = lines.stream()
                .collect(Collectors.toMap((k) -> k.attr("data-line"), Element::text));
        namesOfLines.forEach((k, v) -> listOfLines.add(new Line(k, v)));
        listOfLines.forEach(line -> line.addColor(parseColorOfLine(getCssUrl(metropolitan), line)));
        return listOfLines;
    }

    public List<Station> parseStations(Line line) {
        Elements stations = objects.select("div.js-metro-stations.t-metrostation-list-table[data-line = " + line.getID() + "]").select("span.name");
        List<String> namesOfStations = stations.stream().map(Element::text).collect(Collectors.toList());
        return namesOfStations.stream().map(x -> new Station(x, line)).collect(Collectors.toList());
    }

    public List<TreeSet<Station>> parseConnections(List<Line> lineList, Map<String, Station> stationList) {
        List<TreeSet<Station>> result = new ArrayList<>();
        lineList.forEach(line -> {
            Elements connections = objects.select("div.js-metro-stations.t-metrostation-list-table[data-line = " + line.getID() + "]").select("p");
            List<TreeSet<Station>> connectionsOfLine = connections.stream().map(stations -> {
                TreeSet<Station> stationSet = new TreeSet<>();
                stationSet.add(stationList.get(stations.select("span.name").text()));
                for (Line innerLine : lineList) {
                    if (extractStationName(stations, innerLine) != null) {
                        stationSet.add(stationList.get(extractStationName(stations, innerLine)));
                    }
                }
                return stationSet;
            }).filter(s -> s.size() > 1).collect(Collectors.toList());
            result.addAll(connectionsOfLine);
        });
        return result;
    }


    public static String extractStationName(Element element, Line line) {
        String title = element.select("span.ln-" + line.getID()).attr("title");
        if (!title.isBlank() && !title.isEmpty()) {
            int begin = title.indexOf("«") + 1;
            int end = title.lastIndexOf("»");
            return title.substring(begin, end);
        } else return null;
    }

    public static String parseColorOfLine(String cssUrl, Line line) {
        String result = null;
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(cssUrl).openStream())) {
            InputSource source = new InputSource(new InputStreamReader(inputStream));
            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            ErrorHandler errorHandler = new MyErrorHandler();
            parser.setErrorHandler(errorHandler);
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            inputStream.close();
            TableOfColor table = new TableOfColor(new Locale("ru"));
            for (int i = 0; i < rules.getLength(); i++) {
                if (rules.item(i).getCssText().contains("t-icon-metroln.ln-" + line.getID() + "::before")) {
                    String rule = rules.item(i).getCssText();
                    int begin = rule.indexOf("(") + 1;
                    int end = rule.lastIndexOf(")");
                    String rgbCode = rule.substring(begin, end);
                    String hexCode = rgbToHex(rgbCode);   //большинство сайтов переводят из hex-формата в цвета
                    result = table.findNamedColorFromHex(hexCode);
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return result;
    }

    public static String getCssUrl(Document document) {
        return mapUrl + document.select("link[type = text/css]").attr("href");
    }

    private static String rgbToHex(String code) {
        String[] rgb = code.split(",");
        int r = Integer.parseInt(rgb[0].trim());
        int g = Integer.parseInt(rgb[1].trim());
        int b = Integer.parseInt(rgb[2].trim());
        return String.format("%02x%02x%02x", r, g, b);
    }

//    public static String hexToColor(String colorCode) {
//        String result;
//        try {
//            Document tableOfColor = Jsoup.connect(colorFindUrl + colorCode).maxBodySize(0).get();
//            Elements elements = tableOfColor.select("section[id = named]");
//            result = elements.stream().map(el -> el.select("a").first().text()).map(s -> {
//                int end = s.indexOf("#") - 1;
//                return s.substring(0, end);
//            }).findFirst().orElse("Не найден");
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//        return result;
//    }

    public static class MyErrorHandler implements ErrorHandler {
        @Override
        public void warning(CSSParseException exception) throws CSSException {
            logger.info("Warning: " + exception.getMessage());
        }

        @Override
        public void error(CSSParseException exception) throws CSSException {
            logger.warn("Error: " + exception.getMessage());
        }

        @Override
        public void fatalError(CSSParseException exception) throws CSSException {
            logger.error("Fatal Error: " + exception.getMessage());
        }
    }
}
