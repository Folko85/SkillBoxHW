import com.google.gson.*;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import core.Line;
import core.Station;
import core.StationIndex;
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

public class Main {
    private final static String mapUrl = "https://www.moscowmap.ru";
    private final static String colorFindUrl = "https://encycolorpedia.ru/";  //лучшее что нашёл в плане определения цветов
    private final static String jsonFile = "data/map.json";
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static List<Line> allLines;    // все наши линии
    private static Map<String, Station> allStations = new TreeMap<>();  // все наши станции
    private static List<TreeSet<Station>> allConnections = new ArrayList<>(); //все наши переходы(узлы)
    private static StationIndex map = new StationIndex();  //итоговая карта метро

    public static void main(String[] args) {
        try {
            Document metropolitan = Jsoup.connect(mapUrl + "/metro.html#lines").userAgent("Chrome").maxBodySize(0).get();
            Elements objects = metropolitan.select("div#metrodata");
            allLines = getLines(objects);
            allLines.forEach(line -> line.addColor(parseColorOfLine(getCssUrl(metropolitan), line)));  //раскрасим наши линии
            allLines.forEach((line) -> {
                List<Station> stations = getStations(objects, line);
                map.addLine(line);                                  // сразу добавляем линию на карту
                map.addAllLineStations(line, stations);                  // добавляем на карту станции к каждой линии
                stations.forEach(el -> allStations.put(el.getName(), el));   // формируем также общий список станций
            });
            allLines.forEach(line -> {
                allConnections.addAll(getConnections(objects, line));  // добавляем все переходы в список
            });
            allConnections.forEach(x -> map.addConnection(x));    // добавляем переходы на карту
            logger.info("Всего переходов: " + allConnections.size() + ". Без дублей: " + map.getConnections().size());
            mapToFile(map, jsonFile);   // записываем полученный объект в json-файл;
            System.out.println("Всего станций прочитано: " + getStationsCount(jsonFile));
            System.out.println("Всего переходов прочитано: " + getConnectionsCount(jsonFile));
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public static List<TreeSet<Station>> getConnections(Elements elements, Line line) {
        Elements connections = elements.select("div.js-metro-stations.t-metrostation-list-table[data-line = " + line.getID() + "]").select("p");
        return connections.stream().map(nameLine -> {
            TreeSet<Station> stationSet = new TreeSet<>();
            stationSet.add(allStations.get(nameLine.select("span.name").text()));
            for (Line innerLine : allLines) {
                if (drawStationName(nameLine, innerLine) != null) {
                    stationSet.add(allStations.get(drawStationName(nameLine, innerLine)));
                }
            }
            return stationSet;
        }).filter(s -> s.size() > 1).collect(Collectors.toList());
    }

    public static List<Line> getLines(Elements elements) {
        Elements lines = elements.select("span.js-metro-line.t-metrostation-list-header.t-icon-metroln");
        List<Line> listOfLines = new ArrayList<>();
        Map<String, String> namesOfLines = lines.stream()
                .collect(Collectors.toMap((k) -> k.attr("data-line"), Element::text));
        namesOfLines.forEach((k, v) -> listOfLines.add(new Line(k, v)));
        return listOfLines;
    }

    public static List<Station> getStations(Elements elements, Line line) {
        Elements stations = elements.select("div.js-metro-stations.t-metrostation-list-table[data-line = " + line.getID() + "]").select("span.name");
        List<String> namesOfStations = stations.stream().map(Element::text).collect(Collectors.toList());
        return namesOfStations.stream().map(x -> new Station(x, line)).collect(Collectors.toList());
    }

    public static String drawStationName(Element element, Line line) {
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
            for (int i = 0; i < rules.getLength(); i++) {   //вот такой корявый парсер я нашёл
                if (rules.item(i).getCssText().contains("t-icon-metroln.ln-" + line.getID() + "::before")) {
                    String rule = rules.item(i).getCssText();
                    int begin = rule.indexOf("(") + 1;
                    int end = rule.lastIndexOf(")");
                    String rgbCode = rule.substring(begin, end);
                    String hexCode = rgbToHex(rgbCode);   //большинство сайтов переводят из hex-формата в цвета
                    result = hexToColor(hexCode);
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return result;
    }

    private static String rgbToHex(String code) {
        String[] rgb = code.split(",");
        int r = Integer.parseInt(rgb[0].trim());
        int g = Integer.parseInt(rgb[1].trim());
        int b = Integer.parseInt(rgb[2].trim());
        return String.format("%02x%02x%02x", r, g, b);
    }

    public static String hexToColor(String colorCode) {
        String result;
        try {
            Document tableOfColor = Jsoup.connect(colorFindUrl + colorCode).maxBodySize(0).get();
            Elements elements = tableOfColor.select("section[id = named]");
            result = elements.stream().map(el -> el.select("a").first().text()).map(s -> {
                int end = s.indexOf("#") - 1;
                return s.substring(0, end);
            }).findFirst().orElse("Не найден");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    public static String getCssUrl(Document document) {
        return mapUrl + document.select("link[type = text/css]").attr("href");
    }

    public static void mapToFile(StationIndex index, String file) throws IOException {
        String fileName = file.substring(file.lastIndexOf("/") + 1);  //это имя копируемого файла
        String filePath = file.substring(0, file.lastIndexOf("/") + 1);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        if (new File(filePath).mkdir()) {   //если папка, куда копируем не существет, то создаём её
            logger.info("Создана папка: " + Paths.get(filePath).toAbsolutePath());
        }
        FileWriter toJson = new FileWriter(filePath + fileName);
        gson.toJson(index, toJson);
        toJson.flush();
    }

    private static int getStationsCount(String jsonFile) throws IOException   // парсим станции
    {
        BufferedReader jsonReader = new BufferedReader(new FileReader(jsonFile));
        JsonObject obj = new Gson().fromJson(jsonReader, JsonObject.class);
        JsonObject stationsObject = (JsonObject) obj.get("stations");
        int count = stationsObject.keySet().stream().mapToInt(lineNumber -> {
            JsonArray stationsArray = (JsonArray) stationsObject.get(lineNumber);
            return stationsArray.size();
        }).sum();
        jsonReader.close();
        return count;
    }

    private static int getConnectionsCount(String jsonFile) throws IOException {
        BufferedReader jsonReader = new BufferedReader(new FileReader(jsonFile));
        JsonObject obj = new Gson().fromJson(jsonReader, JsonObject.class);
        JsonArray connections = obj.getAsJsonArray("connections");
        int count = connections.size();
        jsonReader.close();
        return count;
    }

    public static class MyErrorHandler implements ErrorHandler {
        //мы не можем поменять фиговый css? но вполне можем спрятать в логи ошибки чтения, поменяв стандартный хендлер css-парсера.
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
