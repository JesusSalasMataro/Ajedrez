package ajedrez;

public class Casilla implements Cloneable {
    
    private int iColumna, iFila;
    private Pieza pzaPieza;
    
    public Casilla( int iCol, int iFil, Pieza pzaP ) {

        iColumna = iCol;
        iFila = iFil;
        pzaPieza = pzaP;
        
    }
    
    public Casilla Clone() {
        
        Pieza pzaP = new Pieza();
        if( pzaPieza != null ) pzaP = pzaPieza.Clone();
        else pzaP = null;
        Casilla casClon = new Casilla(iColumna, iFila, pzaP);
        pzaP = null;
        return casClon;
        
    }
    
    public Pieza getPieza() { return pzaPieza; }
    public void setPieza( Pieza pzaP ) { pzaPieza = pzaP; }
    public int getColumna() { return iColumna; }
    public int getFila() { return iFila; }
    
}
