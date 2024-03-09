package Helper;

public class QuickSort {

    public static void swap(double[][] list, int i, int j)
    {
        double[] temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    public static int partition(double[][] list, int low, int high, int axis)
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

    public static void sort(double[][] list, int low, int high, int axis)
    {
        if (low < high) {
            int pi = partition(list, low, high, axis);

            sort(list, low, pi - 1, axis);
            sort(list, pi + 1, high, axis);
        }
    }
}
