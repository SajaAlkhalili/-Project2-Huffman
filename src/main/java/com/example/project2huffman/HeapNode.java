package com.example.project2huffman;

public class HeapNode {

        int data;
        int charValue;
        String hufCode;
        HeapNode left;
        HeapNode right;
        int leftRow;
        int rightRow;

        public HeapNode() {
            this.data = 0;
            this.left = this.right = null;
            this.hufCode = "";
            this.leftRow = 0;
            this.rightRow = 1;

        }
}
