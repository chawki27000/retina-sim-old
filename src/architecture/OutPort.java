package architecture;

public class OutPort {

    int id;
    Router dest;

    public OutPort(int id) {
        this.id = id;
    }

    public Router getDest() {
        return dest;
    }

    public void setDest(Router dest) {
        this.dest = dest;
    }
}
