package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Eric on 11/23/2015.
 */
public class Score {
    private StringProperty score, name, date;
    public Score(String score, String name){
        this.score = new SimpleStringProperty(score);
        this.name = new SimpleStringProperty(name);
    }


    public StringProperty getName() {
        return name;
    }

    public StringProperty getScore() {
        return score;
    }


}
