package ajedrez;

public class Partida {
    
    //public static final int NINGUNO = 2;
    public static final boolean BLANCAS = true;
    public static final boolean NEGRAS = false;
    
    public boolean bJaqueReyBlanco, bJaqueReyNegro;
    public boolean bMateReyBlanco, bMateReyNegro;
    
    private boolean bTurno;
    
    // Constructor de la clase, establece el primer turno para las blancas
    public Partida() { 
    
        bTurno = BLANCAS; 
        bJaqueReyBlanco = false;
        bJaqueReyNegro = false;
        bMateReyBlanco = false;
        bMateReyNegro = false;
    
    }
   
    // Devuelve el turno actual: BLANCAS (true) o NEGRAS (false)
    public boolean getTurno() { return bTurno; }
    
    // Cambia el turno actual
    public void cambiaTurno() { 
    
        if( bTurno == BLANCAS ) bTurno = NEGRAS;
        else bTurno = BLANCAS;
        
    }
    
    public void setJaqueReyBlanco( boolean bJaque ) { bJaqueReyBlanco = bJaque; }
    public void setJaqueReyNegro( boolean bJaque ) { bJaqueReyNegro = bJaque; }
    
    // Devuelve si el rey por el que se pregunta está en jaque
    public boolean ReyEnJaque( boolean bColorRey ) { 
        
        boolean bResultado;
        
        if( bColorRey == BLANCAS ) bResultado = bJaqueReyBlanco;
        else bResultado = bJaqueReyNegro;
        
        return bResultado;
        
    }
    
}
