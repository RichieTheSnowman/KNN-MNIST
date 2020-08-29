import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Classifier {
    private ArrayList<DataPoint> trainingData;
    private int k;

    public Classifier(int k) {
        this.k = k;
        trainingData = new ArrayList<DataPoint>();
    }

    public void addTrainingData(List<DataPoint> points) {
        trainingData.addAll(points);
        // TODO: add all points from input to the training data
    }

    public void addTrainingData(DataPoint point) {
        trainingData.add(point);
        // TODO: add all points from input to the training data
    }

    public String classify(double[] featureVector) {
        if (trainingData.size() == 0) return "no training data";
        DataPoint UND = new DataPoint("unknown", featureVector);

        double minDist = Double.MAX_VALUE;
        ArrayList<PointPair> closest = new ArrayList<>();


        for(DataPoint p: trainingData){
            double dist = distance(featureVector, p.getData());

            if(closest.size() < k){
                closest.add(new PointPair(UND, p, dist));
            } else{
                PointPair furthestPoint = closest.get(closest.size()-1);
                if(dist < furthestPoint.getDistance()){
                    PointPair newPoint = new PointPair(UND, p, dist);
                    insertNewClosest(newPoint, closest);
                }
            }

        }

        // TODO: write a k-nearest-neighbor classifier.  Return its prediction of "0" to "9"

        return majorityFrom(closest);  // replace this line
    }

    private String majorityFrom(ArrayList<PointPair> closest) {
        int maxCount = 0;
        String majorityLabel = "";

        for (PointPair p : closest) {
            int count = count(p.getP2().getLabel(), closest);
            if(count > maxCount){
                maxCount = count;
                majorityLabel = p.getP2().getLabel();
            }
        }
        return majorityLabel;
    }

    private int count(String label, ArrayList<PointPair> closest) {
        int count = 0;
        for(PointPair p : closest){
            if(p.getP2().getLabel().equals((label))) count++;
        }
        return count;
    }

    private void insertNewClosest(PointPair newPoint, ArrayList<PointPair> closest) {
        for (int i = 0; i < closest.size(); i++) {
            if(newPoint.getDistance() < closest.get(i).getDistance()){
                closest.add(i, newPoint);
            }
            if (closest.size() > k) closest.remove(closest.size()-1);
            return;
            
        }

    }

    public double distance(double[] d1, double[] d2) {
        // TODO:  Use the n-dimensional Euclidean distance formula to find the distance between d1 and d2
        double sumOfSquaresOfDiffs = 0;
        if(d1.length != d2.length){
            return 0;
        }else{
            for (int i = 0; i < d1.length; i++) {
                double diff = d1[i]-d2[i];
                sumOfSquaresOfDiffs += diff*diff;
            }

        }
        return sumOfSquaresOfDiffs; // no sqrt since its slower
    }

    public void test(List<DataPoint> test) {
        ArrayList<DataPoint> correct = new ArrayList<>();
        ArrayList<DataPoint> wrong = new ArrayList<>();

        int i = 0;
        for (DataPoint p : test) {
            String predict = classify(p.getData());
            System.out.print("#" + i + " REAL:\t" + p.getLabel() + " predicted:\t" + predict);
            if (predict.equals(p.getLabel())) {
                correct.add(p);
                System.out.print(" Correct ");
            } else {
                wrong.add(p);
                System.out.print(" WRONG ");
            }

            i++;
            System.out.println(" % correct: " + ((double) correct.size() / i));
        }

        System.out.println(correct.size() + " correct out of " + test.size());
        System.out.println(wrong.size() + " wrong out of " + test.size());
        System.out.println("% Error: " + (double) wrong.size() / test.size());
    }
}
