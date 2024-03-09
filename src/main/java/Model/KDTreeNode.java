package Model;

import java.util.Arrays;

public class KDTreeNode<T extends KDTreeNode> extends Node<Edge<T>> {

    double[] point;
    int axis, LEFT = 0, RIGHT = 1;

    public KDTreeNode(int index, double[] p, int a, KDTreeNode l, KDTreeNode r) {
        super(index);

        point = p;
        axis = a;

        connect(new Edge(this, l));
        connect(new Edge(this, r));
    }

    public void nearest(double[] search, BestNode bestNode)
    {
        double distance = distance(search);
        if (bestNode.distance > distance) {
            bestNode.node = this;
            bestNode.distance = distance;
        }
        if (bestNode.distance == 0) return;

        double d = point[axis] - search[axis];

        KDTreeNode first;
        if (d > 0) { first = left(); } else { first = right(); }
        if (first != null) first.nearest(search, bestNode);

        if (d * d > bestNode.distance) return;

        if (d > 0) { first = right(); } else { first = left(); }
        if (first != null) first.nearest(search, bestNode);
    }

    public double distance(double[] p)
    {
        double d = 0;
        for (int i=0;i<point.length;i++)
        {
            d += (point[i] - p[i]) * (point[i] - p[i]);
        }
        return d;
    }

    public boolean isLeaf()
    {
        return left() == null && right() == null;
    }

    public double getValue()
    {
        return point[axis];
    }

    public double[] getPoint()
    {
        return point;
    }

    public T left()
    {
        return getEdges().get(LEFT).getTo();
    }

    public T right()
    {
        return getEdges().get(RIGHT).getTo();
    }

    public void printTree(String prefix)
    {
        System.out.println(String.format("%s %s", prefix, Arrays.toString(point)));
        if (left() != null) left().printTree("Left");
        if (right() != null) right().printTree("Right");
    }
}
