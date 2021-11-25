import processing.core.PImage;

import java.util.List;

public abstract class Octo extends Sched_Acti{
    public Octo(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public abstract boolean move(WorldModel world, Entity target, EventScheduler scheduler);



//    public Point nextPosition(WorldModel world,
//                                  Point destPos)
//    {
//        int horiz = Integer.signum(destPos.getX() - this.getpositions().getX());
//        Point newPos = new Point(this.getpositions().getX() + horiz,
//                this.getpositions().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - this.getpositions().getY());
//            newPos = new Point(this.getpositions().getX(),
//                    this.getpositions().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getpositions();
//            }
//        }
//
//        return newPos;
//    }



}
