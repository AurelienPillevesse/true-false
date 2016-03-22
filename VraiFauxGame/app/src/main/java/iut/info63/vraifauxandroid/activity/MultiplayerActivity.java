package iut.info63.vraifauxandroid.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Manifest;

import iut.info63.vraifauxandroid.R;

public class MultiplayerActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    ListView mLvDevice;
    TextView mTvDevice;
    ArrayAdapter<String> mArrayAdapter;
    Button mSearchDevice, mStopSearch,  mBtConnect;
    String address;
    private BluetoothService mBluetoothService = null;
    private static final int REQUEST_ENABLE_BT = 3;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mLvDevice.setAdapter(mArrayAdapter);
            }
        }
    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Context activity = getApplicationContext();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.d("TEST", "State = Connected to");
                            Toast.makeText(activity, "STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d("TEST", "State = Connecting");
                            Toast.makeText(activity, "STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothService.STATE_LISTEN:
                            Log.d("TEST", "State = Listening");
                        case BluetoothService.STATE_NONE:
                            Log.d("TEST", "State = Not connected");
                            Toast.makeText(activity, "STATE_NONE ou STATE_LISTEN", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    if (null != activity) {
                        Toast.makeText(activity, "Connected", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        mSearchDevice = (Button) findViewById(R.id.b_search);
        mLvDevice = (ListView) findViewById(R.id.lv_devices);
        mTvDevice = (TextView) findViewById(R.id.tv_devices);
        mStopSearch = (Button) findViewById(R.id.b_stop);
        mBtConnect = (Button)findViewById(R.id.b_connect);

        
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(MultiplayerActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MultiplayerActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

        setupLogic();
    }

    private void setupLogic() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        if (mBluetoothService == null) {
            setupGame();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            Toast.makeText(MultiplayerActivity.this, "Appareil lié par Bluetooth", Toast.LENGTH_LONG).show();
            mLvDevice.setAdapter(mArrayAdapter);
        }

        mSearchDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
                mBluetoothAdapter.enable();
                mBluetoothAdapter.startDiscovery();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter);

                mLvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mBluetoothAdapter.cancelDiscovery();
                        String info = mLvDevice.getItemAtPosition(position).toString();
                        address = info.substring(info.length() - 17);
                        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                        mBtConnect.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        mBtConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                mBluetoothService.connect(device);
            }
        });

        mStopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBluetoothAdapter.isDiscovering())
                unregisterReceiver(mReceiver);
            }
        });
    }

    private void setupGame() {
        mBluetoothService = new BluetoothService(getApplicationContext(), mHandler);
    }

    @Override
    protected void onDestroy() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter.isEnabled()) {

            if(mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }

            unregisterReceiver(mReceiver);
            mBluetoothAdapter.disable();
        }

        if (mBluetoothService != null) {
            mBluetoothService.stop();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_ENABLE_BT)
            return;

        if (resultCode == RESULT_OK) {
            Toast.makeText(MultiplayerActivity.this, "Bluetooth activé", Toast.LENGTH_SHORT).show();
            setupGame();
        } else {
            Toast.makeText(MultiplayerActivity.this, "Bluetooth non activé", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
