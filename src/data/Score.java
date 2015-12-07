package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Eric on 11/23/2015.
 */
public class Score {
    private StringProperty score, name, date;
    public Score(String name, String score, String date){
        this.score = new SimpleStringProperty(score);
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(name);
    }

    public StringProperty getDate() {
        return date;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getScore() {
        return score;
    }
    
    public String getDateUnpacked() {
    	return date.get();
    }
    
    public String getNameUnpacked() {
    	return name.get();
    }
    
    public String getScoreUnpacked() {
    	return score.get();
    }

}
