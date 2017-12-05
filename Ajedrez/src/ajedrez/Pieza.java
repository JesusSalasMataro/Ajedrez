package ajedrez;

public class Pieza implements Cloneable {
    
    public static final int PEON = 1;
    public static final int CABALLO = 2;
    public static final int ALFIL = 3;
    public static final int TORRE = 4;
    public static final int DAMA = 5;
    public static final int REY = 6;
    
    public final int VALOR_PEON = 1;
    public final int VALOR_CABALLO = 3;
    public final int VALOR_ALFIL = 3;
    public final int VALOR_TORRE = 5;
    public final int VALOR_DAMA = 10;
   
    public static final boolean BLANCAS = true;
    public static final boolean NEGRAS = false;
    
    protected int iCodigo;
    protected int iValor;
    protected boolean bColor;
    
    public Pieza() { }
    
    public Pieza( boolean bCol ) {
        
        bColor = bCol;
        
    }
    
    public Pieza( int iCod, int iVal, boolean bCol ) {
        
        iCodigo = iCod;
        iValor = iVal;
        bColor = bCol;
        
    }
    
    public Pieza Clone() {
        
        Pieza pzaClon = new Pieza(iCodigo, iValor, bColor);
        return pzaClon;
        
    }
    
    // Método que sobreescribirán las clases hijas de las piezas concretas.
    // Devuelve si la pieza pretende efectuar un movimiento válido, sin controlar
    // si hay piezas por el camino o no, esto último se controla en la clase
    // tablero.
    public boolean MovimientoValido(
        int iColInicial, int iFilaInicial, int iColFinal, int iFilaFinal )
            { return true; }
    
    // Esta función indica si es posible mover la pieza a la casilla seleccionada.
    // Controla que no haya piezas por el camino o que la pieza destino no sea
    // del mismo color. Devuelve un booleano informando.
    public boolean MoverPosible(
        Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) 
            { return true; }
    
    public boolean getColor() { return bColor; }
    public int getCodigo() { return iCodigo; }
    public void setCodigo( int iCod ) { iCodigo = iCod; }
     
    // Movimiento que realizan los alfiles y la dama
    protected boolean ComprobarMovimientoDiagonal( 
        Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {

                    Casilla[][] iCasillas = tabTablero.getCasillas();
            int iCasTempX, iCasTempY;
            boolean bMover = true;
            
            // Movimiento hacia diagonal superior izquierda
            if( iCasAntX > iCasSelX && iCasAntY > iCasSelY ) {
                iCasTempX = iCasAntX - 1;
                iCasTempY = iCasAntY - 1;
                while( bMover && iCasTempX >= iCasSelX ) {
                    if( iCasillas[iCasTempX][iCasTempY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasTempX][iCasTempY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempX != iCasSelX ) bMover = false;
                        }
                    }
                    iCasTempX = iCasTempX - 1;
                    iCasTempY = iCasTempY - 1;
                }
            }
            
            // Movimiento hacia diagonal superior derecha
            else if( iCasAntX < iCasSelX && iCasAntY > iCasSelY ) {
                iCasTempX = iCasAntX + 1;
                iCasTempY = iCasAntY - 1;
                while( bMover && iCasTempX <= iCasSelX ) {
                    if( iCasillas[iCasTempX][iCasTempY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasTempX][iCasTempY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempX != iCasSelX ) bMover = false;
                        }
                    }
                    iCasTempX = iCasTempX + 1;
                    iCasTempY = iCasTempY - 1;
                }
            }
            
            // Movimiento hacia diagonal inferior izquierda
            if( iCasAntX > iCasSelX && iCasAntY < iCasSelY ) {
                iCasTempX = iCasAntX - 1;
                iCasTempY = iCasAntY + 1;
                while( bMover && iCasTempX >= iCasSelX ) {
                    if( iCasillas[iCasTempX][iCasTempY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasTempX][iCasTempY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempX != iCasSelX ) bMover = false;
                        }
                    }
                    iCasTempX = iCasTempX - 1;
                    iCasTempY = iCasTempY + 1;
                }
            }
            
            // Movimiento hacia diagonal inferior derecha
            if( iCasAntX < iCasSelX && iCasAntY < iCasSelY ) {
                iCasTempX = iCasAntX + 1;
                iCasTempY = iCasAntY + 1;
                while( bMover && iCasTempX <= iCasSelX ) {
                    if( iCasillas[iCasTempX][iCasTempY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasTempX][iCasTempY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempX != iCasSelX ) bMover = false;
                        }
                    }
                    iCasTempX = iCasTempX + 1;
                    iCasTempY = iCasTempY + 1;
                }
            }
            
            return bMover;

        
    }
    
    // Movimiento que realizan las torres y la dama
    protected boolean ComprobarMovimientoHorizontalVertical( 
        Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            Casilla[][] iCasillas = tabTablero.getCasillas();
            int iCasTempX, iCasTempY;
            boolean bMover = true;
            
            // Movimiento hacia arriba
            if( iCasAntX > iCasSelX ) {
                iCasTempX = iCasAntX - 1;
                while( bMover && iCasTempX >= iCasSelX ) {
                    if( iCasillas[iCasTempX][iCasAntY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasTempX][iCasAntY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempX != iCasSelX ) bMover = false;
                        }
                    }
                    iCasTempX = iCasTempX - 1;
                }
            }
            
            // Movimiento hacia abajo
            else if( iCasAntX < iCasSelX ) {
                iCasTempX = iCasAntX + 1;
                while( bMover && iCasTempX <= iCasSelX ) {
                    if( iCasillas[iCasTempX][iCasAntY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasTempX][iCasAntY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempX != iCasSelX ) bMover = false;
                        }
                    }
                    iCasTempX = iCasTempX + 1;
                }
            }
            
            // Movimiento hacia la derecha
            if( iCasAntY < iCasSelY ) {
                iCasTempY = iCasAntY + 1;
                while( bMover && iCasTempY <= iCasSelY ) {
                    if( iCasillas[iCasAntX][iCasTempY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasAntX][iCasTempY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempY != iCasSelY ) bMover = false;
                        }
                    }
                    iCasTempY = iCasTempY + 1;
                }
            }
            
            // Movimiento hacia la izquierda
            else if( iCasAntY > iCasSelY ) {
                iCasTempY = iCasAntY - 1;
                while( bMover && iCasTempY >= iCasSelY ) {
                    if( iCasillas[iCasAntX][iCasTempY].getPieza() == null )
                        bMover = true;
                    else {
                        if( iCasillas[iCasAntX][iCasTempY].getPieza().getColor() == bColor )
                            bMover = false;
                        else {
                            if( iCasTempY != iCasSelY ) bMover = false;
                        }
                    }
                    iCasTempY = iCasTempY - 1;
                }
            }
            
            return bMover;
            
    }

    
}
