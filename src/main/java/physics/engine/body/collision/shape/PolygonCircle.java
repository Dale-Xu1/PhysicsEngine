package physics.engine.body.collision.shape;

import physics.engine.body.Body;
import physics.engine.body.collision.Collision;
import physics.engine.body.shape.Circle;
import physics.engine.math.Vector2;

public class PolygonCircle implements ShapeCollision
{

    private static class SupportEdge // I made this term up
    {
        private final Vector2 left;
        private final Vector2 right;

        private final Vector2 normal;
        private final float distance;

        private SupportEdge(Vector2 left, Vector2 right, Vector2 normal, float distance)
        {
            this.left = left;
            this.right = right;
            this.normal = normal;
            this.distance = distance;
        }
    }


    @Override
    public Collision testCollision(Body a, Body b)
    {
        if (a.getShape() instanceof Circle)
        {
            // Invert collision if circle is A
            Collision collision = getCollision(b, a);
            return (collision == null) ? null : collision.getInverse();
        }

        return getCollision(a, b);
    }

    private Collision getCollision(Body a, Body b)
    {
        // Get shape data
        TransformedPolygon polygon = new TransformedPolygon(a);

        Vector2 position = b.getPosition();
        float radius = b.getShape().getRadius();

        // Find support
        SupportEdge support = findAxisLeastPenetration(polygon, position, radius);
        if (support == null) return null;

        // Test which corner is the circle's support point
        Vector2 leftToRight = support.right.sub(support.left);

        Vector2 left = position.sub(support.left);
        Vector2 right = position.sub(support.right);

        if (left.dot(leftToRight) <= 0)
        {
            return cornerCollision(left, position, radius);
        }
        else if (right.dot(leftToRight) >= 0)
        {
            return cornerCollision(right, position, radius);
        }
        else
        {
            // Support point is neither corners
            return centerCollision(support, position, radius);
        }
    }

    private Collision cornerCollision(Vector2 vertex, Vector2 position, float radius)
    {
        // Point on circle that points to corner of polygon
        float dist = vertex.mag();
        if (dist >= radius) return null;

        Vector2 normal = vertex.normalize(); // Vertex is a vector that points from the corner to the circle's center
        Vector2 start = position.add(normal.mult(-radius));

        return new Collision(radius - dist, normal, start);
    }

    private Collision centerCollision(SupportEdge support, Vector2 position, float radius)
    {
        // Point on circle that points to edge of polygon
        float penetration = radius - support.distance;
        Vector2 start = position.sub(support.normal.mult(radius));

        return new Collision(penetration, support.normal, start);
    }


    private SupportEdge findAxisLeastPenetration(TransformedPolygon polygon, Vector2 position, float radius)
    {
        SupportEdge support = null;

        // Get polygon data
        Vector2[] vertices = polygon.getVertices();
        Vector2[] normals = polygon.getNormals();

        for (int i = 0; i < vertices.length; i++)
        {
            Vector2 vertex = vertices[i];
            Vector2 normal = normals[i];

            Vector2 relative = position.sub(vertex);
            float projected = relative.dot(normal);

            // Axis found where circle doesn't intersect
            if (projected >= radius) return null;

            if ((support == null) || (projected > support.distance))
            {
                // Edge where circle can get farthest from without leaving bounds
                Vector2 right = vertices[(i + 1) % vertices.length];
                support = new SupportEdge(vertex, right, normal, projected);
            }
        }

        return support;
    }

}
