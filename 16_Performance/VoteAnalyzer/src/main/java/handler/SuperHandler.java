package handler;

import model.Voter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import repository.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuperHandler extends DefaultHandler {

    private Voter voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthDay);
            } else if (qName.equals("visit") && voter != null) {
                DBConnection.countVoter(voter.getName(), birthDayFormat.format(voter.getBirthDay()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")) {
            voter = null;
        }
    }

    public void duplicatedVoters() throws SQLException {
        DBConnection.printVoterCounts();
    }
}
