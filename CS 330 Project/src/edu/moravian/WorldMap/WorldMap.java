package edu.moravian.WorldMap;

import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.math.Point2D;
import edu.moravian.tower.Tower;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author barros
 */
public class WorldMap implements Drawable {

    private final int IMAGE_ID = 0;
    private ArrayList<ArrayList<WorldCell>> topography; // a matrix that describes the map
    // as a series of specialized cells
    private int appearenceID; // ID that identifies its visual representation
    // within the View
    private double width; // width of the map in terms of the game world
    private double height; // height of the map in terms of the game world
    private int numHorizCells; // the number of cells representing the world horizontally
    private int numVertCells; // the number of cells representing the world vertically
    private double cellWidth; // the width (in game world terms) of each cell
    private double cellHeight; // the height (in game world terms) of each cell
    private LinkedList<PathCell> startingPoints;
    private LinkedList<PathCell> endingPoints;

    /**
     * A WorldMap is a discrete representation of a Tower Defense map, which is
     * treated as a large grid. The map thus treats all interactions with it as
     * taking place in terms of the cells of the grid. The WorldMap provides
     * resources for Creep spawning and navigation.
     *
     * @param mapDirLocation the location of an external data file describing
     * the map within the local file system
     * @param mapWidth the width of the WorldMap in terms of the game world
     * @param mapHeight the height of the WorldMap in terms of the game world
     */
    public WorldMap(String mapDirLocation, double mapWidth, double mapHeight)
    {

        width = mapWidth;
        height = mapHeight;

        try {
            // retrieve the backing data for the WorldMap from the MapBuilder
            // based on the external directory location provided to us
            topography = MapBuilder.getMapRepresentation(mapDirLocation);
            System.out.println("TOPO SIZE " + topography.size() + " " + topography.get(0).size());
        } catch (Exception ex) {
            // well shit
            System.out.println("MapBuilder encountered an error: " + ex.getMessage() + ex.toString());
        }

                System.out.println(topography.get(2).get(0) instanceof PathCell);
        
        numHorizCells = topography.get(0).size(); // number of rows
        numVertCells = topography.size(); // number of columns
        
        System.out.println("Map Size " + mapWidth + "  " + mapHeight);

        cellWidth = width / numHorizCells;
        //cellHeight = height / numVertCells;
        cellHeight= cellWidth;

        setPathableCenterPoints();

        startingPoints = new LinkedList<PathCell>();
        endingPoints = new LinkedList<PathCell>();
        determineStartAndEndCells();

        // has to follow a call to determineStartAndEndCells() method first. Dependancies. =(
        try {
            MapBuilder.initMapData(topography, mapWidth, mapHeight);
        } catch (Exception ex) {
            System.out.println("Fuckin shit: " + ex);
        }
    }

    /**
     * Determines whether the cell corresponding to the provided game world
     * Point is currently occupied.
     *
     * @param pointToCheck a world Point that describes a cell on the WorldMap
     * @return true if occupied, false if unoccupied or unoccupy-able
     */
    public boolean isOccupied(Point2D pointToCheck)
    {
        WorldCell pointCell = convertPointToCell(pointToCheck);
        TowerCell pointCellTower;

        // only TowerCells can be occupied. So.
        // if the point corresponds to a TowerCell...
        if (isATowerCell(pointCell)) {
            // cast the cell to a TowerCell
            pointCellTower = (TowerCell) pointCell;
            // and tell the client whether it's occupied
            return pointCellTower.isOccupied();
        } else { // it's not a TowerCell, so it can never be occupied...
            return false; // so inform the client that it's not occupied
        }
    }

    public boolean canBeOccupied(Point2D pointToCheck)
    {
     
        WorldCell pointCell = convertPointToCell(pointToCheck);
  
        TowerCell pointCellTower;


        if (isATowerCell(pointCell)) {
            // cast the cell to a TowerCell
            pointCellTower = (TowerCell) pointCell;
            // and tell the client whether it's occupied
            return pointCellTower.canBeOccupied();
        } else { // it's not a TowerCell, so it can never be occupied...
            return false; // so inform the client that it's not occupied
        }
    }

    /**
     * Set the cell corresponding to the provided world Point as being occupied.
     * Behavior is not defined for situations in which the described cell cannot
     * be occupied.
     *
     * @param pointToOccupy a world Point corresponding to a cell to be
     * occupied.
     */
    public void setOccupied(Point2D pointToOccupy, Tower t)
    {
        // we presumptuously cast the WorldCell corresponding to the provided
        // WorldPoint to a TowerCell on the basis that the client should have
        // ensured that the location is both occupiable and unoccupied. If they
        // didn't then they're a jackass and deserve what they get.
        TowerCell pointCell = (TowerCell) convertPointToCell(pointToOccupy);

        pointCell.setOccupied(t);
    }

    /**
     * Set the cell corresponding to the provided world Point as being
     * unoccupied. Behavior is not defined for situations in which the described
     * cell cannot be occupied in the first place.
     *
     * @param pointToUnOccupy a world Point corresponding to a cell to be
     * unoccupied.
     */
    public void setUnnoccupied(Point2D pointToUnOccupy)
    {
        // we presumptuously cast the WorldCell corresponding to the provided
        // WorldPoint to a TowerCell on the basis that the client should have
        // ensured that the location is both occupiable and unoccupied. If they
        // didn't then they're a jackass and deserve what they get.
        TowerCell pointCell = (TowerCell) convertPointToCell(pointToUnOccupy);

        pointCell.setUnOccupied();
    }

