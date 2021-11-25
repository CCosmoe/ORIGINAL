import processing.core.PImage;

import java.util.List;

public class Atlantis extends Sched_Acti{
    public Atlantis(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    private int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {

        scheduler.scheduleEvent(this,
                this.createAnimationAction(this,ATLANTIS_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
    }


    public void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}
