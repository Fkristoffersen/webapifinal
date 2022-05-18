package DataSource;

import DataModel.Person;
import java.sql.*;
import java.util.ArrayList;

public class MySQL {

    private String connStr = "jdbc:mysql://localhost:3306/webapidb";
    private String usr = "testUser";
    private String pwd = "testUser";
    private Connection con = null;

    private void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(connStr,usr,pwd);
            System.out.println("Connected...");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("select * from person");
            while (rs.next())
                System.out.println(rs.getInt(1));

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Driver Error. " + e.getMessage());
        }
    }

    private void closeCon() {
        try {
            con.close();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }

    public ArrayList<Person> getALLPersons(){
        connect();
        ArrayList<Person> pList = new ArrayList<>();
        String sql = "select * from person";
        try {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                Person p = new Person(
                        rs.getInt("persId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getBoolean("student"),
                        rs.getString("lastUpdated")
                );
                pList.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Statement Error " + e.getMessage());
        }


        closeCon();
        return pList;
    }
    public Person addPerson(Person person){
        Person p = null;
        connect();
        String sql = "insert into person (firstName, lastName, student) values(?,?,?)";
        try {
            PreparedStatement pstnt = con.prepareStatement(sql);
            pstnt.setString(1, person.getFirstName());
            pstnt.setString(2, person.getLastName());
            pstnt.setBoolean(3, person.isStudent());
            pstnt.executeUpdate();
            Statement stnt = con.createStatement();
            ResultSet pid = stnt.executeQuery("select last_insert_id()");
            pid.next();
            int id = pid.getInt(1);
            p = getPersonById(id);

            //getPersonById((pid.next()).getInt(1));

        } catch (SQLException e) {

            System.out.println("Error Statement" + e.getMessage());
        }

        return p;



    }

    public Person getPersonById(int persID){
        connect();
        Person p = null;
        String sql = "select * from person where persId = ?";

        try {

            PreparedStatement pstat = null;
            pstat = con.prepareStatement(sql);
            pstat.setInt(1,persID);
            ResultSet rs = pstat.executeQuery();
            if(rs.next()) {
                p = new Person(
                        rs.getInt("persId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getBoolean("student"),
                        rs.getString("lastUpdated")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeCon();
        return p;

    }

    public Person updatePerson(Person person, int persId)
    {
        connect();
        Person p = null;
        String sql = "UPDATE person SET firstName = ?, lastName = ?, student = ? WHERE persId = ?";

        PreparedStatement pstnt = null;
        try {
            pstnt = con.prepareStatement(sql);
            pstnt.setString(1, person.getFirstName());
            pstnt.setString(2, person.getLastName());
            pstnt.setBoolean(3, person.isStudent());
            pstnt.setInt(4, persId);
            pstnt.executeUpdate();
            p = getPersonById(persId);

        } catch (SQLException e) {
            System.out.println("Error trying to update id: " + persId +" " + e.getMessage());
        }
        return p;



    }


    public String deletedPerson(int persid) {
        connect();
        Person p = null;
        String sql = "DELETE FROM person WHERE persId = ?";
        PreparedStatement pstnt = null;
        try {
            pstnt = con.prepareStatement(sql);
            pstnt.setInt(1, persid);
            pstnt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error trying to Delete id: " + persid +" " + e.getMessage());
        }
        return "Person ID: "+ persid + " IS DELETED";





    }
}
