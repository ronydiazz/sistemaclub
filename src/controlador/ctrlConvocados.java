/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import formularios.JUGADORES;
import formularios.convocados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modeloSql.Sqlconvocados;
import static modeloSql.Sqlconvocados.condicion;
import modeloSql.Sqljugadores;
import variables.jugadores;

/**
 *
 * @author DELL
 */
public class ctrlConvocados implements ActionListener{
private jugadores jug;
private Sqlconvocados juga;
private convocados frjuga;


public ctrlConvocados(jugadores jug, Sqlconvocados juga, convocados frjuga){

    this.jug=jug;
    this.juga=juga;
    this.frjuga=frjuga;
    this.frjuga.btnAgregar.addActionListener(this);
    this.frjuga.btnQuitar.addActionListener(this);
    this.frjuga.btnMostrar.addActionListener(this);

convocados.combo_cat1.addItemListener(combo1);
convocados.combo_convocados.addItemListener(combo_c);
//convocados.tabla_jugadores.addMouseListener(mou);
frjuga.txt_bus_ci.addKeyListener(tecla);
frjuga.txt_bus_nom.addKeyListener(tecla);
}

  public void iniciar(){
      Sqlconvocados.cargar(convocados.combo_cat1.getSelectedItem().toString(),"");

    }



