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
 
   g.setColor(Color.yellow);
   g.fillRect(0,0,x,y);
 
   g.setColor(Color.green);
   for (int i = 0; i < y; i+=25) g.drawLine(0,i,x,i);
   for (int i = 0; i < x; i+=25) g.drawLine(i,0,i,y);
 
   g.setColor(Color.red);
   g.drawLine(x/2,0,x/2,y);
   g.drawLine(0,y/2,x,y/2);
  
   g.setColor(Color.blue);  
  
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
      System.out.println("No hay conexi�n con la base de datos.");
      return;
      }
       
    try {
     resultado = sentencia.executeQuery("SELECT r1.orden AS orden, r1.coord_x AS a, r1.coord_y AS b, r2.coord_x AS c, r2.coord_y AS d FROM ruta r1, ruta r2 WHERE r1.orden = r2.orden-1 ORDER BY r1.orden");
     while (resultado.next())
      {
       g.drawString("Bod. " + resultado.getString("orden"),resultado.getInt("a"),resultado.getInt("b"));
       g.drawLine(resultado.getInt("a"),resultado.getInt("b"),resultado.getInt("c"),resultado.getInt("d"));
       //Estas cuatro instrucciones que siguen son solo para pintar el punto final, es decir, �
       //mediante la instrucci�n g.drawString que est� luego de cerrar el ciclo while
       aux = resultado.getInt("orden")+1;
       ultimoorden = Integer.toString(aux);
       ultimax =  resultado.getInt("c");
       ultimay = resultado.getInt("d");
      }
      g.drawString("Bod. " + ultimoorden,ultimax, ultimay);
           
      //Se cierra la conexion con la BD
      conn.close();  
    } catch(SQLException e ){      
      System.out.println("Error: " + e.getMessage());
      }              
 }
}