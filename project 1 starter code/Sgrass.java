import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Sgrass extends Sched_Acti{
    public Sgrass( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createActivityAction(this,world, imageStore),
                this.getaction());
    }
   private String FISH_ID_PREFIX = "fish -- ";
   private  int FISH_CORRUPT_MIN = 20000;
   private  int FISH_CORRUPT_MAX = 30000;
   private  String FISH_KEY = "fish";

   public void executeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(this.getpositions());

      if (openPt.isPresent())
      {
         Fish fish = imageStore.createFish(FISH_ID_PREFIX + this.getids(),
                 openPt.get(), FISH_CORRUPT_MIN +
                         Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                 imageStore.getImageList(FISH_KEY));
         world.addEntity(fish);
         fish.scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this,
              this.createActivityAction(this,world, imageStore),
              this.getaction());
   }


}
