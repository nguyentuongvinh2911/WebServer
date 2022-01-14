import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class SQLSelectService extends Service {
    private String requestString;
    private MovieDAO dbLayer;

    //This constructor will be called from the run method of a
    //Responder.  It passes the HTTP request info, and the output
    //object to the service.
    public SQLSelectService(DataOutputStream responseWriter, String requestString) {
        super(responseWriter);
        this.requestString = requestString;
    }

    public void doWork() {
        try {
            //Creates an instance of MoviesDAO for dbLayer
            MovieDAO dbLayer = new MovieDAO();
            //Executes the query to get an ArrayList of Movies
            String criteria = requestString.substring(19, requestString.indexOf("Field") - 1);
            String fieldName = requestString.substring(requestString.indexOf("Field") + 6, requestString.indexOf("Submit") - 1);
            ArrayList<Movie> movies = dbLayer.getMovieByCriteria(fieldName, criteria);
            //Set up the Web page:
            responseWriter.writeBytes("<html><head><title>Comp 233, Query");
            responseWriter.writeBytes("</title ></head><body>");
            //Loops through the arrayList writing it to IE using the
            //responseWriter.  You will have to format the Strings with
            //a little HTML.
            responseWriter.flush();
            for (Movie m : movies){
                responseWriter.flush();
                responseWriter.writeBytes("ID: " + m.getId());
                responseWriter.flush();
                responseWriter.writeBytes("Title: " + m.getTitle());
                responseWriter.flush();
                responseWriter.writeBytes("Director: " + m.getDirector());
                responseWriter.flush();
                responseWriter.writeBytes("--------------------------");
            }
            responseWriter.flush();
            responseWriter.writeBytes("</body></html>");
        } //Then catch exceptions and close class.
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}