package com.example.project2huffman;


public class Heap {
    HeapNode [] heapArray;
    HeapNode[] newHeapArray;
    int arr[];
    int size;
    int capacity;
    int lenNonZero;

    public Heap() {
        for (int i = 0; i < capacity; i++) {
            heapArray[i] = new HeapNode();
        }

    }

    public Heap(int[] arr) {

        this.arr = arr;
        this.capacity = arr.length;
        this.heapArray = new HeapNode[this.capacity];
        for (int i = 0; i < heapArray.length; i++) {
            heapArray[i] = new HeapNode();
        }
        int j = 0;
        for (int i = 0; i < capacity; i++) {

            if (arr[i] > 0) {
                heapArray[j].charValue = i;
                heapArray[j].data = arr[i];
                j++;
            }
        }

        this.lenNonZero = j;
        this.newHeapArray = new HeapNode[this.lenNonZero];
        for (int i = 0; i < newHeapArray.length; i++) {
            newHeapArray[i] = new HeapNode();
            newHeapArray[i].charValue = heapArray[i].charValue;
            newHeapArray[i].data = heapArray[i].data;
        }

    }

    public void buildHeap(HeapNode[] newHeapArray, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (right < this.size && newHeapArray[right].data < newHeapArray[smallest].data) {

            smallest = right;
        }

        if (left < this.size && newHeapArray[left].data < newHeapArray[smallest].data) {

            smallest = left;
        }

        if (smallest != i) {
            HeapNode small = newHeapArray[i];
            newHeapArray[i] = newHeapArray[smallest];
            newHeapArray[smallest] = small;

            buildHeap(newHeapArray, smallest);
        }

    }

    public void buildingHeap(int arr[]) {

        int heap_size = this.lenNonZero / 2;
        this.size = newHeapArray.length;
        for (int i = heap_size; i >= 0; i--)
            buildHeap(newHeapArray, i);

    }

    public void print() {

        for (int i = 0; i < this.size; ++i) {
            System.out.println("arr[" + this.newHeapArray[i].charValue + "] = " + this.newHeapArray[i].data);

        }

    }

    public void insert(HeapNode element) {
        int i = 0;
        if (isFull()) {
            System.out.println("it is Full!!");
        } else {
            i = size++;
            newHeapArray[i] = element;
            while (i > 0 && newHeapArray[(int) Math.ceil(i / 2.0) - 1].data > newHeapArray[i].data) {
                HeapNode cur = newHeapArray[i];
                newHeapArray[i] = newHeapArray[(int) Math.ceil(i / 2.0) - 1];
                newHeapArray[(int) Math.ceil(i / 2.0) - 1] = cur;
                i = ((int) Math.ceil(i / 2.0) - 1);

            }
        }


    }

    public HeapNode deleteMin(int[] arr) {
        int i = 0, child;
        if (isEmpty()) {
            System.out.println("the heap is empty!!");
        }
        HeapNode min = new HeapNode();
        min.charValue = newHeapArray[0].charValue;
        min.data = newHeapArray[0].data;
        min.left = newHeapArray[0].left;
        min.right = newHeapArray[0].right;
        min.leftRow = newHeapArray[0].leftRow;
        min.rightRow = newHeapArray[0].rightRow;
        int s = (size - 1);
        HeapNode last = newHeapArray[s--];
        for (i = 0; i * 2 + 1 <= s; i = child) {
            child = i * 2 + 1;
            if (child < s && newHeapArray[child + 1].data < newHeapArray[child].data)
                child++;
            if (last.data > newHeapArray[child].data)
                newHeapArray[i] = newHeapArray[child];
            else
                break;

        }
        newHeapArray[i] = last;
        this.size = s + 1;
        return min;

    }

    public boolean isFull() {
        if (size < this.lenNonZero)
            return false;
        else
            return true;
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
    }

}

