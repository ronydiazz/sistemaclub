/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloSql;

import coneccion.Conexion;
import formularios.convocados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import variables.jugadores;

/**
 *
 * @author DELL
 */
public class Sqlconvocados extends Conexion {
    
    public static void cargar(String combocat, String valor){
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();
   
      String sql="SELECT id_jugador, num_cedula, nombre, sub, categoria, posicion, convocados FROM JUGADORES"
              + " WHERE categoria='"+combocat+"' and (num_cedula LIKE '%"+valor+"%' or nombre LIKE '%"+valor+"%') ORDER BY id_jugador ASC";
        
        String []titulos={"ID","CEDULA","NOMBRE","CATEGORIA","POSICION","CONVOCADO"};
        String []registros= new String[6];
        DefaultTableModel model= new DefaultTableModel(null, titulos){
          @Override
          public boolean isCellEditable(int i, int i1) {
              return false;} };
        
    
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
            registros[5]=rs.getString("convocados");
            model.addRow(registros);
            }
            convocados.tabla_jugadores.setModel(model);
            convocados.tabla_jugadores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel columnModel= convocados.tabla_jugadores.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);
            columnModel.getColumn(1).setPreferredWidth(70);
            columnModel.getColumn(2).setPreferredWidth(160);
            columnModel.getColumn(3).setPreferredWidth(120);
            columnModel.getColumn(4).setPreferredWidth(160);
            columnModel.getColumn(5).setPreferredWidth(140);
            
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
   
    
    public static int condicion(){
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();
      String combo_con=convocados.combo_convocados.getSelectedItem().toString();
     String sql="SELECT count(convocados)  FROM JUGADORES"
              + " WHERE convocados='"+combo_con+"'";   
    int registro=0;
        try{
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            
            while(rs.next()){
           registro=rs.getInt(1);
            }
            
            //JOptionPane.showMessageDialog(null, registro);
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
        return registro;
    }
    
    public boolean modificar(jugadores jug){
        
        PreparedStatement ps;
        Connection con=getConexion();
        String sql="UPDATE jugadores SET convocados=? WHERE id_jugador=? ";
        
        String combo_con=convocados.combo_convocados.getSelectedItem().toString();
    int registro;
     registro=condicion();
    
   if(combo_con.equals("TITULAR")){
  
   if (registro<11){
       
      try{
        ps=con.prepareStatement(sql);
        ps.setString(1, jug.getConvocados());
       
        ps.setInt(2, jug.getId());
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
   
   }else{
   
   JOptionPane.showMessageDialog(null, "Cantidad Máxima de jugadores Titulares alcanzada");
   }
       
       
   }else if (combo_con.equals("SUPLENTE")){
    
       if(registro<7){
        try{
        ps=con.prepareStatement(sql);
        ps.setString(1, jug.getConvocados());
       
        ps.setInt(2, jug.getId());
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
       
       }else{
      JOptionPane.showMessageDialog(null, "Cantidad Máxima de jugadores Suplentes alcanzada");
       
       }
   }
      return true;
    }

    public boolean quitar_modificar(jugadores jug){
        
        PreparedStatement ps;
        Connection con=getConexion();
        String sql="UPDATE jugadores SET convocados=? WHERE id_jugador=? ";
      try{
        ps=con.prepareStatement(sql);
        ps.setString(1, jug.getConvocados());
        ps.setInt(2, jug.getId());
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
    
     public static void mostrar_convo(String combocat, String convo){
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();
      String sql="SELECT id_jugador, num_cedula, nombre, sub, categoria, posicion, convocados FROM JUGADORES"
              + " WHERE categoria='"+combocat+"' and convocados='"+convo+"' ORDER BY convocados ASC";
        
        String []titulos={"ID","CEDULA","NOMBRE","CATEGORIA","POSICION","CONVOCADO"};
        String []registros= new String[6];
        DefaultTableModel model= new DefaultTableModel(null, titulos){
          @Override
          public boolean isCellEditable(int i, int i1) {
              return false;} };
        
    
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
            registros[5]=rs.getString("convocados");
            model.addRow(registros);
            }
            convocados.tabla_jugadores.setModel(model);
            convocados.tabla_jugadores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel columnModel= convocados.tabla_jugadores.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);
            columnModel.getColumn(1).setPreferredWidth(70);
            columnModel.getColumn(2).setPreferredWidth(160);
            columnModel.getColumn(3).setPreferredWidth(120);
            columnModel.getColumn(4).setPreferredWidth(160);
            columnModel.getColumn(5).setPreferredWidth(140);
            
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
}
