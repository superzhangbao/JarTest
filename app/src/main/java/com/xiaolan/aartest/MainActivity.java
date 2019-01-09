package com.xiaolan.aartest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.xiaolan.device.wash.CurrentStatusListener;
import com.xiaolan.device.wash.DeviceAction;
import com.xiaolan.device.wash.DeviceEngine;
import com.xiaolan.device.wash.OnSendInstructionListener;
import com.xiaolan.device.wash.SerialPortReadDataListener;
import com.xiaolan.device.wash.WashStatusEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity implements OnSendInstructionListener, CurrentStatusListener, SerialPortReadDataListener {

    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        try {
            mSerialPort = new SerialPort(new File("/dev/ttyS3"), 9600, 0, "8", "1", "N");
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeviceEngine.getInstance().open(mInputStream,mOutputStream);
        DeviceEngine.getInstance().setOnSendInstructionListener(this);
        DeviceEngine.getInstance().setOnCurrentStatusListener(this);
        DeviceEngine.getInstance().setOnSerialPortConnectListener(this);

        button.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_HOT));
        button2.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_WARM));
        button3.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_COLD));
        button4.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_DELICATES));
        button5.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_SUPER));
        button6.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_START));
        button7.setOnClickListener(v -> DeviceEngine.getInstance().push(DeviceAction.ACTION_KILL));
    }

    @Override
    public void sendInstructionSuccess(int i, WashStatusEvent washStatusEvent) {

    }

    @Override
    public void sendInstructionFail(int i, String s) {

    }

    @Override
    public void currentStatus(WashStatusEvent washStatusEvent) {

    }

    @Override
    public void onSerialPortReadDataSuccess() {

    }

    @Override
    public void onSerialPortReadDataFail(String s) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }
}
