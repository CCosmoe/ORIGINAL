/*
Viewport ideally helps control what part of the world we are looking at for drawing only what we see
Includes helpful helper functions to map between the viewport and the real world
 */


final class Viewport
{
   private int row;
   private int col;
   private int numRows;
   private int numCols;


   protected int getrows(){

      return row;
   }

   protected int getcols(){

      return col;
   }

   protected int getnumrows(){

      return numRows;
   }

   protected int getnumcols(){

      return numCols;
   }

   public Viewport(int numRows, int numCols)
   {
      this.numRows = numRows;
      this.numCols = numCols;
   }
   public void shift(int col, int row)
   {
      this.col = col;
      this.row = row;
   }
   public Point viewportToWorld(int col, int row)
   {
      return new Point(col + this.col, row + this.row);
   }

   public Point worldToViewport(int col, int row)
   {
      return new Point(col - this.col, row - this.row);
   }

   public boolean contains(Point p)
   {
      return p.getY() >= this.getrows() && p.getY() < this.getrows() + this.getnumrows() &&
              p.getX() >= this.getcols() && p.getX() < this.getcols() + this.getnumcols();
   }
}
