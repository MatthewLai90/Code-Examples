/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegmentationProject;

import FilterInterface.ImageFilter;
import Utilities.RGBPixelArray;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Matthew Lai
 */

public class SRG extends ImageFilter {

    @Override
    public RGBPixelArray filterImage(RGBPixelArray original) {
        
        // Retrieve Color Edge Extraction Image
        RGBPixelArray colorEdge = (new ColorEdgeExtraction()).filterImage(original);
        // Label Edge Regions
        System.out.println("Labeling Edge Regions");
        List<Region> edgeRegions = new ArrayList<>();
        int[][] colorEdgeImage = colorEdge.getGrayscaleValues();
        for (int x = 0; x < colorEdgeImage.length; x++) {
            for (int y = 0; y < colorEdgeImage[x].length; y++) {
                if(colorEdgeImage[x][y] == 1 )
                {
                    final int xF = x;
                    final int yF = y;
                    List<Region> matchingRegions = Collections.synchronizedList(new ArrayList<Region>());
                    edgeRegions.parallelStream().filter(eR -> eR.isAdjacent(xF, yF)).forEach(eR -> matchingRegions.add(eR));
                    if(matchingRegions.isEmpty())
                    {
                        Region newER = new Region();
                        newER.addPoint(x, y);
                        edgeRegions.add(newER);
                    }
                    if(matchingRegions.size() == 1)
                    {
                        matchingRegions.get(0).addPoint(x, y);
                    }
                    if(matchingRegions.size() > 1)
                    {
                        Region newEr = new Region();
                        matchingRegions.forEach(mR -> newEr.absorb(mR));
                        matchingRegions.forEach(mR -> edgeRegions.remove(mR));
                        edgeRegions.add(newEr);
                    }
                }
            }
        }
        
        // Place into Minimum Spanning Tree
        System.out.println("Creating Spanning Tree. EdgeRegions Detected: " + edgeRegions.size());
        PriorityQueue<ERPair> potentialEdges = new PriorityQueue<>();
        for (int i = 0; i < edgeRegions.size(); i++) 
            for (int j = i+1; j < edgeRegions.size(); j++)
                potentialEdges.add(new ERPair(edgeRegions.get(i), edgeRegions.get(j)));
        
        CycleTracker CT = new CycleTracker();
        PriorityQueue<ERPair> mergeSet = new PriorityQueue<>();
        double distThreshold = Math.sqrt(Math.pow(original.getLength(),2) + Math.pow(original.getWidth(),2))*.01;
        Region root = edgeRegions.get(0);
        int pES = potentialEdges.size();
        System.out.println("PESize: " + potentialEdges.size());
        int count = 0;
        while(!potentialEdges.isEmpty())
        {
            ERPair edge = potentialEdges.poll();
            if(CT.addEdge(edge))
            {
                if(edge.distance < distThreshold) 
                {
                    int x1 = edge.edgeRegion1.centroidPoint().x;
                    int y1 = edge.edgeRegion1.centroidPoint().y;
                    int x2 = edge.edgeRegion2.centroidPoint().x;
                    int y2 = edge.edgeRegion2.centroidPoint().y;
                    int[][] red = original.getRed();
                    int[][] green = original.getGreen();
                    int[][] blue = original.getBlue();
                    if(Math.sqrt(Math.pow(red[x1][y1] - red[x2][y2], 2) + Math.pow(green[x1][y1] - green[x2][y2], 2) 
                                                                        + Math.pow(blue[x1][y1] - blue[x2][y2], 2)) < 15)
                        mergeSet.add(edge);
                }
                edge.edgeRegion1.connectedRegions.add(edge.edgeRegion2); // Ugly Ugly Ugly
                edge.edgeRegion2.connectedRegions.add(edge.edgeRegion1);
                root = edge.edgeRegion1;
                if(++count == (edgeRegions.size() - 1)) break;
            }
        }
        System.out.println("Count: " + count);
        // Merge Similar Centroids (Looking at original Image)
        System.out.println("Merging Similar Centroids. MergeSet Size: " + mergeSet.size());
        while(!mergeSet.isEmpty())
        {
            ERPair edge = mergeSet.poll();
            Region eR1 = edge.edgeRegion1;
            Region eR2 = edge.edgeRegion2;
            Region newER = new Region();
            newER.absorb(eR1);
            newER.absorb(eR2);
            eR1.connectedRegions.forEach(eR -> eR.connectedRegions.remove(eR1));
            eR1.connectedRegions.forEach(eR -> eR.connectedRegions.add(newER));
            eR2.connectedRegions.forEach(eR -> eR.connectedRegions.remove(eR2));
            eR2.connectedRegions.forEach(eR -> eR.connectedRegions.add(newER));
            root = newER;
        }
        
        // Calculate Centroids
        System.out.println("Calculating Centroids");
        Set<Region> gRegions = new HashSet<>();
//        Queue<Region> nextRegions = new LinkedList<>();
        Queue<Region> nextRegions = new ConcurrentLinkedQueue<>();
        Set<Region> doneRegions = new HashSet<>();
        nextRegions.add(root);
        while(!nextRegions.isEmpty())
        {
//            System.out.println("NextRegion Size: " + nextRegions.size());
            Region currentRegion = nextRegions.poll();
            doneRegions.add(currentRegion);
            currentRegion.connectedRegions.parallelStream().filter(cR -> !doneRegions.contains(cR)).forEach(cR -> nextRegions.add(cR));
            currentRegion.connectedRegions.forEach(cR -> gRegions.add(new Region(cR.centroidPoint(), currentRegion.centroidPoint())));
        }
        
        // Track Unallocated pixels and order by similarity with Centroid Value
        // If new pixel added to unallocated pixel set already is in set, associate with region with more similarity
        // Step by Step Allocation of pixels, starting with most similar
            // Recalculate modified area centroid after each step
        System.out.println("Allocating Pixels. Number of Regions to Grow: " + gRegions.size());
        Set<Point> allocatedPoints = new HashSet<>();
        PriorityQueue<UnAllocPoint> unallocatedPoints = new PriorityQueue<>();
        gRegions.forEach(gR -> { 
            allocatedPoints.add(new Point(gR.centroidPoint().x, gR.centroidPoint().y));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x+1, gR.centroidPoint().y, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x+1, gR.centroidPoint().y-1, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x+1, gR.centroidPoint().y+1, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x, gR.centroidPoint().y+1, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x, gR.centroidPoint().y-1, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x-1, gR.centroidPoint().y, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x-1, gR.centroidPoint().y+1, original));
            unallocatedPoints.add(new UnAllocPoint(gR, gR.centroidPoint().x-1, gR.centroidPoint().y-1, original));
        });
        
        while(!unallocatedPoints.isEmpty())
        {
            UnAllocPoint p = unallocatedPoints.poll();
            if(allocatedPoints.contains(p.p)) continue;
            if(p.p.x < 0 || p.p.y < 0 || p.p.x >= original.getWidth() || p.p.y >= original.getLength()) continue;
            allocatedPoints.add(p.p);
            p.adjRegion.addPoint(p.p.x, p.p.y, original);
            Arrays.stream(p.adjPoints())
                    .filter(adjP -> !allocatedPoints.contains(adjP))
                    .forEach(adjP -> {
                        if(adjP.x >= 0 && adjP.y >= 0 && adjP.x < original.getWidth() && adjP.y < original.getLength()) 
                            unallocatedPoints.add(new UnAllocPoint(p.adjRegion, adjP.x, adjP.y, original));
                    });
        }
            
        // Merge SRG with color-edge // Not Implemented
        
        // Generate output from given Grown Regions
        int[][] output = new int[original.getWidth()][original.getLength()];
        java.util.Iterator<Region> it = gRegions.iterator();
