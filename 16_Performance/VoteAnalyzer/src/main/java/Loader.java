import handler.DOMHandler;
import handler.SuperHandler;
import handler.XMLHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class Loader {

    public static void main(String[] args) throws Exception {
        String fileName = "res/data-1572M.xml";

        long time = System.currentTimeMillis();

//        DOMHandler.parseFileDOM(fileName);
//        DOMHandler.workTimesVotingDOM();
//        DOMHandler.duplicatedVotersDOM();
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
//        System.out.println("DOM-парсер занимает памяти: " + usage/ 1_000_000 + "Mb\n");
//
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
//
//        XMLHandler handler = new XMLHandler();
//        parser.parse(new File(fileName), handler);
//        XMLHandler.workTimesVotingSAX();
//        handler.duplicatedVotersSAX();
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
//        System.out.println("SAX-парсер занимает памяти: " + usage/ 1_000_000 + "Mb\n");

        SuperHandler superHandler = new SuperHandler();
        parser.parse(new File(fileName), superHandler);
        superHandler.duplicatedVoters();

        time = System.currentTimeMillis() - time;
        LocalTime result = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalTime();
        System.out.println("И файл 1,5Гб записывается в базу данных за : " + result.getMinute()  + " минут и " + result.getSecond() + " секунд\n");
    }
}