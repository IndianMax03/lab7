package listening;

import base.City;

import java.io.Serializable;
import java.util.TreeSet;

public class Response implements Serializable {

    private static final long serialVersionUID = -4311158671713476273L;

    private final String message;
    private final String[] answer;
    private boolean done;
    private City usedCity = null;
    private TreeSet<City> collection = null;

    public Response(String message, String[] answer, boolean done) {
        this.message = message;
        this.answer = answer;
        this.done = done;
    }

    public Response(String message, boolean done) {
        this(message, null, done);
    }

    public Response(String[] answer, boolean done) {
        this(null, answer, done);
    }

    public String[] getAnswer() {
        return answer;
    }

    public String getMessage() {
        return message;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public City getUsedCity() {
        return usedCity;
    }

    public void setUsedCity(City usedCity) {
        this.usedCity = usedCity;
    }

    public TreeSet<City> getCollection() {
        return collection;
    }

    public void setCollection(TreeSet<City> collection) {
        this.collection = collection;
    }
}
