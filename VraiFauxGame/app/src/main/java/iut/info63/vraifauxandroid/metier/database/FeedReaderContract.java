package iut.info63.vraifauxandroid.metier.database;

import android.provider.BaseColumns;

/**
 * Created by Nawhal on 04/03/2016.
 */
public final class FeedReaderContract {
    public FeedReaderContract() {}

    public static abstract class QuestionsFeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Questions";
        public static final String FIRST_COLUMN_NAME = "id";
        public static final String FIRST_COLUMN_TYPE = " INTEGER";
        public static final String SECOND_COLUMN_NAME = "Question";
        public static final String SECOND_COLUMN_TYPE = " TEXT";
        public static final String THIRD_COLUMN_NAME = "Answer";
        public static final String THIRD_COLUMN_TYPE = " INTEGER";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedReaderContract.QuestionsFeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME + FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_TYPE +
                            " CONSTRAINT pk_" + FeedReaderContract.QuestionsFeedEntry.FIRST_COLUMN_NAME + " PRIMARY KEY, " +
                    FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_NAME + FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_TYPE +
                            " CONSTRAINT nn_" + FeedReaderContract.QuestionsFeedEntry.SECOND_COLUMN_NAME + " NOT NULL, " +
                    FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_NAME + FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_TYPE +
                            " CONSTRAINT nn_" + FeedReaderContract.QuestionsFeedEntry.THIRD_COLUMN_NAME + " NOT NULL)";
    }

    public static abstract class HighScoresFeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "HighScores";
        public static final String FIRST_COLUMN_NAME = "id";
        public static final String FIRST_COLUMN_TYPE = " INTEGER";
        public static final String SECOND_COLUMN_NAME = "Name";
        public static final String SECOND_COLUMN_TYPE = " TEXT";
        public static final String THIRD_COLUMN_NAME = "Score";
        public static final String THIRD_COLUMN_TYPE = " INTEGER";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedReaderContract.HighScoresFeedEntry.TABLE_NAME + " (" +
                        FeedReaderContract.HighScoresFeedEntry.FIRST_COLUMN_NAME + FeedReaderContract.HighScoresFeedEntry.FIRST_COLUMN_TYPE + " CONSTRAINT pk_index PRIMARY KEY," +
                        FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_NAME + FeedReaderContract.HighScoresFeedEntry.SECOND_COLUMN_TYPE + " CONSTRAINT nn_name NOT NULL," +
                        FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_NAME + FeedReaderContract.HighScoresFeedEntry.THIRD_COLUMN_TYPE + " CONSTRAINT nn_score NOT NULL" +
                        ")" ;
    }
}
