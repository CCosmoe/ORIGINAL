import java.util.List;
import java.util.Optional;

import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */

abstract class Entity
{
   //private EntityKind kind;
   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;
   private int resourceLimit;
   private int resourceCount;
   private int actionPeriod;
   private int animationPeriod;


//   public EntityKind getentitykind(){
//
//      return kind;
//
//   }

   protected String getids(){

      return id;

   }

   protected Point getpositions(){

      return position;

   }

   protected Point setPosition(Point pos){
      position = pos;
      return pos;
   }

   protected List<PImage> toimages(){

      return images;
   }

   protected int getindex(){

      return imageIndex;
   }

   protected int getlimit(){

      return resourceLimit;
   }
   protected int getcount(){

      return resourceCount;
   }
   protected void setcount(int x){
      resourceCount +=1;
   }

   protected int getaction(){

      return actionPeriod;
   }

   protected int getanimationnow(){

      return animationPeriod;
   }


   public Entity(String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount,
      int actionPeriod, int animationPeriod)
   {
      //this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }




   public int getAnimationPeriod()
   {
      return animationPeriod;
   }



   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }


//      private int QUAKE_ANIMATION_REPEAT_COUNT = 10;
//      private  int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

//   public abstract void scheduleActions(EventScheduler scheduler,
//                                      WorldModel world, ImageStore imageStore);
//
//   public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
  // {
//      switch (this.kind)
//      {
//         case OCTO_FULL:
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//            scheduler.scheduleEvent(this, this.createAnimationAction( 0),
//                    this.getAnimationPeriod());
//            break;

//         case OCTO_NOT_FULL:
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//            scheduler.scheduleEvent(this,
//                    this.createAnimationAction( 0), this.getAnimationPeriod());
//            break;

//         case FISH:
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//            break;

//         case CRAB:
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction( world, imageStore),
//                    this.actionPeriod);
//            scheduler.scheduleEvent(this,
//                    this.createAnimationAction( 0), this.getAnimationPeriod());
//            break;

//         case QUAKE:
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction( world, imageStore),
//                    this.actionPeriod);
//            scheduler.scheduleEvent(this,
//                    this.createAnimationAction( QUAKE_ANIMATION_REPEAT_COUNT),
//                    this.getAnimationPeriod());
//            break;

//         case SGRASS:
//            scheduler.scheduleEvent(this,
//                    this.createActivityAction(world, imageStore),
//                    this.actionPeriod);
//            break;
//         case ATLANTIS:
//            scheduler.scheduleEvent(this,
//                    this.createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT),
//                    this.getAnimationPeriod());
//            break;
   // }
   //}

//   public boolean transformNotFull(WorldModel world,
//                                          EventScheduler scheduler, ImageStore imageStore)
//   {
//      if (this.resourceCount >= this.resourceLimit)
//      {
//         Entity octo = imageStore.createOctoFull(this.id, this.resourceLimit,
//                 this.position, this.actionPeriod, this.animationPeriod,
//                 this.images);
//
//         world.removeEntity( this);
//         scheduler.unscheduleAllEvents( this);
//
//         world.addEntity(octo);
//         octo.scheduleActions(scheduler, world, imageStore);
//
//         return true;
//      }
//
//      return false;
//   }

//   public void transformFull(WorldModel world,
//                                    EventScheduler scheduler, ImageStore imageStore)
//   {
//      //Entity octo = new Octo_full(
//      Entity octo = imageStore.createOctoNotFull(this.id, this.resourceLimit,
//              this.position, this.actionPeriod, this.animationPeriod,
//              this.images);
//
//      world.removeEntity( this);
//      scheduler.unscheduleAllEvents(this);
//
//      world.addEntity(octo);
//      octo.scheduleActions(scheduler, world, imageStore);
//   }

//   public boolean moveToNotFull(WorldModel world,
//                                       Entity target, EventScheduler scheduler)
//   {
//      if (this.position.adjacent(target.position))
//      {
//         this.resourceCount += 1;
//         world.removeEntity(target);
//         scheduler.unscheduleAllEvents(target);
//
//         return true;
//      }
//      else
//      {
//         Point nextPos = this.nextPositionOcto(world, target.position);
//
//         if (!this.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(this, nextPos);
//         }
//         return false;
//      }
//   }
//   public boolean moveToFull(WorldModel world,
//                                    Entity target, EventScheduler scheduler)
//   {
//      if (this.position.adjacent(target.position))
//      {
//         return true;
//      }
//      else
//      {
//         Point nextPos = this.nextPositionOcto(world, target.position);
//
//         if (!this.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant(nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(this, nextPos);
//         }
//         return false;
//      }
//   }
//   public boolean moveToCrab(WorldModel world,
//                                    Entity target, EventScheduler scheduler)
//   {
//      if (this.position.adjacent( target.position))
//      {
//         world.removeEntity( target);
//         scheduler.unscheduleAllEvents(target);
//         return true;
//      }
//      else
//      {
//         Point nextPos = this.nextPositionCrab(world, target.position);
//
//         if (!this.position.equals(nextPos))
//         {
//            Optional<Entity> occupant = world.getOccupant( nextPos);
//            if (occupant.isPresent())
//            {
//               scheduler.unscheduleAllEvents(occupant.get());
//            }
//
//            world.moveEntity(this, nextPos);
//         }
//         return false;
//      }
//   }
//   public Point nextPositionOcto(WorldModel world,
//                                        Point destPos)
//   {
//      int horiz = Integer.signum(destPos.getX() - this.position.getX());
//      Point newPos = new Point(this.position.getX() + horiz,
//              this.position.getY());
//
//      if (horiz == 0 || world.isOccupied(newPos))
//      {
//         int vert = Integer.signum(destPos.getY() - this.position.getY());
//         newPos = new Point(this.position.getX(),
//                 this.position.getY() + vert);
//
//         if (vert == 0 || world.isOccupied( newPos))
//         {
//            newPos = this.position;
//         }
//      }
//
//      return newPos;
//   }
//   public Point nextPositionCrab(WorldModel world,
//                                        Point destPos)
//   {
//      int horiz = Integer.signum(destPos.getX() - this.position.getX());
//      Point newPos = new Point(this.position.getX() + horiz,
//              this.position.getY());
//
//      Optional<Entity> occupant = world.getOccupant( newPos);
//
//      if (horiz == 0 ||
//              (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
//      {
//         int vert = Integer.signum(destPos.getY() - this.position.getY());
//         newPos = new Point(this.position.getX(), this.position.getY() + vert);
//         occupant = world.getOccupant(newPos);
//
//         if (vert == 0 ||
//                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
//         {
//            newPos = this.position;
//         }
//      }
//
//      return newPos;
//   }


//   private String FISH_ID_PREFIX = "fish -- ";
//   private  int FISH_CORRUPT_MIN = 20000;
//   private  int FISH_CORRUPT_MAX = 30000;
//   private  String FISH_KEY = "fish";
//   public void executeSgrassActivity(WorldModel world,
//                                     ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Point> openPt = world.findOpenAround(this.getpositions());
//
//      if (openPt.isPresent())
//      {
//         Entity fish = imageStore.createFish(FISH_ID_PREFIX + entity.getids(),
//                 openPt.get(), FISH_CORRUPT_MIN +
//                         Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
//                 imageStore.getImageList(FISH_KEY));
//         world.addEntity(fish);
//         fish.scheduleActions(scheduler, world, imageStore);
//      }
//
//      scheduler.scheduleEvent(this,
//              this.createActivityAction(world, imageStore),
//              this.getaction());
//   }


//      public void executeCrabActivity(WorldModel world,
//                                   ImageStore imageStore, EventScheduler scheduler)
//   {
//      Optional<Entity> crabTarget = world.findNearest(
//              this.getpositions(), EntityKind.SGRASS);
//      long nextPeriod = this.getaction();
//
//      if (crabTarget.isPresent())
//      {
//         Point tgtPos = crabTarget.get().getpositions();
//
//         if (this.moveToCrab(this, world, crabTarget.get(), scheduler))
//         {
//            Entity quake = imageStore.createQuake(tgtPos,
//                    imageStore.getImageList(QUAKE_KEY));
//
//            world.addEntity( quake);
//            nextPeriod += this.getaction();
//            quake.scheduleActions(scheduler, world, imageStore);
//         }
//      }
//
//      scheduler.scheduleEvent(this,
//              this.createActivityAction(world, imageStore),
//              nextPeriod);
//   }


//   public void executeAtlantisActivity(WorldModel world,
//                                       ImageStore imageStore, EventScheduler scheduler)
//   {
//      scheduler.unscheduleAllEvents(this);
//      world.removeEntity( this);
//   }
//   public PImage getCurrentImage()
//   {
//         return this.toimages().get(this.getindex());
//   }




   public static Animate createAnimationAction(Sched_Acti entity, int repeatCount)
   {
      return new Animate(entity, null, null, repeatCount);
   }


   public static Activities createActivityAction(Sched_Acti entity, WorldModel world,
                                             ImageStore imageStore)
   {
      return new Activities(entity, world, imageStore, 0);
   }


}
