import javax.swing.*;
import java.awt.*;

public class xadrez extends JFrame {
  int TamTab;
  int[] rainhas;
  // JButton x[] = new JButton[64];
  JButton x[];
  int j = 0, casa = 0;

   xadrez(int TamTab, int[] rainhas) {
    this.TamTab = TamTab;
    this.rainhas = rainhas;
    int numCasas = TamTab * TamTab;
    x = new JButton[numCasas];
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setTitle("TABULEIRO DE XADREZ");
    setSize(screenSize.width - 50, screenSize.height - 50);
    setResizable(false);

    getContentPane().setLayout(new GridLayout(TamTab, TamTab, 6, 6));
    for (casa = 0; casa < numCasas; casa++)
      x[casa] = new JButton();
    casa = 0;
    ImageIcon damabranca;
    if (TamTab > 16) {
      damabranca = new ImageIcon("rainha7.png");
    } else {
      damabranca = new ImageIcon("rainha8.png");
    }

    while (casa < numCasas) {

      for (j = 0; j < TamTab; j++) {
        if ((j % 2) == 0) {
          x[casa].setBackground(Color.black);
        } else {
          x[casa].setBackground(Color.white);
        }
        getContentPane().add(x[casa]);
        casa++;
      }
      for (j = 0; j < TamTab; j++) {
        if ((j % 2) == 0) {
          x[casa].setBackground(Color.white);
        } else {
          x[casa].setBackground(Color.black);
        }
        getContentPane().add(x[casa]);
        casa++;
      }

    }
    int[] posRainha = new int[TamTab];
    for (int i = 0; i < rainhas.length; i++) {
      int posicao = rainhas[i] * TamTab + i;
      posRainha[i] = posicao;
    }
    for (int i = 0; i < posRainha.length; i++) {
      x[posRainha[i]].setIcon(damabranca);
    }
    for (int i = 0; i < posRainha.length; i++) {
      for (int j = 1; j + i < posRainha.length; j++) {
        if (posRainha[i] + (TamTab * j) + j == posRainha[i + j]
            || posRainha[i] - (TamTab * j) + j == posRainha[i + j]) {
          x[posRainha[i]].setBackground(Color.RED);
          x[posRainha[i + j]].setBackground(Color.RED);
        }
      }

    }

  }
}