package communication;

public enum Direction {
    WEST ("West Direction"),
    NORTH ("North Direction"),
    EAST ("East Direction"),
    SOUTH ("South Direction");
	// il manque pas le localport ici?

    private String name;

    Direction(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
