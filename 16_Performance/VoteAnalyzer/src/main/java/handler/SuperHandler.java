package handler;

import model.Voter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import repository.DBConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SuperHandler extends DefaultHandler {

    private StringBuilder sqlRequest = new StringBuilder();
    private Voter voter;
    private static DateTimeFormatter birthDayFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private int counter = 0;

    @Override
    public void startDocument() {
        sqlRequest.append("INSERT INTO voter_count(name, birthDate) VALUES");
    }

    @Override
    public void endDocument() {
        if (counter > 0)
            finishSQLRequest();
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
                LocalDate birthDay = LocalDate.parse(attributes.getValue("birthDay"), birthDayFormat);
                voter = new Voter(attributes.getValue("name"), birthDay);
            } else if (qName.equals("visit") && voter != null) {
                counter++;
                sqlRequest.append("('").append(voter.getName()).append("', '")
                        .append(birthDayFormat.format(voter.getBirthDay())).append("'), ");
                if (counter >= 100000) {
                    finishSQLRequest();
                    DBConnection.writeVoters(sqlRequest);
                    sqlRequest.setLength(0);
                    sqlRequest.append("INSERT INTO voter_count(name, birthDate) VALUES");
                    counter = 0;
                }
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

    public void finishSQLRequest() {
        sqlRequest.deleteCharAt(sqlRequest.length() - 1);
        sqlRequest.deleteCharAt(sqlRequest.length() - 1);
        sqlRequest.append(";");
    }
}
