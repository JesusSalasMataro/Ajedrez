package ajedrez;

public class Tablero {

    // Columnas, filas del tablero
    private Casilla[][] iCasillas;
    private Casilla casReyBlanco;
    private Casilla casReyNegro;
    
    // Casillas de la última jugada realizada por si hay que deshacerla
    private Casilla casAntInicial, casAntFinal;
    private Casilla casQueHaceJaque;
    private boolean bReyBlancoMovido, bReyNegroMovido;
    private int iNumJaques;
    
    
    public Tablero() {
        
        // Casilla[columna][fila]
        iCasillas = new Casilla[8][8];
        int i, ii;
        
        // Colocación inicial piezas negras
        iCasillas[0][0] = new Casilla(0, 0, new Torre(Pieza.NEGRAS));
        iCasillas[1][0] = new Casilla(1, 0, new Caballo(Pieza.NEGRAS));
        iCasillas[2][0] = new Casilla(2, 0, new Alfil(Pieza.NEGRAS));
        iCasillas[3][0] = new Casilla(3, 0, new Dama(Pieza.NEGRAS));
        iCasillas[4][0] = new Casilla(4, 0, new Rey(Pieza.NEGRAS));
        iCasillas[5][0] = new Casilla(5, 0, new Alfil(Pieza.NEGRAS));
        iCasillas[6][0] = new Casilla(6, 0, new Caballo(Pieza.NEGRAS));
        iCasillas[7][0] = new Casilla(7, 0, new Torre(Pieza.NEGRAS));
        
        casReyNegro = iCasillas[4][0];
        
        // Peones negras
        for( i=0; i<8; i++ ) {
            iCasillas[i][1] = new Casilla(i, 1, new Peon(Pieza.NEGRAS));
        }
        
        // Colocación inicial piezas blancas
        iCasillas[0][7] = new Casilla(0, 7, new Torre(Pieza.BLANCAS));
        iCasillas[1][7] = new Casilla(1, 7, new Caballo(Pieza.BLANCAS));
        iCasillas[2][7] = new Casilla(2, 7, new Alfil(Pieza.BLANCAS));
        iCasillas[3][7] = new Casilla(3, 7, new Dama(Pieza.BLANCAS));
        iCasillas[4][7] = new Casilla(4, 7, new Rey(Pieza.BLANCAS));
        iCasillas[5][7] = new Casilla(5, 7, new Alfil(Pieza.BLANCAS));
        iCasillas[6][7] = new Casilla(6, 7, new Caballo(Pieza.BLANCAS));
        iCasillas[7][7] = new Casilla(7, 7, new Torre(Pieza.BLANCAS));
        
        casReyBlanco = iCasillas[4][7];
        
        // Peones blancas
        for( i=0; i<8; i++ ) {
            iCasillas[i][6] = new Casilla(i, 6, new Peon(Pieza.BLANCAS));
        }
        
        // Casillas vacías centro del tablero
        for( i=2; i<6; i++ ) {
            for( ii=0; ii<8; ii++) {
                iCasillas[ii][i] = new Casilla(ii, i, null);
            }
        }
        //iCasillas[4][4] = new Casilla(4,4,new Rey(Pieza.NEGRAS));
        //casReyNegro = iCasillas[4][4];
        
    }
    
    public void MoverPieza( int iCasIniX, int iCasIniY, int iCasFinX, int iCasFinY ) {
        
        casAntInicial = iCasillas[iCasIniX][iCasIniY].Clone();
        casAntFinal = iCasillas[iCasFinX][iCasFinY].Clone();
        
        bReyBlancoMovido = false;
        bReyNegroMovido = false;
        
        if( iCasillas[iCasIniX][iCasIniY].getPieza().getCodigo() == Pieza.REY ) {
            if( iCasillas[iCasIniX][iCasIniY].getPieza().getColor() == Pieza.BLANCAS ) {
                iCasillas[iCasFinX][iCasFinY].setPieza(iCasillas[iCasIniX][iCasIniY].getPieza());
                iCasillas[iCasIniX][iCasIniY].setPieza(null);
                casReyBlanco = iCasillas[iCasFinX][iCasFinY];
                bReyBlancoMovido = true;
            }
            else {
                iCasillas[iCasFinX][iCasFinY].setPieza(iCasillas[iCasIniX][iCasIniY].getPieza());
                iCasillas[iCasIniX][iCasIniY].setPieza(null);
                casReyNegro = iCasillas[iCasFinX][iCasFinY];
                bReyNegroMovido = true;
            }
        }
        else {        
            iCasillas[iCasFinX][iCasFinY].setPieza(iCasillas[iCasIniX][iCasIniY].getPieza());
            iCasillas[iCasIniX][iCasIniY].setPieza(null);
        }
        
    }
    
