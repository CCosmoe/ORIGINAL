import java.util.List;
import processing.core.PImage;

final class Background {
   private String id;
   private List<PImage> images;
   private int imageIndex;


   protected int getImageIndex() {
      return imageIndex;
   }

   protected String getids() {

      return id;
   }

   protected  List<PImage> getpimage() {


      return images;
   }

   public Background(String id, List<PImage> images) {
      this.id = id;
      this.images = images;
   }

//   public PImage getCurrentImage(Object entity) {
//
//      return ((Entity) entity).toimages().get(((Entity) entity).getindex());
//
//   }
}