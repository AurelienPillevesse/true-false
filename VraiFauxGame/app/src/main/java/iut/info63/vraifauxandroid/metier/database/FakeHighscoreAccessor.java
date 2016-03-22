package iut.info63.vraifauxandroid.metier.database;

import java.util.ArrayList;
import java.util.List;

import iut.info63.vraifauxandroid.metier.Highscore;

/**
 * Created by Nawhal on 14/03/2016.
 */
public class FakeHighscoreAccessor implements IHighscoresGetter {

    private List<Highscore> highscores;

    public FakeHighscoreAccessor()
    {
        highscores = new ArrayList<>();
        highscores.add(new Highscore("Jean Paul", 2000));
        highscores.add(new Highscore("Jean Michel Fort", 1500));
        highscores.add(new Highscore("Jean Michel Médiocre", 1000));
        highscores.add(new Highscore("Jean Michel Pas Doué", 500));
        highscores.add(new Highscore(0));
    }

    @Override
    public Highscore getByRank(int rank) {
        return highscores.get(rank);
    }

    @Override
    public List<Highscore> getAll() {
        return highscores;
    }

    @Override
    public void put(Highscore highscore) {
        highscores.add(highscore);
    }

    @Override
    public void putAll(List<Highscore> highscoreList) {
        highscores.addAll(highscoreList);
    }
}
