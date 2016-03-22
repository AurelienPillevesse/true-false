package iut.info63.vraifauxandroid.metier;

import java.io.Serializable;

import iut.info63.vraifauxandroid.metier.database.DataBaseHelper;

/**
 * Created by Aurélien on 21/03/2016.
 */
public abstract class IGameManager implements Serializable{

    public abstract int getCompteurGoodAnswer();

    public abstract int getCompteurBadAnswer();

    public abstract void setCompteurGoodAnswer(int value);

    public abstract void setCompteurBadAnswer(int value);

    public abstract void randomQuestion(DataBaseHelper dbh);

    public abstract String getCurrentQuestion();

    public abstract Boolean getCurrentAnswer();

    public abstract boolean verifyAnswer(boolean answer);

}
