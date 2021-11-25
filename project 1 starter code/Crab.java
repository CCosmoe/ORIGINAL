import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Crab extends Octo{

    public Crab( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }




    public Point nextPositions(WorldModel world,
                                  Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.getpositions().getX());
        Point newPos = new Point(this.getpositions().getX() + horiz,
                this.getpositions().getY());

        Optional<Entity> occupant = world.getOccupant( newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass() ==  Fish.class)))
        {
            int vert = Integer.signum(destPos.getY() - this.getpositions().getY());
            newPos = new Point(this.getpositions().getX(), this.getpositions().getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().getClass() == Fish.class)))
            {
                newPos = this.getpositions();
            }
        }

        return newPos;
    }

    public boolean move(WorldModel world,
                              Entity target, EventScheduler scheduler)
    {
        if (this.getpositions().adjacent( target.getpositions()))
        {
            world.removeEntity( target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPositions(world, target.getpositions());

            if (!this.getpositions().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant( nextPos);
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
                    this.createActivityAction(this, world, imageStore),
                    this.getaction());
            scheduler.scheduleEvent(this,
                    this.createAnimationAction(this,0), this.getAnimationPeriod());
    }

    private String QUAKE_KEY = "quake";

    public void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> crabTarget = world.findNearest(
                this.getpositions(), Sgrass.class);
        long nextPeriod = this.getaction();

        if (crabTarget.isPresent())
        {
            Point tgtPos = crabTarget.get().getpositions();

            if (this.move(world, crabTarget.get(), scheduler))
            {
                Quake quake = imageStore.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity( quake);
                nextPeriod += this.getaction();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(this, world, imageStore),
                nextPeriod);
    }


}
