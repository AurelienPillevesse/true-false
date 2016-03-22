package iut.info63.vraifauxandroid.metier;

/**
 * Created by Nawhal on 14/03/2016.
 */
public class Highscore {

    private String name;
        public String getName() { return name; }
    private int score;
        public int getScore() { return score; }

    public Highscore(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    public Highscore(int score)
    {
        this("Anonyme", score);
    }

}
