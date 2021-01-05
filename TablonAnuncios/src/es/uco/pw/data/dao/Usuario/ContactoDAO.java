package es.uco.pw.data.dao.Usuario;

import es.uco.pw.data.dao.common.DAO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

import es.uco.pw.business.DTO.DTOUsuario.ContactoDTO;
import es.uco.pw.business.Usuario.Contacto;


/**
 * 
 * DAO que realiza diferentes operaciones sobre los usuarios presentes en la base de datos
 *
 */
public class ContactoDAO extends DAO {

    public ContactoDAO(String sqlPropertiesPath){
        super(sqlPropertiesPath);
    }
    
    /**
     * 
     * M&eacute;todo que inserta a un usuario
     * 
     * @param contact Instancia de ContactoDTO que generar&aacute; la conexi&oacute; con la base de datos
     * @param password Contrase&ntilde;a del usuario
     * @return Un entero que indica el n&uacute;mero de filas afectadas por la operaci&oacute;n
     */
    public int InsertarContacto(ContactoDTO contact,String password){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.contacto"));
            ps.setString(1, contact.getEmail());
            ps.setString(2, contact.getName());
            ps.setString(3, contact.getLastName());
            java.sql.Date birthDate = new java.sql.Date(contact.getBirthDate().getTime());
            ps.setDate(4, birthDate);
            
            String intereses="";            
            for(String interes : contact.getTagsLists()){
                intereses+=interes.toLowerCase()+",";
            }
            if(contact.getTagsLists().size()>0){
                intereses=intereses.substring(0,intereses.length()-1);
            }
            ps.setString(5, intereses);
            
            status = ps.executeUpdate();

            PreparedStatement psContrasena = conect.prepareStatement(sqlProp.getProperty("insertar.Password"));
            psContrasena.setString(1, contact.getEmail());
            psContrasena.setString(2, password);
            status=psContrasena.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }

   
    
    /**
     * 
     * M&eacute;todo que elimina a un usuario
     * 
     * @param contact Instancia de una clase Contacto
     * @return Un entero que indica el n&uacute;mero de filas afectadas por la operaci&oacute;n
     */

    public int BorrarContacto(Contacto contact){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps=conect.prepareStatement(sqlProp.getProperty("borrar.contacto"));
            ps.setString(1, contact.getEmail());
            status=ps.executeUpdate();
            conect.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return status;
    }

    
    
    /**
     * 
     * M&eacute;todo que modifica a un usuario
     * 
     * @param contactoDTO Instancia de ContactoDTO que generar&aacute; la conexi&oacute; con la base de datos
     * @return Un entero que indica el n&uacute;mero de filas afectadas por la operaci&oacute;n
     */
    public int ModificarContacto(ContactoDTO contactoDTO){
       
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps= conect.prepareStatement(sqlProp.getProperty("modificar.contacto"));
            ps.setString(1, contactoDTO.getName());
            ps.setString(2, contactoDTO.getLastName());
            java.sql.Date birthDate = new java.sql.Date(contactoDTO.getBirthDate().getTime());
            ps.setDate(3, birthDate);
            String intereses="";
            
            for(String interes : contactoDTO.getTagsLists()){
                interes+=interes.toLowerCase()+",";
            }
            if(contactoDTO.getTagsLists().size()>0){
                intereses=intereses.substring(0,intereses.length()-1);
            }
            ps.setString(4, intereses);
            ps.setString(5, contactoDTO.getEmail());

            status=ps.executeUpdate();
        }catch(Exception e){
            e.getMessage();
        }
        return status;
    }
    
    /**
     * 
     * M&eacute;todo que obtiene los datos de un contacto a trav&eacute;s de su email
     * 
     * @param email Email del usuario en cuesti&uacute;n
     * @return Una tabla hash donde la clave es el nombre de cada atributo y el contenido su valor
     */

    public Hashtable<String,String> ObtenerContacto(String email){
        Hashtable<String,String> ret = new Hashtable<String,String>();
        try{
            
            Connection conect = getConection();
            
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getById.contacto"));
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
                 
            while(rs.next()){
                String _email=rs.getString(1);
                String _nombre=rs.getString(2);
                String _apellidos=rs.getString(3);
                java.util.Date _fechaNacimiento = new java.util.Date(rs.getDate(4).getTime());
                String _intereses=rs.getString(5);
                ret=new Hashtable<String,String>();
                ret.put("Email",_email);
                ret.put("Nombre", _nombre);
                ret.put("Apellidos",_apellidos);
                ret.put("Fecha_Nacimiento",_fechaNacimiento.toString());
                ret.put("Intereses", _intereses);
            }
            
           
        }catch(Exception e){
            System.out.println(e);
        }

        return ret;
    }


    /**
     * 
     * M&eacute;todo que obtiene la instancia de un contacto a trav&eacute;s de su email
     * 
     * @param email Email del usuario en cuesti&uacute;n
     * @return Instancia de Contacto con los datos de este
     */

    public Contacto ObtenerContactoById(String email){
        Contacto ret = null;
        try{
            
            Connection conect = getConection();
            
            Properties sqlProp = new Properties();
          
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getById.contacto"));
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
                 
            while(rs.next()){
                String _email=rs.getString(1);
                String _nombre=rs.getString(2);
                String _apellidos=rs.getString(3);
                java.util.Date _fechaNacimiento = new java.util.Date(rs.getDate(4).getTime());
                String _intereses=rs.getString(5);
                ArrayList<String>temas=new ArrayList<String>(Arrays.asList(_intereses.split(",")));
                ret=new Contacto(_email, _nombre, _apellidos, _fechaNacimiento, temas);
                
            }
            
           
        }catch(Exception e){
            System.out.println(e);
        }

        return ret;
    }

    
    /**
     * 
     * M&eacute;todo que obtiene la contrase&ntilde;a de un usuario a trav&eacute;s de su email
     * 
     * @param email Email del usuario en cuesti&uacute;n
     * @return Contrase&ntilde;a de este
     */
    public String ObtenerPasswordUsuario(String email){
        String ret ="";
       
        try{
            Connection conect = getConection();
            
            Properties sqlProp = new Properties();
          
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getPassword.Usuario"));
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
                 
            rs.next();
            ret=rs.getString(1);                
         
            
        }catch(Exception e){
            e.printStackTrace();
        }

        
        
        return ret;
    }
    
    
    /**
     * 
     * M&eacute;todo que obtiene todos los emails de los contactos registrados
     *
     * @return Una lista de strings donde cada elemento es el email de un usuario determinado
     */
    public ArrayList<String> ObtenerEmailContactos(){
        ArrayList<String> ret = new ArrayList<String>();     
        try{
            
            Connection conect = getConection();
            
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getEmails.contacto"));
            
            ResultSet rs=ps.executeQuery();
                 
            while(rs.next()){
                
                String _email=rs.getString(1);

                ret.add(_email);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return ret;
        
    }

}

