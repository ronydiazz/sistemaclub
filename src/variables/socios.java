/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package variables;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author rony
 */
public class socios {
    private int num_socio;
    private String num_ced;
    private String telefono;
    private String nombre;
    private Date fecha_ing;
    private String direccion;
    private byte[] foto;
    private ImageIcon foto2;
    private FileInputStream imput;
    private File rutafile;
 

    public ImageIcon getFoto2() {
        return foto2;
    }

    public void setFoto2(ImageIcon foto2) {
        this.foto2 = foto2;
    }

    public FileInputStream getImput() {
        return imput;
    }

    public void setImput(FileInputStream imput) {
        this.imput = imput;
    }

    public File getRutafile() {
        return rutafile;
    }

    public void setRutafile(File rutafile) {
        this.rutafile = rutafile;
    }

    public int getNum_socio() {
        return num_socio;
    }

    public void setNum_socio(int num_socio) {
        this.num_socio = num_socio;
    }

    public String getNum_ced() {
        return num_ced;
    }

    public void setNum_ced(String num_ced) {
        this.num_ced = num_ced;
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

    public Date getFecha_ing() {
        return fecha_ing;
    }

    public void setFecha_ing(Date fecha_ing) {
        this.fecha_ing = fecha_ing;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
}
