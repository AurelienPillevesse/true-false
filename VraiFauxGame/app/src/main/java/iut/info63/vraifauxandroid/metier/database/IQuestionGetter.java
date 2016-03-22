package iut.info63.vraifauxandroid.metier.database;

import java.util.List;

import iut.info63.vraifauxandroid.metier.Question;

/**
 * Created by Nawhal on 14/03/2016.
 */
public interface IQuestionGetter {

    public Question getByIndex(int index);
    public List<Question> getAll();
    public void put(Question question);
    public void putAll(List<Question> questionList);
    public int count();
}
