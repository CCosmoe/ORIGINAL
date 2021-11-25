import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

/*
ImageStore: to ideally keep track of the images used in our virtual world
 */

final class ImageStore
{
   private Map<String, List<PImage>> images;
   private List<PImage> defaultImages;

   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }
   public List<PImage> getImageList(String key)
   {
      return this.images.getOrDefault(key, this.defaultImages);
   }
   public void loadImages(Scanner in,
                                 PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            processImageLine(this.images, in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
                    lineNumber));
         }
         lineNumber++;
      }
   }

   private  int KEYED_IMAGE_MIN = 5;
   private  int KEYED_RED_IDX = 2;
   private int KEYED_GREEN_IDX = 3;
   private  int KEYED_BLUE_IDX = 4;

   public void processImageLine(Map<String, List<PImage>> images,
                                       String line, PApplet screen)
   {
      String[] attrs = line.split("\\s");
      if (attrs.length >= 2)
      {
         String key = attrs[0];
         PImage img = screen.loadImage(attrs[1]);
         if (img != null && img.width != -1)
         {
            List<PImage> imgs = getImages(images, key);
            imgs.add(img);

            if (attrs.length >= KEYED_IMAGE_MIN)
            {
               int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
               int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
               int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
               setAlpha(img, screen.color(r, g, b), 0);
            }
         }
      }
   }
   public List<PImage> getImages(Map<String, List<PImage>> images,
                                        String key)
   {
      List<PImage> imgs = images.get(key);
      if (imgs == null)
      {
         imgs = new LinkedList<>();
         images.put(key, imgs);
      }
      return imgs;
   }

   private  int COLOR_MASK = 0xffffff;


   public void setAlpha(PImage img, int maskColor, int alpha)
   {
      int alphaValue = alpha << 24;
      int nonAlpha = maskColor & COLOR_MASK;
      img.format = PApplet.ARGB;
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
         {
            img.pixels[i] = alphaValue | nonAlpha;
         }
      }
      img.updatePixels();
   }
   public Atlantis createAtlantis(String id, Point position,
                                       List<PImage> images)
   {
      return new Atlantis(id, position, images,
              0, 0, 0, 0);
   }
   public Octo_Full createOctoFull(String id, int resourceLimit,
                                       Point position, int actionPeriod, int animationPeriod,
                                       List<PImage> images)
   {
      return new Octo_Full(id, position, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }
   public Octo_Not_Full createOctoNotFull(String id, int resourceLimit,
                                          Point position, int actionPeriod, int animationPeriod,
                                          List<PImage> images)
   {
      return new Octo_Not_Full(id, position, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }
   public Obstacle createObstacle(String id, Point position,
                                       List<PImage> images)
   {
      return new Obstacle(id, position, images,
              0, 0, 0, 0);
   }
   public Fish createFish(String id, Point position, int actionPeriod,
                                   List<PImage> images)
   {
      return new Fish(id, position, images, 0, 0,
              actionPeriod, 0);
   }
   public Crab createCrab(String id, Point position,
                                   int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Crab(id, position, images,
              0, 0, actionPeriod, animationPeriod);
   }

   private  String QUAKE_ID = "quake";
   private  int QUAKE_ACTION_PERIOD = 1100;
   private  int QUAKE_ANIMATION_PERIOD = 100;

   public Quake createQuake(Point position, List<PImage> images)
   {
      return new Quake(QUAKE_ID, position, images,
              0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   public  Sgrass createSgrass(String id, Point position, int actionPeriod,
                                     List<PImage> images)
   {
      return new Sgrass(id, position, images, 0, 0,
              actionPeriod, 0);
   }
}
