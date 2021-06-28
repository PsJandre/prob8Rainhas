import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AG {
  public List<Individuo> executar(IndividuoFactory factory, int nGer, int nInd, int nElite) {
    List<Individuo> popIni = new ArrayList<Individuo>(nInd);
    for (int i = 0; i < nInd; i++) {
      Individuo novo = factory.getIndividuo();
      novo.getAvaliacao();
      popIni.add(novo);
    }
    int[] inicial = popIni.get(0).getGenes().clone();
    ImageIcon img = new ImageIcon("rainha4.png");
    JFrame janela = new xadrez(popIni.get(0).getGenes().length, popIni.get(0).getGenes());
    janela.setIconImage(img.getImage());
    janela.setVisible(true);
    for (int g = 0; g < nGer; g++) {

      List<Individuo> filhos = getFilhos(popIni);
      List<Individuo> mutantes = getMutantes(popIni);
      List<Individuo> popAux = new ArrayList<Individuo>(nInd * 3);

      popAux.addAll(popIni);
      popAux.addAll(filhos);
      popAux.addAll(mutantes);

      popIni = new ArrayList<Individuo>(nInd);
      popIni = selecao(popAux, nInd, nElite);
      //Collections.sort(popIni, new Sortbyavaliacao());
      if (!vetIgual(popIni.get(0).getGenes(), inicial)) {
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          System.err.format("IOException: %s%n", e);
        }
        janela.setVisible(false);
        inicial = popIni.get(0).getGenes().clone();
        janela = new xadrez(popIni.get(0).getGenes().length, popIni.get(0).getGenes());
        janela.setIconImage(img.getImage());
        janela.setVisible(true);

      }
      imprimirMelhor(g, popIni);

    }

    return popIni;
  }

  private boolean vetIgual(int[] vet1, int[] vet2) {
    for (int i = 0; i < vet1.length; i++) {
      if (vet1[i] != vet2[i])
        return false;
    }
    return true;

  }

  private void imprimirMelhor(int g, List<Individuo> popIni) {

    Individuo ind0 = popIni.get(0);
    int avaliacaoMin = ind0.getAvaliacao();
    int count = 0;
    for (int i = 0; i < popIni.size(); i++) {
      if (popIni.get(i).getAvaliacao() == 0) {
        count++;
      }
    }

    for (int i = 0; i < ind0.getGenes().length; i++) {
      System.out.print("===");
    }
    System.out.println();
    System.out.println("Numero de soluções encontradas: " + count);
    System.out.println("Geração: " + g);
    System.out.println("Tabuleiro: ");
    for (int i = 0; i < ind0.getGenes().length; i++) {
      System.out.print(ind0.getGenes()[i] + " ");
    }
    System.out.println();
    System.out.println("Colisões: " + avaliacaoMin);

  }

  private List<Individuo> selecao(List<Individuo> popAux, int nInd, int nElite) {
    List<Individuo> newPop = new ArrayList<Individuo>();
    Collections.sort(popAux, new Sortbyavaliacao());
    // Se for de minimizacao ordenar popAux da menor para a maior avaliacao, caso
    // contrario ordenar da maior para a menor avaliacao

    // ==========================================================================
    // Elitismo
    // ==========================================================================
    // Selecionar os "nElite" individuos no inicio de popAux
    // Colocar os "nElite" individuos selecionados em newPop.
    for (int i = 0; i < nElite; i++) {
      newPop.add(popAux.get(0));
      popAux.remove(0);
    }

    // Roleta viciada
    // ==========================================================================
    // Realizar a roleta viciada para selecionar o restante dos individuos ate
    // chegar no total de nInd
    // 0- Inverter a avaliacao de todos os individuos na forma (1/avaliacao) e
    // utilizar estes novos valores para os passos seguintes:
    // 1- Obter a soma (s1) das avaliacoes de todos os individuos que irao
    // participar da roleta
    // 2- Gerar um numero aleatorio (r) que vai de 0 ao s1
    // 3- Para selecionar um individuo da lista, vou percorrendo a lista somando
    // (s2) novamente a avaliacao ate obter um valor de soma maior que o valor
    // aleatorio
    // 4- Retirar o individuo selecionado e voltar para o passo 1.

    // Obs: Realizar os passos 1, 2, 3 e 4 nVezes ate completar
    // newPop.size = nIndv
    double s1 = 0;
    double s2 = 0;
    Random rng = new Random();
    while (newPop.size() < nInd) {
      for (int i = 0; i < popAux.size(); i++) {
        if (popAux.get(i).getAvaliacao() == 0) {
          s1 += (1.0 / (popAux.get(i).getAvaliacao() + 1.0));
        } else {
          s1 += (1.0 / (popAux.get(i).getAvaliacao()));
        }
      }
      double r = rng.nextDouble() * s1;
      for (int i = 0; i < popAux.size(); i++) {
        if (popAux.get(i).getAvaliacao() == 0) {
          s2 += 1.0 / (popAux.get(i).getAvaliacao() + 1.0);
        } else {
          s2 += 1.0 / (popAux.get(i).getAvaliacao());
        }
        if (s2 >= r) {
          newPop.add(popAux.get(i));
          popAux.remove(i);
          break;
        }
      }
    }
    return newPop;
  }

  private List<Individuo> getMutantes(List<Individuo> popIni) {

    List<Individuo> mutantesList = new ArrayList<Individuo>();
    for (int i = 0; i < popIni.size(); i++) {
      Individuo p1 = popIni.get(i);
      mutantesList.add(p1.mutar());
    }
    return mutantesList;
  }

  private List<Individuo> getFilhos(List<Individuo> popIni) {
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

    return filhosList;
  }
}
