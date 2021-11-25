import processing.core.PImage;

import java.util.List;

public abstract class Sched_Acti extends Entity{
    public Sched_Acti(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }


    public abstract void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore);
    public abstract void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler);
}
