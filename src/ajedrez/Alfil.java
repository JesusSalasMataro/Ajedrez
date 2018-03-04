package ajedrez;

public class Alfil extends Pieza {
    
        public Alfil( boolean bCol ) {
        
            super(bCol);
            iCodigo = ALFIL;
            iValor = VALOR_ALFIL;
        
        }
        
        @Override
        public boolean MovimientoValido( int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            return  ( (Math.abs(iCasAntX - iCasSelX) >= 1) && 
                      (Math.abs(iCasAntX - iCasSelX) == Math.abs(iCasAntY - iCasSelY)) );
            
        }
        
        @Override
        public boolean MoverPosible( Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
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
   
}
