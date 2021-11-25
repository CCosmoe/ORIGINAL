import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

/*
WorldView ideally mostly controls drawing the current part of the whole world
that we can see based on the viewport
*/

final class WorldView
{
   private PApplet screen;
   private WorldModel world;
   private int tileWidth;
   private int tileHeight;
   private Viewport viewport;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }
   public int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }

   public void shiftView(int colDelta, int rowDelta)
   {
      int newCol = clamp(this.viewport.getcols() + colDelta, 0,
              this.world.getNumCols() - this.viewport.getnumcols());
      int newRow = clamp(this.viewport.getrows() + rowDelta, 0,
              this.world.getNumRows() - this.viewport.getnumrows());

      this.viewport.shift(newCol, newRow);
   }

   public void drawBackground()
   {
      for (int row = 0; row < this.viewport.getnumrows(); row++)
      {
         for (int col = 0; col < this.viewport.getnumcols(); col++)
         {
            Point worldPoint = viewport.viewportToWorld( col, row);
            Optional<PImage> image = world.getBackgroundImage(
                    worldPoint);
            if (image.isPresent())
            {
               this.screen.image(image.get(), col * this.tileWidth,
                       row * this.tileHeight);
            }
         }
      }
   }

   public void drawEntities()
   {
      for (Entity entity : this.world.getEntities())
      {
         Point pos = entity.getpositions();

         if (viewport.contains(pos))
         {
            Point viewPoint = viewport.worldToViewport(pos.getX(), pos.getY());
            this.screen.image(this.getCurrentImage(entity),
                    viewPoint.getX() * this.tileWidth, viewPoint.getY() * this.tileHeight);
         }
      }
   }

   public PImage getCurrentImage(Entity entity)
   {
      if (entity != null)
      {

         return ((Entity)entity).toimages().get(((Entity)entity).getindex());

      }
      else
      {
         throw new UnsupportedOperationException(
            String.format("getCurrentImage not supported for %s",
            entity));
      }
   }

   public void drawViewport()
   {
      drawBackground();
      drawEntities();
   }
}
