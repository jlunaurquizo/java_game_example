package battlegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game extends JFrame{
    // elementos del juego
    static Ejercito ejercito1, ejercito2;
    static String reino1, reino2;
    static Soldado[][] tablero;
    static JButton[][] casilleros;
    static JPanel panelInterfaz, panelTitulo, panelJugador1, panelJugador2, panelCampoBatalla; 
    static JButton mover1, atacar1, rendirSoldado1, rendirEj1, huir1, defender1, mover2, atacar2, rendirSoldado2, rendirEj2, huir2, defender2;
    static JLabel labelMensajes, labelTurno;
    static JTextArea log;
    static JDialog dialogoResultado;
    static int turno=1;
    // 0 inicio turno, 1 seleccion soldado, 2 seleccion accion, 3 seleccion destino y mover, 4 cambiar turno
    static String[] estados = {"Seleccione un soldado de su ejercito","Seleccione la acción a realizar", "Seleccione la posición objetivo o destino", "Turno terminado"};
    static int estado=0; 
    static Posicion posOrigen, posDestino;

    static JFrame principal = new JFrame();
    
    public Game(String r1, String r2){
        reino1 = r1;
        reino2 = r2;
        setTitle("Juego de Batallas");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarJuego();
        createContents();
        setVisible(true);
    }
    private class Listener implements ActionListener{
        public void actionPerformed(ActionEvent evento){
            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    if(evento.getSource() == casilleros[i][j]){
                        if(estado==0){
                            verificarCasilleroOrigen(i, j);
                        }
                        else if(estado==2){
                            verificarCasilleroDestino(i,j);
                        }
                        else if(estado==5){
                            verificarCasilleroEnemigo(i, j);
                        }
                    }
                }
            }
            if(evento.getSource()==mover1 || evento.getSource()==mover2){
                if(estado==1){
                    estado=2;
                    System.out.println("Estado 2");
                    labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
                    labelMensajes.setText("");
                }
            }
            else if(evento.getSource()==atacar1 || evento.getSource()==atacar2){
                if(estado==1){
                    estado=5;
                    System.out.println("Estado 5 ataque");
                    labelTurno.setText("Turno del jugador "+ turno + ": " + estados[2]);
                    labelMensajes.setText("");
                }
            }
            else if(evento.getSource()==huir1 || evento.getSource()==huir2){
                if(estado==6){
                    posOrigen = posDestino;
                    System.out.println("Estado 6b huir");
                    labelTurno.setText("Seleccione la ubicación a la que huir (perdera 1 turno)");
                    labelMensajes.setText("");
                    forzarTurno();
                    estado=2;
                }
            }
            else if(evento.getSource()==defender1 || evento.getSource()==defender2){
                if(estado==6){
                    System.out.println("Estado 6b defender");
                    labelTurno.setText("Turno del jugador "+ turno + ": atacando.");
                    labelMensajes.setText("");
                    atacar();
                    cambiarTurno();
                }
            }
            else if(evento.getSource()==rendirSoldado1 || evento.getSource()==rendirSoldado2){
                if(estado==1){
                    eliminarSoldado(posOrigen);
                    estado = 4;
                    System.out.println("Estado 4");
                    deshabilitarMenuAcciones();
                    cambiarTurno();
                }
            }
            else if(evento.getSource()==rendirEj1 || evento.getSource()==rendirEj2){
                rendirEjercito();
            }
        }
    }
    private void createContents(){
        System.out.println("Inicializando interfaz...");
        // crear elementos aqui
        panelInterfaz = new JPanel(new BorderLayout());
        panelInterfaz.setPreferredSize(new Dimension(1100,700));
        panelTitulo = new JPanel();
        JPanel panelJugador1 = new JPanel();
        JPanel panelJugador2 = new JPanel();
        panelCampoBatalla = new JPanel(new GridLayout(10,10));
        Listener listener = new Listener();
        // panel titulo
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        JLabel labelTitulo = new JLabel("Batalla");
        labelTitulo.setFont(new Font("Verdana", Font.PLAIN, 20));
        labelTitulo.setPreferredSize(new Dimension(1050,50));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(labelTitulo);
        labelTurno = new JLabel("Turno del jugador "+ turno + ": " + estados[estado]);
        labelTurno.setFont(new Font("Verdana", Font.PLAIN, 14));
        labelTurno.setForeground(new Color(0,153,0));
        panelTitulo.add(labelTurno);
        labelMensajes = new JLabel("");
        labelMensajes.setFont(new Font("Verdana", Font.PLAIN, 13));
        labelMensajes.setForeground(Color.RED);
        panelTitulo.add(labelMensajes);
        // panel Campo Batalla
        casilleros = new JButton[10][10];
        panelCampoBatalla.setPreferredSize(new Dimension(750,500));
        Icon iconCaballero = new ImageIcon( getClass().getResource("i_caballero_small.png") );
        Icon iconArquero = new ImageIcon( getClass().getResource("i_arquero_small.png") );
        Icon iconEspadachin = new ImageIcon( getClass().getResource("i_espadachin_small.png") );
        Icon iconSoldado = new ImageIcon( getClass().getResource("i_soldado_small.png") );
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(tablero[i][j] != null){
                    Icon icono;
                    int tipo = tablero[i][j].getTipo();
                    if(tipo==0){
                        icono = iconEspadachin;
                    }
                    else if(tipo==1){
                        icono = iconArquero;
                    }
                    else if(tipo==2){
                        icono = iconCaballero;
                    }
                    else if(tipo==3){
                        icono = iconSoldado;
                    }
                    else{
                        icono = iconSoldado;
                    }
                    if(ejercito1.getMisSoldados().contains(tablero[i][j])){
                        casilleros[i][j] = new JButton(icono);
                        casilleros[i][j].setBackground(Color.BLUE);
                    }
                    else{
                        casilleros[i][j] = new JButton(icono);
                        casilleros[i][j].setBackground(Color.RED);
                    }
                }
                else{
                    casilleros[i][j] = new JButton("");
                }
                casilleros[i][j].setPreferredSize(new Dimension(75, 50));
                casilleros[i][j].addActionListener(listener);
                panelCampoBatalla.add(casilleros[i][j]);
            }
        }
        //panel jugador 1
        panelJugador1.setLayout(new BoxLayout(panelJugador1, BoxLayout.Y_AXIS));
        JLabel labelJugador1 = new JLabel("Jugador 1");
        labelJugador1.setFont(new Font("Verdana", Font.PLAIN, 14));
        labelJugador1.setForeground(Color.BLUE);
        labelJugador1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel labelReino1 = new JLabel("Reino: " + reino1);
        labelReino1.setFont(new Font("Verdana", Font.PLAIN, 13));
        labelReino1.setForeground(Color.BLUE);
        labelReino1.setAlignmentX(Component.CENTER_ALIGNMENT);
        mover1 = new JButton("Mover");
        mover1.setAlignmentX(Component.CENTER_ALIGNMENT);
        mover1.setEnabled(false);
        mover1.addActionListener(listener);
        atacar1 = new JButton("Atacar");
        atacar1.setAlignmentX(Component.CENTER_ALIGNMENT);
        atacar1.setEnabled(false);
        atacar1.addActionListener(listener);
        huir1 = new JButton("Huir");
        huir1.setAlignmentX(Component.CENTER_ALIGNMENT);
        huir1.setEnabled(false);
        huir1.addActionListener(listener);
        defender1 = new JButton("Defender");
        defender1.setAlignmentX(Component.CENTER_ALIGNMENT);
        defender1.setEnabled(false);
        defender1.addActionListener(listener);
        rendirSoldado1 = new JButton("RendirSoldado");
        rendirSoldado1.setEnabled(false);
        rendirSoldado1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rendirSoldado1.addActionListener(listener);
        rendirEj1 = new JButton("Rendir Ejercito");
        rendirEj1.setEnabled(false);
        rendirEj1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rendirEj1.addActionListener(listener);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 25)));
        panelJugador1.add(labelJugador1);
        panelJugador1.add(labelReino1);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador1.add(mover1);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador1.add(atacar1);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador1.add(huir1);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador1.add(defender1);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador1.add(rendirSoldado1);
        panelJugador1.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador1.add(rendirEj1);
        //panel jugador 2
        panelJugador2.setLayout(new BoxLayout(panelJugador2, BoxLayout.Y_AXIS));
        JLabel labelJugador2 = new JLabel("Jugador 2");
        labelJugador2.setFont(new Font("Verdana", Font.PLAIN, 14));
        labelJugador2.setForeground(Color.RED);
        labelJugador2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel labelReino2 = new JLabel("Reino:" + reino2);
        labelReino2.setFont(new Font("Verdana", Font.PLAIN, 13));
        labelReino2.setForeground(Color.RED);
        labelReino2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mover2 = new JButton("Mover");
        mover2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mover2.setEnabled(false);
        mover2.addActionListener(listener);
        atacar2 = new JButton("Atacar");
        atacar2.setAlignmentX(Component.CENTER_ALIGNMENT);
        atacar2.setEnabled(false);
        atacar2.addActionListener(listener);
        huir2 = new JButton("Huir");
        huir2.setAlignmentX(Component.CENTER_ALIGNMENT);
        huir2.setEnabled(false);
        huir2.addActionListener(listener);
        defender2 = new JButton("Defender");
        defender2.setAlignmentX(Component.CENTER_ALIGNMENT);
        defender2.setEnabled(false);
        defender2.addActionListener(listener);
        rendirSoldado2 = new JButton("RendirSoldado");
        rendirSoldado2.setEnabled(false);
        rendirSoldado2.setAlignmentX(Component.CENTER_ALIGNMENT);
        rendirSoldado2.addActionListener(listener);
        rendirEj2 = new JButton("Rendir Ejercito");
        rendirEj2.setEnabled(false);
        rendirEj2.setAlignmentX(Component.CENTER_ALIGNMENT);
        rendirEj2.addActionListener(listener);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 25)));
        panelJugador2.add(labelJugador2);
        panelJugador2.add(labelReino2);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador2.add(mover2);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador2.add(atacar2);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador2.add(huir2);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador2.add(defender2);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador2.add(rendirSoldado2);
        panelJugador2.add(Box.createRigidArea(new Dimension(10, 15)));
        panelJugador2.add(rendirEj2);
        // panel log
        log = new JTextArea();
        log.setRows(5);
        JScrollPane scroll = new JScrollPane(log);
        // agregar paneles
        panelInterfaz.add(panelTitulo, BorderLayout.NORTH);
        panelInterfaz.add(panelJugador1, BorderLayout.WEST);
        panelInterfaz.add(panelJugador2, BorderLayout.EAST);
        panelInterfaz.add(panelCampoBatalla, BorderLayout.CENTER);
        panelInterfaz.add(scroll, BorderLayout.SOUTH);
        add(panelInterfaz);
    }
    /*public static void main(String[] args){
        Portada portada = new Portada();
        inicializarJuego();
        Game game = new Game();
    }*/
    public static void inicializarJuego(){
        System.out.println("Inicializando juego...");
        ejercito1 = new Ejercito("Francia");
        ejercito1.autogenerarSoldados();
        System.out.println("El ejercito1 tiene " + ejercito1.getMisSoldados().size() + " soldados");
        ejercito2 = new Ejercito("Suiza");
        ejercito2.autogenerarSoldados();
        System.out.println("El ejercito2 tiene " + ejercito2.getMisSoldados().size() + " soldados");
        tablero = new Soldado[10][10];
        desplegarEjercito(ejercito1);
        desplegarEjercito(ejercito2);
        System.out.println("Turno 1");
        System.out.println("Estado 0");
    }
    public static void desplegarEjercito(Ejercito army){
        System.out.println("Desplegando ejercito...");
        Random rand = new Random();
        for(int i=0; i<army.getMisSoldados().size(); i++){
            int fil, col;
            do{
                fil = rand.nextInt(10);
                col = rand.nextInt(10);
            }while(tablero[fil][col] != null);
            army.getMisSoldados().get(i).setFila(fil);
            army.getMisSoldados().get(i).setColumna(col);
            tablero[fil][col] = army.getMisSoldados().get(i);
        }
    }
    public static void verificarCasilleroOrigen(int fil, int col){
        if(casilleros[fil][col].getText().equals("") && casilleros[fil][col].getIcon()==null){
            System.out.println("-> Casillero vacio");
            log.append("-> Casillero vacio");
            labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
            labelMensajes.setText("El casillero seleccionado no contiene ningún soldado. Por favor seleccione otro.");
        }
        else{
            if(turno==1 && casilleros[fil][col].getBackground()==Color.BLUE){
                System.out.println("-> Seleccionado Casillero ejercito1");
                log.append("-> Seleccionado Casillero ejercito1");
                establecerCasilleroOrigen(fil, col);
                habilitarMenuAcciones();
                estado = 1;
                labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
                labelMensajes.setText("");
            }
            else if(turno==2 && casilleros[fil][col].getBackground()==Color.RED){
                System.out.println("-> Seleccionado Casillero ejercito2");
                establecerCasilleroOrigen(fil, col);
                habilitarMenuAcciones();
                System.out.println("Estado 1");
                estado = 1;
                labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
                labelMensajes.setText("");
            }
            else{
                System.out.println("-> Casillero con soldado de otro ejercito");
                labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
                labelMensajes.setText("El casillero no contiene un soldado de su ejercito");
            }
        }
    }
    public static void verificarCasilleroDestino(int fil, int col){
        if(casilleros[fil][col].getText().equals("") && casilleros[fil][col].getIcon()==null){
            System.out.println("-> casillero vacio");
            establecerCasilleroDestino(fil,col);
            moverSoldado();
            deshabilitarMenuAcciones();
            cambiarTurno();
        }
        else{
            System.out.println("-> casillero ocupado");
            labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
            labelMensajes.setText("El casillero seleccionado no esta disponible");
        }
    }
    public static void verificarCasilleroEnemigo(int fil, int col){
        System.out.println("verificando casillero enemigo");
        if(turno==1 && casilleros[fil][col].getBackground()==Color.RED){
            establecerCasilleroDestino(fil, col);
            //atacar();
            deshabilitarMenuAcciones();
            //cambiarTurno();
            habilitarMenuRespuesta();
            estado=6;
            //forzarTurno();
            System.out.println("-> Esperando respuesta del oponente...");
            labelTurno.setText("Esperando la respuesta del oponente");
            //labelMensajes.setText("El casillero seleccionado no tiene un soldado enemigo");
        }
        else if(turno==2 && casilleros[fil][col].getBackground()==Color.BLUE){
            establecerCasilleroDestino(fil, col);
            //atacar();
            deshabilitarMenuAcciones();
            //cambiarTurno();
            habilitarMenuRespuesta();
            estado=6;
            //forzarTurno();
            System.out.println("-> Esperando respuesta del oponente...");
            labelTurno.setText("Esperando la respuesta del oponente");
        }
        else{
            System.out.println("-> casillero sin soldado enemigo");
            labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
            labelMensajes.setText("El casillero seleccionado no tiene un soldado enemigo");
        }
    }
    public static void establecerCasilleroOrigen(int fila, int columna){
        System.out.println("Establecer casillero origen " + fila + ", " + columna);
        posOrigen = new Posicion(fila, columna);
        estado = 1;
        System.out.println("Estado 1");
    }
    public static void establecerCasilleroDestino(int fila, int columna){
        System.out.println("Establecer casillero destino " + fila + ", " + columna);
        posDestino = new Posicion(fila, columna);
        estado = 3;
        System.out.println("Estado 3");
    }
    public static void habilitarMenuAcciones(){
        if(turno==1){
            System.out.println("Habilitar menu acciones J1");
            mover1.setEnabled(true);
            if(verificarEnemigoCercano()){
                atacar1.setEnabled(true);
            }
            rendirSoldado1.setEnabled(true);
            rendirEj1.setEnabled(true);
        }
        else if(turno==2){
            System.out.println("Habilitar menu acciones J2");
            mover2.setEnabled(true);
            if(verificarEnemigoCercano()){
                atacar2.setEnabled(true);
            }
            rendirSoldado2.setEnabled(true);
            rendirEj2.setEnabled(true);
        }
    }
    public static void deshabilitarMenuAcciones(){
        if(turno==1){
            System.out.println("Deshabilitando menu acciones J1");
            mover1.setEnabled(false);
            atacar1.setEnabled(false);
            rendirEj1.setEnabled(false);
            rendirSoldado1.setEnabled(false);
        }
        else if(turno==2){
            System.out.println("Deshabilitando menu acciones J2");
            mover2.setEnabled(false);
            atacar2.setEnabled(false);
            rendirEj2.setEnabled(false);
            rendirSoldado2.setEnabled(false);
        }
    }
    public static void habilitarMenuRespuesta(){
        if(turno==1){
            huir2.setEnabled(true);
            defender2.setEnabled(true);
        }
        else if(turno==2){
            huir1.setEnabled(true);
            defender1.setEnabled(true);
        }
    }
    public static void deshabilitarMenuRespuesta(){
        huir2.setEnabled(false);
        defender2.setEnabled(false);
        huir1.setEnabled(false);
        defender1.setEnabled(false);
    }
    public static boolean verificarEnemigoCercano(){
        System.out.println("Verificando enemigo cercano...");
        boolean result = false;
        Posicion[] posiciones = new Posicion[8];
        posiciones[0] = new Posicion(posOrigen.getFila()-1, posOrigen.getColumna()-1);
        posiciones[1] = new Posicion(posOrigen.getFila()-1, posOrigen.getColumna());
        posiciones[2] = new Posicion(posOrigen.getFila()-1, posOrigen.getColumna()+1);
        posiciones[3] = new Posicion(posOrigen.getFila(), posOrigen.getColumna()-1);
        posiciones[4] = new Posicion(posOrigen.getFila(), posOrigen.getColumna()+1);
        posiciones[5] = new Posicion(posOrigen.getFila()+1, posOrigen.getColumna()-1);
        posiciones[6] = new Posicion(posOrigen.getFila()+1, posOrigen.getColumna());
        posiciones[7] = new Posicion(posOrigen.getFila()+1, posOrigen.getColumna()+1);
        if(turno==1){
            System.out.print("Posiciones a atacar de 2: ");
            for(int i=0; i<8; i++){
                if(posiciones[i].getFila()>0 && posiciones[i].getFila()<10 && posiciones[i].getColumna()>0 && posiciones[i].getColumna()<10){
                    if( (casilleros[posiciones[i].getFila()][posiciones[i].getColumna()]).getBackground()==Color.RED ){
                        System.out.print(posiciones[i].getFila()+","+posiciones[i].getColumna()+" - ");
                        result = true;
                    }
                }
            }
            System.out.print("\n");
        }
        else if(turno==2){
            System.out.print("Posiciones a atacar de 1: ");
            for(int i=0; i<8; i++){
                if(posiciones[i].getFila()>0 && posiciones[i].getFila()<10 && posiciones[i].getColumna()>0 && posiciones[i].getColumna()<10){
                    if( (casilleros[posiciones[i].getFila()][posiciones[i].getColumna()]).getBackground()==Color.BLUE ){
                        System.out.print(posiciones[i].getFila()+","+posiciones[i].getColumna()+" - ");
                        result = true;
                    }
                }
            }
            System.out.print("\n");
        }
        System.out.println(result + " es posible atacar");
        return result;
    }
    public static void moverSoldado(){
        if(estado == 3){
            // Actualizar array tablero
            System.out.println("Mover de " + posOrigen.getFila() + ", " + posOrigen.getColumna() + " a " + posDestino.getFila() + ", " + posDestino.getColumna());
            System.out.println("Actualizando array tablero");
            //   Colocar copia del soldado en destino
            tablero[posDestino.getFila()][posDestino.getColumna()] = tablero[posOrigen.getFila()][posOrigen.getColumna()];
            //   Borrar origen
            tablero[posOrigen.getFila()][posOrigen.getColumna()] = null;
            // Actualizar información del soldado
            cambiarPosicionSoldado();
            // Actualizar casilleros (interfaz)
            //   Colocar soldado en nueva posicion
            System.out.println("Actualizando movimiento en interfaz");
            if(turno==1){
                Icon icono = casilleros[posOrigen.getFila()][posOrigen.getColumna()].getIcon();
                casilleros[posDestino.getFila()][posDestino.getColumna()].setIcon(icono);
                casilleros[posDestino.getFila()][posDestino.getColumna()].setBackground(Color.BLUE);
            }
            else{
                Icon icono = casilleros[posOrigen.getFila()][posOrigen.getColumna()].getIcon();
                casilleros[posDestino.getFila()][posDestino.getColumna()].setIcon(icono);
                casilleros[posDestino.getFila()][posDestino.getColumna()].setBackground(Color.RED);
            }
            //   borrar 
            casilleros[posOrigen.getFila()][posOrigen.getColumna()].setIcon(null);
            casilleros[posOrigen.getFila()][posOrigen.getColumna()].setBackground(null);
            // Cambiar estado
            estado = 4;
            System.out.println("Estado 4");
        }
    }
    public static void cambiarPosicionSoldado(){
        System.out.println("Cambiando posicion de soldado en ejercito");
        int indice;
        if(turno == 1){
            indice = ejercito1.getMisSoldados().indexOf(tablero[posDestino.getFila()][posDestino.getColumna()]);
            ejercito1.getMisSoldados().get(indice).setFila(posDestino.getFila());
            ejercito1.getMisSoldados().get(indice).setColumna(posDestino.getColumna());
            System.out.println(" - movimiento realizado a "+posDestino.getFila()+","+posDestino.getColumna());
        }
        else if(turno == 2){
            indice = ejercito2.getMisSoldados().indexOf(tablero[posDestino.getFila()][posDestino.getColumna()]);
            ejercito2.getMisSoldados().get(indice).setFila(posDestino.getFila());
            ejercito2.getMisSoldados().get(indice).setColumna(posDestino.getColumna());
            System.out.println(" - movimiento realizado a "+posDestino.getFila()+","+posDestino.getColumna());
        }
    }
    public static void atacar(){
        System.out.println("Atacar...");
        //obtener información de ambos soldados
        Soldado atacante = tablero[posOrigen.getFila()][posOrigen.getColumna()];
        Soldado atacado = tablero[posDestino.getFila()][posDestino.getColumna()];
        System.out.println("Atacante - A:" + atacante.getNivelAtaque() + ", D:" + atacante.getNivelDefensa() + ", V:" + atacante.getVidaActual());
        System.out.println("Atacado - A:" + atacado.getNivelAtaque() + ", D:" + atacado.getNivelDefensa() + ", V:" + atacado.getVidaActual());
        //aplicar métrica
        if(atacante.getNivelAtaque()>atacado.getNivelDefensa()){
            int ataqueRestante = atacante.getNivelAtaque() - atacado.getNivelDefensa();
            if(ataqueRestante>=atacado.getVidaActual()){
                System.out.println("- Eliminar soldado atacado");
                eliminarSoldado(posDestino);
                moverSoldado();
            }
            else{
                System.out.println("- Eliminar soldado atacante");
                eliminarSoldado(posOrigen);
            }
        }
        else if(atacante.getNivelAtaque()==atacado.getNivelDefensa()){
            if(atacante.getVidaActual()>=atacado.getVidaActual()){
                System.out.println("- Eliminar soldado atacado");
                eliminarSoldado(posDestino);
                moverSoldado();
            }
            else{
                System.out.println("- Eliminar soldado atacante");
                eliminarSoldado(posOrigen);
            }
        }
        else{
            if(atacado.getNivelAtaque()>atacante.getNivelDefensa()){
                System.out.println("- Eliminar soldado atacante");
                eliminarSoldado(posOrigen);
            }
            else{
                System.out.println("- Eliminar soldado atacado");
                eliminarSoldado(posDestino);
                moverSoldado();
            }
        }
        estado = 4;
        System.out.println("Estado 4");
    }
    public static void eliminarSoldado(Posicion pos){
        //eliminar soldado en ejercito
        if(ejercito1.getMisSoldados().contains(tablero[pos.getFila()][pos.getColumna()])){
            int indice = ejercito1.getMisSoldados().indexOf(tablero[pos.getFila()][pos.getColumna()]);
            ejercito1.getMisSoldados().remove(indice);
            System.out.println("Eliminando soldado en ejercito 1");
        }
        else if(ejercito2.getMisSoldados().contains(tablero[pos.getFila()][pos.getColumna()])){
            int indice = ejercito2.getMisSoldados().indexOf(tablero[pos.getFila()][pos.getColumna()]);
            ejercito2.getMisSoldados().remove(indice);
            System.out.println("Eliminando soldado en ejercito 2");
        }
        //eliminar soldado en array tablero
        System.out.println("Eliminando soldado en array tablero");
        tablero[pos.getFila()][pos.getColumna()] = null;
        //actualizar interfaz
        System.out.println("Eliminando soldado en interfaz");
        casilleros[pos.getFila()][pos.getColumna()].setIcon(null);
        casilleros[pos.getFila()][pos.getColumna()].setBackground(null);
    }
    public static void cambiarTurno(){
        verificarEjercito();
        if(estado == 4){
            System.out.println("Cambiando turno");
            if(turno==1){
                turno = 2;
                System.out.println("Turno 2");
            }
            else if(turno==2){
                System.out.println("Turno 1");
                turno = 1;
            }
            estado = 0;
            System.out.println("Estado 0");
            labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
            labelMensajes.setText("");
        }
        deshabilitarMenuRespuesta();
    }
    public static void forzarTurno(){
        verificarEjercito();
        if(estado == 6){
            System.out.println("Forzando turno");
            if(turno==1){
                turno = 2;
                System.out.println("Turno 2");
            }
            else if(turno==2){
                System.out.println("Turno 1");
                turno = 1;
            }
            estado = 1;
            System.out.println("Estado 1 forzado");
            //labelTurno.setText("Turno del jugador "+ turno + ": " + estados[estado]);
            //labelMensajes.setText("");
        }
        deshabilitarMenuRespuesta();
    }
    public static void rendirEjercito(){
        System.out.println("Rendir ejercito... " + turno);
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(turno==1){
                    if(casilleros[i][j].getBackground()==Color.BLUE){
                        casilleros[i][j].setIcon(null);
                        casilleros[i][j].setBackground(null);
                    }
                }
                else if(turno==2){
                    if(casilleros[i][j].getBackground()==Color.RED){
                        casilleros[i][j].setIcon(null);
                        casilleros[i][j].setBackground(null);
                    }
                }
            }
        }
        // mostrar dialogo;
        if(turno== 1){
            mostrarResultado(2);
        }
        else{
            mostrarResultado(1);
        }
    }
    public static void mostrarResultado(int ganador){
        System.out.println("Mostrar resultado...");
        dialogoResultado = new JDialog();
        //dialogoResultado.setLayout(new BoxLayout(dialogoResultado, BoxLayout.Y_AXIS));
        dialogoResultado.setLayout(new FlowLayout());
        JLabel labelResultado = new JLabel("El jugador " + ganador + " gano la batalla!!!");
        System.out.println("El jugador " + ganador + " gano la batalla!!!");
        labelResultado.setFont(new Font("Verdana", Font.PLAIN, 16));
        labelResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton botonOk = new JButton("SALIR");
        botonOk.addActionListener ( new ActionListener(){
            public void actionPerformed( ActionEvent e ){
                System.out.println("Finalizar juego");
                System.exit(0);
            }
        });
        dialogoResultado.add(labelResultado);
        dialogoResultado.add(botonOk);
        dialogoResultado.setSize(300, 150);
        dialogoResultado.setVisible(true);
    }
    public static void verificarEjercito(){
        System.out.println("Verificar ejercitos (c.soldados): "+ejercito1.getMisSoldados().size()+"-"+ejercito2.getMisSoldados().size());
        if(ejercito1.getMisSoldados().size()==0){
            System.out.println("Ejercito 1: vacio");
            mostrarResultado(2);
        }
        else if(ejercito2.getMisSoldados().size()==0){
            System.out.println("Ejercito 2 vacio");
            mostrarResultado(1);
        }
    }
}