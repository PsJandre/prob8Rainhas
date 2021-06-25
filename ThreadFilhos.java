import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadFilhos implements Runnable {
  private List<Individuo> popIni;
  private List<Individuo> popFilhos;

  public ThreadFilhos(List<Individuo> popIni) {
    this.popIni = popIni;
  }

  @Override
  public void run() {
    List<Individuo> filhosList = new ArrayList<Individuo>();
    List<Individuo> paisList = new ArrayList<Individuo>();
    paisList.addAll(popIni);

    Random rand = new Random();
    for (int i = 0; i < popIni.size() / 2; i++) {
      int r1 = rand.nextInt(paisList.size());
      Individuo p1 = paisList.get(r1);
      paisList.remove(r1);

      int r2 = rand.nextInt(paisList.size());
      Individuo p2 = paisList.get(r2);
      paisList.remove(r2);

      List<Individuo> filhos = p1.recombinar(p2);
      filhosList.addAll(filhos);

    }

    this.popFilhos = filhosList;
  }

  public List<Individuo> getFilhos() {
    return this.popFilhos;
  }
}
