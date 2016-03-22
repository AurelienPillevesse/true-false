package iut.info63.vraifauxandroid.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {

    private static final String NAME_SECURE = "VraiFauxSecure";

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;

    public BluetoothService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }

    /**
     * Set l'état actuel de la connexion
     */
    private void setState(int state) {
        mState = state;

        mHandler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    /**
     * Retourne l'état actuel de la connexion
     */
    public int getState() {
        return mState;
    }

    /**
     * Démarre le service Bluetooth (AcceptThread plus particulièrement)
     */
    public void start() {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_LISTEN);

        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread(true);
            mAcceptThread.start();
        }
    }

    /**
     * Démarre ConnectThread pour la connexion à un autre appareil
     */
    public void connect(BluetoothDevice device) {

        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * Démmare ConnectedThread pour commencer à gérer une connexion Bluetooth
     *
     * @param socket BluetoothSocket avec laquelle la connexion a été créée
     * @param device BluetoothDevice qui a été connecté
     */
    public void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType) {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }

        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();

        Message msg = mHandler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }

    /**
     * Stop tous les threads
     */
    public void stop() {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }

        setState(STATE_NONE);
    }

    /**
     * Ecrit au ConnectedThread
     *
     * @param out Bytes à écrire
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    /**
     * Indique que la connexion a échouée et notifie l'activité
     */
    private void connectionFailed() {
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        BluetoothService.this.start();
    }

    /**
     * Indique que la connexion a été perdue et notifie l'activité
     */
    private void connectionLost() {
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        BluetoothService.this.start();
    }

    /**
     * Ce thread attends pour une connexion jusqu'à ce qu'une connexion soit acceptée
     */
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;
        private String mSocketType;

        public AcceptThread(boolean secure) {
            BluetoothServerSocket tmp = null;
            mSocketType = "Secure";

            try {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID);
            } catch (IOException e) {
                Log.d("TEST", "Socket Type: " + mSocketType + "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket = null;

            while (mState != STATE_CONNECTED) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.d("TEST", "Socket Type: " + mSocketType + "accept() failed", e);
                    break;
                }

                if (socket != null) {
                    synchronized (BluetoothService.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                connected(socket, socket.getRemoteDevice(), mSocketType);
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.d("TEST", "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.d("TEST", "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = "Secure";

            try {
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.d("TEST", "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread" + mSocketType);
            mAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.d("TEST", "unable to close() " + mSocketType + " socket during connection failure", e2);
                }
                e.printStackTrace();
                connectionFailed();
                return;
            }

            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d("TEST", "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    /**
     * Ce thread est exécuté quand une connexion est en cours entre deux appareils
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d("TEST", "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.d("TEST", "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.d("TEST", "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);

                    mHandler.obtainMessage(Constants.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    connectionLost();
                    BluetoothService.this.start();
                    break;
                }
            }
        }

        /**
         * Ecrit au OutStream
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Log.d("TEST", "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d("TEST", "close() of connect socket failed", e);
            }
        }
    }
}