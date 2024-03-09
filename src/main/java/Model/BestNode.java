package Model;

public class BestNode<T extends KDTreeNode>
{
    public T node;
    public double distance;

    public BestNode()
    {
        this(null, Double.MAX_VALUE);
    }

    public BestNode(T n, double d)
    {
        node = n;
        distance = d;
    }
}
