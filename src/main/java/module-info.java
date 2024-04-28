module com.example.project2huffman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project2huffman to javafx.fxml;
    exports com.example.project2huffman;
}