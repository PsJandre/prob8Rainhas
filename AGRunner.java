import java.util.List;

import javax.swing.JFrame;

public class AGRunner {
  public static void main(String[] args) {
    int numRainhas = 16;
    int numInd = 40;
    int elitismo = (int) (numInd * 0.2);
    int numGer = 2000;
    IndividuoFactory factory = new IndividuoNRainhasFactory(numRainhas);
    AG ag = new AG();
    List<Individuo> indList = ag.executar(factory, numGer, numInd, elitismo);
    // JFrame janela = new xadrez(numRainhas, indList.get(0).getGenes());
    // janela.setVisible(true);
  }
}
