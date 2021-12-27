package battlegame;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Soldado{
    // atributos
    String nombre;
    int nivelAtaque, nivelDefensa, vidaActual;
    int velocidad;
    int fila, columna;
    boolean vive;
    String[] tipos = {"Espadachin", "Arquero", "Caballero", "Lancero"};
    int tipo;
    // otras variables
    public Random rand = new Random();
    // constructores
    public Soldado(String nomb, int nivelAtaq, int nivelDef, int vida, int veloc, int tip){
        nombre = nomb;
        nivelAtaque = nivelAtaq;
        nivelDefensa = nivelDef;
        vidaActual = vida;
        velocidad = veloc;
        tipo = tip;
    }
    // metodos set y get
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNivelAtaque(int ataque) {
        nivelAtaque = ataque;
    }
    public int getNivelAtaque() {
        return nivelAtaque;
    }
    public void setNivelDefensa(int defensa) {
        nivelDefensa = defensa;
    }
    public int getNivelDefensa() {
        return nivelDefensa;
    }
    public void setVidaActual(int life){
        vidaActual = life;
    }
    public int getVidaActual(){
        return vidaActual;
    }
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    public int getVelocidad() {
        return velocidad;
    }
    public void setFila(int fil) {
        fila = fil;
    }
    public int getFila() {
        return fila;
    }
    public void setColumna(int col) {
        columna = col;
    }
    public int getColumna() {
        return columna;
    }
    public boolean getVive(){
        return vive;
    }
    public void setVive(boolean vive) {
        this.vive = vive;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public int getTipo() {
        return tipo;
    }
    // otros metodos
    /*public void atacar(){
        actitud = "ofensiva";
        avanzar();
    }
    public void defender(){
        actitud = "defensiva";
        velocidad = 0;
    }
    public void avanzar(){
        velocidad++;
    }
    public void retroceder(){
        if(velocidad > 0){
            velocidad = 0;
            actitud = "defensiva";
        }
        else if(velocidad == 0){
            velocidad = -1;
        }
    }
    public void serAtacado(){
        nivelVida--;
        if(nivelVida <= 0){
            morir();
        }
    }
    public void huir(){
        actitud = "fuga";
        velocidad += 2;
    }
    public void morir(){
        vive = false;
    }
    public String toString(){
        return " - " + nombre + ":\t Nivel de Vida: " + vidaActual + "\t Ataque: " + nivelAtaque + "\t Defensa: " + nivelDefensa + 
        "\tVelocidad: " + velocidad + "\t Actitud: " + actitud + "\t Posicion: " + fila + "," + columna;
    }*/
}
