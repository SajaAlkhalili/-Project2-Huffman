package com.example.project2huffman;

import com.example.project2huffman.HeapNode;

import java.io.DataInputStream;
import java.io.InputStream;

public class Header {
    String numbers = "";
    String charactors = "";
    String all = "";
    HeapNode root;
    int numOfAddZero;
    int headerSize;
    String fileExt = "";
    DataInputStream fis;
    HeapNode headerTree;
    String bin = "";
    String chars = "";

    public Header() {

    }

    public Header(HeapNode root) {
        this.root = root;

    }

    public InputStream getFis() {
        return fis;
    }

    public void setFis(DataInputStream fis) {
        this.fis = fis;
    }
    //build header tree from huffman code
    public String headerTree(HeapNode root) {

        if (root != null) {

            if (root.left == null && root.right == null) {
                numbers = numbers + "1";
                charactors = charactors + (char) root.charValue;
                all = all + "1" + (char) root.charValue;
                return all;

            }
            numbers += "0";
            all += "0";
            headerTree(root.left);
            headerTree(root.right);

        }
        return all;

    }
    //read the header from the compressed file
    public void readHeader() throws Exception {
        //read the length of the extension of the file
        int x = fis.read();
        byte[] buffer = new byte[x];
        //read the extension of the file
        fis.read(buffer);
        for (int i = 0; i < x; i++)
            fileExt += (char) buffer[i];
        //read the size of the number in the header in bytes
        int numInBytes = fis.read();
        //read the size of the numbers in the header in bits
        int numInBits = fis.readInt();

        //read the numbers from the compressed file and convert it to binary string
        buffer = new byte[numInBytes];
        fis.read(buffer);
        String binCode = "";

        for (int i = 0; i < buffer.length; i++) {
            int ch = buffer[i];
            if (ch < 0)
                ch = 256 + ch;
            binCode = Integer.toBinaryString(ch);
            while (binCode.length() < 8)
                binCode = "0" + binCode;

            bin += binCode;
            //to delete the added zeroes
            if (bin.length() > numInBits) {
                bin = bin.substring(0, numInBits);
            }
        }

        int numChar = fis.read();
        this.headerSize = numInBytes + numChar;
        buffer = new byte[numChar + 1];
        fis.read(buffer);
        for (int i = 0; i < buffer.length; i++) {
            chars += (char) buffer[i];
        }
        //read the number of added zeroes
        this.numOfAddZero = fis.read();

    }

    int j = 0, index = 0;

    //build the tree that read from the header from compressed file

    public void buildHeaderTree(String bin, String Chars, HeapNode headTree) {
        if (j == bin.length())
            return;
        //Initialize the tree
        if (this.headerTree == null) {
            this.headerTree = new HeapNode();
            j++;
            buildHeaderTree(bin, Chars, this.headerTree);

        } else {
            //if the character is 1 that means it is leaf
            if (bin.charAt(j) == '1') {
                HeapNode temp = new HeapNode();
                j++;
                //if the left null store the node on the left and store the value of byte there
                if (headTree.left == null) {
                    temp.charValue = Chars.charAt(index++);
                    temp.data = 1;
                    headTree.left = temp;
                    //if the left is not null store the node in the right and store the value of the byte there
                } else {
                    temp.charValue = Chars.charAt(index++);
                    temp.data = 1;
                    headTree.right = temp;

                }
                //if the right null call the method
                if (headTree.right == null)
                    buildHeaderTree(bin, Chars, headTree);

                //if the character is zero that means the node is not leaf
            } else if (bin.charAt(j) == '0') {
                HeapNode temp = new HeapNode();
                j++;

                //if the left is null put the character node in the left of the current node
                if (headTree.left == null) {
                    headTree.left = temp;

                    if (headTree.left != null)
                        buildHeaderTree(bin, Chars, headTree.left);
                    if (headTree.right == null)
                        buildHeaderTree(bin, Chars, headTree);

                    //if the left is not null put the character node in the right and call the method at the right node of the current node
                } else {
                    headTree.right = temp;
                    buildHeaderTree(bin, Chars, headTree.right);
                }

            }
        }

    }
    public String traverse(HeapNode root) {

        if (root != null) {

            if (root.left == null && root.right == null) {
                all = all + "1"+(char)root.charValue;
                return all;

            }

            all+="0";
            headerTree(root.left);
            headerTree(root.right);


        }
        return all;

    }


}
