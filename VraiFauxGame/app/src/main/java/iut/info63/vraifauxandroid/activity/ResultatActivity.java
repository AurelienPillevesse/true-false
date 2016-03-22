package iut.info63.vraifauxandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iut.info63.vraifauxandroid.R;

public class ResultatActivity extends AppCompatActivity {

    Bundle extras;
    TextView mTvCmptGoodAnswer;
    Button mBtRestart, mBtStartMenu;
    int compteurGoodAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        extras = getIntent().getExtras();
        if (extras != null) {
            compteurGoodAnswer = extras.getInt("goodanswer");
        }

        mTvCmptGoodAnswer = (TextView)findViewById(R.id.tv_cmptGoodAnswer);
        mBtRestart = (Button)findViewById(R.id.b_restart);
        mBtStartMenu = (Button)findViewById(R.id.b_returnStartMenu);

        mBtRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mBtStartMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mTvCmptGoodAnswer.setText(String.valueOf(compteurGoodAnswer));
    }
}
