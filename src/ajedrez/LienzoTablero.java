package ajedrez;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LienzoTablero extends JPanel implements MouseListener {

    public int TAMANO_PIEZA = 60;
    public int PIXEL_SUPIZQ_PIEZA_X = 17;
    public int PIXEL_SUPIZQ_PIEZA_Y = 17;
    public int PIXEL_SUPIZQ_TABLERO_X = 14;
    public int PIXEL_SUPIZQ_TABLERO_Y = 14;
    public int PIXELS_DESPLAZAMIENTO = 69;
    
    private Image[] imgPiezas;
    public static Image imgTablero;
    
    private Partida ptdPartida;
    private Tablero tabTablero;
    private Casilla[][] casCasillas;
    private int iCasSelX, iCasSelY, iCasAntX, iCasAntY;

    public LienzoTablero() {
        
        super();
        
        int i, ii;
        
        imgTablero = new ImageIcon(getClass().getResource("./images/tablero2.png")).getImage();

        imgPiezas = new Image[12];
        imgPiezas[0] = new ImageIcon(getClass().getResource("./images/peon_blancas.png")).getImage();
        imgPiezas[1] = new ImageIcon(getClass().getResource("./images/caballo_blancas.png")).getImage();
        imgPiezas[2] = new ImageIcon(getClass().getResource("./images/alfil_blancas.png")).getImage();
        imgPiezas[3] = new ImageIcon(getClass().getResource("./images/torre_blancas.png")).getImage();
        imgPiezas[4] = new ImageIcon(getClass().getResource("./images/dama_blancas.png")).getImage();
        imgPiezas[5] = new ImageIcon(getClass().getResource("./images/rey_blancas.png")).getImage();
        
        imgPiezas[6] = new ImageIcon(getClass().getResource("./images/peon_negras.png")).getImage();
        imgPiezas[7] = new ImageIcon(getClass().getResource("./images/caballo_negras.png")).getImage();
        imgPiezas[8] = new ImageIcon(getClass().getResource("./images/alfil_negras.png")).getImage();
        imgPiezas[9] = new ImageIcon(getClass().getResource("./images/torre_negras.png")).getImage();
        imgPiezas[10] = new ImageIcon(getClass().getResource("./images/dama_negras.png")).getImage();
        imgPiezas[11] = new ImageIcon(getClass().getResource("./images/rey_negras.png")).getImage();
        
        iCasSelX = -1;
        iCasSelY = -1;
        
        addMouseListener(this);
        
    }
    
    @Override 
    public void paintComponent( Graphics g ) {
        
        super.paintComponent(g);
        
        int i, ii;
        int iPixelCorreccion, iIndiceImagen;
        
        g.drawImage(imgTablero, 0, 0,
            imgTablero.getWidth(null), 
            imgTablero.getHeight(null), null);
        
        for( i=0; i<8; i++ ) {
            iPixelCorreccion = 0;
            for( ii=0; ii<8; ii++ ) {
                if( casCasillas[ii][i].getPieza() != null ) {
                    if( i > 3 || ii > 3 ) iPixelCorreccion = 1;
                    if( casCasillas[ii][i].getPieza().getColor() == Pieza.BLANCAS )
                        iIndiceImagen = casCasillas[ii][i].getPieza().getCodigo() - 1;
                    else
                        iIndiceImagen = casCasillas[ii][i].getPieza().getCodigo() + 5;
                    g.drawImage(imgPiezas[iIndiceImagen],     
                        PIXEL_SUPIZQ_PIEZA_X + PIXELS_DESPLAZAMIENTO*ii + iPixelCorreccion, 
                        PIXEL_SUPIZQ_PIEZA_Y + PIXELS_DESPLAZAMIENTO*i + iPixelCorreccion,
                        TAMANO_PIEZA, TAMANO_PIEZA, null);           
                }
            }
        }
        
        // Si hay una casilla seleccionada le pintamos el borde de color para resaltarla
        if( PiezaSeleccionada() ) {
            ((Graphics2D)g).setStroke(new BasicStroke(3));
            g.setColor(Color.ORANGE);
            // Pixel de corrección necesario para una visualización correcta
            switch( iCasSelX ) {
                case 4: case 5: iPixelCorreccion = 0; break;
                case 6: case 7: iPixelCorreccion = -1; break;
                default: iPixelCorreccion = 1; break;
            }
            g.drawRect(
                iCasSelX*PIXELS_DESPLAZAMIENTO + PIXEL_SUPIZQ_TABLERO_X - iPixelCorreccion,
                iCasSelY*PIXELS_DESPLAZAMIENTO + PIXEL_SUPIZQ_TABLERO_Y,
                PIXELS_DESPLAZAMIENTO - 1, PIXELS_DESPLAZAMIENTO - 1);               
        }
        
    }
    
    @Override public void mouseClicked( MouseEvent e ) { }    
    @Override public void mouseEntered( MouseEvent e ) { }
    @Override public void mouseExited( MouseEvent e ) { }
    @Override public void mouseReleased( MouseEvent e ) { }
    
    @Override 
    public void mousePressed( MouseEvent e ) { 
        
        boolean bCambioPiezaSelec;
        Pieza pzaCasillaDestino, pzaTemp;
        
        if( ClickDentroTablero(e.getY(), e.getX() ) ) {
            // Comprobamos si ya hay una casilla seleccionada, en este caso
            // este click será para realizar un movimiento de pieza
            if( PiezaSeleccionada() ) {
                // Recogemos las coordenadas de la nueva casilla clickada y guardamos
                // las de la casilla anterior
                iCasAntY = iCasSelY;
                iCasAntX = iCasSelX;
                pzaCasillaDestino = PiezaCasilla(e.getY(), e.getX());
               
                // Si la casilla destino es una pieza del mismo color, simplemente es
                // un cambio de selección de pieza
                bCambioPiezaSelec = false;
                if( pzaCasillaDestino != null ) {
                    if( pzaCasillaDestino.getColor() == casCasillas[iCasAntX][iCasAntY].getPieza().getColor())
                        bCambioPiezaSelec = true;
                }
                
                // En caso contrario se realiza el movimiento de la pieza si es posible.
                if( !bCambioPiezaSelec ) {           
                    switch( casCasillas[iCasAntX][iCasAntY].getPieza().getCodigo() ) {
                        case Pieza.PEON:
                                Peon pzaPeon = new Peon(casCasillas[iCasAntX][iCasAntY].getPieza().getColor());
                                // Comprueba si el movimiento es un movimiento válido del peón
                                if( pzaPeon.MovimientoValido(iCasAntX, iCasAntY, iCasSelX, iCasSelY)) {
                                    // Comprueba que el movimiento se pueda realizar (que no haya piezas por medio, etc)
                                    if( pzaPeon.MoverPosible(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY) ) {
                                        // Efectuamos el movimiento de la pieza
                                        tabTablero.MoverPieza(iCasAntX, iCasAntY, iCasSelX, iCasSelY);
                                        // Si después de realizar ese movimiento el rey se encuentra en jaque
                                        // ese movimiento no es posible, por lo que lo deshacemos y no
                                        // cambiamos el turno
                                        if( tabTablero.HayJaque(ptdPartida.getTurno()) ) {
                                            tabTablero.DeshacerUltimoMovimiento();
                                        }
                                        else {
                                            ptdPartida.cambiaTurno();
                                            // Comprobamos ahora si el rey contrario se encuentra en situación
                                            // de jaque mate, en caso afirmativo finaliza la partida
                                            if( tabTablero.HayJaque(ptdPartida.getTurno())) {
                                                if( tabTablero.JaqueMate(ptdPartida.getTurno()) ) {
                                                
                                                }
                                            }
                                            // Comprobar ahogado
                                        } 
                                    }
                                }
                                pzaPeon = null;
                                break;
                        case Pieza.CABALLO:
                                Caballo pzaCaballo = new Caballo(casCasillas[iCasAntX][iCasAntY].getPieza().getColor());
                                if( pzaCaballo.MovimientoValido(iCasAntX, iCasAntY, iCasSelX, iCasSelY)) {
                                    if( pzaCaballo.MoverPosible(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY) ) {                 
                                        tabTablero.MoverPieza(iCasAntX, iCasAntY, iCasSelX, iCasSelY);
                                        if( tabTablero.HayJaque(ptdPartida.getTurno()) ) {
                                            tabTablero.DeshacerUltimoMovimiento();
                                        }
                                        else {
                                            ptdPartida.cambiaTurno();
                                        } 
                                    }
                                }
                                pzaCaballo = null;
                                break;
                        case Pieza.ALFIL:
                                Alfil pzaAlfil = new Alfil(casCasillas[iCasAntX][iCasAntY].getPieza().getColor());
                                if( pzaAlfil.MovimientoValido(iCasAntX, iCasAntY, iCasSelX, iCasSelY)) {
                                    if( pzaAlfil.MoverPosible(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY) ) {
                                        tabTablero.MoverPieza(iCasAntX, iCasAntY, iCasSelX, iCasSelY);
                                        if( tabTablero.HayJaque(ptdPartida.getTurno()) ) {
                                            tabTablero.DeshacerUltimoMovimiento();
                                        }
                                        else {
                                            ptdPartida.cambiaTurno();
                                        } 
                                    }
                                }
                                pzaAlfil = null;
                                break;
                        case Pieza.TORRE:
                                Torre pzaTorre = new Torre(casCasillas[iCasAntX][iCasAntY].getPieza().getColor());
                                if( pzaTorre.MovimientoValido(iCasAntX, iCasAntY, iCasSelX, iCasSelY)) {
                                    if( pzaTorre.MoverPosible(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY) ) {
                                        tabTablero.MoverPieza(iCasAntX, iCasAntY, iCasSelX, iCasSelY);
                                        if( tabTablero.HayJaque(ptdPartida.getTurno()) ) {
                                            tabTablero.DeshacerUltimoMovimiento();
                                        }
                                        else {
                                            ptdPartida.cambiaTurno();
                                        } 
                                    }
                                }
                                pzaTorre = null;
                                break;
                        case Pieza.DAMA:
                                Dama pzaDama = new Dama(casCasillas[iCasAntX][iCasAntY].getPieza().getColor());
                                if( pzaDama.MovimientoValido(iCasAntX, iCasAntY, iCasSelX, iCasSelY)) {
                                    if( pzaDama.MoverPosible(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY) ) {
                                        tabTablero.MoverPieza(iCasAntX, iCasAntY, iCasSelX, iCasSelY);
                                        if( tabTablero.HayJaque(ptdPartida.getTurno()) ) {
                                            tabTablero.DeshacerUltimoMovimiento();
                                        }
                                        else {
                                            ptdPartida.cambiaTurno();
                                        } 
                                    }
                                }
                                pzaDama = null;
                                break;
                        case Pieza.REY:
                                Rey pzaRey = new Rey(casCasillas[iCasAntX][iCasAntY].getPieza().getColor());
                                if( pzaRey.MovimientoValido(iCasAntX, iCasAntY, iCasSelX, iCasSelY)) {
                                    if( pzaRey.MoverPosible(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY) ) {
                                        tabTablero.MoverPieza(iCasAntX, iCasAntY, iCasSelX, iCasSelY);
                                        if( tabTablero.HayJaque(ptdPartida.getTurno()) ) {
                                            tabTablero.DeshacerUltimoMovimiento();
                                        }
                                        else {
                                            ptdPartida.cambiaTurno();
                                        } 
                                    }
                                }
                                pzaRey = null;
                                break;
                    }
                    iCasSelX = -1;
                    iCasSelY = -1;
                }
                
            }
            // Si no hay una casilla seleccionada, se realiza la selección
            else {
                pzaCasillaDestino = PiezaCasilla(e.getY(), e.getX());
                // Comprobamos que se trata de una pieza del jugador al que le
                // toca jugar, en caso contrario no se realiza la selección
                if( pzaCasillaDestino != null ) {
                    if( pzaCasillaDestino.getColor() == Pieza.BLANCAS ) {
                        if( ptdPartida.getTurno() != ptdPartida.BLANCAS ) {
                            iCasSelX = -1;
                            iCasSelY = -1;
                        }
                    }
                    else {
                        if( ptdPartida.getTurno() != ptdPartida.NEGRAS ) {
                            iCasSelX = -1;
                            iCasSelY = -1;
                        }
                    }
                }
                // En caso de tratarse de una casilla vacía anulamos la selección
                else {
                    iCasSelX = -1;
                    iCasSelY = -1;
                }
            }
            
            repaint();

        }
        pzaCasillaDestino = null;
        
    }

    // Recoge las coordenadas (0-7, 0-7) en el tablero de la casilla seleccionada
    public Pieza PiezaCasilla( int iPosY, int iPosX ) {
    
        iCasSelY = (iPosY - PIXEL_SUPIZQ_TABLERO_Y) / PIXELS_DESPLAZAMIENTO;   
        iCasSelX = (iPosX - PIXEL_SUPIZQ_TABLERO_X) / PIXELS_DESPLAZAMIENTO;
        
        return casCasillas[iCasSelX][iCasSelY].getPieza();
        
    }
    
    // Comprueba si se ha hecho click dentro del tablero
    public boolean ClickDentroTablero( int iPosY, int iPosX ) {
        
        return ( (iPosX <= (PIXEL_SUPIZQ_PIEZA_Y + PIXELS_DESPLAZAMIENTO*8)) &&
                 (iPosY >= PIXEL_SUPIZQ_PIEZA_X) && (iPosX >= PIXEL_SUPIZQ_PIEZA_Y) &&
                 (iPosY <= (PIXEL_SUPIZQ_PIEZA_X + PIXELS_DESPLAZAMIENTO*8)) );

    }
    
    public void setTablero( Tablero tabT ) {
        
        tabTablero = tabT;
        casCasillas = tabTablero.getCasillas();
               
    }
    
    public void setPartida( Partida ptdP ) {
        
        ptdPartida = ptdP;
        
    }
    
    public boolean PiezaSeleccionada() {
        
        return (iCasSelX >= 0);

    }
      
}

