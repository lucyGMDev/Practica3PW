package es.uco.pw.display.useBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CustomerBean implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
    String email="";
    String nombre="";
    String apellidos="";
    String contraseña="";
    Date fechaNacimiento=new Date();
    ArrayList<String> intereses=new ArrayList<String>();

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public ArrayList<String> getIntereses() {
		return intereses;
	}
	public void setIntereses(ArrayList<String> intereses) {
		this.intereses=intereses;
    }

}