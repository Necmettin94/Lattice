import java.io.IOException;

interface ILattice {
    void add(JGraphTProcessEnum operation, String source, String destination);
    void initialize(); //Default Path, data.txt
    void initialize(String filePath);
    void showVertices();
    void showConnects();
    void toGraph();
    void showFile() throws IOException;
}
