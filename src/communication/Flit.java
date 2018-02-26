package communication;

public class Flit {

    FlitType type;

    public Flit(FlitType type) {
        this.type = type;
    }

    public FlitType getType() {
        return type;
    }
}
