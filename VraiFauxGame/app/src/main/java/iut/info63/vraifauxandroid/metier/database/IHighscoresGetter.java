package iut.info63.vraifauxandroid.metier.database;

import java.util.List;

import iut.info63.vraifauxandroid.metier.Highscore;
import iut.info63.vraifauxandroid.metier.Question;

/**
 * Created by Nawhal on 14/03/2016.
 */
public interface IHighscoresGetter {

    public Highscore getByRank(int rank);
    public List<Highscore> getAll();
    public void put(Highscore highscore);
    public void putAll(List<Highscore> highscoreList);
}
