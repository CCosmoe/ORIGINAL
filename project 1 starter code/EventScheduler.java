import java.util.*;

/*
EventScheduler: ideally our way of controlling what happens in our virtual world
 */

final class EventScheduler
{
   private PriorityQueue<Event> eventQueue;
   private Map<Entity, List<Event>> pendingEvents;
   private double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }
   public void scheduleEvent(Entity entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * this.timeScale);
      Event event = new Event(action, time, entity);

      this.eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = this.pendingEvents.getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      this.pendingEvents.put(entity, pending);
   }
   public void unscheduleAllEvents(Entity entity)
   {
      List<Event> pending = this.pendingEvents.remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            this.eventQueue.remove(event);
         }
      }
   }
   public void removePendingEvent(Event event)
   {
      List<Event> pending = this.pendingEvents.get(event.gete());

      if (pending != null)
      {
         pending.remove(event);
      }
   }
   public void updateOnTime(long time)
   {
      while (!this.eventQueue.isEmpty() &&
              this.eventQueue.peek().gett() < time)
      {
         Event next = this.eventQueue.poll();
         this.removePendingEvent(next);

         next.getac().executeAction(this);
      }
   }

//   public void executeAction(Action action)
//   {
//      switch (action.getacttionakind())
//      {
//         case ACTIVITY:
//            this.executeActivityAction(action);
//            break;
//
//         case ANIMATION:
//            action.executeAnimationAction(this);
//            break;
//      }
//   }


//   public void executeAtlantisActivity(Entity entity, WorldModel world,
//                                        ImageStore imageStore)
//   {
//      this.unscheduleAllEvents(entity);
//      world.removeEntity( entity);
//   }

//   private String FISH_ID_PREFIX = "fish -- ";
//   private  int FISH_CORRUPT_MIN = 20000;
//   private  int FISH_CORRUPT_MAX = 30000;
//   private  String FISH_KEY = "fish";
//
//   public void executeSgrassActivity(Entity entity, WorldModel world,
//                                     ImageStore imageStore)
//   {
//      Optional<Point> openPt = world.findOpenAround(entity.getpositions());
//
//      if (openPt.isPresent())
//      {
//         Entity fish = imageStore.createFish(FISH_ID_PREFIX + entity.getids(),
//                 openPt.get(), FISH_CORRUPT_MIN +
//                         Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
//                 imageStore.getImageList(FISH_KEY));
//         world.addEntity(fish);
//         fish.scheduleActions(this, world, imageStore);
//      }
//
//      this.scheduleEvent(entity,
//              entity.createActivityAction(world, imageStore),
//              entity.getaction());
//   }

//   public void executeQuakeActivity(Entity entity, WorldModel world,
//                                    ImageStore imageStore)
//   {
//      this.unscheduleAllEvents(entity);
//      world.removeEntity( entity);
//   }



//   private String QUAKE_KEY = "quake";



//   public void executeOctoFullActivity(Entity entity, WorldModel world,
//                                       ImageStore imageStore)
//   {
//      Optional<Entity> fullTarget = world.findNearest( entity.getpositions(),
//              EntityKind.ATLANTIS);
//
//      if (fullTarget.isPresent() &&
//              octfull.moveToFull(world, fullTarget.get(), this))
//      {
//         //at atlantis trigger animation
//         fullTarget.get().scheduleActions(this, world, imageStore);
//
//         //transform to unfull
//         octfull.transformFull(world, this, imageStore);
//      }
//      else
//      {
//         this.scheduleEvent(entity,
//                 entity.createActivityAction(world, imageStore),
//                 entity.getaction());
//      }
//   }

//   public void executeOctoNotFullActivity(Entity entity,
//                                          WorldModel world, ImageStore imageStore)
//   {
//      Optional<Entity> notFullTarget = world.findNearest(entity.getpositions(),
//              EntityKind.FISH);
//
//      if (!notFullTarget.isPresent() ||
//              !octnot.moveToNotFull(world, notFullTarget.get(), this) ||
//              !octnot.transformNotFull(world, this, imageStore))
//      {
//         this.scheduleEvent(entity,
//                 entity.createActivityAction(world, imageStore),
//                 entity.getaction());
//      }
//   }

//   private String CRAB_ID_SUFFIX = " -- crab";
//   private  int CRAB_PERIOD_SCALE = 4;
//   private  int CRAB_ANIMATION_MIN = 50;
//   private  int CRAB_ANIMATION_MAX = 150;
//   private  String CRAB_KEY = "crab";


//
//   public void executeFishActivity(Entity fishentity, WorldModel world,
//                                   ImageStore imageStore)
//   {
//      Point pos = fishentity.getpositions();  // store current position before removing
//
//      world.removeEntity( fishentity);
//      this.unscheduleAllEvents(fishentity);
//
//      Entity crab = imageStore.createCrab(fishentity.getids() + CRAB_ID_SUFFIX,
//              pos, fishentity.getaction() / CRAB_PERIOD_SCALE,
//              CRAB_ANIMATION_MIN +
//                      Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
//              imageStore.getImageList( CRAB_KEY));
//
//      world.addEntity( crab);
//      crab.scheduleActions(this, world, imageStore);
//   }
}
