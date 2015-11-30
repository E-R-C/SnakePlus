package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Eric on 11/23/2015.
 */
public class Score {
    private String score, name, date;
    public Score(String name, String score, String date){
        this.score = score;
        this.name = name;
        this.date = name;
    }

    public StringProperty getDate() {
        return new SimpleStringProperty(date);
    }

    public StringProperty getName() {
        return new SimpleStringProperty(name);
    }

    public StringProperty getScore() {
        return new SimpleStringProperty(score);
    }

}
