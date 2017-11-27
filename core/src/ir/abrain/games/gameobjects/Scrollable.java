package ir.abrain.games.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {

    // Protected is similar to private, but allows inheritance by subclasses.
    protected Vector2 position;
    protected Vector2 velocity;
    protected float width;
    protected float height;
    protected boolean isScrolledLeft;
    protected boolean isScrolledRight;

    public Scrollable(float x, float y, float width, float height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
        isScrolledRight = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        // If the Scrollable object is no longer visible:
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }
        if (position.x > width) {
            isScrolledRight = true;
        }
    }

    // Reset: Should Override in subclass for more specific behavior.
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
        isScrolledRight = false;
    }

    public void setScrollSpeed(float scrollSpeed)
    {
        velocity = new Vector2(scrollSpeed, 0);
    }

    // Getters for instance variables
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public float getTailXLeft() {
        return position.x + width;
    }
   public float getTailXRight() {
        return position.x - width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isScrolledRight() {
        return isScrolledRight;
    }
}