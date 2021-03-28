
package controlador;

import coneccion.Conexion;
import formularios.JUGADORES;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
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
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;
import modeloSql.Sqljugadores;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import variables.CustomImageIcon;
import variables.jugadores;


public class ctrlJugadores implements ActionListener {
    
private jugadores jug;
private Sqljugadores juga;
private JUGADORES frjuga;

public ctrlJugadores(jugadores jug, Sqljugadores juga, JUGADORES frjuga){
this.jug=jug;
this.juga=juga;
this.frjuga=frjuga;
this.frjuga.btnRegistrar.addActionListener(this);
this.frjuga.btnModificar.addActionListener(this);
this.frjuga.btnEliminar.addActionListener(this);
this.frjuga.btnCargarImagen.addActionListener(this);
this.frjuga.combo_cat.addItemListener(combo);
this.frjuga.btnNuevo.addActionListener(this);
JUGADORES.combo_cat1.addItemListener(combo1);
JUGADORES.tabla_jugadores.addMouseListener(mou);
frjuga.txt_bus_ci.addKeyListener(tecla);
frjuga.txt_bus_nom.addKeyListener(tecla);
frjuga.txt_nombre.addKeyListener(tecla);

}
  public void iniciar(){
      Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(),"");
 desbloqueoCombo();
    }
  
    @Override
    public void actionPerformed(ActionEvent ae) {
        
      if(ae.getSource()==frjuga.btnCargarImagen){
          cargarImagen();
     
    }
      
    if(ae.getSource()==frjuga.btnRegistrar){
        
   registrar();
    }
    
    if(ae.getSource()==frjuga.btnModificar){
    modificar();
    }
    
    if(ae.getSource()==frjuga.btnEliminar){
          try {
              eliminar();
          } catch (SQLException ex) {
              Logger.getLogger(ctrlJugadores.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    if(ae.getSource()==frjuga.btnNuevo){
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
    if(ke.getSource()==frjuga.txt_bus_ci){
        if(JUGADORES.combo_cat1.getSelectedItem().equals("PRIMERA")){
          Sqljugadores.cargar("PRIMERA",frjuga.txt_bus_ci.getText());
        }
        if(JUGADORES.combo_cat1.getSelectedItem().equals("JUVENIL")){
          Sqljugadores.cargar("JUVENIL",frjuga.txt_bus_ci.getText());
        }
        if(JUGADORES.combo_cat1.getSelectedItem().equals("FORMATIVA")){
          Sqljugadores.cargar("FORMATIVA",frjuga.txt_bus_ci.getText());
        }
      
    
    }
    if(ke.getSource()==frjuga.txt_bus_nom){
          frjuga.txt_bus_nom.setText (frjuga.txt_bus_nom.getText().toUpperCase());
        if(JUGADORES.combo_cat1.getSelectedItem().equals("PRIMERA")){
            
         Sqljugadores.cargar("PRIMERA",frjuga.txt_bus_nom.getText());
        }
         if(JUGADORES.combo_cat1.getSelectedItem().equals("JUVENIL")){
          Sqljugadores.cargar("JUVENIL",frjuga.txt_bus_nom.getText());
        }
        if(JUGADORES.combo_cat1.getSelectedItem().equals("FORMATIVA")){
          Sqljugadores.cargar("FORMATIVA",frjuga.txt_bus_nom.getText());
        }
    
    }
    if(ke.getSource()==frjuga.txt_nombre){
      frjuga.txt_nombre.setText (frjuga.txt_nombre.getText().toUpperCase());
    
    }
   
//    if(ke.getSource()==frjuga.txt_bus_nom ){
//      frjuga.txt_bus_nom.setText (frjuga.txt_bus_nom.getText().toUpperCase());
//    
//    }
    
    }   
};
    
    ItemListener combo = (ItemEvent ie) -> {
        desbloqueoCombo();
};
    
        ItemListener combo1= new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent ie) {
         Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(), "");
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
    int fila= JUGADORES.tabla_jugadores.getSelectedRow();
    String codigo= String.valueOf(JUGADORES.tabla_jugadores.getValueAt(fila, 0));
    List ls;
    
    try{
    ls=Sqljugadores.mostrar(codigo);
    
    for(int i=0;i<ls.size();i++){
    jugadores jugg= new jugadores();
    jugg= (jugadores) ls.get(i);
    frjuga.txt_id.setText(Integer.toString(jugg.getId()));
    frjuga.txt_cedula.setText(jugg.getNum_ced());
    frjuga.combo_cat.setSelectedItem(jugg.getCategoria());
    if(jugg.getCategoria().equals("FORMATIVA")){
     
      frjuga.combo_sub.setEnabled(true);
      frjuga.combo_sub.setSelectedItem(jugg.getSub());
    }else{
        frjuga.combo_sub.setSelectedItem(0);
    frjuga.combo_sub.setEnabled(false);
    }
    
    frjuga.txt_tel.setText(jugg.getTelefono());
    frjuga.txt_nombre.setText(jugg.getNombre());
    frjuga.txt_fecha.setDate(jugg.getFecha());
    frjuga.combo_posicion.setSelectedItem(jugg.getPosicion());
    
    
                try{
                    byte[] bi = jugg.getFoto();
                    if (bi!=null){
                     BufferedImage image = null;
                    InputStream in = new ByteArrayInputStream(bi);
                    //error abajo
                    image = ImageIO.read(in);
                    ImageIcon imgi = new ImageIcon(image.getScaledInstance(138, 175, 0));
                    JUGADORES.foto.setIcon(imgi);
                      JUGADORES.foto.updateUI();
                    }else{
                     JUGADORES.foto.setIcon(null);
                    }
                   

                }catch(IOException ex){
               //     JUGADORES.foto.setIcon("No imagen");
                    System.out.println(ex);    
                }

//                JUGADORES.foto.setIcon(jugg.getFoto2());
//                   JUGADORES.foto.updateUI();
    }
    }catch(IOException ex){
        System.out.println(ex);    
    }
    
    }
    
    public void desbloqueoCombo(){
        if( frjuga.combo_cat.getSelectedItem()!="FORMATIVA"){
    frjuga.combo_sub.setSelectedItem("");
    frjuga. combo_sub.setEnabled(false);
       }else{
       frjuga.combo_sub.setEnabled(true);    
       frjuga.combo_sub.setSelectedItem("Seleccione una opción");
        }
    }
    
    public void registrar(){
        if(frjuga.combo_cat.getSelectedItem()!="Seleccione una opción" && 
          frjuga.combo_posicion.getSelectedItem()!="Seleccione una opción" && 
         !frjuga.txt_cedula.getText().equals("") && !frjuga.txt_cedula.getText().equals("") && 
          frjuga.txt_fecha.getDate()!=(null) && !frjuga.txt_nombre.getText().equals("") &&
           !frjuga.txt_tel.getText().equals("")){
     if(juga.existe(frjuga.txt_cedula.getText()) == 0){
   
     jug.setNum_ced(frjuga.txt_cedula.getText());
     jug.setCategoria(frjuga.combo_cat.getSelectedItem().toString());
     jug.setSub(frjuga.combo_sub.getSelectedItem().toString());
     jug.setTelefono(frjuga.txt_tel.getText());
     jug.setNombre(frjuga.txt_nombre.getText());
     
     java.util.Date utilStartDate = frjuga.txt_fecha.getDate();
    java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());

     jug.setFecha(sqlStartDate);
     jug.setPosicion(frjuga.combo_posicion.getSelectedItem().toString());

      jug.setImput(jug.getImput());
                jug.setRutafile(jug.getRutafile());


     if(juga.registrar(jug)){
     JOptionPane.showMessageDialog(null, "Registro exitoso");
     actualizar_combo();
     limpiar();
     
      Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(),"");
      
      }
    }else{
     JOptionPane.showMessageDialog(null, "El jugador ya existe");
     
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
                if (fis!=null && fichero !=null){
                 jug.setImput(fis);
                jug.setRutafile(fichero);
                }
               
                
 rsscalelabel.RSScaleLabel.setScaleLabel(JUGADORES.foto, fc.getSelectedFile().toString());
            } catch (HeadlessException | FileNotFoundException ex) {
             //  JOptionPane.showMessageDialog(null, "Error al Guardar Imagen");
                System.out.println(ex);
            }
        }
    }
    
    public void agregar (File ruta){
     
        try{
            byte[] icono = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
            jug.setFoto(icono);
        }catch(IOException ex){
            System.out.println(ex);
             JOptionPane.showMessageDialog(null, ex);
     
            jug.setFoto(null);
        }
        juga.registrar(jug);
      
    }
        
    public void eliminar() throws SQLException{
        try{
        int fila= JUGADORES.tabla_jugadores.getSelectedRow();
        
            if(fila<0){
            JOptionPane.showMessageDialog(null, "Seleccione alguna fila");
          
            }else {
                 int ide = Integer.parseInt(JUGADORES.tabla_jugadores.getValueAt(fila, 0).toString());
                 jug.setId(ide);
                if(JOptionPane.showConfirmDialog(null, "¿Eliminar el registro?", "",
                        JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                if(juga.eliminar(jug)){
                    
                JOptionPane.showMessageDialog(null, "Eliminado correctamente", "Información", JOptionPane.OK_OPTION);
      //          Tabla.removeRow(fila);
      actualizar_combo();
              limpiar();
               
               Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(),"");
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
         int fila= JUGADORES.tabla_jugadores.getSelectedRow();
         if(fila<0){
            JOptionPane.showMessageDialog(null, "Seleccione alguna fila");
          
            }else {
      if(frjuga.combo_cat.getSelectedItem()!="Seleccione una opción" &&
         frjuga.combo_posicion.getSelectedItem()!="Seleccione una opción" && 
         !frjuga.txt_cedula.getText().equals("") && !frjuga.txt_cedula.getText().equals("") && 
          frjuga.txt_fecha.getDate()!=(null) && !frjuga.txt_nombre.getText().equals("") &&
          !frjuga.txt_tel.getText().equals("")){
 
          
     jug.setNum_ced(frjuga.txt_cedula.getText());
     jug.setCategoria(frjuga.combo_cat.getSelectedItem().toString());
     jug.setSub(frjuga.combo_sub.getSelectedItem().toString());
     jug.setTelefono(frjuga.txt_tel.getText());
     jug.setNombre(frjuga.txt_nombre.getText());
     
     java.util.Date utilStartDate = frjuga.txt_fecha.getDate();
    java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());

     jug.setFecha(sqlStartDate);
     jug.setPosicion(frjuga.combo_posicion.getSelectedItem().toString());

      jug.setImput(jug.getImput());
      jug.setRutafile(jug.getRutafile());

      jug.setId(Integer.parseInt(frjuga.txt_id.getText()));
      
       try {
        Icon icons = JUGADORES.foto.getIcon();
        BufferedImage bi = new BufferedImage(icons.getIconWidth(), icons.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icons.paintIcon(null, g, 0, 0);
        g.setColor(Color.WHITE);
        g.drawString(JUGADORES.foto.getText(), 10, 20);
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
    
       jug.setFoto(bytes); 
       
     //  jug.setFoto(bytes); 
    } catch (IOException d) {
        JOptionPane.showMessageDialog(null, d);
    }
 if(JOptionPane.showConfirmDialog(null, "Modificar el registro?", "", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
     if(juga.modificar(jug)){
     JOptionPane.showMessageDialog(null, "Modificación exitosa");
     actualizar_combo();
     limpiar();     
      Sqljugadores.cargar(JUGADORES.combo_cat1.getSelectedItem().toString(),"");
      }
 }
      }else{
        
     JOptionPane.showMessageDialog(null, "Llene todos los Campos y/o Seleccione todas las opciones");
     }
    
    }
   }
    
//    static Image iconToImage(Icon icon) { 
//      if (icon instanceof ImageIcon) {
//      return ((ImageIcon)icon).getImage();
//     } else {
//     int w = icon.getIconWidth();
//     int h = icon.getIconHeight(); 
//     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
//     GraphicsDevice gd = ge.getDefaultScreenDevice(); 
//     GraphicsConfiguration gc = gd.getDefaultConfiguration(); 
//     BufferedImage image = gc.createCompatibleImage(w, h); 
//     Graphics2D g = image.createGraphics(); 
//     icon.paintIcon(null, g, 0, 0); 
//     g.dispose(); 
//     return image; } }
    
    
   void limpiar(){
   frjuga.txt_cedula.setText("");
   frjuga.txt_fecha.setDate(null);
   frjuga.txt_id.setText("");
   frjuga.txt_nombre.setText("");
   frjuga.txt_tel.setText("");
   frjuga.combo_cat.setSelectedIndex(0);
   frjuga.combo_posicion.setSelectedIndex(0);
   frjuga.combo_sub.setSelectedItem("");
   JUGADORES.foto.setIcon(null);
   }
   
   void actualizar_combo(){
   
   JUGADORES.combo_cat1.setSelectedItem(frjuga.combo_cat.getSelectedItem());
   }
   
   void reporte(){
   
    try {
        Conexion cc= new Conexion();
        Connection con=cc.getConexion();
        
        JasperReport repor = null;
        String path ="src\\reportes\\report_Jugadores.jasper";
        repor = (JasperReport) JRLoader.loadObjectFromFile(path);
        JasperPrint jprint= JasperFillManager.fillReport(repor, null, con);
        JasperViewer view = new JasperViewer(jprint, false);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
        
    } catch (JRException ex) {
        Logger.getLogger(ctrlJugadores.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
}
