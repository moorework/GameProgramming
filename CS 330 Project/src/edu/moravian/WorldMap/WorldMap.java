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
        
        // set PathCell center points
        WorldCell wCell;
        PathCell pCell;
        double halfCellWidth = cellWidth / 2.0;
        double halfCellHeight = cellHeight / 2.0;
        double centerX;
        double centerY;
        int topographySize = topography.size();
        for (int i = 0; i < topographySize; i++) {
            for (int j = 0; j < topographySize; j++) {
                wCell = topography.get(i).get(j);
                
                if (wCell.isPathable()) {
                    
                    pCell = (PathCell) wCell;
                    
                    // take into account that World starts in lower left corner
                    centerX = halfCellWidth + ((topographySize - 1 - i) * cellWidth);
                    centerY = halfCellHeight + ((topographySize - 1 - j) * cellHeight);
                    
                    pCell.setCenterPoint(new Point2D(centerX, centerY));
                }
            }
        }
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

    public NavigationalGraph getNavPath()
      {
        NavigationalGraph ret = new NavigationalGraph();
        
        WorldCell wCell;
        int row = 0;
        int col = 0;
        boolean foundPathCell = false;
        while (foundPathCell == false) {
            wCell = topography.get(row).get(col);
            
            if (wCell.isPathable()) {
                foundPathCell = true;
            }
            else {
                row++;
                col++;
            }
        }
        
        return computeVertex(ret, null, row, col, 0);
      }
    
    private NavigationalGraph computeVertex(NavigationalGraph navGraph, PathCell pred, int row, int col, double edgeWeight) {
        WorldCell thisCell = topography.get(row).get(col);
        PathCell pCell;
        
        // base cases
        if (thisCell.isPathable() == false) {
            return navGraph;
        }
        
        pCell = (PathCell) thisCell;
        
        // if we've already visited this cell from the predecessor...
        if (navGraph.containsEdge(pCell, pred)) {
            // then we don't have to compute its position in the graph again
            return navGraph;
        }
        
        if (pred != null) {
            navGraph.addEdge(pCell, pred, edgeWeight);
        }
        
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != row && j != col) {
                    return computeVertex(navGraph, pCell, i, j, DIAGONAL_WEIGHT);
                }
                else {
                    return computeVertex(navGraph, pCell, i, j, CARDINAL_WEIGHT);
                }
            }
        }
        
        return navGraph;
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
    
    private final double CARDINAL_WEIGHT = 1;
    private final double DIAGONAL_WEIGHT = Math.sqrt(2);
  }
