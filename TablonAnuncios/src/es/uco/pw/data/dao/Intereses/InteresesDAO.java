package es.uco.pw.data.dao.Intereses;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Properties;

import es.uco.pw.data.dao.common.DAO;


/**
 * 
 * DAO que obtiene todos los intereses almacenados en la base de datos, funcionando igual que el DAO general
 *
 */
public class InteresesDAO extends DAO {

    public InteresesDAO(String sqlPropertiesPath){
        super(sqlPropertiesPath);
    }
    
    /**
     * 
     * Metodo que obtiene todos los intereses
     * 
     * @return Una tabla hash con estos, donde la clave es su id (llave primaria en la base de datos) y 
     * el valor el nombre en s &iacute; del inter&eacute;s
     */
    public Hashtable<Integer, String> DevolverIntereses() {
        Hashtable<Integer, String> ret = new Hashtable<Integer, String>();

        try {
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("mostrar.Intereses"));
            ResultSet result = ps.executeQuery();

            while(result.next()){
                
                ret.put(result.getInt(1),result.getString(2));
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }

        return ret;

    }

}
