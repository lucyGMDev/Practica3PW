package es.uco.pw.business.Usuario;

import java.util.Date;
import java.util.ArrayList;

/**
 * 
 * 
 * 
 * Clase Contacto, el cual representa a cada usuario registrado en el sistema
 * 
 * <p>
 * Los atributos almacenados de cada uno de ellos son:
 * </p>
 * 
 * <ul>
 * 
 * <li>E-mail</li>
 * 
 * <li>Nombre</li>
 * 
 * <li>Apellidos</li>
 * 
 * <li>Fecha de nacimiento (en formato dd-mm-aaaa)</li>
 * <li>Lista de intereses</li>
 * 
 *
 * </ul>
 * 
 * 
 * 
 * 
 * 
 */
public class Contacto {
    private String email;
    private String name;
    private String lastName;
    private Date birthDate;
    private ArrayList<String> tagsLists;

   
    public Contacto(String email, String name, String lastName, Date birthDate, ArrayList<String> tagsLists) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.setTagsLists(tagsLists);
    }

    
    public ArrayList<String> getTagsLists() {
        return tagsLists;
    }


    
    public void setTagsLists(ArrayList<String> tagsLists) {
        this.tagsLists = tagsLists;
    }

    
    public String getEmail() {
        return email;
    }

 

    

    public Date getBirthDate() {
        return birthDate;
    }

  

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

   
   
    public String getLastName() {
        return lastName;
    }

    

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   
    public String getName() {
        return name;
    }


   
    public void setName(String name) {
        this.name = name;
    }

    
   
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * Sobrecarga de la función toString
     * 
     * @return Devuelve una frase en forma de cadena con todos los datos del contacto
     */
    @Override
    public String toString() {
        String ret= "Soy el contacto con email "+getEmail()+" mi nombre es "+getName()+" "+getLastName()+"\n"+
        "mi cumpleaños es el dia "+getBirthDate().toString();

        return ret;
    }
}


