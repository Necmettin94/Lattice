import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFileForLattice implements IReadFromFileForLattice {
    private ILattice lattice;
    private String path;

    ReadFromFileForLattice(Lattice lattice) { //Default directory
        path = "data.txt";
        this.lattice = lattice;
    }

    ReadFromFileForLattice(Lattice lattice, String path) {
        this.path = path;
        this.lattice = lattice;
    }

    @Override
    public void toGraph() {
        lattice.toGraph();
    }

    @Override
    public void read() {
        if(new File(path).exists()){
            try {
                readAllLines();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void readAllLines() throws IOException {
        File file = new File(path);
        FileReader readFile = new FileReader(file);
        BufferedReader reader = new BufferedReader(readFile);
        int lineNumber = 0;
        String line;

        while ((line = reader.readLine()) != null){
            lineNumber++;
            lineSplit(line,lineNumber);
        }
        reader.close();
    }

    private void lineSplit(String line,int lineNumber) throws ArrayIndexOutOfBoundsException{
        String[] separatedText = line.split(",");
        try {
            String type = separatedText[0].trim();
            String source = separatedText[1];
            String destination = separatedText[2];
            latticeProcess(type,source,destination,lineNumber);
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void latticeProcess(String type, String source, String destination, int lineNumber){
        switch (type){
            case "ConnectsTo":
                setLattice(JGraphTProcessEnum.ConnectsTo,source,destination);
                break;
            case "Requires":
                setLattice(JGraphTProcessEnum.Requires,source,destination);
                break;
            case "Excludes":
                setLattice(JGraphTProcessEnum.Excludes,source,destination);
                break;
            default:
                System.out.println("Unexpected line type. Line number : " + lineNumber);
        }
    }

    private void setLattice(JGraphTProcessEnum type, String source, String destination){
        lattice.add(type,source,destination);
    }

}
