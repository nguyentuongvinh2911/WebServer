import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MovieDAO {
    public Connection getConnection() {
        Connection conn = null;
        try {
            // Specifying type of connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Build connection string
            String connectionUrl = "jdbc:sqlserver://localhost:1434;DatabaseName=Movies; " + "User=javaApps;Password=java";

            // Create connection
            conn = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }

    public ArrayList<Movie> getMovies() {
        // Setup
        ArrayList<Movie> movies = new ArrayList<>();

        // Create connection
        Connection conn = getConnection();

        // Sending and receiving sql queries
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Id, Title, Director FROM Movies");
            while (rs.next()) {
                Movie m = new Movie(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3));
                movies.add(m);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public Movie getMovieById(int id) {
        // Setup
        Movie movie = new Movie();
        // Create connection
        Connection conn = getConnection();

        // Sending and receiving sql queries
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Id, Title, Director, [Description] FROM Movies WHERE Id = " + id);
            if (rs.next()) {
                movie = new Movie(Integer.parseInt(rs.getString(1)),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4)
                );
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public void updateMovie(Movie m) {
        // Create connection
        Connection conn = getConnection();

        // Sending and receiving sql queries
        try {
            Statement stmt = conn.createStatement();
            String update = "UPDATE Movies SET Title= '"+m.getTitle()+
                    "',Director = '"+m.getDirector()+
                    "', Description ='"+
                    m.getDescription()+
                    "' Where Id = "+m.getId();
            stmt.executeUpdate(update);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Movie> getMovieByCriteria(String field, String criteria){
        // Setup
        ArrayList<Movie> movies = new ArrayList<>();

        // Create connection
        Connection conn = getConnection();

        // Sending and receiving sql queries
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM MOVIES WHERE "+ field +" = ‘" + criteria + "'");
            while (rs.next()) {
                Movie m = new Movie(Integer.parseInt(rs.getString(1)),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4));
                movies.add(m);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
