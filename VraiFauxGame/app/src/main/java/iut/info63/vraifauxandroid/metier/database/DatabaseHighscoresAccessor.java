package iut.info63.vraifauxandroid.metier.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import iut.info63.vraifauxandroid.metier.Highscore;

/**
 * Created by Nawhal on 14/03/2016.
 */
public class DatabaseHighscoresAccessor implements IHighscoresGetter {
    private DataBaseHelper dataBaseHelper;

    public DatabaseHighscoresAccessor(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public Highscore getByRank(int rank) {
        dataBaseHelper.openReadable();

        String[] projection = {
                FeedReaderContract.HighScoresFeedEntry.FIRST_COLUMN_NAME,
                FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_NAME,
                FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_NAME
        };

        Cursor c = dataBaseHelper.getDatabase().query(
                FeedReaderContract.HighScoresFeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                FeedReaderContract.HighScoresFeedEntry.FIRST_COLUMN_NAME + "=" + rank,
                null,
                null,
                null,
                null                                 // The sort order
        );

        c.moveToFirst();
        return new Highscore(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_NAME)),
                Integer.parseInt(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_NAME))));
    }

    @Override
    public List<Highscore> getAll() {
        dataBaseHelper.openReadable();

        String[] projection = {"*"};

        Cursor c = dataBaseHelper.getDatabase().query(
                FeedReaderContract.HighScoresFeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,
                null,
                null,
                null,
                null                                 // The sort order
        );

        c.moveToFirst();

        List<Highscore> HighscoreList = new ArrayList<>();

        while (!c.isAfterLast()) {
            HighscoreList.add(new Highscore(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_NAME)),
                    Integer.parseInt(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_NAME)))));
            c.moveToNext();
        }

        return HighscoreList;
    }

    @Override
    public void put(Highscore highscore) {
        dataBaseHelper.openWritable();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_NAME, highscore.getName());
        values.put(FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_NAME, highscore.getScore());

        dataBaseHelper.getDatabase().insert(
                FeedReaderContract.HighScoresFeedEntry.TABLE_NAME,
                null,
                values);
    }

    @Override
    public void putAll(List<Highscore> highscoreList) {
        dataBaseHelper.openWritable();
        ContentValues values;

        for (Highscore highscore : highscoreList) {
            values = new ContentValues();
            values.put(FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_NAME, highscore.getName());
            values.put(FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_NAME, highscore.getScore());

            dataBaseHelper.getDatabase().insert(
                    FeedReaderContract.HighScoresFeedEntry.TABLE_NAME,
                    null,
                    values);
        }
    }
}