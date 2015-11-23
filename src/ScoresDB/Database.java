package ScoresDB;

import data.Score;
import data.Tuple;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by Eric on 11/23/2015.
 */
public class Database {
    private Connection con;
    private Statement stat;
    private ObservableList<Score> scores;


    public void load_db() throws SQLException {
        openConnection();
        String cmd = "CREATE TABLE IF NOT EXISTS SCORES (Name VARCHAR, Date VARCHAR, Score VARCHAR);";
        execute(cmd);
        populate_lists();
        closeConnection();
    }

    public void populate_lists() throws SQLException {
        openConnection();
        ResultSet rs = stat.executeQuery( "SELECT * FROM SCORES DESC LIMIT 10;" );
        while(rs.next()){
            scores.add(new Score(rs.getString("Name"), rs.getString("Score"), rs.getString("Date")));
        }
        closeConnection();
    }
    private void openConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection("jdbc:sqlite:story.db");
        stat = con.createStatement();
    }
    private void closeConnection() throws SQLException{
        stat.close();
        con.close();
    }
    private void execute(String cmd) throws SQLException {
        openConnection();
        System.out.println(cmd);
        stat.execute(cmd);
        closeConnection();
    }
    public ObservableList getScores(){
        return scores;
    }
}
