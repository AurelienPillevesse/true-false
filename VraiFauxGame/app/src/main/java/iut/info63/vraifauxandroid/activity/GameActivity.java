package iut.info63.vraifauxandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import iut.info63.vraifauxandroid.R;
import iut.info63.vraifauxandroid.metier.GameManager;
import iut.info63.vraifauxandroid.metier.IGameManager;
import iut.info63.vraifauxandroid.metier.Question;
import iut.info63.vraifauxandroid.metier.database.DataBaseHelper;
import iut.info63.vraifauxandroid.metier.database.DatabaseQuestionAccessor;
import iut.info63.vraifauxandroid.metier.database.FakeQuestionAccessor;

public class GameActivity extends AppCompatActivity {

    private ImageView mIvHeart1, mIvHeart2, mIvHeart3;
    private Button mButtonTrue, mButtonFalse, mButtonNextQuestion, mButtonScore;
    private TextView mTvQuestion, mTvResult;

    private IGameManager mGameManager;
    private DataBaseHelper dbh;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("game", mGameManager);
        if(mButtonNextQuestion.getVisibility() == View.VISIBLE || mButtonScore.getVisibility() == View.VISIBLE) {
            outState.putBoolean("repondu", true);
            outState.putString("stringreponse", mTvResult.getText().toString());
        } else {
            outState.putBoolean("repondu", false);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try {
            dbh = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mButtonTrue = (Button)findViewById(R.id.b_true);
        mButtonFalse = (Button)findViewById(R.id.b_false);
        mButtonNextQuestion = (Button)findViewById(R.id.b_next);
        mButtonScore = (Button)findViewById(R.id.b_score);
        mTvResult = (TextView)findViewById(R.id.tv_result);
        mIvHeart1 = (ImageView)findViewById(R.id.iv_heart1);
        mIvHeart2 = (ImageView)findViewById(R.id.iv_heart2);
        mIvHeart3 = (ImageView)findViewById(R.id.iv_heart3);

        mTvQuestion = (TextView)findViewById(R.id.tv_question);

        if(savedInstanceState != null) {
            Log.d("TEST", savedInstanceState.toString());
            mGameManager = (IGameManager) savedInstanceState.getSerializable("game");

            mTvQuestion.setText(mGameManager.getCurrentQuestion());
            switch (mGameManager.getCompteurBadAnswer()) {
                case 1:
                    mIvHeart3.setImageResource(R.drawable.emptyheart);
                    break;
                case 2:
                    mIvHeart3.setImageResource(R.drawable.emptyheart);
                    mIvHeart2.setImageResource(R.drawable.emptyheart);
                    break;
                case 3:
                    mIvHeart3.setImageResource(R.drawable.emptyheart);
                    mIvHeart2.setImageResource(R.drawable.emptyheart);
                    mIvHeart1.setImageResource(R.drawable.emptyheart);
                    break;
            }

            if(savedInstanceState.getBoolean("repondu")) {
                mButtonTrue.setVisibility(View.INVISIBLE);
                mButtonFalse.setVisibility(View.INVISIBLE);
                if(mGameManager.getCompteurBadAnswer() == 3) {
                    mButtonScore.setVisibility(View.VISIBLE);
                } else {
                    mButtonNextQuestion.setVisibility(View.VISIBLE);
                }
                mTvResult.setText(savedInstanceState.getString("stringreponse"));
            }
        } else {
            mGameManager = new GameManager();
            displayQuestion();
        }

        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonTrue.setVisibility(View.INVISIBLE);
                mButtonFalse.setVisibility(View.INVISIBLE);

                modificationAboutAnswer(true);

                if(mGameManager.getCompteurBadAnswer() < 3) {
                    mButtonNextQuestion.setVisibility(View.VISIBLE);
                }
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonTrue.setVisibility(View.INVISIBLE);
                mButtonFalse.setVisibility(View.INVISIBLE);

                modificationAboutAnswer(false);

                if(mGameManager.getCompteurBadAnswer() < 3) {
                    mButtonNextQuestion.setVisibility(View.VISIBLE);
                }
            }
        });

        mButtonNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonNextQuestion.setVisibility(View.INVISIBLE);
                mButtonTrue.setVisibility(View.VISIBLE);
                mButtonFalse.setVisibility(View.VISIBLE);

                mTvResult.setText("");

                displayQuestion();
            }
        });

        mButtonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultatActivity.class);
                intent.putExtra("goodanswer", mGameManager.getCompteurGoodAnswer());
                startActivity(intent);
                finish();
            }
        });
    }


    private void modificationAboutAnswer(boolean answer) {
        if(mGameManager.verifyAnswer(answer)) {
            mTvResult.setText("Bonne réponse");
        } else {
            mTvResult.setText("Mauvaise réponse");

            switch (mGameManager.getCompteurBadAnswer()) {
                case 1:
                    mIvHeart3.setImageResource(R.drawable.emptyheart);
                    break;
                case 2:
                    mIvHeart2.setImageResource(R.drawable.emptyheart);
                    break;
                case 3:
                    mIvHeart1.setImageResource(R.drawable.emptyheart);
                    mButtonScore.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


    private void displayQuestion() {
        if(mGameManager.getCompteurBadAnswer() < 3) {
            mGameManager.randomQuestion(dbh);
            mTvQuestion.setText(mGameManager.getCurrentQuestion());
        }
    }
}