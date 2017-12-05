package ajedrez;

public class Rey extends Pieza {
    
        public Rey( boolean bCol ) {
        
            super(bCol);
            iCodigo = REY;
            iValor = 99;
        
        }
 
        @Override
        public boolean MovimientoValido( int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            return  (   (Math.abs(iCasAntX - iCasSelX) == 1) ||
                        (Math.abs(iCasAntY - iCasSelY) == 1)
                    );       
        }
        
        @Override
        public boolean MoverPosible( Tablero tabTablero, int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            Casilla[][] iCasillas = tabTablero.getCasillas();
            boolean bMover = false;
            
            if( iCasillas[iCasSelX][iCasSelY].getPieza() == null ) {
                bMover = true;
            }
            else {
                if( iCasillas[iCasSelX][iCasSelY].getPieza().getColor() != bColor )
                    bMover = true;
            }
            
            return bMover;            
            
        }

}
