/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import formularios.Socios;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modeloSql.Sqljugadores;
import modeloSql.Sqlsocios;
import variables.jugadores;
import variables.socios;


/**
 *
 * @author rony
 */
public class ctrlSocios implements ActionListener {
    
private socios socio;
private Sqlsocios sqlsocio;
private Socios frsocios;

   private FileInputStream fisfoto;
   private int longitudBytes;
   
public ctrlSocios(socios socio, Sqlsocios sqlsocio, Socios frsocios){
this.socio=socio;
this.sqlsocio=sqlsocio;
this.frsocios=frsocios;
this.frsocios.btnRegistrar.addActionListener(this);
this.frsocios.btnModificar.addActionListener(this);
this.frsocios.btnEliminar.addActionListener(this);
this.frsocios.btnCargar.addActionListener(this);
this.frsocios.btnNuevo.addActionListener(this);

Socios.tabla_socio.addMouseListener(mou);
frsocios.txt_bus_ci.addKeyListener(tecla);
frsocios.txt_bus_nom.addKeyListener(tecla);
frsocios.txt_nom.addKeyListener(tecla);

}


  public void iniciar(){
      Sqlsocios.cargar("");
    }
  
    @Override
    public void actionPerformed(ActionEvent ae) {
        
      if(ae.getSource()==frsocios.btnCargar){
          cargarImagen();
     
    }
      
    if(ae.getSource()==frsocios.btnRegistrar){
        
   registrar();
    }
    
    if(ae.getSource()==frsocios.btnModificar){
    modificar();
    }
    
    if(ae.getSource()==frsocios.btnEliminar){
          try {
              eliminar();
          } catch (SQLException ex) {
              Logger.getLogger(ctrlJugadores.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
 if(ae.getSource()==frsocios.btnNuevo){
    limpiar();
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
    if(ke.getSource()==frsocios.txt_bus_ci){
        
        Sqlsocios.cargar(frsocios.txt_bus_ci.getText());
    
    }
    if(ke.getSource()==frsocios.txt_bus_nom){
        frsocios.txt_bus_nom.setText (frsocios.txt_bus_nom.getText().toUpperCase());
        Sqlsocios.cargar(frsocios.txt_bus_nom.getText());
    
    }
    if(ke.getSource()==frsocios.txt_nom){
      frsocios.txt_nom.setText (frsocios.txt_nom.getText().toUpperCase());
    
    }

    }   
};
    

    MouseListener mou= new MouseListener() {
    @Override
    public void mouseClicked(MouseEvent me) {
        mostrar();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
};
    
    public void mostrar(){
  //      JOptionPane.showMessageDialog(null, "entra al codigo");
    int fila= Socios.tabla_socio.getSelectedRow();
    String codigo= String.valueOf(Socios.tabla_socio.getValueAt(fila, 0));
    List ls;
    
    ls=Sqlsocios.mostrar(codigo);
    for(int i=0;i<ls.size();i++){
        socios socio= new socios();
        socio= (socios) ls.get(i);
        frsocios.txt_socio.setText(Integer.toString(socio.getNum_socio()));
        frsocios.txt_ci.setText(socio.getNum_ced());
        
        frsocios.txt_tel.setText(socio.getTelefono());
        frsocios.txt_nom.setText(socio.getNombre());
        frsocios.txt_direc.setText(socio.getDireccion());
        frsocios.txt_fecha.setDate(socio.getFecha_ing());
        
        
        try{
            byte[] bi = socio.getFoto();
            if (bi!=null){
                BufferedImage image = null;
                InputStream in = new ByteArrayInputStream(bi);
                //error abajo
                image = ImageIO.read(in);
                ImageIcon imgi = new ImageIcon(image.getScaledInstance(138, 175, 0));
                frsocios.imagen.setIcon(imgi);
                frsocios.imagen.updateUI();
            }else{
                     frsocios.imagen.setIcon(null);
                    }
            
            
        }catch(IOException ex){
            //     JUGADORES.foto.setIcon("No imagen");
            System.out.println(ex);
        }
        
//                JUGADORES.foto.setIcon(jugg.getFoto2());
//                   JUGADORES.foto.updateUI();
    }
    
    }
    

    
    public void registrar(){
        if(!frsocios.txt_nom.getText().equals("") && !frsocios.txt_ci.getText().equals("") && 
           !frsocios.txt_direc.getText().equals("") &&
           frsocios.txt_fecha.getDate()!=null && !frsocios.txt_tel.getText().equals("")){
            
     if(sqlsocio.existe(frsocios.txt_ci.getText()) == 0){
   
     socio.setNum_ced(frsocios.txt_ci.getText());

     socio.setTelefono(frsocios.txt_tel.getText());
     socio.setNombre(frsocios.txt_nom.getText());
     socio.setDireccion(frsocios.txt_direc.getText());
     
     java.util.Date utilStartDate = frsocios.txt_fecha.getDate();
     java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());

     socio.setFecha_ing(sqlStartDate);

     socio.setImput(socio.getImput());
     socio.setRutafile(socio.getRutafile());

     if(sqlsocio.registrar(socio)){
     JOptionPane.showMessageDialog(null, "Registro exitoso");
      Sqlsocios.cargar("");
      limpiar();
      }
    }else{
     JOptionPane.showMessageDialog(null, "El socio ya existe");
     
         }
      }else{
        
     JOptionPane.showMessageDialog(null, "Llene todos los Campos y/o Seleccione todas las opciones");
     }
    }
    
    public void cargarImagen(){

         JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG, PNG","jpg","png");
        fc.setFileFilter(filtro);
        int seleccion = fc.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();

            try {

                FileInputStream fis = new FileInputStream(fichero);
                socio.setImput(fis);
                socio.setRutafile(fichero);
                
 rsscalelabel.RSScaleLabel.setScaleLabel(frsocios.imagen, fc.getSelectedFile().toString());
            } catch (HeadlessException | FileNotFoundException ex) {
             //  JOptionPane.showMessageDialog(null, "Error al Guardar Imagen");
                System.out.println(ex);
            }
        }
// JFileChooser se = new JFileChooser();
//        se.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        int estado = se.showOpenDialog(null);
//        if (estado == JFileChooser.APPROVE_OPTION) {
//            try {
//
//                fisfoto = new FileInputStream(se.getSelectedFile());
//                this.longitudBytes = (int) se.getSelectedFile().length();
//
//                Image icono = ImageIO.read(se.getSelectedFile()).getScaledInstance(frsocios.imagen.getWidth(), frsocios.imagen.getHeight(), Image.SCALE_DEFAULT);
//                frsocios.imagen.setIcon(new ImageIcon(icono));
//                frsocios.imagen.updateUI();
//
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }
    public void agregar (File ruta){
     
        try{
            byte[] icono = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
            socio.setFoto(icono);
        }catch(IOException ex){
            System.out.println(ex);
             JOptionPane.showMessageDialog(null, ex);
     
            socio.setFoto(null);
        }
        sqlsocio.registrar(socio);
      
    }
        
        
    public void eliminar() throws SQLException{
        try{
        int fila= Socios.tabla_socio.getSelectedRow();
        
            if(fila<0){
            JOptionPane.showMessageDialog(null, "Seleccione alguna fila");
          
            }else {
                 int ide = Integer.parseInt(Socios.tabla_socio.getValueAt(fila, 0).toString());
                 socio.setNum_socio(ide);
                if(JOptionPane.showConfirmDialog(null, "¿Eliminar el registro?", "",
                        JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                if(sqlsocio.eliminar(socio)){
                    
                JOptionPane.showMessageDialog(null, "Eliminado correctamente", "Información", JOptionPane.OK_OPTION);
      //          Tabla.removeRow(fila);
               Sqlsocios.cargar("");
               limpiar();
                }else{
                JOptionPane.showMessageDialog(null, "Error al eliminar", "Información", JOptionPane.OK_CANCEL_OPTION);
                }
             }
         }
        
        }catch(HeadlessException | NumberFormatException ex){
            System.out.println(ex);
        }
          
        }
    
    public void modificar(){
          int fila= Socios.tabla_socio.getSelectedRow();
        
            if(fila<0){
            JOptionPane.showMessageDialog(null, "Seleccione alguna fila");
          
            }else {
     if(!frsocios.txt_nom.getText().equals("") && 
         !frsocios.txt_ci.getText().equals("") && 
           !frsocios.txt_direc.getText().equals("") &&
           frsocios.txt_fecha.getDate()!=null && !frsocios.txt_tel.getText().equals(" ")){
            
   
   
     socio.setNum_ced(frsocios.txt_ci.getText());

     socio.setTelefono(frsocios.txt_tel.getText());
     socio.setNombre(frsocios.txt_nom.getText());
     socio.setDireccion(frsocios.txt_direc.getText());
     
     java.util.Date utilStartDate = frsocios.txt_fecha.getDate();
     java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());

     socio.setFecha_ing(sqlStartDate);
    // if(socio.getImput()!=null && socio.getRutafile()!=null){
    
     socio.setImput(socio.getImput());
     socio.setRutafile(socio.getRutafile());
    // }
     
 
     socio.setNum_socio(Integer.parseInt(frsocios.txt_socio.getText()));
     
   
     try {
        Icon icons = frsocios.imagen.getIcon();
        BufferedImage bi = new BufferedImage(icons.getIconWidth(), icons.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icons.paintIcon(null, g, 0, 0);
        g.setColor(Color.WHITE);
        g.drawString(frsocios.imagen.getText(), 10, 20);
        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        ImageIO.write(bi, "jpg", os);
        InputStream fis = new ByteArrayInputStream(os.toByteArray());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum);
          //  System.out.println("read " + readNum + " bytes,");
            }
         byte[] bytes = bos.toByteArray();
       socio.setFoto(bytes); 
    } catch (IOException d) {
        JOptionPane.showMessageDialog(null, d);
    }
     
     
if(JOptionPane.showConfirmDialog(null, "Modificar el registro?", "", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
     if(sqlsocio.modificar(socio)){
     JOptionPane.showMessageDialog(null, "Modificación exitosa");
      Sqlsocios.cargar("");
      limpiar();
      }
}
      }else{
        
     JOptionPane.showMessageDialog(null, "Campos vacios o Seleccione una opción");
     }
    
    }
  }
    
    void limpiar(){
    frsocios.txt_socio.setText("");
    frsocios.txt_ci.setText("");
    frsocios.txt_tel.setText("");
    frsocios.txt_fecha.setDate(null);
    frsocios.txt_direc.setText("");
    frsocios.txt_nom.setText("");
    frsocios.imagen.setIcon(null);
    
    }
}
