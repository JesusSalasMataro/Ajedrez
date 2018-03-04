package ajedrez;

public class Dama extends Pieza {
    
        public Dama( boolean bCol ) {
        
            super(bCol);
            iCodigo = DAMA;
            iValor = VALOR_DAMA;
        
        }

        @Override
        public boolean MovimientoValido( int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            return  (   (   (iCasAntX != iCasSelX && iCasAntY == iCasSelY) ||   // Movimiento horizontal o vertical
                            iCasAntX == iCasSelX && iCasAntY != iCasSelY
                        )
                        ||
                        (   (Math.abs(iCasAntX - iCasSelX) >= 1) &&        // Movimiento diagonal
                            (Math.abs(iCasAntX - iCasSelX) == Math.abs(iCasAntY - iCasSelY))
                        )
                    );       
        }
        
        @Override
        public boolean MoverPosible( Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            boolean bMover;
            
            if( iCasAntX != iCasSelX && iCasAntY != iCasSelY ) 
                bMover = ComprobarMovimientoDiagonal(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY);
            else 
                bMover = ComprobarMovimientoHorizontalVertical(tabTablero, iCasAntX, iCasAntY, iCasSelX, iCasSelY);            
            
            return bMover;
            
        }
        
}
