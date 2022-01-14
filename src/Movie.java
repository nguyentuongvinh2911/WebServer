public class Movie {
    // Variables
    private int id;
    private String title;
    private String director;
    private String description;

    // Constructors

    public Movie() {
    }

    public Movie(int id, String title, String director) {
        setId(id);
        setTitle(title);
        setDirector(director);
    }

    public Movie(int id, String title, String director, String description) {
        setId(id);
        setTitle(title);
        setDirector(director);
        setDescription(description);
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
