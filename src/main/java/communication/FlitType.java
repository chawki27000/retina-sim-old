package communication;

public enum FlitType {

    HEAD("Head"),
    BODY("Body"),
    TAIL("Tail");

    private String type = "";

    FlitType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return type;
    }
}
