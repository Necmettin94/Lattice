import java.io.IOException;

class App {

    public static void main(String[] args) throws IOException {
        String filePath = "data.txt";

        ILattice lattice = new Lattice();
        lattice.initialize(filePath);
        lattice.add(JGraphTProcessEnum.ConnectsTo,"A","C"); //Dosyadan okuduktan sonra elle işlem yapılabiliyor
        lattice.showConnects();
        lattice.toGraph();

        lattice.showFile(); /*Bu kod windows'da çalışıyor. Ancak büyük ihtimal MacOS da çalışmayacaktır,
                              çünkü cmd komutu ile çalışıyor. MacOS terminal'de aynı kod işe yaramayacaktır.
                              Test sürecinde renderlanmış dosyayı daha hızlı görmek için kullanıyorum.*/


        //.............Another usage.............//
        /*IReadFromFileForLattice readFileForLattice = new ReadFromFileForLattice(new Lattice());
        readFileForLattice.read();
        readFileForLattice.toGraph();*/
    }
}
