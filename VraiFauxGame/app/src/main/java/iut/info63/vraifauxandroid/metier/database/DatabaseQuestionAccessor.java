package iut.info63.vraifauxandroid.metier.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import iut.info63.vraifauxandroid.metier.Question;

/**
 * Created by Nawhal on 14/03/2016.
 */
public class DatabaseQuestionAccessor implements IQuestionGetter {

    private DataBaseHelper dataBaseHelper;
    private int biggestIndex;

    public DatabaseQuestionAccessor(DataBaseHelper dataBaseHelper)
    {
        this.dataBaseHelper = dataBaseHelper;
        biggestIndex = getBiggestIndex();
        if (biggestIndex == -1)
        {
            FakeQuestionAccessor fqa = new FakeQuestionAccessor();
            putAll(fqa.getAll());
        }
    }

    @Override
    public Question getByIndex(int index) throws NoSuchElementException {
        Cursor c = getQuery(index);

        if (! c.moveToFirst())
            throw new NoSuchElementException("There is no question at index "+index+".");

        return new Question(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_NAME)),
                Integer.parseInt(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_NAME))) == 1 ? true : false);
    }

    @Override
    public List<Question> getAll() {
        Cursor c = getQuery(-1);

        c.moveToFirst();

        List<Question> questionList = new ArrayList<>();

        while (!c.isAfterLast())
        {
            questionList.add(new Question(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_NAME)),
                    Integer.parseInt(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_NAME))) == 1 ? true : false));
            c.moveToNext();
        }

        return questionList;
    }

    private Cursor getQuery(int index)
    {
        dataBaseHelper.openReadable();

        String[] projection = {
                FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_NAME,
                FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_NAME
        };

        return dataBaseHelper.getDatabase().query(
                FeedReaderContract.QuestionsFeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                index < 0 ? null : FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME + "=" + index,
                null,
                null,
                null,
                null                                 // The sort order
        );
    }

    @Override
    public void put(Question question) {
        dataBaseHelper.openWritable();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME, ++biggestIndex);
        values.put(FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_NAME, question.getQuestion());
        values.put(FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_NAME, question.getAnswer() ? 1 : 0);

        dataBaseHelper.getDatabase().insert(
                FeedReaderContract.QuestionsFeedEntry.TABLE_NAME,
                null,
                values);
    }

    @Override
    public void putAll(List<Question> questionList) {
        dataBaseHelper.openWritable();

        for (Question question : questionList)
        {
            put(question);
        }
    }

    private int getBiggestIndex()
    {
        dataBaseHelper.openReadable();

        String[] projection = {
                "MAX(" + FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME + ")"
        };

        Cursor c = dataBaseHelper.getDatabase().query(
                FeedReaderContract.QuestionsFeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,
                null,
                null,
                null,
                null                                 // The sort order
        );

        if (! c.moveToFirst() || c.getString(c.getColumnIndexOrThrow("MAX(" + FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME + ")")) == null)
            return -1;

        return Integer.parseInt(c.getString(c.getColumnIndexOrThrow("MAX(" + FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME + ")")));
    }

    public int count() {
        return biggestIndex;
    }

    public void testDeleteAll()
    {
        dataBaseHelper.getWritableDatabase().delete(FeedReaderContract.QuestionsFeedEntry.TABLE_NAME, null, null);
        biggestIndex = -1;
    }
}