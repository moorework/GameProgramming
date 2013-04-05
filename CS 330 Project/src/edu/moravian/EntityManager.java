package edu.moravian;

import edu.moravian.agent.FoodEntity;
import java.util.LinkedList;

/**
 * This class manages entities.  This is used primarily to give and store
 * global entity information in one place
 * @author James Moore (moore.work@live.com)
 */
public class EntityManager
  {

    LinkedList<FoodEntity> foods;
    public static final EntityManager instance = new EntityManager();
    
    private EntityManager()
      {
        foods = new LinkedList<FoodEntity>();
      }
    
    public void addFood(FoodEntity food)
      {
        foods.add(food);
      }
    
    public LinkedList<FoodEntity> getFoods()
      {
        return foods;
      }
  }
