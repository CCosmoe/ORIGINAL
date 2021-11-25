/*
Action: ideally what our various entities might do in our virutal world
 */

import java.sql.PseudoColumnUsage;

abstract class Action
{
   public Sched_Acti entity;
   private WorldModel world;
   private ImageStore imageStore;




   protected Sched_Acti getentity(){


      return entity;
   }

   protected WorldModel worlds(){

      return world;

   }

   protected ImageStore getimage(){

      return imageStore;

   }

   public Action(Sched_Acti entity, WorldModel world,
      ImageStore imageStore)
   {
//      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
   }

   public abstract void executeAction(EventScheduler scheduler);


//   public void executeAnimationAction(EventScheduler scheduler)
//   {
//      this.getentity().nextImage();
//
//      if (this.repeatCount != 1)
//      {
//         scheduler.scheduleEvent(this.getentity(),
//                 Entity.createAnimationAction(entity,Math.max(this.repeatCount - 1, 0)),
//                this.getentity().getAnimationPeriod());
//      }
//   }

//   public void executeActivityAction(EventScheduler scheduler)
//   {
//      entity.executeActivity(world, imageStore, scheduler);
//   }

//   public void executeAction(EventScheduler scheduler)
//   {
//      switch (this.getacttionakind())
//      {
//         case ACTIVITY:
//            this.executeActivityAction(scheduler);
//            break;
//
//         case ANIMATION:
//            this.executeAnimationAction(scheduler);
//            break;
//      }
//   }
}
