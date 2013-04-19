package edu.moravian.WorldMap;

import edu.moravian.graphics.DrawLocation;
import edu.moravian.graphics.Drawable;
import edu.moravian.graphics.GraphicsIDHolder;
import edu.moravian.graphics.Sprite;
import edu.moravian.math.Point2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.jgrapht.Graph;

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
    public WorldMap(String mapDirLocation, double mapWidth, double mapHeight) throws FileNotFoundException
      {
        width = mapWidth;
        height = mapHeight;

        topography = MapBuilder.getMapRepresentation(mapDirLocation);
        appearenceID = MapBuilder.getAppearenceID(mapDirLocation);

        numHorizCells = topography.size();
        numVertCells = topography.get(0).size();

        cellWidth = width / numHorizCells;
        cellHeight = height / numVertCells;
      }

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

    public void setOccupied(Point2D pointToOccupy)
      {
        // we presumptuously cast the WorldCell corresponding to the provided
        // WorldPoint to a TowerCell on the basis that the client should have
        // ensured that the location is both occupiable and unoccupied. If they
        // didn't then they're a jackass and deserve what they get.
        TowerCell pointCell = (TowerCell) convertPointToCell(pointToOccupy);

        pointCell.setOccupied();
      }

    public void setUnnocupied(Point2D pointToUnOccupy)
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
     * @param pointLocation
     * @return
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

    public Graph<PathCell, Integer> getNavPath()
      {
        // TODO on hold - seems jGraphT is a collection of interfaces, not implemented classes
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
  }
