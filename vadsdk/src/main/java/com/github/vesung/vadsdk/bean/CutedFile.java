package com.github.vesung.vadsdk.bean;

import java.util.List;

/**
 * Created by exlink on 2018/9/10.
 */

public class CutedFile {
    private String fileName;
    private List<Byte> fileContent;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Byte> getFileContent() {
        return fileContent;
    }

    public void setFileContent(List<Byte> fileContent) {
        this.fileContent = fileContent;
    }
}
