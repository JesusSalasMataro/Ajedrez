package ajedrez;

public class Peon extends Pieza {
    
        private boolean bMovimientoDiagonal;
    
        public Peon( boolean bCol ) {
        
            super(bCol);
            iCodigo = PEON;
            iValor = VALOR_PEON;
            bMovimientoDiagonal = false;
        
        }
        
        @Override
        public boolean MovimientoValido( 
            int iColInicial, int iFilaInicial, int iColFinal, int iFilaFinal ) {
            
            boolean bValido = false;
            bMovimientoDiagonal = false;
            
            // Las blancas siempre estarán abajo, por tanto los peones blancos
            // solo podrán mover hacia arriba
            if( bColor == BLANCAS ) {
                // El peón puede avanzar una o dos casillas máximo
                if( iFilaFinal == iFilaInicial - 1 ) {
                    // Comprobamos si se trata de un avance
                    if( iColInicial == iColFinal ) {
                        bValido = true;                       
                    }
                    // Movimiento en diagonal, se trataría de matar una pieza
                    else {
                        if( Math.abs(iColInicial - iColFinal) == 1 ) {
                            bValido = true;
                            bMovimientoDiagonal = true;
                        }                       
                    }
                }
                // Avance de dos casillas
                else {
                    
                    if( (iFilaFinal == iFilaInicial - 2) && (iFilaInicial == 6) && 
                        (iColInicial == iColFinal) ) {
                            bValido = true;
                    }
                }
                
            }
            // Movimiento de peón de las negras
            else {
                // El peón puede avanzar una o dos casillas máximo
                if( iFilaFinal == iFilaInicial + 1 ) {
                    // Comprobamos si se trata de un avance
                    if( iColInicial == iColFinal ) {
                        bValido = true;                       
                    }
                    // Movimiento en diagonal, se trataría de matar una pieza
                    else {
                        if( Math.abs(iColInicial - iColFinal) == 1 ) {
                            bValido = true;
                            bMovimientoDiagonal = true;
                        }                       
                    }
                }
                // Avance de dos casillas
                else {
                    
                    if( (iFilaFinal == iFilaInicial + 2) && (iFilaInicial == 1) && 
                        (iColInicial == iColFinal) ) {
                            bValido = true;
                    }
                }
            }
            
            return bValido;
        }
        
        @Override
        public boolean MoverPosible( Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            Casilla[][] iCasillas = tabTablero.getCasillas();
            boolean bMover = false;
            
            if( bColor == BLANCAS ) {                
                if( MovimientoDiagonal() ) {
                    if( iCasillas[iCasSelX][iCasSelY].getPieza() != null ) {
                        if( iCasillas[iCasSelX][iCasSelY].getPieza().getColor() == NEGRAS )
                            bMover = true;
                    }
                }
                else {
                    // Avance de una casilla
                    if( (iCasAntY - iCasSelY) == 1 ) {
                        if( iCasillas[iCasSelX][iCasSelY].getPieza() == null )
                            bMover = true;
                    }
                    // Avance de dos casillas
                    else {
                        if( (iCasillas[iCasSelX][iCasSelY].getPieza() == null) &&
                            (iCasillas[iCasSelX][iCasSelY+1].getPieza() == null) )
                                bMover = true;
                    }
                }
            } 
            else {                
                if( MovimientoDiagonal() ) {
                    if( iCasillas[iCasSelX][iCasSelY].getPieza() != null ) {
                        if( iCasillas[iCasSelX][iCasSelY].getPieza().getColor() == BLANCAS )
                            bMover = true;
                    }
                }
                else {
                    // Avance de una casilla
                    if( (iCasSelY - iCasAntY) == 1 ) {
                        if( iCasillas[iCasSelX][iCasSelY].getPieza() == null )
                            bMover = true;
                    }
                    // Avance de dos casillas
                    else {
                        if( (iCasillas[iCasSelX][iCasSelY].getPieza() == null) &&
                            (iCasillas[iCasSelX][iCasSelY-1].getPieza() == null) )
                                bMover = true;
                    }
                }
            } 
            
            return bMover;
        }
        
        // Después de haber ejecutado un movimiento válido esta función informa
        // de si se ha tratado de un movimiento para matar una pieza contaria
        public boolean MovimientoDiagonal() {
            
            return bMovimientoDiagonal;
            
        }

    
}
