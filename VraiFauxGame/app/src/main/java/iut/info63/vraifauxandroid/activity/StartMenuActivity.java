package iut.info63.vraifauxandroid.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.Set;

import iut.info63.vraifauxandroid.R;

public class StartMenuActivity extends AppCompatActivity {

    Button mButtonStart, mButtonMultiplayer, mButtonCredits;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (getResources().getConfiguration().orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_UNDEFINED:
                setContentView(R.layout.activity_startmenu);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.landscape_startmenu);
                break;
        }

        mButtonStart = (Button)findViewById(R.id.b_play);
        mButtonMultiplayer = (Button)findViewById(R.id.b_multiplayer);
        mButtonCredits = (Button)findViewById(R.id.b_credits);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            mButtonMultiplayer.setVisibility(View.INVISIBLE);
        } else {
            mButtonMultiplayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MultiplayerActivity.class);
                    startActivity(intent);
                }
            });
        }

        mButtonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditsActivity.class);
                startActivity(intent);
            }
        });
    }
}
