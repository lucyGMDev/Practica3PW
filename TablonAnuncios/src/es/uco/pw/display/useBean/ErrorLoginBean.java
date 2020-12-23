package es.uco.pw.display.useBean;

import java.io.Serializable;

public class ErrorLoginBean implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int numerosIntentos = 3;
	
	public void setNumerosIntentos(int numerosIntentos){
		this.numerosIntentos=numerosIntentos;
	}
	public int getNumerosIntentos(){
		return this.numerosIntentos;
	}
}