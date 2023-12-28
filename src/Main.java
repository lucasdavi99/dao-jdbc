import db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = DB.getConnection();

            statement = conn.prepareStatement("INSERT INTO seller"
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "Lucas Davi");
            statement.setString(2, "lucas@gmail.com");
            statement.setDate(3, new java.sql.Date(sdf.parse("17/08/1999").getTime()));
            statement.setDouble(4, 3000.0);
            statement.setInt(5, 4);

           int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                 ResultSet resultSet = statement.getGeneratedKeys();

                 while (resultSet.next()) {
                     int id = resultSet.getInt(1);
                     System.out.println("Pronto! Id = " + id);
                 }
            } else {
                System.out.println("Nenhuma linha foi alterada!");
            }

        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}