package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;
import physics.engine.math.Vector2;

public class Polygon extends Shape
{

    public static Polygon create(Vector2[] vertices, float density)
    {
        // TODO: Implement polygon mass calculation
        return null;
    }

    public static Polygon create(Vector2[] vertices)
    {
        return create(vertices, 1);
    }


    public static Polygon createRectangle(float width, float height, float density)
    {
        // Calculate vertex positions
        float halfWidth = width / 2;
        float halfHeight = height / 2;

        Vector2[] vertices = new Vector2[]
        {
            new Vector2(-halfWidth, -halfHeight),
            new Vector2(halfWidth, -halfHeight),
            new Vector2(halfWidth, halfHeight),
            new Vector2(-halfWidth, halfHeight),
        };

        // Calculate mass and inertia
        float magSq = (width * width) + (height * height);

        float mass = (width * height) * density;
        float inertia = mass * magSq / 12;

        // Calculate radius for broad phase collision detection
        float radius = (float) Math.sqrt(magSq) / 2;

        return new Polygon(vertices, mass, inertia, radius);
    }

    public static Polygon createRectangle(float width, float height)
    {
        return createRectangle(width, height, 1);
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
