package Helper;

import Model.KDTreeNode;

public abstract class TreeHelper<T extends KDTreeNode> {

    int INDEX = 0;

    public T createKDTree(double[][] list)
    {
        return createKDTree(list, 0, list.length, 0);
    }

    public T createKDTree(double[][] list, int low, int high, int depth)
    {
        if (low >= high) return null;

        int axis = depth % list[0].length;
        QuickSort.sort(list, low, high-1, axis);

        int mid = (low + high) / 2;

        return createNode(INDEX++, list[mid], axis,
                createKDTree(list, low, mid, depth+1),
                createKDTree(list, mid+1, high, depth+1));
    }

    protected abstract T createNode(int index, double[] p, int axis, T left, T right);

}
