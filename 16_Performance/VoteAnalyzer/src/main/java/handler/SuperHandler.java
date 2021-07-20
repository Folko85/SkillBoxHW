package handler;

import model.Voter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import repository.DBConnection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SuperHandler extends DefaultHandler {

    private StringBuilder sqlRequest = new StringBuilder();
    private Voter voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public void startDocument() {
        sqlRequest.append("INSERT INTO voter_count(name, birthDate) VALUES");
    }

    @Override
    public void endDocument() {
        sqlRequest.deleteCharAt(sqlRequest.length() - 1);
        sqlRequest.deleteCharAt(sqlRequest.length() - 1);
        sqlRequest.append(";");
        try {
            DBConnection.writeVoters(sqlRequest);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthDay);
            } else if (qName.equals("visit") && voter != null) {
                    sqlRequest.append("('").append(voter.getName()).append("', '")
                            .append(birthDayFormat.format(voter.getBirthDay())).append("'), ");
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