    @Override
    public void actionPerformed(ActionEvent ae) {
      if(ae.getSource()==frjuga.btnAgregar){
          
     agregar_mostrar();
    }
    
      if(ae.getSource()==frjuga.btnQuitar){
          mod_quitar();
     quitar();
    }
      if(ae.getSource()==frjuga.btnMostrar){
          
    // mostrar();
    Sqlconvocados.mostrar_convo(convocados.combo_cat1.getSelectedItem().toString(), convocados.combo_convocados.getSelectedItem().toString());
    }
    
    }
    
    
    KeyListener tecla= new KeyListener() {
    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    if(ke.getSource()==frjuga.txt_bus_ci){
        if(convocados.combo_cat1.getSelectedItem().equals("PRIMERA")){
          Sqlconvocados.cargar("PRIMERA",frjuga.txt_bus_ci.getText());
        }
        if(convocados.combo_cat1.getSelectedItem().equals("JUVENIL")){
          Sqlconvocados.cargar("JUVENIL",frjuga.txt_bus_ci.getText());
        }
        if(convocados.combo_cat1.getSelectedItem().equals("FORMATIVA")){
          Sqlconvocados.cargar("FORMATIVA",frjuga.txt_bus_ci.getText());
        }
      
    
    }
    if(ke.getSource()==frjuga.txt_bus_nom){
          frjuga.txt_bus_nom.setText (frjuga.txt_bus_nom.getText().toUpperCase());
        if(convocados.combo_cat1.getSelectedItem().equals("PRIMERA")){
            
         Sqlconvocados.cargar("PRIMERA",frjuga.txt_bus_nom.getText());
        }
         if(convocados.combo_cat1.getSelectedItem().equals("JUVENIL")){
          Sqlconvocados.cargar("JUVENIL",frjuga.txt_bus_nom.getText());
        }
        if(convocados.combo_cat1.getSelectedItem().equals("FORMATIVA")){
          Sqljugadores.cargar("FORMATIVA",frjuga.txt_bus_nom.getText());
        }
        }
    }   
};
  
     ItemListener combo1= new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent ie) {
     Sqlconvocados.cargar(convocados.combo_cat1.getSelectedItem().toString(), "");
     
      String conv="";
    if(!"Seleccione una opción".equals(conv)){
    conv =convocados.combo_convocados.getSelectedItem().toString();
    }      
    String cat=convocados.combo_cat1.getSelectedItem().toString();
    frjuga.lblcate.setText(cat+" - "+conv);
    }
};
     
     ItemListener combo_c= new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent ie) {
     //Sqljugadores.cargar(convocados.combo_cat1.getSelectedItem().toString(), "");
//    if(!"Seleccione una opción".equals(convocados.combo_convocados.getSelectedItem().toString())){
//    
//    }
     
    String conv="";
    if(!"Seleccione una opción".equals(conv)){
    conv =convocados.combo_convocados.getSelectedItem().toString();
    }
           
           
    String cat=convocados.combo_cat1.getSelectedItem().toString();
    
    frjuga.lblcate.setText(cat+" - "+conv);
             
    }
};
     
     
    
      String []titulos={"ID","CEDULA","NOMBRE","CATEGORIA","POSICION","CONVOCADO"};
      DefaultTableModel model= new DefaultTableModel(null, titulos){
      @Override
       public boolean isCellEditable(int i, int i1) {
           return false; }};
      
       public void agregar_mostrar(){
 //   String codigo= String.valueOf(convocados.tabla_jugadores.getValueAt(fila, 0));
     if(!"Seleccione una opción".equals(convocados.combo_convocados.getSelectedItem().toString())){
         
         int registro;
     registro=condicion(); 
      if(registro<12){
          int fila= convocados.tabla_jugadores.getSelectedRow();
         if(convocados.tabla_conv.getRowCount()<12 &&
                 convocados.combo_convocados.getSelectedItem().toString().equals("TITULAR")){
        
     if(fila>=0){
         JOptionPane.showMessageDialog(null, "ingresa al metodo mostrar");
        String datos[]= new String[6];
         datos[0]=convocados.tabla_jugadores.getValueAt(fila, 0).toString();
         datos[1]=convocados.tabla_jugadores.getValueAt(fila, 1).toString();
         datos[2]=convocados.tabla_jugadores.getValueAt(fila, 2).toString();
         datos[3]=convocados.tabla_jugadores.getValueAt(fila, 3).toString();
         datos[4]=convocados.tabla_jugadores.getValueAt(fila, 4).toString();
     // String convocad=frjuga.combo_convocados.getSelectedItem().toString();
         datos[5]=convocados.combo_convocados.getSelectedItem().toString();
      
          model.addRow(datos);
         // model.removeRow(fila);
          convocados.tabla_conv.setModel(model);
          agregar();
            convocados.tabla_conv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel columnModel=  convocados.tabla_conv.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);
            columnModel.getColumn(1).setPreferredWidth(80);
            columnModel.getColumn(2).setPreferredWidth(150);
            columnModel.getColumn(3).setPreferredWidth(120);
            columnModel.getColumn(4).setPreferredWidth(150);
            columnModel.getColumn(5).setPreferredWidth(120);
       //     }
     }else{
     JOptionPane.showMessageDialog(null, "Seleccione un Jugador");
     }
     }else if(convocados.tabla_conv.getRowCount()<8 &&
              convocados.combo_convocados.getSelectedItem().toString().equals("SUPLENTE")){
         
     if(fila>=0){
         
        String datos[]= new String[6];
         datos[0]=convocados.tabla_jugadores.getValueAt(fila, 0).toString();
         datos[1]=convocados.tabla_jugadores.getValueAt(fila, 1).toString();
         datos[2]=convocados.tabla_jugadores.getValueAt(fila, 2).toString();
         datos[3]=convocados.tabla_jugadores.getValueAt(fila, 3).toString();
         datos[4]=convocados.tabla_jugadores.getValueAt(fila, 4).toString();
     // String convocad=frjuga.combo_convocados.getSelectedItem().toString();
         datos[5]=convocados.combo_convocados.getSelectedItem().toString();
      
          model.addRow(datos);
         // model.removeRow(fila);
          convocados.tabla_conv.setModel(model);
          agregar();
            convocados.tabla_conv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel columnModel=  convocados.tabla_conv.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);
            columnModel.getColumn(1).setPreferredWidth(80);
            columnModel.getColumn(2).setPreferredWidth(150);
            columnModel.getColumn(3).setPreferredWidth(120);
            columnModel.getColumn(4).setPreferredWidth(150);
            columnModel.getColumn(5).setPreferredWidth(120);
       //     }
     }else{
     JOptionPane.showMessageDialog(null, "Seleccione un Jugador");
     }
  }
}     
     }else{
     JOptionPane.showMessageDialog(null, "Seleccione una opción (Titulares o Suplentes)");
     }
   }
   
    public void agregar(){
  String convo= convocados.combo_convocados.getSelectedItem().toString();
     int fila= convocados.tabla_jugadores.getSelectedRow();
     String codigo= String.valueOf(convocados.tabla_jugadores.getValueAt(fila, 0));
    
      jug.setConvocados(convo);
      jug.setId(Integer.parseInt(codigo));
      
     if(juga.modificar(jug)){
//     JOptionPane.showMessageDialog(null, "Modificación exitosa");
//       
//      Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(),"");
      }
    }
    
    void quitar(){
    int fila= convocados.tabla_conv.getSelectedRow();
    model.removeRow(fila);
    //aca crear un for que sea mientras sea menor que count ejecute y llene las filas de la columna contar
    }
    
    
    void mod_quitar(){
    
     // convocados.combo_convocados.getSelectedItem().toString();
     
    int fila= convocados.tabla_conv.getSelectedRow();
     String codigo= String.valueOf(convocados.tabla_conv.getValueAt(fila, 0));
     String convo= "";
    // JOptionPane.showMessageDialog(null, "estira bien de la fila la palabra:"+convo);
    
      jug.setConvocados(convo);
      jug.setId(Integer.parseInt(codigo));
      
     if(juga.modificar(jug)){
//     JOptionPane.showMessageDialog(null, "Modificación exitosa");
//       
//      Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(),"");
      }
    
    }
}
