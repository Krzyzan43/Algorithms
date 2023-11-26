import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ConvexHull {
    private static HashMap<PolarPoint, Point> map;
    private static List<PolarPoint> polarPoints;

    public static List<Point> solve(List<Point> points) throws IllegalArgumentException {
        Point startingPoint = getUndermostPoint(points);
        GeneratePolarPoints(startingPoint, points); // Ignores points with r=0
        SortByAngleAndDistance(polarPoints);
        RemoveDuplicateAngles(polarPoints);
        if (polarPoints.size() < 2)
            throw new IllegalArgumentException();
        PolarPoint initialPoint = new PolarPoint(startingPoint.x, startingPoint.y, 0, 0);
        map.put(initialPoint, startingPoint);

        return findConvexHull(initialPoint);
    }

    private static Point getUndermostPoint(List<Point> points) {
        Point lowest = points.get(0);
        for (Point point : points) {
            if (point.y < lowest.y) {
                lowest = point;
            } else if (point.y == lowest.y && point.x < lowest.x)
                lowest = point;
        }
        return lowest;
    }

    private static void GeneratePolarPoints(Point reference, List<Point> points) {
        polarPoints = new ArrayList<>(points.size());
        map = new HashMap<>(points.size());

        for (int i = 0; i < points.size(); i++) {
            Point cartesian = points.get(i);
            double refX = cartesian.x - reference.x;
            double refY = cartesian.y - reference.y;
            double refAngle = Math.atan2(refY, refX);
            double refR = Math.sqrt(refX * refX + refY * refY);
            if (refR == 0)
                continue;

            PolarPoint polar = new PolarPoint(cartesian.x, cartesian.y, refAngle, refR);
            map.put(polar, cartesian);
            polarPoints.add(polar);
        }
    }

    private static void SortByAngleAndDistance(List<PolarPoint> points) {
        points.sort((o1, o2) -> {
            int angleRes = Double.compare(o1.refAngle, o2.refAngle);
            if (angleRes != 0)
                return angleRes;
            else
                return Double.compare(o2.refR, o1.refR);
        });
    }

    private static void RemoveDuplicateAngles(List<PolarPoint> points) {
        PolarPoint lastPoint = new PolarPoint(0, 0, -1, -1);
        Iterator<PolarPoint> it = points.iterator();
        while (it.hasNext()) {
            PolarPoint current = it.next();
            if (current.refAngle == lastPoint.refAngle) {
                it.remove();
            } else {
                lastPoint = current;
            }
        }
    }

    private static List<Point> findConvexHull(PolarPoint initialPoint) {
        LinkedList<PolarPoint> hull = new LinkedList<>();
        hull.add(initialPoint);
        hull.add(polarPoints.get(0));
        hull.add(polarPoints.get(1));

        for (int i = 2; i < polarPoints.size(); i++) {
            while (!turnsLeft(hull.get(hull.size() - 2), hull.getLast(), polarPoints.get(i))) {
                hull.removeLast();
            }
            hull.add(polarPoints.get(i));
        }

        List<Point> points = new ArrayList<>(hull.size());
        for (PolarPoint polar : hull) {
            points.add(map.get(polar));
        }
        points.add(map.get(initialPoint));
        return points;
    }

    private static boolean turnsLeft(PolarPoint prevP, PolarPoint currentP, PolarPoint nextP) {
        Point prev = map.get(prevP);
        Point current = map.get(currentP);
        Point next = map.get(nextP);

        float ax = current.x - prev.x;
        float ay = current.y - prev.y;
        float bx = next.x - current.x;
        float by = next.y - current.y;

        return ax * by - ay * bx > 0;
    }
}

class PolarPoint {
    int absX;
    int absY;

    double refR;
    double refAngle;

    public PolarPoint(int x, int y, double angle, double refR) {
        this.absX = x;
        this.absY = y;
        this.refAngle = angle;
        this.refR = refR;
    }

    @Override
    public String toString() {
        return "{" + absX + ", " + absY + "}, " + refAngle;
    }
}
