package physics.engine.body.collision;

import physics.engine.body.Body;
import physics.engine.body.collision.shape.CircleCircle;
import physics.engine.body.collision.shape.PolygonCircle;
import physics.engine.body.collision.shape.PolygonPolygon;
import physics.engine.body.shape.Circle;
import physics.engine.body.shape.Polygon;
import physics.engine.body.shape.Shape;

public class CollisionDetector
{

    private final CircleCircle circleCircle = new CircleCircle();
    private final PolygonPolygon polygonPolygon = new PolygonPolygon();
    private final PolygonCircle polygonCircle = new PolygonCircle();


    public Collision testCollision(Body a, Body b)
    {
        Shape shapeA = a.getShape();
        Shape shapeB = b.getShape();

        if (shapeA instanceof Circle)
        {
            if (shapeB instanceof Circle)
            {
                return circleCircle.testCollision(a, b);
            }
            else if (shapeB instanceof Polygon)
            {
                return polygonCircle.testCollision(a, b);
            }
        }
        else if (shapeA instanceof Polygon)
        {
            if (shapeB instanceof Circle)
            {
                return polygonCircle.testCollision(a, b);
            }
            else if (shapeB instanceof Polygon)
            {
                return polygonPolygon.testCollision(a, b);
            }
        }

        // Unknown shape
        throw new RuntimeException("Collision detector was given an unknown shape");
    }

}
