import handler.DOMHandler;
import handler.XMLHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Loader {

    public static void main(String[] args) throws Exception {
        String fileName = "res/data-18M.xml";

        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        DOMHandler.parseFileDOM(fileName);
        DOMHandler.workTimesVotingDOM();
        DOMHandler.duplicatedVotersDOM();

        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
        System.out.println("DOM-парсер занимает памяти: " + usage/ 1_000_000 + "Mb\n");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(new File(fileName), handler);
        XMLHandler.workTimesVotingSAX();
        handler.duplicatedVotersSAX();

        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
        System.out.println("SAX-парсер занимает памяти: " + usage/ 1_000_000 + "Mb\n");

//        SuperHandler superHandler = new SuperHandler();
//        parser.parse(new File(fileName), superHandler);
//        superHandler.duplicatedVoters();
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
//        System.out.println("Super-парсер занимает памяти: " + usage/ 1_000_000 + "Mb\n");
    }
}