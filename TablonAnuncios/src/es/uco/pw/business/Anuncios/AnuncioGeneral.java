package es.uco.pw.business.Anuncios;

import java.util.ArrayList;
import java.util.Date;

import es.uco.pw.business.Usuario.Contacto;


/**
 * 
 * 
 * Clase que representa a un anuncio general
 */
public class AnuncioGeneral extends Anuncio {

    public AnuncioGeneral(int id, String titulo, String cuerpo, Date fecha_publicacion,
            Contacto propietario, EstadoAnuncio estadoAnuncio, ArrayList<Contacto> destinatarios) {
        super(id, TipoAnuncio.General, titulo, cuerpo, fecha_publicacion, propietario, estadoAnuncio, destinatarios);
        
    }
    
}
