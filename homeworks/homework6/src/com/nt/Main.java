package com.nt;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

public class Main
{
	static String genID = "select java_obj_seq.nextval from dual";

    static String writeSQL = "begin insert into java_objects(id,classname,bytes) "
                           + " values (?,?,empty_blob()) "
                           + " return bytes into ?; end;";

    static String readSQL
            = "select bytes from java_objects where id = ?";

    public static long write(Connection conn, Object o) throws Exception 
    {
        
        long id = nextval(conn);
        String className = o.getClass().getName();
        CallableStatement stmt = conn.prepareCall(writeSQL);
        stmt.setLong(1, id);
        stmt.setString(2, className);
        stmt.registerOutParameter(3, java.sql.Types.BLOB);
        stmt.executeUpdate();
        BLOB blob = (BLOB) stmt.getBlob(3);
        OutputStream os = blob.getBinaryOutputStream();
        ObjectOutputStream oop = new ObjectOutputStream(os);
        oop.writeObject(o);
        oop.flush();
        oop.close();
        os.close();
        stmt.close();
        System.out.println("Done serializing " + className);
        return id;
        
    }

    public static Object read(Connection conn, long id) throws Exception 
    {
        PreparedStatement stmt = conn.prepareStatement(readSQL);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        InputStream is = rs.getBlob(1).getBinaryStream();
        ObjectInputStream oip = new ObjectInputStream(is);
        Object o = oip.readObject();
        String className = o.getClass().getName();
        oip.close();
        is.close();
        stmt.close();
        System.out.println("Done de-serializing " + className);
        return o;
        
    }

    private static long nextval(Connection conn) throws SQLException 
    {
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(genID);
        rs.next();
        long id = rs.getLong(1);
        rs.close();
        stmt.close();
        return id;
        
    }

    public static void main(String[] argv) throws Exception 
    {

        DriverManager.registerDriver(new OracleDriver());
        Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");
        sqlConnection.setAutoCommit(false);

        Polygon polygon = new Polygon(4, 4, 4, "Red");

        long id = write(sqlConnection, polygon);
        sqlConnection.commit();

        System.out.println("ID= " + id);
        System.out.println("Object= " + read(sqlConnection, id));
        sqlConnection.close();
        
    }
}

/*
drop sequence java_obj_seq;
/

create sequence java_obj_seq;
/

drop table java_objects;
/

create table java_objects
(
id number,
classname varchar2(2048),
bytes blob default empty_blob()
)
/
*/