import java.util.ArrayList;
import java.util.List;

public class ThreadMutante implements Runnable {
  private List<Individuo> popIni;
  private List<Individuo> popMutante;

  public ThreadMutante(List<Individuo> popIni) {
    this.popIni = popIni;
  }

  public List<Individuo> getMutantes() {
    return this.popMutante;
  }

  @Override
  public void run() {

    List<Individuo> mutantesList = new ArrayList<Individuo>();
    for (int i = 0; i < popIni.size(); i++) {
      Individuo p1 = popIni.get(i);
      mutantesList.add(p1.mutar());
    }
    this.popMutante = mutantesList;
  }

}
