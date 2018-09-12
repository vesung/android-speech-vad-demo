package com.github.vesung.speechvaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.vesung.speechvaddemo.asr.AsrListener;
import com.github.vesung.speechvaddemo.asr.AsrLiveEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DemoActivity extends AppCompatActivity {

    private AsrListener asrListener = new AsrListener(){

        @Override
        public void onResult(String result) {
            Log.i("识别结果", result);
            Message msg = new Message();
            msg.obj = result;
            uiHandler.sendMessage(msg);
        }

        @Override
        public void liveBegin() {

        }

        @Override
        public void liveEnd() {
            Message msg = new Message();
            msg.obj = "识别完成";
            uiHandler.sendMessage(msg);
        }
    };

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
                tv.setText(tv.getText() + "\n" + msg.obj);
        }
    };


    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tv = (TextView) findViewById(R.id.textView);

        doLiveAsr();
    }

    private void doLiveAsr() {

        AsrLiveEngine liveEngine = new AsrLiveEngine.Builder()
                .listener(asrListener)
                .api("http://192.168.4.51:8080/asr/baiduresp")
                .build();


        try {
            InputStream is = new FileInputStream("/sdcard/cmakedemo/pcm/16k_1.pcm");
            liveEngine.live(is);
            is.close();
            Message msg = new Message();
            msg.obj = "文件读取完成";
            uiHandler.sendMessage(msg);
//            liveEngine.liveClose();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
