package com.github.vesung.vadsdk.asr;

/**
 * Created by wangjing.dc on 2018/9/12.
 */

class PCMFile {
    private String fileName;
    private byte[] fileContent;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}
