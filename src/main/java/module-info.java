

module io.github.gleidsonmt.gncarousel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens io.github.gleidsonmt.gncarousel to javafx.fxml, javafx.base;

    exports io.github.gleidsonmt.gncarousel;
}