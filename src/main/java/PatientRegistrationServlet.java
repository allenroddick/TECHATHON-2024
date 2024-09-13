import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class PatientRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get parameters from the form
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // JDBC connection details
        String jdbcURL = "jdbc:postgresql://localhost:5432/hospital_db";
        String jdbcUsername = "postgres";
        String jdbcPassword = "your_password";

        // SQL query to insert the patient's details
        String sql = "INSERT INTO patients (name, age, gender, email, phone, address) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Establish connection with the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, gender);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, address);

            // Execute the query
            int result = statement.executeUpdate();

            // Check if the record was successfully inserted
            if (result > 0) {
                out.println("<h1>Patient Registered Successfully!</h1>");
            } else {
                out.println("<h1>Error in Registration.</h1>");
            }

            // Close the connection
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Database connection error!</h1>");
        }
    }
}
