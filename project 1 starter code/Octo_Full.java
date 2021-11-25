import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Octo_Full extends positionfunc {
    public Octo_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }



    public void transformFull(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore) {
        Octo_Not_Full octo = imageStore.createOctoNotFull(this.getids(), this.getlimit(),
                this.getpositions(), this.getaction(), this.getanimationnow(),
                this.toimages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleActions(scheduler, world, imageStore);
    }

    public boolean move(WorldModel world,
                              Entity target, EventScheduler scheduler) {
        if (this.getpositions().adjacent(target.getpositions())) {
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getpositions());

            if (!this.getpositions().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore) {

        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(this,world, imageStore),
                    this.getaction());
            scheduler.scheduleEvent(this, this.createAnimationAction(this,0),
                    this.getAnimationPeriod());
            ;
        }

    }
    public void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest( this.getpositions(),
                Atlantis.class);

        if (fullTarget.isPresent() &&
                this.move(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((Sched_Acti)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    this.createActivityAction(this,world, imageStore),
                    this.getaction());
        }
    }





}
