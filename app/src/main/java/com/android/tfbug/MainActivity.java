package com.android.tfbug;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TFBug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load input data
        byte[] inputData = loadFile("input.dat");

        // Just show model md5
        loadFile("model.tflite");

        // Load model
        ModelRunner modelRunner = new ModelRunner(getAssets(), "model.tflite");

        // Run model
        modelRunner.runModel(inputData);
    }

    byte[] loadFile(String filename)
    {
        try {
            InputStream is = getAssets().open(filename);
            int length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            Log.i(TAG, String.format("File %s md5: %s", filename, getMD5(data)));
            return data;
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public  String getMD5(byte[] source) {
        StringBuilder sb = new StringBuilder();
        java.security.MessageDigest md5 = null;
        try {
            md5 = java.security.MessageDigest.getInstance("MD5");
            md5.update(source);
        } catch (Exception e) {
        }
        if (md5 != null) {
            for (byte b : md5.digest()) {
                sb.append(String.format("%02X", b));
            }
        }
        return sb.toString().toLowerCase();
    }

}