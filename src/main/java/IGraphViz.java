import java.io.IOException;

interface IGraphViz {
    void add(String source,String Destination);
    void renderGraph();
    void outputLocation(String filePath);
    void showFile() throws IOException;
}
