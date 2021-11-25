import processing.core.PImage;

import java.util.List;

public class Fish extends Sched_Acti{
    public Fish(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }




    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createActivityAction(this, world, imageStore),
                this.getaction());
    }


    private String CRAB_ID_SUFFIX = " -- crab";
    private  int CRAB_PERIOD_SCALE = 4;
    private  int CRAB_ANIMATION_MIN = 50;
    private  int CRAB_ANIMATION_MAX = 150;
    private  String CRAB_KEY = "crab";


    public void executeActivity( WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getpositions();  // store current position before removing

        world.removeEntity( this);
        scheduler.unscheduleAllEvents(this);

        Crab crab = imageStore.createCrab(this.getids() + CRAB_ID_SUFFIX,
                pos, this.getaction() / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                imageStore.getImageList( CRAB_KEY));

        world.addEntity( crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }
}
