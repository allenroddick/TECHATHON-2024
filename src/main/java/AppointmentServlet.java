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

@WebServlet("/schedule")
public class AppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get parameters from the form
        String patientName = request.getParameter("patient_name");
        String doctorName = request.getParameter("doctor_name");
        String appointmentDate = request.getParameter("appointment_date");
        String appointmentTime = request.getParameter("appointment_time");
        String contactNumber = request.getParameter("contact_number");
        String reason = request.getParameter("reason");

        // JDBC connection details
        String jdbcURL = "jdbc:postgresql://localhost:5432/appointment_db";
        String jdbcUsername = "postgres";
        String jdbcPassword = "your_password";

        // SQL query to insert the appointment details
        String sql = "INSERT INTO appointments (patient_name, doctor_name, appointment_date, appointment_time, contact_number, reason) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Establish connection with the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, patientName);
            statement.setString(2, doctorName);
            statement.setDate(3, java.sql.Date.valueOf(appointmentDate));
            statement.setTime(4, java.sql.Time.valueOf(appointmentTime));
            statement.setString(5, contactNumber);
            statement.setString(6, reason);

            // Execute the query
            int result = statement.executeUpdate();

            // Check if the record was successfully inserted
            if (result > 0) {
                out.println("<h1>Appointment Scheduled Successfully!</h1>");
            } else {
                out.println("<h1>Error in Scheduling Appointment.</h1>");
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
