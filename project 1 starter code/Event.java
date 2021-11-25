final class Event
{
   private Action action;
   private long time;
   private Entity entity;

   public Event(Action action, long time, Entity entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }

   protected Action getac(){

      return action;

   }

   protected long gett(){

      return time;

   }

   protected Entity gete(){

      return entity;

   }

}
