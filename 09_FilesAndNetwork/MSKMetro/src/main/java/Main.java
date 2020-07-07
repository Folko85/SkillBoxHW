import com.google.gson.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import core.Line;
import core.Parser;
import core.Station;
import core.StationIndex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private final static String jsonFile = "data/map.json";
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static List<Line> allLines;    // все наши линии
    private static Map<String, Station> allStations = new TreeMap<>();  // все наши станции
    private static List<TreeSet<Station>> allConnections = new ArrayList<>(); //все наши переходы(узлы)
    private static StationIndex map = new StationIndex();  //итоговая карта метро

    public static void main(String[] args) {
        try {
            Parser metroParser = new Parser();
            allLines = metroParser.parseLines();                                      //добавляеи линии в список
            allLines.forEach((line) -> {
                map.addLine(line);                                                     // сразу добавляем линию на карту
                List<Station> stationsOfLine = metroParser.parseStations(line);         //получаем список станций для каждой линии
                map.addAllLineStations(line, stationsOfLine);                            // добавляем на карту станции к каждой линии
                stationsOfLine.forEach(el -> allStations.put(el.getName(), el));         // формируем также общий список станций
            });
            allConnections = metroParser.parseConnections(allLines, allStations);      // добавляем переходы в список
            allConnections.forEach(x -> map.addConnection(x));                         // добавляем переходы на карту
            logger.info("Всего переходов: " + allConnections.size() + ". Без дублей: " + map.getConnections().size());
            mapToFile(map, jsonFile);                                                  // записываем полученный объект в json-файл;
            System.out.println("Всего станций прочитано: " + getStationsCount(jsonFile));
            System.out.println("Всего переходов прочитано: " + getConnectionsCount(jsonFile));
        } catch (Exception ex) {
            logger.error(ex);
        }
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
}
