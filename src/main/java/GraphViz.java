import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class GraphViz implements IGraphViz {
    private boolean isStart;
    private StringBuilder graphString = new StringBuilder();
    private String filePath = null;
    private File pngFile;

    GraphViz() {
        isStart = false;
    }

    @Override
    public void outputLocation(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void add(String source, String destination) {
        if(!isStart){
            String startString = "digraph D {rankdir=BT;\n";
            graphString.append(startString);
            isStart = true;
        }
        graphString.append(addString(source,destination));
    }

    private String addString(String source,String Destination){ //Our code converted to GraphViz code.
        return source + "->" + Destination + "\n";
    }

    @Override
    public void renderGraph() {
        graphString.append("\n}");
        try {
            renderGraph(graphString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderGraph(String graphString) throws IOException {
        if(filePath == (null)){
            filePath = pathControl(null);
        }else{
            filePath = pathControl(filePath);
        }
        pngFile = new File(filePath + "Graph_" + new Date().getTime() + ".png");
        Graphviz.fromString(graphString).render(Format.PNG).toFile(pngFile);
        System.out.println("Png file path : " + pngFile.getAbsolutePath());
    }

    @Override
    public void showFile() throws IOException{
        if(pngFile == null){
            System.out.println("Null file path!!");
        }else {
            String [] commands = {"cmd.exe" , "/c", "start" , "\"DummyTitle\"", "\"" + pngFile + "\""};
            java.lang.Process process = Runtime.getRuntime().exec(commands);
        }
    }

    private String pathControl(String path){
        if(path != null && path.length() > 2){
            if(!path.substring(filePath.length() - 2).equals("\\")){
                path = path + "\\";
            }
        }else {
            path = "ExampleGraph\\";
        }
        return path;
    }
}