    public Tower getTowerAtPoint(Point2D point)
    {
        TowerCell pointCell = (TowerCell) convertPointToCell(point);

        return pointCell.getTower();
    }

    /**
     * Get the game world point that describes the upper-left hand corner of the
     * cell corresponding to the provided Point2D.
     *
     * @param pointLocation a world Point describing the cell who's corner point
     * is to be retrieved
     * @return the top-left corner-point of the cell
     */
    public Point2D getCornerPoint(Point2D pointLocation)
    {
        double pointX = pointLocation.getX();
        double pointY = pointLocation.getY();

        int horizCellNum = (int) (pointX / cellWidth);
        int vertCellNum = (int) (pointY / cellHeight);

        horizCellNum = horizCellNum;
        vertCellNum = vertCellNum;

        double cornerPointX = cellWidth * horizCellNum;
        double cornerPointY = cellHeight * vertCellNum;

        return new Point2D(cornerPointX, cornerPointY);
    }

    /**
     * Retrieves a NavPath that describes the navigable space within the
     * WorldMap for use in computing AI navigational paths.
     *
     * @return a Graph describing the navigable world
     */
    public NavGraph getNavPath()
    {
        NavGraph ret = new NavGraph();

        WorldCell cell;
        PathCell pathCell = null;
        PathCell adjCell = null;

        // find an initial PathCell to generate our graph from; we assume that
        // all PathCells are ultimately contiguous
        int numRows = topography.size();
        int numColumns = topography.get(0).size();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                cell = topography.get(i).get(j); // get the cell described

                // if the cell described is a PathCell...
                if (cell.isPathable() == true) {
                    // then we've located a PathCell from which to start our NavGraph
                    // generation
                    pathCell = (PathCell) cell;

                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {

                            if (k != i && l != j) {
                                // we're not adding edges to diagonal cells
                            } else if (k == i && l == j) {
                                // we're not adding edges from a cell to itself
                            } else if (isValidPathCell(k, l)) {
                                adjCell = (PathCell) topography.get(k).get(l);

                                ret.addEdge(pathCell, adjCell, CARDINAL_DIST);
                            }

                        }
                    }

                }
            }
        }

        return ret;
    }

    private boolean isValidPathCell(int i, int j)
    {
        if (i >= topography.size() || i < 0) {
            return false;
        } else if (j >= topography.get(i).size() || j < 0) {
            return false;
        }

        WorldCell cell = topography.get(i).get(j);

        if (cell.isPathable() == true) {
            return true;
        }

        return false;
    }

    @Override
    public Point2D getPos()
    {
        return new Point2D(0, 0);
    }

    //TODO make sure to test the map quality
    @Override
    public DrawLocation getDrawLocation()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getGraphicsID()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean isATowerCell(WorldCell cellToCheck)
    {
        return cellToCheck.canBeOccupied();
    }

    private WorldCell convertPointToCell(Point2D worldPoint)
    {
  double pointX = worldPoint.getX();
        double pointY = worldPoint.getY();

        int horizCellNum = (int) (pointX / cellWidth);

        int vertCellNum = (int) (pointY / cellHeight);

        if (horizCellNum > topography.get(0).size()) {
            return null;
        } else if (vertCellNum > topography.size()) {
            return null;
        }


        // these computations have assumed that the matrix has its origin in the
        // lower-left, as in the world, but it doesn't; it's in the top-left. So
        // we flip it before using it.
        vertCellNum = numVertCells - 1 - vertCellNum;

        try {
            System.out.println(topography.get(vertCellNum - 1).get(horizCellNum - 1));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // matrices are row-by-column
        return topography.get(vertCellNum - 1).get(horizCellNum -1);
    }

    private void setPathableCenterPoints()
    {
        WorldCell cell;

        PathCell pathCell;
        double centerX;
        double centerY;
        for (int i = 0; i < topography.size(); i++) {
            for (int k = 0; k < topography.get(0).size(); k++) {
                cell = topography.get(i).get(k);

                if (cell.isPathable() == true) {
                    pathCell = (PathCell) cell;

                    centerX = (i * cellWidth) + (cellWidth / 2.0);
                    centerY = ((numVertCells - 1 - k) * cellHeight) + (cellHeight / 2.0);

                    pathCell.setCenterPoint(new Point2D(centerX, centerY));
                }
            }
        }
    }

    private void determineStartAndEndCells()
    {

        WorldCell cell;
        PathCell pathCell;
        for (int i = 0; i < topography.size(); i++) {
            for (int k = 0; k < topography.get(0).size(); k++) {
                cell = topography.get(i).get(k);

                if (cell.isPathable() == true) {
                    pathCell = (PathCell) cell;

                    if (pathCell.isSpawn() == true) {
                        startingPoints.add(pathCell);
                    } else if (pathCell.isEnd() == true) {
                        endingPoints.add(pathCell);
                    }
                }
            }
        }
    }

    public LinkedList<Point2D> getStartingPoints()
    {
        LinkedList<Point2D> startPoints = new LinkedList<Point2D>();

        for (int i = 0; i < startingPoints.size(); i++) {
            startPoints.add(startingPoints.get(i).getCenterPoint());
        }

        return startPoints;
    }

    public LinkedList<Point2D> getEndingPoints()
    {
        LinkedList<Point2D> endPoints = new LinkedList<Point2D>();

        for (int i = 0; i < endingPoints.size(); i++) {
            endPoints.add(endingPoints.get(i).getCenterPoint());
        }

        
        
        return endPoints;
    }
    
    private final Double CARDINAL_DIST = new Double(1.0);
    private final Double DIAGONAL_DIST = new Double(Math.sqrt(2));
}
