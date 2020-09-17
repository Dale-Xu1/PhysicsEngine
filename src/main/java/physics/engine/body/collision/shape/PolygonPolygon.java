package physics.engine.body.collision.shape;

import physics.engine.body.Body;
import physics.engine.body.collision.Collision;
import physics.engine.body.shape.Polygon;
import physics.engine.math.Vector2;

public class PolygonPolygon implements ShapeCollision
{

    @Override
    public Collision testCollision(Body a, Body b)
    {
        // TODO: Polygon-polygon collision detection
        return null;
    }

    private void findSupportPoint(Body body, Vector2 normal, Vector2 point)
    {

    }

}
