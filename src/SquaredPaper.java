/*Este ejemplo fue tomado de internet y fue modificado para acceder y
 pintar figuras desde la BD*/
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.math.*;
 
public class SquaredPaper extends JFrame {
 public void paint(Graphics g) {
   Dimension d = getSize();
   int x = d.width;
   int y = d.height;
   String ultimoorden = "";
   int ultimax = 0, ultimay = 0, aux = 0;
 
   g.setColor(Color.blue);
   g.fillRect(0,0,x,y);
 
   g.setColor(Color.cyan);
   for (int i = 0; i < y; i+=25) g.drawLine(0,i,x,i);
   for (int i = 0; i < x; i+=25) g.drawLine(i,0,i,y);
 
   g.setColor(Color.black);
   g.drawLine(x/2,0,x/2,y);
   g.drawLine(0,y/2,x,y/2);
   
   g.setColor(Color.red);  
  
    Connection conn;
    Statement sentencia;
    ResultSet resultado;

    try{ // Se carga el driver JDBC-ODBC
     Class.forName ("oracle.jdbc.driver.OracleDriver");
    } catch( Exception e ) {
      System.out.println("No se pudo cargar el driver JDBC");
      return;           
      }

    try{ // Se establece la conexi�n con la base de datos Oracle Express
     conn = DriverManager.getConnection  
     ("jdbc:oracle:thin:@Rafa-Desktop:1521:xe", "raejimenezca", "bases2018");
      sentencia = conn.createStatement();
    } catch( SQLException e ) {
      System.out.println("No hay conexion con la base de datos.");
      return;
      }
       
    try {
     resultado = sentencia.executeQuery("SELECT r1.id_bodega, r1.x AS X0, r1.y AS Y0, r2.x AS x1, r2.y AS Y1 FROM (SELECT id_bodega, x, y FROM BODEGA RB1 NATURAL JOIN (SELECT ID_BODEGA FROM RUTA GROUP BY(ID_BODEGA)) R) r1, (SELECT id_bodega, x, y FROM BODEGA RB1 NATURAL JOIN (SELECT ID_BODEGA FROM RUTA GROUP BY(ID_BODEGA)) R) r2 WHERE r1.id_bodega = r2.id_bodega-1 ORDER BY TO_NUMBER(r1.id_bodega)");
     while (resultado.next())
      {
       g.drawString("Bod. " + resultado.getString("id_bodega"),resultado.getInt("X0"),resultado.getInt("Y0"));
       g.drawLine(resultado.getInt("X0"),resultado.getInt("Y0"),resultado.getInt("X1"),resultado.getInt("Y1"));
       //Estas cuatro instrucciones que siguen son solo para pintar el punto final, es decir, �
       //mediante la instrucci�n g.drawString que est� luego de cerrar el ciclo while
       aux = resultado.getInt("id_bodega")+1;
       ultimoorden = Integer.toString(aux);
       ultimax =  resultado.getInt("X1");
       System.out.println("ultimax: " + ultimax);
       ultimay = resultado.getInt("Y1");
       System.out.println("ultimay: " + ultimay);
      }
      g.drawString("Bod. " + ultimoorden,ultimax, ultimay);
           
      //Se cierra la conexion con la BD
      conn.close();  
    } catch(SQLException e ){      
      System.out.println("Error: " + e.getMessage());
      }              
 }
}