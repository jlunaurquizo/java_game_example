package battlegame;
import java.util.ArrayList;
import java.util.Random;

class Ejercito{
    // atributos
    private ArrayList<Soldado> misSoldados = new ArrayList<>();
    private String reino;
    // constructores
    Ejercito(String rei){
        reino = rei;
    }
    // metodos set y get
    public ArrayList<Soldado> getMisSoldados() {
        return misSoldados;
    }
    public void setMisSoldados(ArrayList<Soldado> misSoldados) {
        this.misSoldados = misSoldados;
    }
    public void setReino(String reino) {
        this.reino = reino;
    }
    public String getReino() {
        return reino;
    }
    // otros metodos
    public String toString(){
        String cadena = "";
        cadena = cadena + "\n" + "Ejercito de " + reino + ":\n";
        for(int i=0; i<misSoldados.size(); i++){
            cadena += misSoldados.get(i).toString() + "\n";
        }
        return cadena;
    }
    public void autogenerarSoldados(){
        Random rand = new Random();
        int numeroSoldados = rand.nextInt(10)+1;
        for(int i=0; i<numeroSoldados; i++){
            // atributos con valores similares
            int nivelAtaque = rand.nextInt(5)+1;
            int nivelDefensa = rand.nextInt(5)+1;
            int velocidad = rand.nextInt(3);
            // atributos especificos para cada tipo de soldado
            String nombre = "";
            int vidaActual = 0;
            // cantidad aleatoria por cada tipos de soldado
            int tipoSoldado = rand.nextInt(4);
            switch(tipoSoldado){
                case 0:
                    nombre = "Espadachin_"+i;
                    vidaActual = rand.nextInt(2)+3;
                    break;
                case 1:
                    nombre = "Arquero_"+i;
                    vidaActual = rand.nextInt(3)+1;
                    break;
                case 2:
                    nombre = "Caballero_"+i;
                    vidaActual = rand.nextInt(3)+3;
                    break;
                case 3:
                    nombre = "Lancero_"+1;
                    vidaActual = rand.nextInt(2)+1;
                    break;
                default:
                    break;
            }
            misSoldados.add(new Soldado(nombre, nivelAtaque, nivelDefensa, vidaActual, velocidad, tipoSoldado));
        }
    }
    /*public int buscarSoldadoMayorVida(){
        int indiceMaximo = 0, maximo = 0;
        for(int i=0; i<misSoldados.size(); i++){
            if(misSoldados.get(i).getVidaActual() > maximo){
                indiceMaximo = i;
                maximo = misSoldados.get(i).getVidaActual();
            }
        }
        return indiceMaximo;
    }
    public void verRankingPoder(){ //ordenamiento Burbuja por nivel de vida
        ArrayList<Soldado> copiaSoldados = new ArrayList<>(misSoldados);
        Soldado temp;
        for(int i=1; i<copiaSoldados.size(); i++){
            for(int j=0; j<copiaSoldados.size()-1; j++){
                if(copiaSoldados.get(j).getVidaActual() < copiaSoldados.get(i).getVidaActual()){
                    temp = copiaSoldados.get(i);
                    copiaSoldados.set(i, copiaSoldados.get(j));
                    copiaSoldados.set(j, temp);
                }
            }
        }
        System.out.println(ANSI_BLUE + "\nRanking de Poder (Por nivel de vida):" + ANSI_RESET);
        for(int i=0; i<copiaSoldados.size(); i++){
            System.out.println(copiaSoldados.get(i).toString());
        }
    }
    public boolean estaVacio(){
        boolean vacio = true;
        for(int i=0; i<misSoldados.size(); i++){
            if(misSoldados.get(i).getVive() == true)
                vacio = false;
        }
        return vacio;
    }
    public float promedioPuntosVida(){
        float sumaPuntos = 0;
        float numSoldados = misSoldados.size();
        for(int i=0; i<numSoldados; i++){
            sumaPuntos += misSoldados.get(i).getVidaActual();
        }
        float promedio = sumaPuntos/numSoldados;
        return promedio;
    }
    public void mostrarDatos(){
        // Mostrar datos de los ejercitos
        System.out.println(toString());
        System.out.println(ANSI_BLUE + "El soldado con mas puntos de vida es " + ANSI_RESET);
        System.out.println(misSoldados.get(buscarSoldadoMayorVida()).toString());
        System.out.println(ANSI_BLUE + "El promedio de puntos de vida del ejercito es " + ANSI_RESET + promedioPuntosVida());
        verRankingPoder();
    }
    /*public ArrayList<Soldado2> ordenarEjercitoSeleccion(){
        ArrayList<Soldado2> copiaArmy = new ArrayList<>(soldados);
        Soldado2 menor, temp;
        int pos;
        for(int i=0; i<copiaArmy.size(); i++){
            menor = copiaArmy.get(i);
            pos = i;
            for(int j=i+1; j<copiaArmy.size(); j++){
                if(copiaArmy.get(j).getVidaActual() > menor.getVidaActual()){
                    menor = copiaArmy.get(j);
                    pos = j;
                }
            }
            if(pos != i){
                temp = copiaArmy.get(i);
                copiaArmy.set(i, copiaArmy.get(pos));
                copiaArmy.set(pos, temp);
            }
        }
        return copiaArmy;
    }*/
    /*public void buscarSoldadoMayorNivelAtaque(){
        int indiceMaximo = 0, maximo = 0;
        for(int i=0; i<misSoldados.size(); i++){
            if(misSoldados.get(i).getNivelAtaque() > maximo){
                indiceMaximo = i;
                maximo = misSoldados.get(i).getNivelAtaque();
            }
        }
        System.out.println(ANSI_BLUE + "El soldado con mayor nivel de ataque es:\n" + ANSI_RESET + misSoldados.get(indiceMaximo).toString());
    }*/
    /*public void mostrarEjercito(){
        for(int i=0; i<soldados.size(); i++){
            soldados.get(i).imprimirSoldado();
        }
    }*/
    /*public void mostrarEjercito(ArrayList<Soldado2> army){
        for(int i=0; i<army.size(); i++){
            army.get(i).imprimirSoldado();
        }
    }*/
}