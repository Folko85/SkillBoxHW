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
        long parseAndWritingTime = System.currentTimeMillis();
        parser.parse(new File(fileName), superHandler);
        parseAndWritingTime = System.currentTimeMillis() - parseAndWritingTime;
        long readTime = System.currentTimeMillis();
        superHandler.duplicatedVoters();
        readTime = System.currentTimeMillis() - readTime;
        time = System.currentTimeMillis() - time;
        LocalTime result = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime resultWrite = Instant.ofEpochMilli(parseAndWritingTime).atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime resultRead = Instant.ofEpochMilli(readTime).atZone(ZoneId.systemDefault()).toLocalTime();
        System.out.println("И файл 1,5Гб записывается в базу данных (вместе с созданием индекса) за : " + result.getMinute()  + " минут и " + result.getSecond() + " секунд\n");
        System.out.println("При этом время парсинга и записи в базу составляет : " + resultWrite.getMinute()  + " минут и " + resultWrite.getSecond() + " секунд\n");
        System.out.println("Время чтения из базы составляет : " + resultRead.getMinute()  + " минут и " + resultRead.getSecond() + " секунд\n");
    }
}