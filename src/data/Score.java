package data;

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

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

}
