# android-speech-vad-demo


## 环境及原理
### 简介

项目地址： https://github.com/vesung/android-speech-vad-demo

集成 [webrtc](https://webrtc.org/) 开源项目，vad模块，具体算法*GMM*(Gaussian Mixture Model)。

此demo可以粗略地按照静音切分音频。

### 原理

一个FRAME时长默认10s，通过webrtc的vad计算这个FRAME是否是活动（ACTIVE: 有声音， INACTIVE，静音）。

这样可以获得音频的所有FRAME的活动值（ACTIVE或者INACTIVE）。从而在静音（INACTIVE）的音频段上的切分音频。

### 运行环境

- android studio 3.0以上,
- ndk 17.1以上（使用android studio sdkmanager 进行安装）
- cmake（使用android studio sdkmanager 进行安装）
- 任意操作系统



## 运行及结果

### 准备工作

克隆或者下载本项目，导入Android studio工具
拷贝 /pcm/16k_1.pcm 文件到android手机目录 /sdcard/cmakedemo/pcm
在android手机创建目录/sdcard/cmakedemo/output_pcm

### 准备好后直接运行工程即可

### 结果
运行成功手机上会显示切分后的文件列表
文件名如下
```
16k_1.pcm_0-12989_A.pcm         // 第0-12989毫秒的音频， 
16k_1.pcm_33730-47389_A.pcm     // 第33730-47389毫秒的音频
16k_1.pcm_0-4049_A.pcm         // A 表示此段有声音，不是完全的静音
···
16k_1.pcm_114060-121689_I.pcm  // I 表示此段都是静音
```

## 参数设置

### common.h
```c
// MULTI = 1 2 3 webrtc vad可以设置分别为以10ms 20ms 30ms作为包
#define MULTI 1

// VAD 模式 Aggressiveness mode (0, 1, 2, or 3). 数值越大，判断越是粗略，连着的静音或者响声增多
#define WEBRTC_VAD_MODE 3

// 有声音的音频段，xxx 毫秒后，认为该音频段结束，不能为0
#define FILE_CUT_SILENCE_AFTER_ACTIVE_MS 500

// 静音的音频段，切分xxx毫秒给之后有声音的音频段
#define FILE_CUT_SILENCE_BEFORE_ACTIVE_MS 300

// 最小的音频切分长度，即每个切分后音频文件不少于这个时长，最后一个除外
#define FILE_CUT_MIN_MS  (10 * 1000)

// 最大的音频切分长度，即每个切分后音频文件不多于这个时长
#define FILE_CUT_MAX_MS  (60 * 1000)

/** 上述示例中音频文件尽可能依次满足如下条件
1. 最短10s，最长 60s。 
2. 每个音频文件的最后的静音500ms， 
2. 每个音频文件的开始的静300ms。
 */
```

### 切割逻辑修改

demo因为考虑到流式，采用了尽快切割的方法。如果您对这个切割算法不满意的话，可以对照periods_print的结果, 自行修改file_cut.c内的代码逻辑

```c
// [5150, 5220) [5230, 6380) [6520, 6970) [7000, 8040) [8080, 8670)
// 表示 [5150, 5220) 等区间内是有声音的，其它区间均为静音，
// 如果需要切割的话，应该在静音区间选择合适的位置切割
```