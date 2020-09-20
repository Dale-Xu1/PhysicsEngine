package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;
import physics.engine.math.Vector2;

public class Polygon extends Shape
{

    public static Polygon create(Vector2[] vertices, float density)
    {
        // Find farthest distance
        float maxDistanceSq = 0;

        float area = 0;
        float moment = 0;

        for (int i = 0; i < vertices.length; i++)
        {
            Vector2 vertex1 = vertices[i];
            Vector2 vertex2 = vertices[(i + 1) % vertices.length];

            // Test if distance is greatest from origin
            float distanceSq = vertex1.magSq();
            if (distanceSq > maxDistanceSq)
            {
                maxDistanceSq = distanceSq;
            }

            // Calculate area of triangle
            float cross = vertex1.cross(vertex2);
            float triangleArea = cross / 2;
            area += triangleArea;

            // Calculate inertia
            float x = (vertex1.x * vertex1.x) + (vertex2.x * vertex2.x) + (vertex1.x * vertex2.x);
            float y = (vertex1.y * vertex1.y) + (vertex2.y * vertex2.y) + (vertex1.y * vertex2.y);
            moment += cross * (x + y);
        }

        float distance = (float) Math.sqrt(maxDistanceSq);

        float mass = area * density;
        float inertia = mass * moment / 12;

        return new Polygon(vertices, mass, inertia, distance);
    }

    public static Polygon create(Vector2[] vertices)
    {
        return create(vertices, 1);
    }

    public static Polygon createStatic(Vector2[] vertices)
    {
        return create(vertices, 0);
    }


    private final Vector2[] vertices;
    private final Vector2[] normals;


    @SuppressWarnings("SuspiciousNameCombination")
    public Polygon(Vector2[] vertices, float mass, float inertia, float radius)
    {
        super(mass, inertia, radius);

        this.vertices = vertices;
        normals = new Vector2[vertices.length];

        // Calculate normals
        for (int i = 0; i < normals.length; i++)
        {
            // Calculate direction of face
            Vector2 vertex1 = vertices[i];
            Vector2 vertex2 = vertices[(i + 1) % vertices.length];

            Vector2 face = vertex2.sub(vertex1);

            // Get vector perpendicular to face
            normals[i] = new Vector2(face.y, -face.x).normalize();
        }
    }


    @Override
    public void render(GraphicsContext gc, World world)
    {
        gc.beginPath();

        // Move to start
        Vector2 start = vertices[0];
        Vector2 screenStart = world.toScreen(start);

        gc.moveTo(screenStart.x, screenStart.y);

        // Draw vertices
        for (int i = 1; i < vertices.length; i++)
        {
            Vector2 vertex = vertices[i];
            Vector2 screenVertex = world.toScreen(vertex);

            gc.lineTo(screenVertex.x, screenVertex.y);
        }

        gc.closePath();
        gc.stroke();
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
