/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package variables;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.stream.Stream;
import javax.swing.ImageIcon;

/**
 *
 * @author rony
 */
public class jugadores {
 private   int id;
 private   String num_ced;
 private    String categoria;
 private    String sub;
 private    String telefono;
 private    String nombre;
 private    Date fecha;
 private    String posicion;
 private    String rutaa;
 private    String convocados;

    public String getConvocados() {
        return convocados;
    }

    public void setConvocados(String convocados) {
        this.convocados = convocados;
    }
 private    byte[] foto;
 private    ImageIcon foto2;
 private FileInputStream imput;
 private File rutafile;
 private Stream binary;

    public Stream getBinary() {
        return binary;
    }

    public void setBinary(Stream binary) {
        this.binary = binary;
    }

    public File getRutafile() {
        return rutafile;
    }

    public void setRutafile(File rutafile) {
        this.rutafile = rutafile;
    }

    public FileInputStream getImput() {
        return imput;
    }

    public void setImput(FileInputStream imput) {
        this.imput = imput;
    }

    public ImageIcon getFoto2() {
        return foto2;
    }

    public void setFoto2(ImageIcon foto2) {
        this.foto2 = foto2;
    }
    
    public String getRutaa() {
        return rutaa;
    }

    public void setRutaa(String rutaa) {
        this.rutaa = rutaa;
    }
  

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNum_ced() {
        return num_ced;
    }

    public void setNum_ced(String num_ced) {
        this.num_ced = num_ced;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    
    
}
