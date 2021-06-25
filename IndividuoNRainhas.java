import java.util.*;

public class IndividuoNRainhas extends Individuo {

  private int nRainhas;// Tamanho do tabuleiro
  private int[] genes;
  private int TaxaMutacao = 10;

  public int[] getGenes() {
    return this.genes;
  }

  public IndividuoNRainhas(int nRainhas, int[] genes) {
    this.nRainhas = nRainhas;
    this.genes = genes;
  }

  public IndividuoNRainhas(int nRainhas) {
    this.nRainhas = nRainhas;
    int[] genes = new int[nRainhas];
    for (int index = 0; index < genes.length; index++) {
      genes[index] = -1;
    }
    Random rng = new Random();
    for (int index = 0; index < genes.length; index++) {
      boolean novoNumero = false;
      int numero = -1;
      while (!novoNumero) {
        numero = rng.nextInt(nRainhas);
        novoNumero = verificarExistencia(numero, genes);
      }
      genes[index] = numero;

    }
    this.genes = genes;
  }

  private boolean verificarExistencia(int numero, int[] genes) {
    for (int index = 0; index < genes.length; index++) {
      if (numero == genes[index]) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<Individuo> recombinar(Individuo p2) {
    List<Individuo> filhos = new ArrayList<Individuo>();
    int[] GenesF1 = new int[this.nRainhas];
    int[] GenesF2 = new int[this.nRainhas];
    int[] GenesP1 = this.genes;
    int[] GenesP2 = p2.getGenes();
    for (int index = 0; index < GenesF1.length; index++) {
      GenesF1[index] = -1;
      GenesF2[index] = -1;
    }
    for (int index = 0; index < genes.length; index++) {
      if (index < genes.length / 2) {
        GenesF1[index] = GenesP1[index];
        GenesF2[index] = GenesP2[index];
      } else {
        if (verificarExistencia(GenesP2[index], GenesF1)) {
          GenesF1[index] = GenesP2[index];
        }
        if (verificarExistencia(GenesP1[index], GenesF2)) {
          GenesF2[index] = GenesP1[index];
        }
      }
    }
    completaGenes(GenesF1);
    completaGenes(GenesF2);
    Individuo filho1 = new IndividuoNRainhas(this.nRainhas, GenesF1);
    Individuo filho2 = new IndividuoNRainhas(this.nRainhas, GenesF2);
    filhos.add(filho1);
    filhos.add(filho2);
    return filhos;
  }

  private void completaGenes(int[] vet) {
    for (int index = 0; index < vet.length; index++) {
      Random rng = new Random();
      boolean novoNumero = false;
      int numero = -1;
      if (vet[index] == -1) {
        while (!novoNumero) {
          numero = rng.nextInt(nRainhas);
          novoNumero = verificarExistencia(numero, vet);
        }
        vet[index] = numero;
      }
    }
  }

  @Override
  public Individuo mutar() {
    boolean mutado = false;
    int[] IndMut = this.genes.clone();
    Random rng = new Random();
    int GeneOrigem = 0;
    int GeneDestino = 0;
    for (int index = 0; index < IndMut.length; index++) {
      int OcorreMutacao = rng.nextInt(100);
      if (OcorreMutacao >= 99 - TaxaMutacao) {
        GeneOrigem = index;
        GeneDestino = index;
        while (GeneDestino == GeneOrigem) {
          GeneDestino = rng.nextInt(nRainhas);
        }
        int aux = IndMut[GeneOrigem];
        IndMut[GeneOrigem] = IndMut[GeneDestino];
        IndMut[GeneDestino] = aux;
        mutado = true;
      }
    }
    if (!mutado) {
      GeneOrigem = rng.nextInt(nRainhas);
      GeneDestino = rng.nextInt(nRainhas);
      while (GeneDestino == GeneOrigem) {
        GeneDestino = rng.nextInt(nRainhas);
      }
      int aux = IndMut[GeneOrigem];
      IndMut[GeneOrigem] = IndMut[GeneDestino];
      IndMut[GeneDestino] = aux;
    }

    return new IndividuoNRainhas(nRainhas, IndMut);
  }

  @Override
  protected int avaliar() {
    int colisao = 0;
    for (int index = 0; index < genes.length; index++) {
      for (int j = 1; j + index < genes.length; j++) {
        if ((genes[index + j] == genes[index] + j) || (genes[index + j] == genes[index] - j)) {
          colisao++;
        }
      }
    }
    return colisao;
  }

}
