package Helper;

import Model.KDTreeNode;

public class KDTreeHelper extends TreeHelper {
    protected KDTreeNode createNode(int index, double[] p, int axis, KDTreeNode left, KDTreeNode right) {
        return new KDTreeNode(index, p, axis, left, right);
    }
}
