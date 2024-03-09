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

    public static void testFullLookup(int w, int h)
    {
        KDTreeTest test = new KDTreeTest();

        double[][] points = test.createTestData(w, h);

        long t1 = System.currentTimeMillis();
        KDTreeNode tree = test.createKDTree(points);
//        tree.printTree("Start");

        long t2 = System.currentTimeMillis();
        for (int i=0;i<w;i++)
        {
            for (int j=0;j<h;j++)
            {
                KDTreeNode.BestNode bestNode = new KDTreeNode.BestNode(null, Double.MAX_VALUE);
                tree.nearest(new double[]{i, j}, bestNode);

                if (bestNode.distance != 0)
                {
                    System.out.println(String.format("i: %s j: %s d: %s n: %s",
                            i, j, bestNode.distance, Arrays.toString(bestNode.node.point)));
                }
            }
        }

        long t3 = System.currentTimeMillis();

        System.out.println(String.format("Size of Tree: %s->%s", points.length, test.INDEX));
        System.out.println(String.format("Tree Creation: %s", t2 - t1));
        System.out.println(String.format("Tree Nearest Search: %s", t3 - t2));
    }

    public static void testSingleLookup(double[][] points, double[] lookup)
    {
        KDTreeTest test = new KDTreeTest();

        long t1 = System.currentTimeMillis();
        KDTreeNode tree = test.createKDTree(points);
//        tree.printTree("Start");

        long t2 = System.currentTimeMillis();
        KDTreeNode.BestNode bestNode = new KDTreeNode.BestNode(null, Double.MAX_VALUE);
        tree.nearest(lookup, bestNode);

        long t3 = System.currentTimeMillis();

        System.out.println(String.format("Best Match: %s", Arrays.toString(bestNode.node.point)));
        System.out.println(String.format("Size of Tree: %s->%s", points.length, test.INDEX));
        System.out.println(String.format("Tree Creation: %s", t2 - t1));
        System.out.println(String.format("Tree Nearest Search: %s", t3 - t2));
    }

    public static void testDuplicateData()
    {
        testSingleLookup(new double[][]{{0, 0}, {0, 0}, {0, 0}, {1, 1}, {1, 1}, {2, 2}},
                new double[]{2, 2});
    }

    public static void testSmallFullLookup()
    {
        testFullLookup(10, 10);
    }
    public static void testMediumFullLookup()
    {
        testFullLookup(100, 100);
    }
    public static void testLargeLookup()
    {
        testFullLookup(1000, 1000);
    }

    public static void main(String[] args)
    {
        testSmallFullLookup();
//        testMediumFullLookup();
//        testLargeLookup();
//        testDuplicateData();
    }
}
