import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;

public class Lattice implements ILattice{
    private IGraphViz graphViz;
    private Graph<String, DefaultEdge> directedGraph;
    private ReadFromFileForLattice readFromFileForLattice;

    Lattice() {
        directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    @Override
    public void add(JGraphTProcessEnum operation, String source, String destination) {
        if(operation.equals(JGraphTProcessEnum.ConnectsTo)){
            connectsTo(source,destination);
        }else if(operation.equals(JGraphTProcessEnum.Requires)){
            requiresAdd(source,destination);
        }else if(operation.equals(JGraphTProcessEnum.Excludes)){
            checkExcludesVertices(source,destination);
        }
    }

    private void connectsTo(String source, String destination){
        addVertex(source);
        addVertex(destination);
        addEdge(source,destination);
    }

    private void addEdge(String source,String destination){
        if(!directedGraph.containsEdge(source,destination)){
            directedGraph.addEdge(source, destination);
        }
    }
    private void requiresAdd(String source, String destination) {
        if(!containVerticesForRequires(source,destination)){
            connectsTo(destination,source);
        }
    }
    private boolean containVerticesForRequires(String source, String destination){
        if(directedGraph.containsVertex(source)){
            return directedGraph.containsVertex(destination);
        }
        else
            return true;
    }

    private void checkExcludesVertices(String source,String destination){
        if(containVerticesForExcludes(source,destination)){
            excludes(destination);
        }
    }
    private boolean containVerticesForExcludes(String source, String destination){
        if(directedGraph.containsVertex(source)){
            return directedGraph.containsVertex(destination);
        }
        else
            return false;
    }
    private void excludes(String vertex){
        if(directedGraph.containsVertex(vertex)){
            for (DefaultEdge edge:directedGraph.edgesOf(vertex)){
                if(directedGraph.getEdgeSource(edge).equals(vertex)){
                    excludes(directedGraph.getEdgeTarget(edge));
                    directedGraph.removeVertex(directedGraph.getEdgeTarget(edge));
                    directedGraph.removeVertex(vertex);
                }
                if(directedGraph.getEdgeTarget(edge).equals(vertex)){
                    directedGraph.removeVertex(vertex);
                }
            }
        }
    }

    private void addVertex(String vertex){
        if(!directedGraph.containsVertex(vertex)){
            directedGraph.addVertex(vertex);
        }
    }

    @Override
    public void initialize(String filePath) {
        readFromFileForLattice = new ReadFromFileForLattice(this,filePath);
        readFromFileForLattice.read();
    }
    @Override
    public void initialize() { //Default directory
        readFromFileForLattice = new ReadFromFileForLattice(this,"data.txt");
        readFromFileForLattice.read();
    }

    @Override
    public void showVertices() {
        System.out.println(directedGraph.vertexSet());
    }

    @Override
    public void showConnects() {
        System.out.println(directedGraph.edgeSet());
    }

    @Override
    public void toGraph() {
        graphViz = new GraphViz();
        for(DefaultEdge edges:directedGraph.edgeSet()) {
            String[] edge= edges.toString().split(":");
            String src = edge[0].replace("(", "");
            src = src.trim();
            String dst = edge[1].replace(")", "");
            dst = dst.trim();
            graphViz.add(src, dst);
        }
        graphViz.renderGraph();
    }

    @Override
    public void showFile() throws IOException {
        if(graphViz != null)
            graphViz.showFile();
        else
            System.out.println("Null graph!!");
    }
}