//        gRegions.parallelStream().forEach(c ->
        while(it.hasNext())
        {
            Region c = it.next();
            int maxX = -1;
            int maxY = -1;
            int minX = original.getWidth();
            int minY = original.getLength();
            java.util.Iterator<Point> pIt = c.points.iterator();
            while(pIt.hasNext())
            {
                Point p = pIt.next();
                if(p.x > maxX) maxX = p.x;
                if(p.y > maxY) maxY = p.y;
                if(p.x < minX) minX = p.x;
                if(p.y < minY) minY = p.y;
            }
            if(maxY-minY >= 0) {
                int[] maxXA = new int[maxY-minY+1];
                int[] minXA = new int[maxY-minY+1];
                for (int i = 0; i < minXA.length; i++) {
                    maxXA[i] = -1;
                    minXA[i] = original.getWidth();
                }
                pIt = c.points.iterator();
                while(pIt.hasNext())
                {
                    Point p = pIt.next();
                    if(p.x > maxXA[p.y-minY]) maxXA[p.y-minY] = p.x;
                    if(p.x < minXA[p.y-minY]) minXA[p.y-minY] = p.x;
                }
                for (int i = 0; i < minXA.length; i++) {
                    if(maxXA[i] >= 0)
                    {
                        output[maxXA[i]][i+minY] = 1;
                        output[minXA[i]][i+minY] = 1;
                    }
                }
            }
            
        }//);
       
        return new RGBPixelArray(output);
    }

    @Override
    public String getFilterName() {
        return "Seeded Region Growing";
    }

    @Override
    public String getFilterDecription() {
        return "Segments the image using the Seeded Region Growing method utilizing seeds obtained from Color Edge Extraction";
    }

    @Override
    public String getFilterCategory() {
        return "ProjectFilters";
    }
    
    private class CycleTracker
    {
        List<Set<Region>> cycleTracker;
        public CycleTracker()
        {
            cycleTracker = new ArrayList<>();
        }
        
        public boolean addEdge(ERPair edge)
        {
            Region edgeRegion1 = edge.edgeRegion1;
            Region edgeRegion2 = edge.edgeRegion2;
            int setCountBothInOne = (int)cycleTracker.parallelStream().filter(rSet -> (rSet.contains(edgeRegion1) && rSet.contains(edgeRegion2))).count();
//            System.out.println(setCountBothInOne);
            if(setCountBothInOne > 0) return false;
            
            int setCountR1 = (int)cycleTracker.parallelStream().filter(rSets -> rSets.contains(edgeRegion1)).count();
            int setCountR2 = (int)cycleTracker.parallelStream().filter(rSets -> rSets.contains(edgeRegion2)).count();
            
            if(setCountR1 == 0 && setCountR2 == 0) 
            {
                Set<Region> newRegion = new HashSet<>();
                newRegion.add(edgeRegion1);
                newRegion.add(edgeRegion2);
                cycleTracker.add(newRegion);
                return true;
            }
            if(setCountR1 == 1 && setCountR2 == 0) 
            {
                cycleTracker.stream().filter(rSets -> rSets.contains(edgeRegion1)).findFirst().get().add(edgeRegion2);
                return true;
            }
            
            if(setCountR1 == 0 && setCountR2 == 1)
            {
                cycleTracker.stream().filter(rSets -> rSets.contains(edgeRegion2)).findFirst().get().add(edgeRegion1);
                return true;
            }
            
            if(setCountR1 == 1 && setCountR2 == 1)
            {
                Set<Region> s1 = cycleTracker.stream().filter(rSets -> rSets.contains(edgeRegion1)).findFirst().get();
                Set<Region> s2 = cycleTracker.stream().filter(rSets -> rSets.contains(edgeRegion2)).findFirst().get();
                mergeSets(s1, s2);
                return true;
            }
            
            return false;
        }
        
        private void mergeSets(Set<Region> s1, Set<Region> s2)
        {
            Set<Region> newSet = new HashSet<>();
            newSet.addAll(s1);
            newSet.addAll(s2);
            cycleTracker.remove(s1);
            cycleTracker.remove(s2);
            cycleTracker.add(newSet);
        }
    }
    
    private class UnAllocPoint implements Comparable<UnAllocPoint>
    {
        Point p;
        int diff;
        Region adjRegion;
        public UnAllocPoint(Region adjRegion, int x, int y, int[][] r, int[][] g, int[][] b)
        {
            p = new Point(x,y);
            int aX = adjRegion.centroidPoint().x;
            int aY = adjRegion.centroidPoint().y;
            this.adjRegion = adjRegion;
            diff = Math.abs(r[x][y] - adjRegion.r(aX,aY)) + Math.abs(g[x][y] - adjRegion.g(aX,aY)) + Math.abs(b[x][y] - adjRegion.b(aX,aY));
        }
        
        public UnAllocPoint(Region adjRegion, int x, int y, RGBPixelArray original)
        {
            int[][] r = original.getRed();
            int[][] g = original.getGreen();
            int[][] b = original.getBlue();
            p = new Point(x,y);
            int aX = adjRegion.centroidPoint().x;
            int aY = adjRegion.centroidPoint().y;
            
            //add bound checks 
            this.adjRegion = adjRegion;
            if(x < 0 || aX < 0 || y < 0 || aY < 0 || 
                    x >= original.getWidth() || aX >= original.getWidth() 
                    || y >= original.getLength() || aY >= original.getLength())
                diff = Integer.MAX_VALUE;
            else
                diff = Math.abs(r[x][y] - adjRegion.r(aX,aY)) 
                        + Math.abs(g[x][y] - adjRegion.g(aX,aY)) 
                        + Math.abs(b[x][y] - adjRegion.b(aX,aY));
        }
        
        public Point[] adjPoints()
        {
            return new Point[] {
                new Point(p.x+1, p.y+1), new Point(p.x+1, p.y), new Point(p.x+1, p.y-1),
                new Point(p.x, p.y+1), new Point(p.x, p.y-1), 
                new Point(p.x-1, p.y+1), new Point(p.x-1, p.y), new Point(p.x-1, p.y-1)
            };
        }

        @Override
        public int compareTo(UnAllocPoint t) {
            return diff - t.diff;
        }
    }
    
    // Should Really put into seperate files
    private class ERPair implements Comparable<ERPair>
    {
        Region edgeRegion1;
        Region edgeRegion2;
        double distance;
        
        public ERPair(Region ER1, Region ER2)
        {
            edgeRegion1 = ER1;
            edgeRegion2 = ER2;
            distance = ER1.centroidPoint().distance(ER2.centroidPoint());
        }
        
        public Point getCentroid()
        {
            double x1 = edgeRegion1.centroidPoint().getX();
            double x2 = edgeRegion2.centroidPoint().getX();
            double y1 = edgeRegion1.centroidPoint().getY();
            double y2 = edgeRegion2.centroidPoint().getY();
            return new Point((int)(x1+x2)/2,(int)(y1+y2)/2);
        }
        
        public double getCentroidX()
        {
            double x1 = edgeRegion1.centroidPoint().getX();
            double x2 = edgeRegion2.centroidPoint().getX();
            return (x1+x2)/2;
        }
        
        public double getCentroidY()
        {
            double y1 = edgeRegion1.centroidPoint().getY();
            double y2 = edgeRegion2.centroidPoint().getY();
            return (y1+y2)/2;
        }

        @Override
        public int compareTo(ERPair t) {
            return (int) ((distance - t.distance)*10000);
        }
        
        @Override
        public boolean equals(Object obj)
        {
            return (((ERPair)obj).edgeRegion1.equals(edgeRegion1) &&
                    ((ERPair)obj).edgeRegion2.equals(edgeRegion2)) ||
                    (((ERPair)obj).edgeRegion1.equals(edgeRegion2) &&
                    ((ERPair)obj).edgeRegion2.equals(edgeRegion1)) ;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 47 * hash + Objects.hashCode(this.edgeRegion1) + Objects.hashCode(this.edgeRegion2);
            return hash;
        }
    }

    private class Region implements Comparable<Region>
    {
        Set<Region> connectedRegions;
        Set<Point> points;
        int n;
        int ySum, xSum;
        int rSum, gSum, bSum;
        public Region()
        {
            connectedRegions = new HashSet<>();
            ySum = 0;
            xSum = 0;
            n = 0;
            points = new HashSet<>();
            rSum = 0; gSum = 0; bSum = 0;
        }
        
        public Region(Point p1, Point p2)
        {
            connectedRegions = new HashSet<>();
            xSum = p1.x + p2.x;
            ySum = p1.y + p2.y;
            n = 2;
            points = new HashSet<>();
            rSum = 0; gSum = 0; bSum = 0;
        }
        
        public boolean isAdjacent(int x, int y)
        {
            return points.contains(new Point(x-1,y)) ||
                    points.contains(new Point(x,y-1)) ||
                    points.contains(new Point(x+1,y)) ||
                    points.contains(new Point(x,y+1));
        }
        
        public boolean isIncluded(int x, int y)
        {
            return points.contains(new Point(x,y));
        }
        
        public void addPoint(int x, int y)
        {
            ySum += y;
            xSum += x;
            n++;
            points.add(new Point(x,y));
        }
        
        public void addPoint(int x, int y, RGBPixelArray original)
        {
            rSum += original.getRed()[x][y];
            gSum += original.getGreen()[x][y];
            bSum += original.getBlue()[x][y];
            ySum += y;
            xSum += x;
            n++;
            points.add(new Point(x,y));
        }
        
        public int r(int x, int y) { return rSum/n; }
        public int g(int x, int y) { return gSum/n; }
        public int b(int x, int y) { return bSum/n; }
        
        public void absorb(Region eR)
        {
            connectedRegions.addAll(eR.connectedRegions);
            this.points.addAll(eR.points);
            n += eR.n;
            ySum += eR.ySum;
            xSum += eR.xSum;
            rSum += eR.rSum;
            gSum += eR.gSum;
            bSum += eR.bSum;
        }
        
        public Point centroidPoint()
        {
            return new Point(Math.round(xSum/n), Math.round(ySum/n));
        }
        
        public boolean equals(Region other)
        {
            return other.n == n && other.xSum == xSum && other.ySum == ySum && other.points.equals(points);
        }
        
        @Override
        public boolean equals(Object obj) {
            
            return //connectedRegions.equals(((Region)obj).connectedRegions) &&
//                    points.equals(((Region) obj).points) &&
                    n == ((Region) obj).n &&
                    ySum == ((Region) obj).ySum &&
                    xSum == ((Region) obj).xSum &&
                    rSum == ((Region) obj).rSum &&
                    gSum == ((Region) obj).gSum &&
                    bSum == ((Region) obj).bSum;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Objects.hashCode(this.points);
            hash = 67 * hash + this.n;
            hash = 67 * hash + this.ySum;
            hash = 67 * hash + this.xSum;
            return hash;
        }

        @Override
        public int compareTo(Region t) {
            return hashCode() - t.hashCode();
        }
    }
    
}
