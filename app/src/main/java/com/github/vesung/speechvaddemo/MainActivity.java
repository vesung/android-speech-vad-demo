package com.github.vesung.speechvaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.widget.TextView;

import com.github.vesung.speechvaddemo.bean.CutedFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    // MULTI = 1 2 3 webrtc vad可以设置分别为以10ms 20ms 30ms作为包
    static final int MULTI = 1;
    static final int framesize = 160 * MULTI;

    TextView tv;

    private ArrayBlockingQueue<CutedFile> writeFileQueen = new ArrayBlockingQueue(2000);


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
                tv.setText(tv.getText() + "\n" + msg.obj);
        }
    };
    private final Thread writeFileThread = new Thread(){
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        private String post(String url, String content){
            FormBody fbody = new FormBody.Builder()
                    .add("pcm", content)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(fbody)
                    .build();
            try {
                Response res = client.newCall(request).execute();
                return res.body().string();
            } catch (IOException e) {
                Log.e("MainActivity", "通讯失败", e);
            }
            return null;
        }



        @Override
        public void run() {
            while (true){
                try {
                    CutedFile data = writeFileQueen.take();
                    if(data.getFileName().endsWith("_I.pmc")){
                        Log.i("mainActivity", "静音部分不用识别" + data.getFileName());
                        continue;
                    }

                    List<Byte> content = data.getFileContent();

                    byte[] buffer = new byte[content.size()];
                    for(int index=0; index<content.size(); index++){
                        buffer[index] = content.get(index);
                    }

                    try{
                        String b64 = Base64.encodeToString(buffer, Base64.DEFAULT);
                        String msg = this.post("http://192.168.4.51:8080/asr/baiduresp", b64);
                        Log.i("000000", msg);

                        Message uimsg = new Message();
                        uimsg.obj = msg;
                        mHandler.sendMessage(uimsg);
                    }catch (Exception e){
                        Log.e("...............", "========", e);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public MainActivity(){
        writeFileThread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vad_demo();

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    private void vad_demo() {
        try {
            this.vadOpen();

            File file = new File("/sdcard/cmakedemo/pcm/16k_1.pcm");
            FileInputStream is = new FileInputStream(file);
            byte[] buffer = new byte[framesize * 2];
            List<Byte> listBuffer = new ArrayList<>();

            while(true) {
                int read = is.read(buffer);
                if (read == -1)
                    break;

                addFrameList(listBuffer, buffer);
                String ret = this.vadProcessFrame(buffer);
                if(! ret.equals("0")){
                    CutedFile cfile = new CutedFile();
                    cfile.setFileName(ret);
                    cfile.setFileContent(listBuffer);
                    writeFileQueen.put(cfile);

                    listBuffer = new ArrayList<>();
                }
            }

            this.vadClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFrameList(List<Byte> listBuffer, byte[] buffer) {
        for(int i=0; i<buffer.length; i++){
            listBuffer.add(buffer[i]);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
//
//    public native void vadOpen() throws Exception;
//
//    public native void vadClose() throws Exception;
//
//    public native String vadProcessFrame(byte[] frame);

    public  String stringFromJNI(){
        return "";
    }
    public void vadOpen(){

    }
    public void vadClose(){

    }
    public String vadProcessFrame(byte[] buffer){
        return "";
    }


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
