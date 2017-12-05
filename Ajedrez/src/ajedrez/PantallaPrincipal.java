package ajedrez;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class PantallaPrincipal extends JFrame {

    public int ALTO_PANEL_SUPERIOR = 40;
    public int ALTO_PANEL_INFERIOR = 40;
    public int ANCHO_PANEL_JUGADAS = 200;
    public int PIXELS_BORDES_X = 30;
    public int PIXELS_BORDES_Y = 30;
        
    public PantallaPrincipal() {

        super("Ajedrez");
        
        int iAnchoPantalla, iAltoPantalla;
        
        // Panel que contiene el tablero. Esta clase también contiene las imágenes
        // tanto del tablero como de las piezas
        LienzoTablero litLienzoTablero = new LienzoTablero();

        // Definición de las características de la pantalla principal
        iAnchoPantalla = LienzoTablero.imgTablero.getWidth(null) 
            + ANCHO_PANEL_JUGADAS + PIXELS_BORDES_X;
        iAltoPantalla = LienzoTablero.imgTablero.getHeight(null) 
            + ALTO_PANEL_SUPERIOR + ALTO_PANEL_INFERIOR + PIXELS_BORDES_Y;
        setSize(new Dimension(iAnchoPantalla, iAltoPantalla));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel contenedor principal
        JPanel pnlPanelPrincipal = new JPanel();
        setContentPane(pnlPanelPrincipal);
        
        // Definición del layout de la pantalla principal
        GridBagLayout bglPanelPrincipal = new GridBagLayout();
        GridBagConstraints gbcLayout = new GridBagConstraints();
        pnlPanelPrincipal.setLayout(bglPanelPrincipal);
        pnlPanelPrincipal.setBorder(new LineBorder(new Color(0, 0, 0), 2));

        // Panel superior que contiene el nombre del jugador con negras
        JPanel pnlSuperior = new JPanel();
        pnlSuperior.setPreferredSize(
            new Dimension(LienzoTablero.imgTablero.getWidth(this) + ANCHO_PANEL_JUGADAS, 30));
        pnlSuperior.setBorder(new LineBorder(new Color(0, 0, 0), 1));
        gbcLayout.gridx = 0;
        gbcLayout.gridy = 0;
        gbcLayout.gridwidth = 2;
        gbcLayout.gridheight = 1;
        pnlPanelPrincipal.add(pnlSuperior, gbcLayout);
        
        // Tablero de juego
        litLienzoTablero.setPreferredSize(
            new Dimension(LienzoTablero.imgTablero.getWidth(null), 
                LienzoTablero.imgTablero.getHeight(null)));
        gbcLayout.gridx = 0;
        gbcLayout.gridy = 1;
        gbcLayout.gridwidth = 1;
        gbcLayout.gridheight = 1;
        pnlPanelPrincipal.add(litLienzoTablero, gbcLayout);
       
        // Panel inferior que contiene el nombre del jugador con negras
        JPanel pnlInferior = new JPanel();
        pnlInferior.setPreferredSize(
            new Dimension(LienzoTablero.imgTablero.getWidth(this) + ANCHO_PANEL_JUGADAS, 30));
        pnlInferior.setBorder(new LineBorder(new Color(0, 0, 0), 1));
        gbcLayout.gridx = 0;
        gbcLayout.gridy = 2;
        gbcLayout.gridwidth = 2;
        gbcLayout.gridheight = 1;
        pnlPanelPrincipal.add(pnlInferior, gbcLayout);

        // Panel que contiene las listas de jugadas a la derecha de la pantalla
        JPanel pnlJugadas = new JPanel();
        pnlJugadas.setPreferredSize(
            new Dimension(ANCHO_PANEL_JUGADAS, LienzoTablero.imgTablero.getHeight(null)));
        JTextArea txtJugadas = new JTextArea();
        pnlJugadas.setBorder(new LineBorder(new Color(0, 0, 0), 1));
        gbcLayout.gridx = 1;
        gbcLayout.gridy = 1;
        gbcLayout.gridwidth = 1;
        gbcLayout.gridheight = 1;
        pnlPanelPrincipal.add(pnlJugadas, gbcLayout);
        
        Partida ptdPartida = new Partida();
        Tablero tabTablero = new Tablero();
        litLienzoTablero.setPartida(ptdPartida);
        litLienzoTablero.setTablero(tabTablero);
        litLienzoTablero.repaint();
             
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
