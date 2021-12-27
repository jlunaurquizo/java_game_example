package battlegame;

public class Posicion {
    int fila, columna;
    Posicion(int a, int b){
        fila = a;
        columna = b;
    }
    public void setFila(int fila) {
        this.fila = fila;
    }
    public void setColumna(int columna) {
        this.columna = columna;
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
}
