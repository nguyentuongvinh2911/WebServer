import java.io.DataOutputStream;

public abstract class Service {
    // Variable
    DataOutputStream responseWriter;

    public Service(DataOutputStream responseWriter) {
        this.responseWriter = responseWriter;
    }

    public abstract void doWork();
}
