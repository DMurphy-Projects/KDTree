import java.util.Arrays;
import Model.Node;

public class KDTreeTest {

    int INDEX = 0;

    public void swap(double[][] list, int i, int j)
    {
        double[] temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    public int partition(double[][] list, int low, int high, int axis)
    {
        double pivot = list[high][axis];
        int i = (low - 1);

        for(int j = low; j <= high - 1; j++) {
            if (list[j][axis] < pivot) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return (i + 1);
    }

    public void quickSort(double[][] list, int low, int high, int axis)
    {
        if (low < high) {
            int pi = partition(list, low, high, axis);

            quickSort(list, low, pi - 1, axis);
            quickSort(list, pi + 1, high, axis);
        }
    }

    public double[][] sublist(double[][] list, int start, int end)
    {
        double[][] sub = new double[end - start][];

        for (int i=0;i<sub.length;i++)
        {
            sub[i] = list[start + i];
        }

        return sub;
    }

    public KDTreeNode createKDTree(double[][] list, int low, int high, int depth)
    {
        int length = high - low;
        if (length == 0) return null;

        int axis = depth % list[0].length;
        if (length == 1)
        {
            //create leaf
            return new KDTreeNode(INDEX++, list[low], axis, null, null);
        }

        quickSort(list, low, high, axis);

        int mid = (low + high) / 2;
        return new KDTreeNode(INDEX++, list[mid], axis,
                createKDTree(list, low, mid, depth+1),
                createKDTree(list, mid, high, depth+1));
    }

    public double[][] createTestData(int width, int height)
    {
        double[][] data = new double[width*height][];

        for (int w=0;w<width;w++)
        {
            for (int h=0;h<height;h++)
            {
                data[w * height + h] = new double[]{w, h};
            }
        }

        return data;
    }

    public static void main(String[] args)
    {
        KDTreeTest test = new KDTreeTest();

        double[][] points = test.createTestData(100, 100);

        double[][] treePoints = points;
        long t1 = System.currentTimeMillis();
        KDTreeNode tree = test.createKDTree(treePoints, 0, treePoints.length-1, 0);
//        tree.printTree("Start");

        long t2 = System.currentTimeMillis();
        KDTreeNode.BestNode bestNode = new KDTreeNode.BestNode(null, Double.MAX_VALUE);
        tree.nearest(new double[]{50, 50}, bestNode);
        long t3 = System.currentTimeMillis();

        System.out.println(Arrays.toString(bestNode.node.point));

        System.out.println(String.format("Size of Tree: %s", treePoints.length));
        System.out.println(String.format("Tree Creation: %s", t2 - t1));
        System.out.println(String.format("Tree Nearest Search: %s", t3 - t2));
    }
}
