package com.docmgmt.dto;

public class DTOFolderSize
{
    long sizeBytes=0;
    double sizeKBytes=0;
    double sizeMBytes=0;
    double sizeGBytes=0;
    int noOfFiles=0;
    int noOfFolders=0;

    public int getNoOfFiles() {
        return noOfFiles;
    }

    public void setNoOfFiles(int noOfFiles) {
        this.noOfFiles = noOfFiles;
    }

    public int getNoOfFolders() {
        return noOfFolders;
    }

    public void setNoOfFolders(int noOfFolders) {
        this.noOfFolders = noOfFolders;
    }
    
    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public double getSizeGBytes() {
        return sizeGBytes;
    }

    public void setSizeGBytes(double sizeGBytes) {
        this.sizeGBytes = sizeGBytes;
    }

    public double getSizeKBytes() {
        return sizeKBytes;
    }

    public void setSizeKBytes(double sizeKBytes) {
        this.sizeKBytes = sizeKBytes;
    }

    public double getSizeMBytes() {
        return sizeMBytes;
    }

    public void setSizeMBytes(double sizeMBytes) {
        this.sizeMBytes = sizeMBytes;
    }
}