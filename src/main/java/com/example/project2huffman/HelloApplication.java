package com.example.project2huffman;


import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    int[] arr = new int[256];
    Heap heap = new Heap(arr);
    static TextField browseField = new TextField("enter CHOOSE FILE →");
    TableNode[] table = new TableNode[256];
    Header head;
    int maxLen = 0;
    HeapNode root;

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        Text title = new Text("COMPRESSION & DECOMPRESSION");
        title.setFont(Font.font("Times New Roman", 25));
        title.setStrokeWidth(2);
        title.setStroke(Color.BLACK);
        title.setFill(Color.LIGHTPINK);
        title.setLayoutX(50);
        title.setLayoutY(100);

        browseField.setFocusTraversable(false);
        browseField.setDisable(true);
        //browseField.setStyle(" -fx-font-family: \"Times New Roman\";\r\n" + " -fx-font-size: 20;\r\n"
        //		+ "   -fx-padding: 4,4,4,4;\r\n" + "   -fx-border-color: purple;\r\n" + "   -fx-border-width: 1.5;\r\n"
        //		+ "   -fx-background-color:  linear-gradient(to right, lightpink ,lightyellow);\r\n"
        //		+ " -fx-text-fill: purple;");


        Button browse = new Button("CHOOSE FILE");
        browse.setPrefWidth(150);
        browse.setStyle(
                "-fx-border-color:purple;-fx-background-color:lightyellow");
        browse.setTextFill(Color.PURPLE);
        browse.setFont(new Font("Times New Roman", 20));
        browse.setPadding(new Insets(10));

        HBox browseHBox = new HBox(35);
        browseHBox.getChildren().addAll(browseField, browse);
        browseHBox.setAlignment(Pos.CENTER);


        Button compres = new Button("Compression");
        compres.setPrefWidth(150);
        compres.setDisable(true);
        compres.setPadding(new Insets(10, 10, 10, 10));
        compres.setStyle("-fx-background-color: #b33951; -fx-text-fill: White; -fx-border-width: 2; -fx-border-color: #80283b");
        compres.setFont(Font.font("Georgia", FontWeight.BOLD, 14));

        Button header = new Button("Header");
        header.setPrefWidth(150);
        header.setDisable(true);
        header.setStyle(
                "-fx-border-color:purple;-fx-background-color:lightyellow");
        header.setTextFill(Color.PURPLE);
        header.setFont(new Font("Times New Roman", 20));
        header.setPadding(new Insets(10));

        Button stat = new Button("Table");
        stat.setPrefWidth(150);
        stat.setDisable(true);
        stat.setStyle(
                "-fx-border-color:purple;-fx-background-color:lightyellow");
        stat.setTextFill(Color.PURPLE);
        stat.setFont(new Font("Times New Roman", 20));
        stat.setPadding(new Insets(10));

        HBox comHBox = new HBox(35);
        comHBox.getChildren().addAll(header, stat);
        comHBox.setAlignment(Pos.CENTER);

        Button decompres = new Button(" Decompression");
