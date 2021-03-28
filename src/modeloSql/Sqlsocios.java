/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloSql;

import coneccion.Conexion;
import formularios.Socios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import variables.socios;

/**
 *
 * @author rony
 */
public class Sqlsocios extends Conexion {
    
    private Socios frsocios;
   public static void cargar(String valor){
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();

   String sql="SELECT id_socio, num_ced, telefono, nombre_ape, fecha_ing FROM SOCIOS"
                  + " WHERE num_ced LIKE '%"+valor+"%' or nombre_ape LIKE '%"+valor+"%' ORDER BY id_socio ASC";
        
        String []titulos={"ID","NUMERO DE C.I","NOMBRE/APELLIDO","FECHA INGRESO","TELEFONO"};
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
            registros[0]=rs.getString("id_socio");
            registros[1]=rs.getString("num_ced");
            registros[2]=rs.getString("nombre_ape");
            registros[3]=rs.getString("fecha_ing");
            registros[4]=rs.getString("telefono");
            model.addRow(registros);
            }
            Socios.tabla_socio.setModel(model);
            Socios.tabla_socio.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel columnModel= Socios.tabla_socio.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(130);
            columnModel.getColumn(2).setPreferredWidth(160);
            columnModel.getColumn(3).setPreferredWidth(115);
            columnModel.getColumn(4).setPreferredWidth(115);
            
        } catch (SQLException ex){
            Logger.getLogger(Sqlsocios.class.getName()).log(Level.SEVERE, null, ex);
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

   public static List mostrar(String valor){
    
      Conexion cc= new Conexion();
      Connection con=cc.getConexion();
        String sql="SELECT id_socio, num_ced, telefono, nombre_ape, fecha_ing, direccion, imagen_socio from socios "
                + "WHERE id_socio ='"+valor+"'";
      List listSocio= new ArrayList(8);
        try{
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            while(rs.next()){
           socios soc= new socios();
           soc.setNum_socio(rs.getInt("id_socio"));
           soc.setNum_ced(rs.getString("num_ced"));
           soc.setTelefono(rs.getString("telefono"));
           soc.setNombre(rs.getString("nombre_ape"));
           soc.setFecha_ing(rs.getDate("fecha_ing"));
           soc.setDireccion(rs.getString("direccion"));
           soc.setFoto(rs.getBytes("imagen_socio"));
           listSocio.add(soc);
            } 
        } catch (SQLException ex){
            Logger.getLogger(Sqlsocios.class.getName()).log(Level.SEVERE, null, ex);
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
        return listSocio;
    }
  
   public boolean registrar(socios soc){
        PreparedStatement ps;
        Connection con=getConexion();
        String sql="INSERT INTO socios (num_ced, telefono, nombre_ape, fecha_ing, direccion, imagen_socio) VALUES (?,?,?,?,?,?)";
        try{
        ps=con.prepareStatement(sql);
        ps.setString(1, soc.getNum_ced());
        ps.setString(2, soc.getTelefono());
        ps.setString(3, soc.getNombre());
        ps.setDate(4, soc.getFecha_ing());
        ps.setString(5, soc.getDireccion());
        if(soc.getImput()!=null || soc.getRutafile()!=null){
            ps.setBinaryStream(6, soc.getImput(), (int) soc.getRutafile().length()); 
        }else{
        ps.setBinaryStream(6, null, 0);
        }
      //  ps.setBytes(6, soc.getFoto());
        ps.execute();
        ps.close();
        return true;
        } catch (SQLException ex){
            Logger.getLogger(Sqlsocios.class.getName()).log(Level.SEVERE, null, ex);
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
    
   public boolean modificar(socios soc){
        PreparedStatement ps;
        Connection con=getConexion();
        String sql="UPDATE socios set num_ced=?, telefono=?, nombre_ape=?, fecha_ing=?, direccion=?, imagen_socio=? WHERE id_socio=?";
        try{
        ps=con.prepareStatement(sql);
        ps.setString(1, soc.getNum_ced());
        ps.setString(2, soc.getTelefono());
        ps.setString(3, soc.getNombre());
        ps.setDate(4, soc.getFecha_ing());
        ps.setString(5, soc.getDireccion());
        
     //   soc.getImput()!=null && soc.getRutafile()!=null
    
          
//               if(soc.getImput()!=null && soc.getRutafile()!=null){
//              ps.setBinaryStream(6, soc.getImput(), (int) soc.getRutafile().length());  
//               }else{
//                ps.setBinaryStream(6, null, 0);
//               }
           
        ps.setBytes(6, soc.getFoto());
        ps.setInt(7, soc.getNum_socio());
        ps.execute();
        ps.close();
        return true;
        } catch (SQLException ex){
            Logger.getLogger(Sqlsocios.class.getName()).log(Level.SEVERE, null, ex);
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
     
   public boolean eliminar(socios soc){
        PreparedStatement ps;
        ResultSet rs;
        Connection con=getConexion();
        String sql="DELETE FROM socios WHERE id_socio=?";
        try{
        ps=con.prepareStatement(sql);
         
        ps.setInt(1, soc.getNum_socio());
        rs=ps.executeQuery();
        
        if ( rs.next()) {
            
         return true;
        }
        return false;
        
        } catch (SQLException ex){
            Logger.getLogger(Sqlsocios.class.getName()).log(Level.SEVERE, null, ex);
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
    
     public int existe(String soc){
        PreparedStatement ps;
        ResultSet rs;
        Connection con=getConexion();
        String sql="select count (id_socio) from socios WHERE num_ced=?";
        try{
        ps=con.prepareStatement(sql);
         
        ps.setString(1, soc);
        rs=ps.executeQuery();
        
        if ( rs.next()) {
            
         return rs.getInt(1);
        }
        return 1;
        
        } catch (SQLException ex){
            Logger.getLogger(Sqlsocios.class.getName()).log(Level.SEVERE, null, ex);
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
