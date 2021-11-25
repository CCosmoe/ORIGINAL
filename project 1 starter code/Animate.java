public class Animate extends Action{
    private int repeatCount;

    public Animate( Sched_Acti entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        super( entity, world, imageStore);
    }


    public void executeAction(EventScheduler scheduler)
    {

        this.executeAnimationAction(scheduler);

    }
    public void executeAnimationAction(EventScheduler scheduler)
    {
        this.getentity().nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.getentity(),
                    Entity.createAnimationAction(entity,Math.max(this.repeatCount - 1, 0)),
                    this.getentity().getAnimationPeriod());
        }
    }
}
