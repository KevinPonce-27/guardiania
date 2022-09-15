/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kdpr.guardiania.data;
//Este archivo contiene la configuracion de la conexion por ODBC de la BD, donde se 
//Cargan todos los drives necesarios y archivos que permiten realizar las operaciones
//con las tablas de la BD

//paquete que permite configurar las funciones de red
import java.net.URL;

//Paquete con las funcion que pemite configurar la conexion de la BD y tiene definido 
//los controladores necesarios para las conexiones de la BD //Connection - ResultSet(consulta selet) - Statement(ejecutar consultas insert, update, delete) - SQException (Excepciones en SQL)
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 *
 * @author Gleiston
 */
public class Conexion {

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    //Constructor de la clase Conexion
    public Conexion() {
        try {
            //Clase que especifica el nombre de los controladores que se van
            //ha utilizar en la carga de la BD en este caso son los de Access
            String driver = "org.postgresql.Driver";
            //url es un texto que contiene la ruta del nombre o la direccion
            //de coneccion de la base da Datos conectada al JDBC
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "P@$7Gr3$QL";

            Class.forName(driver);  //loads the driver

            try {

                //Con es el objeto creado para la coneccion donde se especifican los
                //parametros de la ubicacion de la BD, login si la base de datos
                //posee seguridad y por ultimo la clave
                //DriverManager.getConnection es el servicio que permite establecer
                //la conexion ABRIR CONEXION!!!
                connection = DriverManager.getConnection(url, user, password);

                //checkForWarning es una funcion que recibe como parametro
                //el listado de los errores generados en la conexion
                checkForWarning(connection.getWarnings());

                statement = connection.createStatement();

                //Es un drvie que permite cargar las configuraciones del proveedor
                //de la BD en este caso las reglas de configuraciones de pOSTgRESS
                DatabaseMetaData dma = connection.getMetaData();

                System.out.println("\nConectado a: " + dma.getURL());
                //System.out.println("Rurta de la base de datos: "+con."");
                System.out.println("Driver       " + dma.getDriverName());
                System.out.println("Version      " + dma.getDriverVersion());
                System.out.println("");

            } catch (SQLException ex) {
                System.out.println("\n*** SQLException caught ***\n");
                connection = null;
                while (ex != null) {
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("Message:  " + ex.getMessage());
                    System.out.println("Vendor:   " + ex.getErrorCode());
                    ex = ex.getNextException();
                    System.out.println("");
                }
            } catch (java.lang.Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("No encontro driver");
        }
    }

    /*
    * Constructor de la clase Conexion que contiene el parametro 
    * host        : Nombre del servidor
    * databaseName:
    * user        : 
    * password    :     
     */
    public Conexion(String host, String databaseName, String user, String password) {
        try {
            //Clase que especifica el nombre de los controladores que se van
            //ha utilizar en la carga de la BD en este caso son los de Access
            Class.forName("org.postgresql.Driver");  //loads the driver

            try {
                //url es un texto que contiene la ruta del nombre o la direccion
                //de coneccion de la base da Datos conectada al JDBC
                String url = "jdbc:postgresql://" + host.trim() + ":5432/" + databaseName;

                //Con es el objeto creado para la coneccion donde se especifican los
                //parametros de la ubicacion de la BD, login si la base de datos
                //posee seguridad y por ultimo la clave
                //DriverManager.getConnection es el servicio que permite establecer
                //la conexion ABRIR CONEXION!!!
                connection = DriverManager.getConnection(url, user, password);

                //checkForWarning es una funcion que recibe como parametro
                //el listado de los errores generados en la conexion
                checkForWarning(connection.getWarnings());

                statement = connection.createStatement();

                //Es un drvie que permite cargar las configuraciones del proveedor
                //de la BD en este caso las reglas de configuraciones de pOSTgRESS
                DatabaseMetaData dma = connection.getMetaData();

                System.out.println("\nConectado a: " + dma.getURL());
                //System.out.println("Rurta de la base de datos: "+con."");
                System.out.println("Driver       " + dma.getDriverName());
                System.out.println("Version      " + dma.getDriverVersion());
                System.out.println("");

            } catch (SQLException ex) {
                System.out.println("\n*** SQLException caught ***\n");

                while (ex != null) {
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("Message:  " + ex.getMessage());
                    System.out.println("Vendor:   " + ex.getErrorCode());
                    ex = ex.getNextException();
                    System.out.println("");
                }
            } catch (java.lang.Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("No encontro driver");
        }
    }

    private static boolean checkForWarning(SQLWarning warn) throws SQLException {
        boolean rc = false;

        if (warn != null) {
            System.out.println("\n *** Warning ***\n");
            rc = true;
            while (warn != null) {
                System.out.println("SQLState: " + warn.getSQLState());
                System.out.println("Message:  " + warn.getMessage());
                System.out.println("Vendor:   " + warn.getErrorCode());
                System.out.println("");
                warn = warn.getNextWarning();
            }
        }
        return rc;
    }

    public ResultSet select(String sentence) throws SQLException {
        resultSet = statement.executeQuery(sentence);

        return resultSet;
    }

    public int update(String sentence) throws SQLException {
        return statement.executeUpdate(sentence);
    }

    public void cerrarConexion() {
        try {
            //Cierra la conexion de la Base de Datos
            connection.close();
            System.out.println("Cerrada la conexion con la Base de Datos");
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion con la Base de datos. \n" + e);
        }
    }

}