package physics.engine.body.collision.shape;

import physics.engine.body.Body;
import physics.engine.body.collision.Collision;

public interface ShapeCollision
{

    Collision testCollision(Body a, Body b);

}
