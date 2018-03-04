package ajedrez;

public class Torre extends Pieza {
    
        public Torre( boolean bCol ) {
        
            super(bCol);
            iCodigo = TORRE;
            iValor = VALOR_TORRE;
        
        }
        
        @Override
        public boolean MovimientoValido( int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            return  ( (iCasAntX != iCasSelX && iCasAntY == iCasSelY) ||
                       iCasAntX == iCasSelX && iCasAntY != iCasSelY);
            
        }
        
        @Override
        public boolean MoverPosible( Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {

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
