public class PointPair {
    private DataPoint p1, p2;
    private double dist;

    public PointPair(DataPoint p1, DataPoint p2, double dist) {
        this.p1 = p1;
        this.p2 = p2;
        this.dist = dist;
    }

    public DataPoint getP1() {
        return p1;
    }

    public DataPoint getP2() {
        return p2;
    }

    public double getDistance() {
        return dist;
    }
}
