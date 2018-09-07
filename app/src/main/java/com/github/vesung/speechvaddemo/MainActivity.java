package com.github.vesung.speechvaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native void vadOpen() throws Exception;

    public native void vadClose() throws Exception;

    public native int vadProcessFrame(byte[] frame);


    void read_int16_bytes(InputStream is, short data) {
        int MULTI = 1; // 1, 2, 3
        int FRAME_SIZE = 160 * MULTI;
        int shortlength = 2;// 2字节，16位

        try {
            byte[] datab = new byte[FRAME_SIZE * shortlength];
            while(is.read() != 0){
                is.read(datab);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
