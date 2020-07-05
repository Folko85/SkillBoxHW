import core.Line;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class MainTest {
    private static String mapUrl = "https://www.moscowmap.ru/metro.html#lines";
    Line line;
    Document metropolitan;
    Elements objects;

    @Before
    public void setUp() throws Exception {
        metropolitan = Jsoup.connect(mapUrl).userAgent("Chrome").maxBodySize(0).get();
        objects = metropolitan.select("div#metrodata");
        line = new Line("1", "Тестовая");
    }

    @Test
    public void testDrawStationName(){
        Elements connections = objects.select("div.js-metro-stations.t-metrostation-list-table[data-line = 2]").select("p");
        List<String> really = connections.stream().map(x-> Main.drawStationName(x, line))
                .filter(y -> y != null && !y.isEmpty() && !y.isBlank()).collect(Collectors.toList());
        List<String> mustBe = new ArrayList<>();
        mustBe.add("Охотный ряд"); // ищем станцию на первой линии, на которую переходим со второй линии
        assertEquals(mustBe, really);
    }

    @Test
    public void testParseColorOfLine(){
        String cssUrl =Main.getCssUrl(metropolitan);
        String really = Main.parseColorOfLine(cssUrl, line);
        String mustBe = "Транспортный красный";
        assertEquals(mustBe, really);
    }

    @Test
    public void testCodeToColor(){
        String code = "ABAB09";
        String really = Main.hexToColor(code);
        String mustBe = "Латунный";
        assertEquals(mustBe, really);
    }

    @After
    public void tearDown() {

    }
}
