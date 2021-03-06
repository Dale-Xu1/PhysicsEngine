package physics.engine.body.collision.shape;

import physics.engine.body.Body;
import physics.engine.body.collision.Collision;
import physics.engine.math.Vector2;

public class PolygonPolygon implements ShapeCollision
{

    private static class SupportPoint
    {
        private final Vector2 vertex;
        private final Vector2 normal;
        private final float distance;

        private SupportPoint(Vector2 vertex, Vector2 normal, float distance)
        {
            this.vertex = vertex;
            this.normal = normal;
            this.distance = distance;
        }
    }


    @Override
    public Collision testCollision(Body a, Body b)
    {
        // Transform polygons to world coordinates
        TransformedPolygon polygonA = new TransformedPolygon(a);
        TransformedPolygon polygonB = new TransformedPolygon(b);

        // Find axis of least penetration on both bodies
        SupportPoint supportA = findAxisLeastPenetration(polygonA, polygonB);
        if (supportA == null) return null;

        SupportPoint supportB = findAxisLeastPenetration(polygonB, polygonA);
        if (supportB == null) return null;


        // Choose smaller axis
        boolean isSmallerA = supportA.distance < supportB.distance;
        SupportPoint support = isSmallerA ? supportA : supportB;

        // Convert to collision
        Collision collision = new Collision(support.distance, support.normal, support.vertex);
        return isSmallerA ? collision : collision.getInverse(); // Invert if support point was from B
    }

    private SupportPoint findAxisLeastPenetration(TransformedPolygon a, TransformedPolygon b)
    {
        SupportPoint minSupport = null;

        Vector2[] vertices = a.getVertices();
        Vector2[] normals = a.getNormals();

        for (int i = 0; i < vertices.length; i++)
        {
            Vector2 vertex = vertices[i];
            Vector2 normal = normals[i];

            SupportPoint support = findSupportPoint(b.getVertices(), normal, vertex);

            // If one axis results in no collision, stop checking other axis
            if (support == null) return null;

            // Get axis with smallest penetration
            if ((minSupport == null) || (support.distance < minSupport.distance))
            {
                minSupport = support;
            }
        }

        return minSupport;
    }

    private SupportPoint findSupportPoint(Vector2[] vertices, Vector2 normal, Vector2 point)
    {
        Vector2 support = null;
        float distance = 0; // Start at 0 so no vertices in front of face are returned

        for (Vector2 vertex : vertices)
        {
            // Project vertex in normal of face
            Vector2 relative = vertex.sub(point);
            float projected = relative.dot(normal);

            if (projected < distance)
            {
                // Choose point furthest behind face
                support = vertex;
                distance = projected;
            }
        }

        return (support == null) ? null : new SupportPoint(support, normal, -distance);
    }

}
