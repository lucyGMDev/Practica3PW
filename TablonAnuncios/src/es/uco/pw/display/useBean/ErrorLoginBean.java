package es.uco.pw.display.useBean;

import java.io.Serializable;

/**
 *Clase que analiza cu&aacute;ntas veces el usuario ha errado al iniciar sesi&oacute;n.
 *
 *<p>
 *
 *En caso de ser m&aacute;s de 3, se redirige a una p&aacute;gina de error
 *
 *</p>
 */


public class ErrorLoginBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int numerosIntentos = 3;
	
	public void setNumerosIntentos(int numerosIntentos){
		this.numerosIntentos=numerosIntentos;
	}
	public int getNumerosIntentos(){
		return this.numerosIntentos;
	}
}