package physics.engine.body.collision.shape;

import physics.engine.body.Body;
import physics.engine.body.shape.Polygon;
import physics.engine.math.Matrix2;
import physics.engine.math.Vector2;

public class TransformedPolygon
{

    private final Vector2[] vertices;
    private final Vector2[] normals;


    public TransformedPolygon(Body body)
    {
        // Get body data
        Polygon polygon = (Polygon) body.getShape();

        Vector2 position = body.getPosition();
        Matrix2 rotation = new Matrix2(body.getRotation());

        // Get polygon data
        Vector2[] polygonVertices = polygon.getVertices();
        Vector2[] polygonNormals = polygon.getNormals();

        vertices = new Vector2[polygonVertices.length];
        normals = new Vector2[polygonNormals.length];

        for (int i = 0; i < vertices.length; i++)
        {
            Vector2 vertex = polygonVertices[i];
            Vector2 normal = polygonNormals[i];

            // Transform vertices and rotate normals
            vertices[i] = position.add(rotation.mult(vertex));
            normals[i] = rotation.mult(normal);
        }
    }


    public Vector2[] getVertices()
    {
        return vertices;
    }

    public Vector2[] getNormals()
    {
        return normals;
    }

}
