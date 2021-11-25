import processing.core.PImage;

import java.util.List;

public class Quake extends Sched_Acti{
    public Quake( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }





    private int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {

        scheduler.scheduleEvent(this,
                this.createActivityAction( this,world, imageStore),
                this.getaction());
        scheduler.scheduleEvent(this,
                this.createAnimationAction( this,QUAKE_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
    }


    public void executeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity( this);
    }
}
