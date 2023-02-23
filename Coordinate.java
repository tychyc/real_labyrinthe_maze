public class Coordinate {
    int x;
    int y;
    Coordinate parent;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

}
