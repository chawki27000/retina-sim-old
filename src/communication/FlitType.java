package communication;

public enum FlitType {

    HEAD("Header"),
    DATA("Data");

    private String type = "";

    FlitType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return type;
    }
}
