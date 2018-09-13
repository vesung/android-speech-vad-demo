package com.github.vesung.vadsdk.asr;

/**
 * 语音识别事件监听
 * Created by wangjing.dc on 2018/9/12.
 */

public interface AsrListener {

    void onResult(String result);

    void liveBegin();

    void liveEnd();
}
