package es.uco.pw.data.dao.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



/**
 * 
 * 
 * 
 * 
 * Clase DAO utilizada para obtener informaci&oacute;n desde la base de datos utilizando la API JDBC y el fichero de propiedades sql.properties
 *
 */
public class DAO {
    protected String sqlPropertiesPath;
    public DAO(String sqlPropertiesPath){
        this.sqlPropertiesPath=sqlPropertiesPath;
    }
    
    /**
     * 
     * Funcion que obtiene la conexi&oacute;n con la base de datos
     * 
     * @return una instancia de la misma
     */
    protected Connection getConection(){
        Connection conect = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream(sqlPropertiesPath);
            sqlProp.load(is);
            conect = DriverManager.getConnection(sqlProp.getProperty("servidor.url"), 
                                                 sqlProp.getProperty("servidor.user"), 
                                                 sqlProp.getProperty("servidor.password"));

        }catch(Exception e){
            System.out.println(e);
        }

        return conect;
    }

    
}

