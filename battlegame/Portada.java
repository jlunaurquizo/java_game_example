package battlegame;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Portada extends JFrame implements ItemListener{
    private String reino1, reino2;
    private JComboBox listaReinos1, listaReinos2;
    public Portada(){
        //mainPanel.setSize(1050, 550);
        setTitle("Juego de Batallas");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        setVisible(true);
    }
    public static void main(String[] args) {
        Portada portada = new Portada();
    }
    private void createContents(){
        System.out.println("Inicializando Portada");
        String[] reinos = {"Seleccionar...", "Francia", "Inglaterra", "Portugal", "Germania", "Normandia"};
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon(getClass().getResource("portada.jpg"))));
        setLayout(new GridBagLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel indicacion = new JLabel("Seleccione el reino para cada jugador:", SwingConstants.RIGHT);
        //indicacion.setHorizontalAlignment(CENTER_ALIGNMENT);
        JLabel labelReino1 = new JLabel("Reino 1");
        listaReinos1 = new JComboBox<>(reinos);
        listaReinos1.addItemListener(this);
        listaReinos1.setAlignmentX(0);
        JLabel labelReino2 = new JLabel("Reino 2");
        listaReinos2 = new JComboBox<>(reinos);
        listaReinos2.addItemListener(this);
        listaReinos2.setAlignmentX(0);
        JButton iniciar = new JButton("Iniciar Juego");
        JLabel labelError = new JLabel("");
        labelError.setForeground(Color.RED);
        iniciar.addActionListener ( new ActionListener(){
            public void actionPerformed( ActionEvent e ){
                if(listaReinos1.getSelectedIndex()==0 || listaReinos2.getSelectedIndex()==0){
                    System.out.println("-> Faltan seleccionar reinos");
                    labelError.setText("Debe seleccionar reinos para ambos jugadores!!");
                }
                else{
                    System.out.println("Reinos seleccionados");
                    ocultar();
                }
            }
        });
        mainPanel.add(indicacion);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 15)));
        mainPanel.add(labelReino1);
        mainPanel.add(listaReinos1);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 15)));
        mainPanel.add(labelReino2);
        mainPanel.add(listaReinos2);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 15)));
        mainPanel.add(iniciar);
        mainPanel.add(labelError);
        add(mainPanel, new GridBagConstraints());
    }
    private void ocultar(){
        System.out.println("Ocultando portada y creando partida");
        this.setVisible(false);
        reino1 = (String)listaReinos1.getSelectedItem();
        reino2 = (String)listaReinos2.getSelectedItem();
        Game game = new Game(reino1, reino2);
        this.dispose();
    }
    public void itemStateChanged(ItemEvent e){
        String seleccion = (String)e.getItem();
        if(e.getSource()==listaReinos1 && e.getStateChange()==ItemEvent.SELECTED){
            listaReinos2.removeItem(seleccion);
        }
        else if(e.getSource() == listaReinos2 && e.getStateChange()==ItemEvent.SELECTED){
            listaReinos1.removeItem(seleccion);
        }
    }
}