//        decompres.setStyle(
//                "-fx-border-color:purple;-fx-background-color:lightyellow");
//        decompres.setTextFill(Color.PURPLE);
//        decompres.setFont(new Font("Times New Roman", 20));
//        decompres.setPadding(new Insets(10));
        decompres.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        decompres.setStyle("-fx-background-color: #91c7b1; -fx-text-fill: White;  -fx-border-width: 2; -fx-border-color: #6a9485");

        decompres.setPadding(new Insets(10));


        HBox options = new HBox(35);
        options.getChildren().addAll(compres, decompres);
        options.setAlignment(Pos.CENTER);

        VBox allThing = new VBox(35);
        allThing.getChildren().addAll(browseHBox, options, comHBox);
        allThing.setAlignment(Pos.CENTER);
        allThing.setLayoutX(100);
        allThing.setLayoutY(200);

        Pane pane = new Pane();
        pane.getChildren().addAll(title, allThing);

        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        //browse file to read & compress it
        browse.setOnAction(e -> {

            FileChooser BFile = new FileChooser();
            BFile.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));


            File chooseFile = BFile.showOpenDialog(primaryStage);
            if (chooseFile != null)
                browseField.setText(chooseFile.getAbsolutePath());



            //enable using buttons
            compres.setDisable(false);


            //read array of bytes from the chosen file
            byte[] buffer = new byte[256];
            try {
                File fi=new File(browseField.getText());

                int x=fi.getName().lastIndexOf(".");
                String huf =fi.getName().substring(x+1);

                //to prevent choose huf files
                if(huf.equalsIgnoreCase("huf"))
                    throw new Exception();

                InputStream file = new FileInputStream(fi);

                for (int i = 0; i < arr.length; i++)
                    arr[i] = 0;


                while (file.available() > 0) {// while loop that continues  still bytes available to be read from the file
                    int numBytes = file.read(buffer);
                    for (int i = 0; i < numBytes; i++) {
                        int ch = buffer[i];

                        //if the byte is negative

                        if (ch < 0) {
                            int r = 256 - (-2 * ch);//تحويل البايت السالبة لموجب
                            ch = r + (-1 * ch);
                        }

                        //count the frequency of the each byte
                        arr[ch]++;

                    }
                }

                huffmanCode(arr);

                //show the table that has the byte and its frequency and is huffman code and its length
                stat.setOnAction(r -> {

                    ObservableList<TableNode> data = FXCollections.observableArrayList();
                    for (int i = 0; i < table.length; i++)
                        data.add(table[i]);

                    TableView<TableNode> tableview = new TableView<TableNode>();
                    tableview.setPrefSize(750, 700);
                    tableview.setLayoutX(80);
                    tableview.setLayoutY(90);

                    TableColumn<TableNode, Integer> dataChar = new TableColumn<TableNode, Integer>("dataChar");
                    dataChar.setMinWidth(200);
                    dataChar.setCellValueFactory(new PropertyValueFactory<TableNode, Integer>("dataChar"));

                    TableColumn<TableNode, Integer> Freq = new TableColumn<TableNode, Integer>("Freq");
                    Freq.setMinWidth(200);
                    Freq.setCellValueFactory(new PropertyValueFactory<TableNode, Integer>("Freq"));

                    TableColumn<TableNode, String> HuffCode = new TableColumn<TableNode, String>("HuffCode");
                    HuffCode.setMinWidth(200);
                    HuffCode.setCellValueFactory(new PropertyValueFactory<TableNode, String>("HuffCode"));

                    TableColumn<TableNode, Integer> lenBits = new TableColumn<TableNode, Integer>("lenBits");
                    lenBits.setMinWidth(200);
                    lenBits.setCellValueFactory(new PropertyValueFactory<TableNode, Integer>("lenBits"));

                    tableview.setItems(data);

                    tableview.getColumns().addAll(dataChar, Freq, HuffCode, lenBits);

                    Button backTable = new Button("BACK");
//                    backTable.setLayoutX(800);
//                    backTable.setLayoutY(800);
                    backTable.setAlignment(Pos.TOP_CENTER);
                    backTable.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
                    backTable.setStyle("-fx-background-color: #91c7b1; -fx-text-fill: White;  -fx-border-width: 2; -fx-border-color: #6a9485");
                    backTable.setOnAction(q -> {
                        primaryStage.setScene(scene);
                    });

                    Pane tablePane = new Pane();
                    tablePane.getChildren().addAll(tableview, backTable);

                    Scene tableScene = new Scene(tablePane, 900, 900);
                    primaryStage.setScene(tableScene);
                    primaryStage.show();

                });

                //compress the chosen file

                compres.setOnAction(value -> {
                    try {
                        header.setDisable(false);
                        stat.setDisable(false);
                        writeHuffmanToFile(table, head);
                        head.all="";
                        Alert fileCompressed = new Alert(Alert.AlertType.INFORMATION);
                        fileCompressed.setTitle("Ready File");
                        fileCompressed.setContentText("The Compressed File Is Ready");
                        fileCompressed.show();
                    } catch (Exception e1) {
                        Alert fileCompressed = new Alert(Alert.AlertType.ERROR);
                        fileCompressed.setTitle("ERROR");
                        fileCompressed.setContentText("The File Is Not Correct Or Somthing Wrong");
                        fileCompressed.show();
                    }
                });

                //show the information about the header

                header.setOnAction(value->{
                    head=new Header(root);
                    String str=head.headerTree(root);

                    TextArea headerArea=new TextArea();
                    headerArea.setPrefWidth(500);
                    headerArea.setPrefHeight(500);
                    headerArea.setFont(Font.font(STYLESHEET_CASPIAN, 25));
                    headerArea.setEditable(false);


                    RadioButton headerSize=new RadioButton("the size of header");

                    RadioButton headerTree=new RadioButton("the tree header(Huffman Code)");

                    RadioButton fileExt=new RadioButton("the extention of file");

                    Button back=new Button("BACK");
//                    back.setLayoutX(600);
//                    back.setLayoutY(620);
                    back.setAlignment(Pos.TOP_CENTER);
                    back.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
                    back.setStyle("-fx-background-color: #91c7b1; -fx-text-fill: White;  -fx-border-width: 2; -fx-border-color: #6a9485");
                    ToggleGroup toggle=new ToggleGroup();
                    toggle.getToggles().addAll(headerSize,headerTree,fileExt);

                    HBox headerHbox=new HBox(25);
                    headerHbox.getChildren().addAll(fileExt,headerSize,headerTree);
                    headerHbox.setAlignment(Pos.CENTER);


                    VBox headerVbox=new VBox(35);
                    headerVbox.getChildren().addAll(headerHbox,headerArea);
                    headerVbox.setAlignment(Pos.CENTER);
                    headerVbox.setLayoutX(100);
                    headerVbox.setLayoutY(50);

                    Pane headerPane =new Pane();
                    headerPane.getChildren().addAll(headerVbox,back);

                    back.setOnAction(val->{
                        primaryStage.setScene(scene);
                    });

                    //print the size of the header

                    headerSize.selectedProperty().addListener(val ->{
                        headerArea.setText("The com.example.project2huffman.Header Size = "+(((head.numbers.length()/8)+1)+head.charactors.length()));
                    });

                    int y = fi.getName().lastIndexOf(".");
                    String string = fi.getName().substring(y+1);

                    //print the extension of the chosen file

                    fileExt.selectedProperty().addListener(val ->{
                        headerArea.setText("The File Extension : "+string);
                    });

                    //print the tree of the header that help us to get the symbol of all bytes

                    headerTree.selectedProperty().addListener(val ->{
                        headerArea.setText(str);


                    });

                    Scene headerScene=new Scene(headerPane,700,700);
                    primaryStage.setScene(headerScene);




                });

                file.close();

            } catch (Exception e1) {
                Alert fileCompressed = new Alert(Alert.AlertType.ERROR);
                fileCompressed.setTitle("ERROR");
                fileCompressed.setContentText("The File Is Not Correct Or Somthing Wrong");
                fileCompressed.show();
            }

        });




        //choose file & decompress it
        decompres.setOnAction(val -> {
            try {




                deCompressFile(primaryStage);
                Alert fileCompressed = new Alert(Alert.AlertType.INFORMATION);
                fileCompressed.setTitle("Ready File");
                fileCompressed.setContentText("The Decompressed File Is Ready");
                fileCompressed.show();
            } catch (Exception e1) {
                Alert fileCompressed = new Alert(Alert.AlertType.ERROR);
                fileCompressed.setTitle("ERROR");
                fileCompressed.setContentText("The File Is Not Correct Or Somthing Wrong");
                fileCompressed.show();
            }
        });






    }
    //build the huffman tree

    public void huffmanCode(int[] arr) {
        Heap heap = new Heap(arr);

        //build heap to the nodes of bytes
        heap.buildingHeap(arr);
        root = new HeapNode();


        for (int i = 1; i < heap.lenNonZero; i++) {
            HeapNode z = new HeapNode();
            //delete the two min nodes of the heap
            HeapNode x = heap.deleteMin(arr);
            HeapNode y = heap.deleteMin(arr);

            //insert new node and put the first min in the left and the 2nd in the right
            z.left = x;
            z.right = y;
            //the freq of the inserted node is the addition of the 2 min nodes deleted
            z.data = x.data + y.data;
            root = z;
            heap.insert(z);

        }
        printCode(heap, root, "");
        informationTable(heap, arr);
        head = new Header(root);

    }
    //the information table that has all bytes & its freq & its huffman code & its length
    public void informationTable(Heap heap, int[] arr) {

        for (int i = 0; i < table.length; i++)
            table[i] = new TableNode();
        for (int i = 0; i < table.length; i++) {
            table[i].dataChar = i;
            table[i].freq = arr[i];
            table[heap.heapArray[i].charValue].huffCode = heap.heapArray[heap.heapArray[i].charValue].hufCode;
            table[i].lenBits = table[i].huffCode.length();
        }

    }
    //get the huffman code of the non zero freq bytes
    public void printCode(Heap heap, HeapNode root, String s) {
        if (root.left == null && root.right == null) {
            heap.heapArray[root.charValue].hufCode = s;
            root.hufCode = root.hufCode + s;

            //get the total number of bits in the chosen file
            maxLen += root.data * (root.hufCode.length());
            return;
        }

        printCode(heap, root.left, s + root.leftRow);
        printCode(heap, root.right, s + root.rightRow);

    }
    //compress the file and write it to output file
    public void writeHuffmanToFile(TableNode[] table, Header header) throws Exception {
        File fi = new File(browseField.getText());
        InputStream file = new FileInputStream(fi);
        byte[] buffer = new byte[200];
        int y = fi.getName().lastIndexOf(".");
        String string = fi.getName().substring(0, y);
        DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(string+".huf"));
        byte temp = 0;

        //build the header tree
        header.headerTree(header.root);


        int x = fi.getName().lastIndexOf(".");
        String field = fi.getName().substring(x + 1);
        //write the length of the extension file to the compressed file
        fileOut.write(field.length());

        //create array of bytes and write the ext of the file to the out file
        byte[] extBuffer = new byte[field.length()];
        for (int i = 0; i < extBuffer.length; i++) {
            extBuffer[i] = (byte) field.charAt(i);
        }

        fileOut.write(extBuffer);

        //write the size of the header that has the leaf and non leaf nodes and bytes
        fileOut.write(header.numbers.length() / 8 + 1);
        fileOut.writeInt(header.numbers.length());

        //write the header tree to the out file
        String headerString = header.numbers;
        int headerBytes = 8;
        int m = 0;
        int headerLen = header.numbers.length();
        for (int i = headerLen; i >= headerBytes; i -= 8) {
            temp = toByte(headerString.substring(m, m + 8));
            fileOut.write(temp);
            headerLen -= 8;
            m += 8;

        }
        //if the header length greater than 8
        if (headerLen > 0) {
            headerString = headerString.substring(m);
            while (headerString.length() < 8)
                headerString += "0";
            temp = toByte(headerString.substring(0, 8));
            fileOut.write(temp);

        }


        byte[] charBuffer = new byte[header.charactors.length()];
        for (int i = 0; i < charBuffer.length; i++)
            charBuffer[i] = (byte) header.charactors.charAt(i);

        fileOut.write(header.charactors.length() - 1);
        fileOut.write(charBuffer);

        //write the number of zeroes that i added when compress to complete to 8 bits
        fileOut.write(8 - (maxLen % 8));

        int sum = 0;
        String huffman = "";

        while (file.available() > 0) {
            //read buffer from file
            int numBytes = file.read(buffer);
            for (int i = 0; i < numBytes; i++) {
                int ch = buffer[i];

                if (ch < 0) {
                    int r = 256 - (-2 * ch);
                    ch = r + (-1 * ch);
                }
                //convert each 32 bytes (256 bits)
                int available = 256 - sum;
                /*if the length of the huffman code for this byte less than the available space
                 * add this huffman code to the available space and add its length to sum
                 * to decrement the available space */

                if (available >= table[ch].lenBits) {
                    sum += table[ch].lenBits;
                    huffman += table[ch].huffCode;

                    //if the sum reach to 256 that means that the number of bytes is 32 and there is no available space
                    if (sum == 256) {
                        //create array of 32 bytes and write it to the out file
                        byte[] buff = comp32Bytes(huffman);
                        fileOut.write(buff);
                        //make the available space is zero and huffman code empty
                        sum = 0;
                        huffman = "";
                    }

                    //if the available space less than huffman code length
                } else {
                    //substring the huffman code string from 0 to the available space and append it to the huffman string
                    huffman += table[ch].huffCode.substring(0, available);

                    //create array of 32 bytes and write it to the out file
                    byte[] buff = comp32Bytes(huffman);
                    fileOut.write(buff);

                    //make the available space equal to the remain string and the sum equal to its length
                    huffman = table[ch].huffCode.substring(available);
                    sum = huffman.length();
                }

            }

        }

        //if the reading of file end and the there is bytes in the huffman string
        int k = 0;
        String remHuff = huffman;

        while (sum >= 8) {
            //convert every 8 bits to byte and write it to the file then substring the remHuf string
            temp = toByte(huffman.substring(k, k + 8));
            if (remHuff.length() > 8) {
                remHuff = remHuff.substring(8);
            }
            fileOut.write(temp);
            sum -= 8;
            k += 8;

        }
        //if there is bits less than 8 append zeroes to it then convert to byte and write it to the file
        if (sum < 8) {
            while (remHuff.length() < 8)
                remHuff += "0";
            temp = toByte(remHuff.substring(0, 8));
            fileOut.write(temp);

        }



        file.close();
        fileOut.flush();
        fileOut.close();

    }
    //method to convert 256 characters in the string to 32 byte and store them into array
    public byte[] comp32Bytes(String huff) {
        byte[] bytes = new byte[32];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = 0;
        while (huff.length() < 256)
            huff += "0";
        byte answer;
        int count = 0;
        for (int i = 0; i < 256; i += 8) {
            answer = toByte(huff.substring(i, i + 8));

            bytes[count++] = answer;
        }

        return bytes;
    }
    //method to convert every 8 characters in the string into byte
    public byte toByte(String huff) {

        int bit = 0;
        byte temp = 0;
        byte firstByte = 0;
        for (int i = 0; i < huff.length(); bit++, i++)
            if (huff.charAt(i) == '1') {
                firstByte = (byte) (1 << (7 - bit % 8));
                //10000
                temp |= firstByte;//00010000
            }
        return temp;
    }
    //decompress a compressed file
    public void deCompressFile(Stage primaryStage) throws Exception {
        //browse compressed file and read it
        TextField brCompFile = new TextField();
        FileChooser BFile = new FileChooser();

        BFile.getExtensionFilters().add(new ExtensionFilter("huf Files","*.huf"));

        File chooseFile = BFile.showOpenDialog(primaryStage);
        if (chooseFile != null)
            brCompFile.setText(chooseFile.getAbsolutePath());


        File fileIn = new File(brCompFile.getText());
        DataInputStream fis;

        Header header = new Header();
        fis = new DataInputStream(new FileInputStream(fileIn));
        int x = fileIn.getName().lastIndexOf(".");
        String firstName = fileIn.getName().substring(0, x);

        //set the browsed file in the header and read header from the browsed file
        header.setFis(fis);
        header.readHeader();

        //find the number of bits of the original file before compressing
        int bytesSize = (fis.available() * 8);
        int numOfAllBits = bytesSize - header.numOfAddZero;

        OutputStream fileOut = new FileOutputStream(firstName+"."+header.fileExt);

        //create array of bytes
        byte[] buffer = new byte[32];
        byte[] outBuffer = new byte[32];

        //build the tree that is read from the compressed file
        header.buildHeaderTree(header.bin, header.chars, header.headerTree);


        HeapNode tree = header.headerTree;
        //countBytes to count the number of bytes that will be writen to the out file
        int countBytes = 0;
        //counts to count the bytes that read from the in file
        int counts = 0;
        //counter to count the number of bits read
        int counter = 0;

        //read buffer from in file
        int size = fis.read(buffer);

        //convert the first byte to binary string and increment counts
        int ch = buffer[counts];
        if (ch < 0)
            ch += 256;
        String binString = Integer.toBinaryString(ch);
        while (binString.length() < 8)
            binString = "0" + binString;
        counts++;


        while (counter < numOfAllBits) {
            //i to count the bits in the binary string
            int i = 0;

            while (tree.left != null && tree.right != null) {
                counter++;
                //i reach to the end of the string append the next byte and convert it to binary string
                if (i == (binString.length())) {
                    //if counts reach to the last index of the array read the next buffer and set counts zero
                    if (counts == (size)) {
                        size = fis.read(buffer);
                        counts = 0;

                    }

                    int chh = buffer[counts];
                    if (chh < 0)
                        chh += 256;

                    String bin = Integer.toBinaryString(chh);

                    while (bin.length() < 8)
                        bin = "0" + bin;
                    binString += bin;

                    counts++;

                }
                //if the bit is zero go to left
                if (binString.charAt(i) == '0')
                    tree = tree.left;
                    //if the bit is 1 go to write
                else
                    tree = tree.right;
                i++;

            }
            //substring the found character from the string
            binString = binString.substring(i);
            //add the character to the out buffer
            outBuffer[countBytes] = (byte) tree.charValue;
            countBytes++;
            //when the the number of bytes reach to 32 in the array write the buffer in to out file and set countBytes zero
            if (countBytes == 32) {
                fileOut.write(outBuffer);
                countBytes = 0;
            }
            //return to the root when find the character
            tree = header.headerTree;

        }
        //if the number of bytes less than 32 bytes
        if (countBytes > 0) {
            fileOut.write(outBuffer, 0, countBytes);
        }

        fis.close();
        fileOut.flush();
        fileOut.close();




    }

}