    public void DeshacerUltimoMovimiento() {
        
        iCasillas[casAntInicial.getColumna()][casAntInicial.getFila()] = casAntInicial.Clone();
        iCasillas[casAntFinal.getColumna()][casAntFinal.getFila()] = casAntFinal.Clone();
        
        if( bReyBlancoMovido )
            casReyBlanco = iCasillas[casAntInicial.getColumna()][casAntInicial.getFila()];
        if( bReyNegroMovido )
            casReyNegro = iCasillas[casAntInicial.getColumna()][casAntInicial.getFila()];
       
    }
    
    // Comprueba si hay alguna pieza haciendo jaque al rey en la posición actual
    public boolean HayJaque( boolean iRey ) {
        
        Casilla casRey;
        boolean bJaqueFilaColumna, bJaqueDiagonales, bJaqueCaballos, bJaquePeon, bReyAdyacente;
        
        if( iRey == Partida.BLANCAS ) casRey = casReyBlanco;
        else casRey = casReyNegro;
        
        // Comprobamos los diferentes jaques posibles por separado para así poder contar
        // el número de jaques (propiedad iNumJaques) para en los casos de jaque múltiple
        // poder calcular más eficientemente las alternativas de movimiento del rey.
        bJaqueFilaColumna = JaqueFilaColumna(casRey);
        bJaqueDiagonales = JaqueDiagonales(casRey);
        bJaqueCaballos = JaqueCaballos(casRey);
        bJaquePeon = JaquePeon(casRey);
        bReyAdyacente = ReyAdyacente(casRey);

        return( bJaqueFilaColumna || bJaqueDiagonales ||
                bJaqueCaballos || bJaquePeon || bReyAdyacente );
        
    }
    
    public boolean JaqueDoble( ) {
        return (iNumJaques == 2);
    }
    
    public boolean JaqueFilaColumna( Casilla casRey ) {
        
        int iFil, iCol;
        boolean bColorRey;
        boolean bFinCamino;
        boolean bJaque = false;
        
        // Nos indicará si se trata de un jaque doble, lo cual nos será útil para el
        // algoritmo de calcular el jaque mate, ya que ante un jaque doble la única
        // defensa que tiene el rey es moverse.
        iNumJaques = 0;
                    
        // Comprobaremos a continuación si existe alguna pieza enemiga amenazando
        // al rey en la misma fila o columna (dama o torre)
        bColorRey = casRey.getPieza().getColor();
        iFil = casRey.getFila();
        bFinCamino = false;
        
        // Comprobamos la fila hacia la izquierda
        iCol = casRey.getColumna() - 1;
        while( !bJaque && !bFinCamino && (iCol >= 0) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.TORRE) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iCol = iCol - 1;
        }
  
