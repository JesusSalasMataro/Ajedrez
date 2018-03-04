package ajedrez;

public class Caballo extends Pieza {
    
        public Caballo( boolean bCol ) {
        
            super(bCol);
            iCodigo = CABALLO;
            iValor = VALOR_CABALLO;
        
        }
        
        @Override
        public boolean MovimientoValido( int iCasAntX, int iCasAntY, int iCasSelX, int iCasSelY ) {
            
            return  ( ((Math.abs(iCasAntX - iCasSelX) == 1) && (Math.abs(iCasAntY - iCasSelY) == 2)) || 
                    ((Math.abs(iCasAntX - iCasSelX) == 2) && (Math.abs(iCasAntY - iCasSelY) == 1)) );
            
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
