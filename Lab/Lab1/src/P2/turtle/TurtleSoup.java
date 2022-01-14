/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (sides - 2) * 180.0 / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int) Math.round(360.0 / (180.0 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double radian = Math.atan2(targetY - currentY, targetX - currentX); // atan2 from -pi to pi
        if (radian < 0) radian += 2 * Math.PI; // radian < 0, then + 2*pi
        double angle = radian / Math.PI * 180.0; // radian converted to angle
        // turtle's 0 points up with a clockwise rotation, so true angle is as follows:
        angle = angle > 90 ? 90 + 360 - angle : 90 - angle;
        // calculate positive bearing or just 0
        double bearing = angle - currentBearing;
        return bearing >= 0 ? bearing : 360 + bearing;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        double currentBearing = 0.0, Bearing; // start with 0.0
        int currentX = xCoords.get(0), currentY = yCoords.get(0);
        int targetX, targetY;
        List<Double> calculatedBearings = new ArrayList<>();
        for (int i = 1; i < xCoords.size(); i++) {
            targetX = xCoords.get(i);
            targetY = yCoords.get(i);
            // calculate the bearing by two points
            Bearing = calculateBearingToPoint(currentBearing, currentX, currentY, targetX, targetY);
            calculatedBearings.add(Bearing);
            currentBearing = Bearing;
            // the target point becomes next current point 
            currentX = targetX;
            currentY = targetY;
        }
        return calculatedBearings;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        if (points.size() <= 3) // undoubtedly the convex hull
            return points;
        Set<Point> convexHull = new HashSet<Point>();
        Point firstPoint = points.iterator().next();
        // begin with the most lower-right point
        for (Point p : points)
            if (p.x() < firstPoint.x())
                firstPoint = p;
            else if (p.x() == firstPoint.x() && p.y() < firstPoint.y()) 
                firstPoint = p;
        Point lastConvexPoint = firstPoint;
        int temp = 0;
        do {
            double currentBearing = 360.0;
            Point nextConvexPoint = null;
            for (Point p : points) {
                if (temp == 1 && p == firstPoint); // the 3rd point cannot be the first point
                else if(!convexHull.contains(p) && p != lastConvexPoint) {
                    double bearing = calculateBearingToPoint(0, 
                        (int)lastConvexPoint.x(), (int)lastConvexPoint.y(), (int)p.x(), (int)p.y());
                    // let all the points at the right side of edge(lastConvexPoint, nextConvexPoint)
                    if (bearing < currentBearing) {
                        nextConvexPoint = p;
                        currentBearing = bearing;
                    }
                    // if over 2 points are on the same edge, we choose the farthest point
                    else if (bearing == currentBearing) {
                        double lengthPow_next = Math.pow(lastConvexPoint.x() - nextConvexPoint.x(), 2) +
                                                   Math.pow(lastConvexPoint.y() - nextConvexPoint.y(), 2);
                        double lengthPow_p = Math.pow(lastConvexPoint.x() - p.x(), 2) +
                                                Math.pow(lastConvexPoint.y() - p.y(), 2);
                        if (lengthPow_next < lengthPow_p) nextConvexPoint = p;
                    }
                }
            }
            temp++;
            // add the chosed point to the set of convex hull
            convexHull.add(nextConvexPoint);
            lastConvexPoint = nextConvexPoint;
        } while (!convexHull.contains(firstPoint));
        return convexHull;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        int step = 60, count = 18;
		for (int i = 0; i < count; i++) {
            switch (i % 10) {
                case 0:
                turtle.color(PenColor.BLACK);
                break;
                case 1:
                turtle.color(PenColor.GRAY);
                break;
                case 2:
                turtle.color(PenColor.RED);
                break;
                case 3:
                turtle.color(PenColor.PINK);
                break;
                case 4:
                turtle.color(PenColor.ORANGE);
                break;
                case 5:
                turtle.color(PenColor.YELLOW);
                break;
                case 6:
                turtle.color(PenColor.GREEN);
                break;
                case 7:
                turtle.color(PenColor.CYAN);
                break;
                case 8:
                turtle.color(PenColor.BLUE);
                break;
                case 9:
                turtle.color(PenColor.MAGENTA);
                break;
                }
			drawRegularPolygon(turtle, 360 / step, step);
			turtle.turn(360 / count);
		}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        // drawSquare(turtle, 40);
        // drawRegularPolygon(turtle, 5, 50);
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
