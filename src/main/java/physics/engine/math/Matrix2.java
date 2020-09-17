package physics.engine.math;

public class Matrix2
{

    public final float m00, m01;
    public final float m10, m11;


    public Matrix2(float m00, float m01, float m10, float m11)
    {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
    }

    public Matrix2(float angle)
    {
        // Create rotation matrix
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        m00 = cos;
        m01 = -sin;
        m10 = sin;
        m11 = cos;
    }


    public Vector2 mult(Vector2 vector)
    {
        // Perform matrix multiplication
        float x = (m00 * vector.x) + (m01 * vector.y);
        float y = (m10 * vector.x) + (m11 * vector.y);

        return new Vector2(x, y);
    }

}
