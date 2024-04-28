package com.example.project2huffman;

public class TableNode {
    int dataChar;
    int freq;
    int lenBits;
    String huffCode;

    public TableNode() {
        this.dataChar = 0;
        this.freq = 0;
        this.lenBits = 0;
        this.huffCode = "";
    }

    public TableNode(int dataChar, int freq, String huffCode, int lenBits) {
        this.dataChar = dataChar;
        this.freq = freq;
        this.huffCode = huffCode;
        this.lenBits = lenBits;
    }

    public int getDataChar() {
        return dataChar;
    }

    public void setDataChar(int dataChar) {
        this.dataChar = dataChar;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getLenBits() {
        return lenBits;
    }

    public void setLenBits(int lenBits) {
        this.lenBits = lenBits;
    }

    public String getHuffCode() {
        return huffCode;
    }

    public void setHuffCode(String huffCode) {
        this.huffCode = huffCode;
    }
}
