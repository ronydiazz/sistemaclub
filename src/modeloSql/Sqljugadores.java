/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloSql;

import coneccion.Conexion;
import formularios.JUGADORES;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import variables.jugadores;

/**
 *
 * @author rony
 */
public class Sqljugadores extends Conexion{

    
    public static void cargar(String combocat, String valor){
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();
   
      String sql="SELECT id_jugador, num_cedula, nombre, sub, categoria, posicion FROM JUGADORES"
              + " WHERE categoria='"+combocat+"' and (num_cedula LIKE '%"+valor+"%' or nombre LIKE '%"+valor+"%') ORDER BY id_jugador ASC";
        
        String []titulos={"ID","CEDULA","NOMBRE","CATEGORIA","POSICION"};
        String []registros= new String[5];
        DefaultTableModel model= new DefaultTableModel(null, titulos){
          @Override
          public boolean isCellEditable(int i, int i1) {
              return false;
          }
        
        };
        try{
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            
            while(rs.next()){
            registros[0]=rs.getString("id_jugador");
            registros[1]=rs.getString("num_cedula");
            registros[2]=rs.getString("nombre");
            registros[3]=rs.getString("categoria"); 
            String sub =registros[3];
            if(sub.equals("FORMATIVA")){
              registros[3]=rs.getString("sub");
            }else{
            registros[3]=rs.getString("categoria"); 
            }
            registros[4]=rs.getString("posicion");
            model.addRow(registros);
            }
            JUGADORES.tabla_jugadores.setModel(model);
            JUGADORES.tabla_jugadores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel columnModel= JUGADORES.tabla_jugadores.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);
            columnModel.getColumn(1).setPreferredWidth(80);
            columnModel.getColumn(2).setPreferredWidth(180);
            columnModel.getColumn(3).setPreferredWidth(120);
            columnModel.getColumn(4).setPreferredWidth(170);
            
        } catch (SQLException ex){
            Logger.getLogger(Sqljugadores.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
            }finally{
        if(con!=null){
        try{
        con.close();
        }catch(SQLException ex){
        System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
       }
      }
    }

    public static List mostrar(String valor) throws IOException{
  //  JUGADORES J= new JUGADORES();
   BufferedImage buffimg = null;
            byte[] image = null;
            
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();
      String sql="SELECT id_jugador, num_cedula, categoria, sub, telefono, nombre, fecha_nac, posicion, imagen FROM JUGADORES "
                + "WHERE id_jugador ='"+valor+"'";
      List listjug= new ArrayList(9);
          try{
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            while(rs.next()){
           jugadores jug= new jugadores();
           jug.setId(rs.getInt("id_jugador"));
           jug.setNum_ced(rs.getString("num_cedula"));
           jug.setCategoria(rs.getString("categoria"));
           
           jug.setSub(rs.getString("sub"));
           jug.setTelefono(rs.getString("telefono"));
           jug.setNombre(rs.getString("nombre"));
           jug.setFecha(rs.getDate("fecha_nac"));
           jug.setPosicion(rs.getString("posicion"));
           jug.setFoto(rs.getBytes("imagen"));
      listjug.add(jug);
            
            }
            
        } catch (SQLException ex){
            Logger.getLogger(Sqljugadores.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
            }finally{
        if(con!=null){
        try{
        con.close();
        }catch(SQLException ex){
        System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
       }
      }
        return listjug;
    }
  
    public boolean registrar(jugadores jug){
        PreparedStatement ps;
        Connection con=getConexion();
        String sql="INSERT INTO JUGADORES (num_cedula, categoria, sub, telefono, nombre, fecha_nac, posicion, imagen) VALUES (?,?,?,?,?,?,?,?)";
        try{
        ps=con.prepareStatement(sql);
        ps.setString(1, jug.getNum_ced());
        ps.setString(2, jug.getCategoria());
        ps.setString(3, jug.getSub());
        ps.setString(4, jug.getTelefono());
        ps.setString(5, jug.getNombre());
        ps.setDate(6,jug.getFecha());
        ps.setString(7, jug.getPosicion());
       if(jug.getImput()!=null || jug.getRutafile()!=null){
            ps.setBinaryStream(8, jug.getImput(), (int) jug.getRutafile().length()); 
        }else{
        ps.setBinaryStream(8, null, 0);
        }
       
//     Blob blob = jug.getBlob("foto");
// int blobLength=(int)blob.length();
// byte[] blobasbytes = blob.getBytes(1, blobLength);
// ImageIcon icon = new ImageIcon((byte[])blobasbytes);
// ps.setFoto(icon.getImage());
        ps.execute();
        ps.close();
        return true;
        } catch (SQLException ex){
            Logger.getLogger(Sqljugadores.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
             JOptionPane.showMessageDialog(null, ex);
             return false;
            }finally{
        if(con!=null){
        try{
        con.close();
        }catch(SQLException ex){
       //   System.out.println(ex);
         //   JOptionPane.showMessageDialog(null, ex);
        }
       }
      }
    }
    
    public boolean modificar(jugadores jug){
        PreparedStatement ps;
        Connection con=getConexion();
        String sql="UPDATE jugadores SET num_cedula=?, categoria=?, sub=?, telefono=?, nombre=?, fecha_nac=?, posicion=?, imagen=? WHERE id_jugador=?";
        try{
        ps=con.prepareStatement(sql);
        ps.setString(1, jug.getNum_ced());
        ps.setString(2, jug.getCategoria());
        ps.setString(3, jug.getSub());
        ps.setString(4, jug.getTelefono());
        ps.setString(5, jug.getNombre());
        ps.setDate(6, jug.getFecha());
        ps.setString(7, jug.getPosicion());
       // ps.setBytes(8, jug.getFoto());
//       if(jug.getImput()!=null || jug.getRutafile()!=null){
//            ps.setBinaryStream(8, jug.getImput(), (int) jug.getRutafile().length()); 
//        }else{
//        ps.setBinaryStream(8, null, 0);
//        }
          ps.setBytes(8, jug.getFoto());
        ps.setInt(9, jug.getId());
        ps.execute();
        ps.close();
        return true;
        } catch (SQLException ex){
            Logger.getLogger(Sqljugadores.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
             JOptionPane.showMessageDialog(null, ex);
             return false;
            }finally{
        if(con!=null){
        try{
        con.close();
        }catch(SQLException ex){
          System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
       }
      }
    }
     
    public boolean eliminar(jugadores jug) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        Connection con=getConexion();
        String sql="DELETE FROM jugadores WHERE id_jugador=?";
        try{
        ps=con.prepareStatement(sql);
         
        ps.setInt(1, jug.getId());
        rs=ps.executeQuery();
        
        if ( rs.next()) {
            
         return true;
        }
        return false;
        
        } catch (SQLException ex){
            Logger.getLogger(Sqljugadores.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
             JOptionPane.showMessageDialog(null, ex);
             return false;
            }finally{
        if(con!=null){
        try{
            
        con.close();
        }catch(SQLException ex){
          System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
       }
      }
    }
      
     public int existe(String jug){
        PreparedStatement ps;
        ResultSet rs;
        Connection con=getConexion();
        String sql="select count (id_jugador) from jugadores WHERE num_cedula=?";
        try{
        ps=con.prepareStatement(sql);
         
        ps.setString(1, jug);
        rs=ps.executeQuery();
        
        if ( rs.next()) {
            
         return rs.getInt(1);
        }
        return 1;
        
        } catch (SQLException ex){
            Logger.getLogger(Sqljugadores.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
             JOptionPane.showMessageDialog(null, ex);
             return 1;
            }finally{
        if(con!=null){
        try{
            
        con.close();
        }catch(SQLException ex){
          System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
       }
      }
    }
     
     
}
