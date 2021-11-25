public class Activities extends Action {
    public Activities( Sched_Acti entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore);
    }

    public void executeActivityAction(EventScheduler scheduler) {
        getentity().executeActivity(worlds(), getimage(), scheduler);
    }


    public void executeAction(EventScheduler scheduler) {

        this.executeActivityAction(scheduler);
    }
}

