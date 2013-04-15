package edu.moravian.WorldMap;

import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import java.util.ArrayList;

/**
 *
 * @author barros
 */
public class WorldMap implements Drawable
  {

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

    //TODO how do we want to pass in start and end pts?
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
        }
        catch (Exception ex) {
            // well shit
            System.out.println("MapBuilder did not find external file: " + ex);
        }
        // retrieve the visual information from the MapBuilder
        appearenceID = MapBuilder.getAppearenceID(mapDirLocation);

        numHorizCells = topography.size(); // number of rows
        numVertCells = topography.get(0).size(); // number of columns

        cellWidth = width / numHorizCells;
        cellHeight = height / numVertCells;
        
        setPathableCenterPoints();
    }

    /**
     * Determines whether the cell corresponding to the provided game world Point
     * is currently occupied.
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
        if (isATowerCell(pointCell))
          {
            // cast the cell to a TowerCell
            pointCellTower = (TowerCell) pointCell;
            // and tell the client whether it's occupied
            return pointCellTower.isOccupied();
          }
        else
          { // it's not a TowerCell, so it can never be occupied...
            return false; // so inform the client that it's not occupied
          }
      }

    /**
     * Set the cell corresponding to the provided world Point as being occupied.
     * Behavior is not defined for situations in which the described cell cannot
     * be occupied.
     * 
     * @param pointToOccupy a world Point corresponding to a cell to be occupied.
     */
    public void setOccupied(Point2D pointToOccupy)
      {
        // we presumptuously cast the WorldCell corresponding to the provided
        // WorldPoint to a TowerCell on the basis that the client should have
        // ensured that the location is both occupiable and unoccupied. If they
        // didn't then they're a jackass and deserve what they get.
        TowerCell pointCell = (TowerCell) convertPointToCell(pointToOccupy);

        pointCell.setOccupied();
      }

    /**
     * Set the cell corresponding to the provided world Point as being unoccupied.
     * Behavior is not defined for situations in which the described cell cannot
     * be occupied in the first place.
     * 
     * @param pointToUnOccupy a world Point corresponding to a cell to be unoccupied.
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

    /**
     * Get the game world point that describes the upper-left hand corner of the
     * cell corresponding to the provided Point2D.
     * 
     * @param pointLocation a world Point describing the cell who's corner point
     *                      is to be retrieved
     * @return the top-left corner-point of the cell
     */
    public Point2D getCornerPoint(Point2D pointLocation)
      {
        double pointX = pointLocation.getX();
        double pointY = pointLocation.getY();

        int horizCellNum = (int) (pointX / cellWidth);
        int vertCellNum = (int) (pointY / cellHeight);

        horizCellNum = horizCellNum + 1;
        vertCellNum = vertCellNum + 1;

        double cornerPointX = cellWidth * horizCellNum;
        double cornerPointY = cellHeight * vertCellNum;

        return new Point2D(cornerPointX, cornerPointY);
      }

    /**
     * Retrieves a NavPath that describes the navigable space within the WorldMap
     * for use in computing AI navigational paths.
     * 
     * @return a Graph describing the navigable world
     */
    public NavPath<PathCell, Double> getNavPath()
    {
        NavPath<PathCell, Double> ret = new NavPath<PathCell, Double>();
        
        WorldCell cell;
        PathCell pathCell;
        for (int i = 0; i < topography.size(); i++) {
            for (int k = 0; k < topography.get(0).size(); k++) {
                cell = topography.get(i).get(k);
                
                if (cell.isPathable() == true) {
                    pathCell = (PathCell) cell;
                    
                    // shit recursion
                    ret.addVertex(pathCell);
                }
                
            }
        }
        
        return null;
      }

    @Override
    public Point2D getPos()
      {//TODO close enough, right?
        return new Point2D(0, 0);
      }

    @Override
    public DrawLocation getDrawLocation()
      {
        throw new UnsupportedOperationException("Not supported yet.");
      }

    @Override
    public GraphicsIDHolder getGraphicsID()
      {
        throw new UnsupportedOperationException("Not supported yet.");
      }

    public Point2D getStartingPoint()
      {
        // TODO implement me
        return null;
      }

    public Point2D getEndPoint()
      {
        // TODO implement me
        return null;
      }

    @Override
    public Sprite getCurrFrame()
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

        if (horizCellNum > topography.size())
          {
            return null;
          }
        else if (vertCellNum > topography.get(0).size())
          {
            return null;
          }

        // these computations have assumed that the matrix has its origin in the
        // lower-left, as in the world, but it doesn't; it's in the top-left. So
        // we flip it before using it.
        vertCellNum = numVertCells - 1 - vertCellNum;

        // matrices are row-by-column
        return topography.get(horizCellNum).get(vertCellNum);
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
    
    private final Double CARDINAL_DIST = new Double(1.0);
    private final Double DIAGONAL_DIST = new Double(Math.sqrt(2));
}
