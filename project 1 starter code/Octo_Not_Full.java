import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Octo_Not_Full extends positionfunc{

    public Octo_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public boolean transformNotFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getcount() >= this.getcount())
        {
            Octo_Full octo = imageStore.createOctoFull(this.getids(), this.getcount(),
                    this.getpositions(), this.getaction(), this.getanimationnow(),
                    this.toimages());

            world.removeEntity( this);
            scheduler.unscheduleAllEvents( this);

            world.addEntity(octo);
            octo.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean move(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        if (this.getpositions().adjacent(target.getpositions()))
        {
            this.setcount(1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getpositions());

            if (!this.getpositions().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                this.createActivityAction(this,world, imageStore),
                this.getaction());
        scheduler.scheduleEvent(this,
                this.createAnimationAction(this,0), this.getAnimationPeriod());
    }



    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getpositions(), Fish.class);

        if (!notFullTarget.isPresent() ||
                !this.move(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(this,world, imageStore),
                    this.getaction());
        }
    }





}
