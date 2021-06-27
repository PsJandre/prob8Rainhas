import java.util.*;

public abstract class Individuo {
    private int avaliacao;
    private boolean avaliado= false;
    private boolean minimizacao = true;
    private int[] genes;

    public abstract List<Individuo> recombinar(Individuo p2);
    public abstract Individuo mutar();
    protected abstract int avaliar();

    public int[] getGenes(){
      return this.genes;
    }
    

    public int getAvaliacao() {
        if(!avaliado){
            avaliacao = avaliar();
        }
        
        return  avaliacao;
    }
    public boolean isMinimizacao() {
        return minimizacao;
    }
    /*
     * Individuo p1 = ....... Individuo p2 = ....... List<Individuo> filhos =
     * p1.recombinar(p2);
     * 
     * Individuo mutante=p1.mutar();
     */
 
}
class Sortbyavaliacao implements Comparator<Individuo>
{
    @Override
    public int compare(Individuo o1, Individuo o2) {
      return o1.getAvaliacao()-o2.getAvaliacao();
    }
}