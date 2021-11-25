import processing.core.PImage;

import java.util.*;
import java.util.function.Function;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private int numRows;
   private  int numCols;
   private  Background[][] background;
   private  Entity[][] occupancy;
   private  Set<Entity> entities;


   protected int getNumRows(){


      return numRows;
   }

   protected int getNumCols(){


      return numCols;
   }

   protected Background[][] getBackground() {
      return background;
   }

   protected Entity[][] getOccupancy(){


      return occupancy;
   }

   protected Set<Entity> getEntities(){

      return entities;
   }

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }
   private final int FISH_REACH = 1;

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -FISH_REACH; dy <= FISH_REACH; dy++)
      {
         for (int dx = -FISH_REACH; dx <= FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
            if (this.withinBounds(this, newPt) &&
                    !this.isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }
   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!this.processLine(in.nextLine(), imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   private final int PROPERTY_KEY = 0;
   private final String BGND_KEY = "background";
   private  final String OCTO_KEY = "octo";
   private final String OBSTACLE_KEY = "obstacle";
   private  final String FISH_KEY = "fish";
   private  final String ATLANTIS_KEY = "atlantis";
   private  final String SGRASS_KEY = "seaGrass";


   public boolean processLine(String line,
                                     ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return this.parseBackground(properties, imageStore);
            case OCTO_KEY:
               return this.parseOcto(properties, imageStore);
            case OBSTACLE_KEY:
               return this.parseObstacle(properties, imageStore);
            case FISH_KEY:
               return this.parseFish(properties, imageStore);
            case ATLANTIS_KEY:
               return this.parseAtlantis(properties, imageStore);
            case SGRASS_KEY:
               return this.parseSgrass(properties, imageStore);
         }
      }

      return false;
   }


   private final int BGND_NUM_PROPERTIES = 4;
   private final int BGND_COL = 2;
   private  final int BGND_ROW = 3;
   private  final int BGND_ID = 1;

   public boolean parseBackground(String [] properties, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         this.setBackground(pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }


   private final int OCTO_NUM_PROPERTIES = 7;
   private  final int OCTO_COL = 2;
   private  final int OCTO_ROW = 3;
   private  final int OCTO_ID = 1;
   private  final int OCTO_LIMIT = 4;
   private  final int OCTO_ACTION_PERIOD = 5;
   private  final int OCTO_ANIMATION_PERIOD = 6;



   public boolean parseOcto(String [] properties,
                                   ImageStore imageStore)
   {
      if (properties.length == OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                 Integer.parseInt(properties[OCTO_ROW]));
         Entity entity = imageStore.createOctoNotFull(properties[OCTO_ID],
                 Integer.parseInt(properties[OCTO_LIMIT]),
                 pt,
                 Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]),
                 imageStore.getImageList( OCTO_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == OCTO_NUM_PROPERTIES;
   }

   private final int OBSTACLE_NUM_PROPERTIES = 4;
   private final int OBSTACLE_COL = 2;
   private final int OBSTACLE_ROW = 3;
   private final int OBSTACLE_ID = 1;
   private final int FISH_NUM_PROPERTIES = 5;


   public boolean parseObstacle(String [] properties,
                                       ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = imageStore.createObstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList( OBSTACLE_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   private final int FISH_COL = 2;
   private  final int FISH_ROW = 3;
   private final int FISH_ID = 1;
   private final int FISH_ACTION_PERIOD = 4;

   public boolean parseFish(String [] properties,
                                   ImageStore imageStore)
   {
      if (properties.length == FISH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                 Integer.parseInt(properties[FISH_ROW]));
         Entity entity = imageStore.createFish(properties[FISH_ID],
                 pt, Integer.parseInt(properties[FISH_ACTION_PERIOD]),
                 imageStore.getImageList( FISH_KEY));
         this.tryAddEntity( entity);
      }

      return properties.length == FISH_NUM_PROPERTIES;
   }

   private final int ATLANTIS_NUM_PROPERTIES = 4;
   private  final int ATLANTIS_COL = 2;
   private final int ATLANTIS_ROW = 3;
   private final int ATLANTIS_ID = 1;
   private final int ATLANTIS_ANIMATION_PERIOD = 70;


   public boolean parseAtlantis(String [] properties,
                                       ImageStore imageStore)
   {
      if (properties.length == ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         Entity entity = imageStore.createAtlantis(properties[ATLANTIS_ID],
                 pt, imageStore.getImageList( ATLANTIS_KEY));
         this.tryAddEntity( entity);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }

   private  final int SGRASS_NUM_PROPERTIES = 5;
   private  final int SGRASS_COL = 2;
   private  final int SGRASS_ROW = 3;
   private  final int SGRASS_ID = 1;
   private final int SGRASS_ACTION_PERIOD = 4;

   public boolean parseSgrass(String [] properties,
                                     ImageStore imageStore)
   {
      if (properties.length == SGRASS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                 Integer.parseInt(properties[SGRASS_ROW]));
         Entity entity = imageStore.createSgrass(properties[SGRASS_ID],
                 pt,
                 Integer.parseInt(properties[SGRASS_ACTION_PERIOD]),
                 imageStore.getImageList( SGRASS_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == SGRASS_NUM_PROPERTIES;
   }
   public void tryAddEntity(Entity entity)
   {
      if (this.isOccupied(entity.getpositions()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }
//   public static boolean withinBounds(WorldModel world, Point pos)
//   {
//      return pos.y >= 0 && pos.y < world.numRows &&
//              pos.x >= 0 && pos.x < world.numCols;
//   }

   public boolean isOccupied(Point pos)
   {
      return this.withinBounds(this, pos) &&
              this.getOccupancyCell(pos) != null;
   }

   public Optional<Entity> findNearest(Point pos,
                                              Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (kind.isInstance(entity))
         {
            ofType.add(entity);
         }
      }

      return pos.nearestEntity(ofType);
   }
   public void addEntity(Entity entity)
   {
      if (this.withinBounds(this, entity.getpositions()))
      {
         this.setOccupancyCell(entity.getpositions(), entity);
         this.entities.add(entity);
      }
   }
   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getpositions();
      if (this.withinBounds(this, pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell( oldPos, null);
         removeEntityAt(this, pos);
         this.setOccupancyCell( pos, entity);
         entity.setPosition(pos);
      }
   }
   public void removeEntity(Entity entity)
   {
      removeEntityAt(this, entity.getpositions());
   }
   public void removeEntityAt(WorldModel world, Point pos)
   {
      if (this.withinBounds(world, pos)
              && this.getOccupancyCell(pos) != null)
      {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         world.entities.remove(entity);
         world.setOccupancyCell(pos, null);
      }
   }

   public void setBackground(Point pos,
                                    Background background)
   {
      if (this.withinBounds(this, pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }
   public Optional<Entity> getOccupant( Point pos)
   {
      if (this.isOccupied(pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }
   public Entity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.getY()][pos.getX()];
   }
   public void setOccupancyCell(Point pos,
                                       Entity entity)
   {
      this.getOccupancy()[pos.getY()][pos.getX()] = entity;
   }

   public void setBackgroundCell(Point pos,
                                        Background background)
   {
      this.background[pos.getY()][pos.getX()] = background;
   }

   public Background getBackgroundCell( Point pos)
   {
      return this.getBackground()[pos.getY()][pos.getX()];
   }

   public PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {

         return ((Background)entity).getpimage()
                 .get(((Background)entity).getImageIndex());
      }
      else if (entity instanceof Entity)
      {

         return ((Entity)entity).toimages().get(((Entity)entity).getindex());

      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         entity));
      }
   }
   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(this, pos))
      {
         return Optional.of(this.getCurrentImage(this.getBackgroundCell(pos)));
      }
      else
      {
         return Optional.empty();
      }
   }
   public boolean withinBounds(WorldModel world, Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < world.getNumRows() &&
              pos.getX() >= 0 && pos.getX() < world.getNumCols();
   }

}