        // Comprobamos la fila hacia la derecha
        iCol = casRey.getColumna() + 1;
        bFinCamino = false;
        while( !bJaque && !bFinCamino && (iCol <= 7) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.TORRE) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iCol = iCol + 1;
        }

        // Comprobamos la columna hacia arriba
        iCol = casRey.getColumna();
        iFil = casRey.getFila() - 1;
        while( !bJaque && !bFinCamino && (iFil >= 0) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.TORRE) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iFil = iFil - 1;
        }
  
        // Comprobamos la columna hacia abajo
        iFil = casRey.getFila() + 1;
        bFinCamino = false;
        while( !bJaque && !bFinCamino && (iFil <= 7) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.TORRE) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iFil = iFil + 1;
        }
        
        return bJaque;
        
    }
    
    public boolean JaqueDiagonales( Casilla casRey ) {
        
        int iFil, iCol;
        boolean bColorRey;
        boolean bFinCamino;
        boolean bJaque = false;
        
      // Comprobaremos a continuación si existe alguna pieza enemiga amenazando
        // al rey a través de las diagonales (dama o alfil)
        bFinCamino = false;
        bColorRey = casRey.getPieza().getColor();
        
        // Comprobamos la diagonal superior izquierda
        iFil = casRey.getFila() - 1;
        iCol = casRey.getColumna() - 1;
        while( !bJaque && !bFinCamino && (iCol >= 0) && (iFil >= 0) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.ALFIL) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iFil = iFil - 1;
            iCol = iCol - 1;
        }

        // Comprobamos la diagonal superior derecha
        bFinCamino = false;
        iFil = casRey.getFila() - 1;
        iCol = casRey.getColumna() + 1;
        while( !bJaque && !bFinCamino && (iCol <= 7) && (iFil >= 0) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.ALFIL) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iFil = iFil - 1;
            iCol = iCol + 1;
        }

        // Comprobamos la diagonal inferior izquierda
        bFinCamino = false;
        iFil = casRey.getFila() + 1;
        iCol = casRey.getColumna() - 1;
        while( !bJaque && !bFinCamino && (iCol >= 0) && (iFil <= 7) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.ALFIL) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iFil = iFil + 1;
            iCol = iCol - 1;
        }

        // Comprobamos la diagonal inferior derecha
        bFinCamino = false;
        iFil = casRey.getFila() + 1;
        iCol = casRey.getColumna() + 1;
        while( !bJaque && !bFinCamino && (iCol <= 7) && (iFil <= 7) ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getColor() == bColorRey ) {
                    bFinCamino = true;
                }
                else {
                    if( (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.ALFIL) ||
                        (iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.DAMA) ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                    else bFinCamino = true;
                }
            }
            iFil = iFil + 1;
            iCol = iCol + 1;
        }

        return bJaque;
        
    }
    
    public boolean JaqueCaballos( Casilla casRey ) {
        
        int iFil, iCol, iFilTemp, iColTemp;
        boolean bColorRey;
        boolean bJaque = false;
                    
        // Comprobaremos a continuación si existe algún caballo enemigo
        // amenazando al rey desde las 8 posiciones posibles
        bColorRey = casRey.getPieza().getColor();
        iFil = casRey.getFila();
        iCol = casRey.getColumna();
        
        iFilTemp = iFil - 1;
        iColTemp = iCol - 2;
        if( iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil - 2;
        iColTemp = iCol - 1;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil - 1;
        iColTemp = iCol + 2;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil - 2;
        iColTemp = iCol + 1;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil + 1;
        iColTemp = iCol - 2;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil + 2;
        iColTemp = iCol - 1;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil + 1;
        iColTemp = iCol + 2;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }
        
        iFilTemp = iFil + 2;
        iColTemp = iCol + 1;
        if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
            if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.CABALLO &&
                    iCasillas[iColTemp][iFilTemp].getPieza().getColor() != bColorRey ) {
                        bJaque = true;
                        casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                        iNumJaques++;
                }
            }
        }

        return bJaque;
    
    }
    
    public boolean JaquePeon( Casilla casRey ) {
        
        int iFil, iCol, iFilTemp, iColTemp;
        boolean bColorRey;
        boolean bJaque = false;
                    
        // Comprobaremos a continuación si existe alguna pieza enemiga amenazando
        // al rey en la misma fila o columna (dama o torre)
        bColorRey = casRey.getPieza().getColor();
        iFil = casRey.getFila();
        iCol = casRey.getColumna();
        
        if( bColorRey == Pieza.BLANCAS ) {
            iFilTemp = iFil - 1;
            iColTemp = iCol - 1;
            if( iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                    if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.PEON &&
                        iCasillas[iColTemp][iFilTemp].getPieza().getColor() == Pieza.NEGRAS ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                }
            }
            iColTemp = iCol + 1;
            if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                    if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.PEON &&
                        iCasillas[iColTemp][iFilTemp].getPieza().getColor() == Pieza.NEGRAS ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                }
            }
        }
        else {
            iFilTemp = iFil + 1;
            iColTemp = iCol - 1;
            if( iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                    if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.PEON &&
                        iCasillas[iColTemp][iFilTemp].getPieza().getColor() == Pieza.BLANCAS ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                }
            }
            iColTemp = iCol + 1;
            if( !bJaque && iColTemp >= 0 && iColTemp <= 7 && iFilTemp >= 0 && iFilTemp <= 7 ) {
                if( iCasillas[iColTemp][iFilTemp].getPieza() != null ) {
                    if( iCasillas[iColTemp][iFilTemp].getPieza().getCodigo() == Pieza.PEON &&
                        iCasillas[iColTemp][iFilTemp].getPieza().getColor() == Pieza.BLANCAS ) {
                            bJaque = true;
                            casQueHaceJaque = iCasillas[iCol][iFil].Clone();
                            iNumJaques++;
                    }
                }
            }            
        }
        
        return bJaque;
        
    }
    
    public boolean ReyAdyacente( Casilla casRey ) {
        
        int iFil, iCol;
        boolean bReyAdyacente = false;
        
        // Casilla superior izquierda al rey
        iFil = casRey.getFila() - 1;
        iCol = casRey.getColumna() - 1;
        if( iFil >= 0 && iCol >= 0 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla superior al rey
        iCol++;
        if( !bReyAdyacente && iFil >= 0 && iCol <= 7 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla superior derecha al rey
        iCol++;
        if( !bReyAdyacente && iFil >= 0 && iCol <= 7 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla izquierda al rey
        iFil = casRey.getFila();
        iCol = casRey.getColumna() - 1;
        if( !bReyAdyacente && iCol >= 0 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla derecha al rey
        iFil = casRey.getFila();
        iCol = casRey.getColumna() + 1;
        if( !bReyAdyacente && iCol <= 7 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla inferior izquierda al rey
        iFil = casRey.getFila() + 1;
        iCol = casRey.getColumna() - 1;
        if( iFil <= 7 && iCol >= 0 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla inferior al rey
        iCol++;
        if( !bReyAdyacente && iFil <= 7 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        // Casilla inferior derecha al rey
        iCol++;
        if( !bReyAdyacente && iFil <= 7 && iCol <= 7 ) {
            if( iCasillas[iCol][iFil].getPieza() != null ) {
                if( iCasillas[iCol][iFil].getPieza().getCodigo() == Pieza.REY ) {
                    bReyAdyacente = true;
                }
            }
        }
        
        return bReyAdyacente;
        
    }
    
    public boolean JaqueMate( boolean iRey ) {
        
        Casilla casRey;
        boolean bJaqueMate = false;
        
        if( iRey == Partida.BLANCAS ) casRey = casReyBlanco.Clone();
        else casRey = casReyNegro.Clone();
        
        // Si el jaque es doble la única salvación que tiene el rey es moverse.
        // Comprobamos si moviéndose a alguna casilla adyacente pasa a estar
        // a salvo de jaque.
        if( JaqueDoble() ) {
            if( !ReyPuedeMover(iRey, casRey) ) bJaqueMate = true;
        }
        // Si el jaque es simple el rey puede salvar el jaque moviéndose o 
        // cubriéndose por otra pieza
        else {
            if( !ReyPuedeMover(iRey, casRey) && !PosibleCubrirJaque(iRey, casRey) ) {
                bJaqueMate = true;
            }
        }
        
        return bJaqueMate;
        
    }
    
    public boolean ReyPuedeMover( boolean iRey, Casilla casRey ) {
        
        int iColRey, iFilaRey, iCol, iFila;
        boolean bPuedeMover = false;
        
        iColRey = casRey.getColumna();
        iFilaRey = casRey.getFila();
        
        // Comprobamos si puede mover a la casilla superior izquierda
        iCol = iColRey - 1;
        iFila = iFilaRey - 1;
        if( iCol >= 0 && iFila >= 0 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }
        
        // Comprobamos si puede mover a la casilla superior
        iCol++;
        if( !bPuedeMover && iFila >= 0 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }

        // Comprobamos si puede mover a la casilla superior derecha
        iCol++;
        if( !bPuedeMover && iCol <= 7 && iFila >= 0 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }
 
        // Comprobamos si puede mover a la casilla iquierda
        iCol = iColRey - 1;
        iFila = iFilaRey;
        if( !bPuedeMover && iCol >= 0 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }
        
        // Comprobamos si puede mover a la casilla derecha
        iCol = iColRey + 1;
        iFila = iFilaRey;
        if( !bPuedeMover && iCol <= 7 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }
        
        // Comprobamos si puede mover a la casilla inferior izquierda
        iCol = iColRey - 1;
        iFila = iFilaRey + 1;
        if( iCol >= 0 && iFila <= 7 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }
        
        // Comprobamos si puede mover a la casilla inferior
        iCol++;
        if( !bPuedeMover && iFila <= 7 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }

        // Comprobamos si puede mover a la casilla inferior derecha
        iCol++;
        if( !bPuedeMover && iCol <= 7 && iFila <= 7 ) {
            MoverPieza(iColRey, iFilaRey, iCol, iFila);
            if( !HayJaque(iRey) ) bPuedeMover = true;
            DeshacerUltimoMovimiento();
        }
        
        return bPuedeMover;
        
    }
    
    public boolean PosibleCubrirJaque( boolean iRey, Casilla casRey ) {
        
        boolean bPosibleCubrirJaque = false;
        
        
        return bPosibleCubrirJaque;
        
    }

    public Casilla[][] getCasillas() { return iCasillas; }
    
    public Casilla getCasilla( int iCol, int iFila ) {
        return iCasillas[iCol][iFila];
    }
    
    public Casilla getCasReyBlanco() { return casReyBlanco; }
    public Casilla getCasReyNegro() { return casReyNegro; }
    
}
