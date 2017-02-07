/*
 * Bailey Thompson
 * Sorting (1.2.0)
 * 6 February 2017
 * Sorts a random array of numbers.
 */

import static java.lang.Integer.parseInt;

import java.util.Arrays;
import javax.swing.JOptionPane;

class Sorting {

    private int minNumber, maxNumber, sortingType;
    private int[] randomNumbers;
    private Integer[] randomNumbersMerge;

    public static void main(String[] args) {
        Sorting sort = new Sorting();
        sort.Sort();
    }

    private void Sort() {
        String[] buttons = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"};
        sortingType = JOptionPane.showOptionDialog(null, "Please pick your sorting mechanism.", "Sort Program",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[2]);
        sortingType += 1;
        if (sortingType == 0) {
            System.exit(0);
        }
        int sizeOfArray = UserInput();
        RandomArrayFill(sizeOfArray);
        long startTime = System.nanoTime();
        switch (sortingType) {
            case 1:
                BubbleSort(sizeOfArray);
                break;
            case 2:
                SelectionSort(sizeOfArray);
                break;
            case 3:
                InsertionSort(sizeOfArray);
                break;
            case 4:
                mergeSort(randomNumbersMerge);
                break;
            case 5:
                final int lowQuickSort = 0;
                final int highQuickSort = randomNumbers.length - 1;

                quickSort(randomNumbers, lowQuickSort, highQuickSort);
                break;
        }
        long totalTime = System.nanoTime() - startTime;
        totalTime /= 1000000000;
        int hours = (int) Math.floor(totalTime / 3600);
        int minutes = (int) Math.floor((totalTime - hours * 3600) / 60);
        int seconds = (int) Math.floor(totalTime - minutes * 60);
        Output(sizeOfArray, hours, minutes, seconds);
    }

    private int UserInput() {
        String word, tempWord;
        do {
            tempWord = JOptionPane.showInputDialog(null, "Please input the size of the array.\nSizes must be a "
                    + "real positive integer value.", "Sort Program", JOptionPane.PLAIN_MESSAGE);
            word = tempWord;
            CheckEmpty(word);
            tempWord = tempWord.replaceAll("[ 0123456789]", "");
        } while (!"".equals(tempWord) || "".equals(word));
        int sizeOfArray = parseInt(word);
        if ("4".equals(sortingType)) {
            randomNumbersMerge = new Integer[sizeOfArray];
        } else {
            randomNumbers = new int[sizeOfArray];
        }
        do {
            tempWord = JOptionPane.showInputDialog(null, "Please input the minimum value.\nValue must be a real "
                    + "integer value.", "Sort Program", JOptionPane.PLAIN_MESSAGE);
            word = tempWord;
            CheckEmpty(word);
            tempWord = tempWord.replaceAll("[ -0123456789]", "");
        } while (!"".equals(tempWord) || "".equals(word));
        minNumber = parseInt(word);
        do {
            tempWord = JOptionPane.showInputDialog(null, "Please input the maximum value.\nValue must be a real "
                    + "integer value.", "Sort Program", JOptionPane.PLAIN_MESSAGE);
            word = tempWord;
            CheckEmpty(word);
            tempWord = tempWord.replaceAll("[ -0123456789]", "");
        } while (!"".equals(tempWord) || "".equals(word));
        maxNumber = parseInt(word);
        while (minNumber > maxNumber || !"".equals(tempWord) && "".equals(word)) {
            tempWord = JOptionPane.showInputDialog(null, "Please input the maximum value.\nValue must be a real "
                            + "integer value.\nMaximum value also cannot be less than minimum value.", "Sort Program",
                    JOptionPane.PLAIN_MESSAGE);
            word = tempWord;
            CheckEmpty(word);
            tempWord = tempWord.replaceAll("[ -0123456789]", "");
            if (!"".equals(word)) {
                maxNumber = parseInt(word);
            }
        }
        return sizeOfArray;
    }

    private void RandomArrayFill(int sizeOfArray) {
        for (int counterFill = 0; counterFill < sizeOfArray; counterFill++) {
            if ("4".equals(sortingType)) {
                randomNumbersMerge[counterFill] = ((int) (Math.random() * (maxNumber - minNumber + 1))) + minNumber;
            } else {
                randomNumbers[counterFill] = ((int) (Math.random() * (maxNumber - minNumber + 1))) + minNumber;
            }
        }
    }

    private void BubbleSort(int sizeOfArray) {
        int temp;
        for (int i = 0; i < sizeOfArray; i++) {
            for (int j = 1; j < (sizeOfArray - i); j++) {
                if (randomNumbers[j - 1] > randomNumbers[j]) {
                    temp = randomNumbers[j - 1];
                    randomNumbers[j - 1] = randomNumbers[j];
                    randomNumbers[j] = temp;
                }
            }
        }
    }

    private void SelectionSort(int sizeOfArray) {
        int temp;
        for (int counter1 = 0; counter1 < sizeOfArray - 1; counter1++) {
            int minIndex = counter1;
            for (int counter2 = counter1 + 1; counter2 < sizeOfArray; counter2++) {
                if (randomNumbers[minIndex] > randomNumbers[counter2]) {
                    minIndex = counter2;
                }
            }
            if (minIndex != counter1) {
                temp = randomNumbers[counter1];
                randomNumbers[counter1] = randomNumbers[minIndex];
                randomNumbers[minIndex] = temp;
            }
        }
    }

    private void InsertionSort(int sizeOfArray) {
        int counter1, counter2, newValue;
        for (counter1 = 1; counter1 < sizeOfArray; counter1++) {
            newValue = randomNumbers[counter1];
            counter2 = counter1;
            while (counter2 > 0 && randomNumbers[counter2 - 1] > newValue) {
                randomNumbers[counter2] = randomNumbers[counter2 - 1];
                counter2--;
            }
            randomNumbers[counter2] = newValue;
        }
    }

    private static void mergeSort(Comparable[] randomNumbersMerge) {
        Comparable[] tmp = new Comparable[randomNumbersMerge.length];
        mergeSort(randomNumbersMerge, tmp, 0, randomNumbersMerge.length - 1);
    }

    private static void mergeSort(Comparable[] randomNumbersMerge, Comparable[] tmp, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(randomNumbersMerge, tmp, left, center);
            mergeSort(randomNumbersMerge, tmp, center + 1, right);
            merge(randomNumbersMerge, tmp, left, center + 1, right);
        }
    }

    private static void merge(Comparable[] randomNumbersMerge, Comparable[] tmp, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while (left <= leftEnd && right <= rightEnd) {
            if (randomNumbersMerge[left].compareTo(randomNumbersMerge[right]) <= 0) {
                tmp[k++] = randomNumbersMerge[left++];
            } else {
                tmp[k++] = randomNumbersMerge[right++];
            }
        }

        while (left <= leftEnd) //copy rest of first half
        {
            tmp[k++] = randomNumbersMerge[left++];
        }

        while (right <= rightEnd) //copy rest of right half
        {
            tmp[k++] = randomNumbersMerge[right++];
        }

        //copy tmp back
        for (int i = 0; i < num; i++, rightEnd--) {
            randomNumbersMerge[rightEnd] = tmp[rightEnd];
        }
    }

    private static void quickSort(int[] arr, int lowQuickSort, int highQuickSort) {
        if (arr == null || arr.length == 0) {
            return;
        }
        if (lowQuickSort >= highQuickSort) {
            return;
        }

        //pick the pivot
        int middle = lowQuickSort + (highQuickSort - lowQuickSort) / 2;
        int pivot = arr[middle];

        //make left < pivot and right > pivot
        int i = lowQuickSort, j = highQuickSort;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }

            while (arr[j] > pivot) {
                j--;
            }

            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        //recursively sort two sub parts
        if (lowQuickSort < j) {
            quickSort(arr, lowQuickSort, j);
        }
        if (highQuickSort > i) {
            quickSort(arr, i, highQuickSort);
        }
    }

    private void Output(int sizeOfArray, int hours, int minutes, int seconds) {
        if (sizeOfArray < 100 && !"4".equals(sortingType)) {
            JOptionPane.showConfirmDialog(null, "The Sorted Numbers Are: " + Arrays.toString(randomNumbers),
                    "Sort Program", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        } else if (sizeOfArray < 100 && "4".equals(sortingType)) {
            JOptionPane.showConfirmDialog(null, "The Sorted Numbers Are: " + Arrays.toString(randomNumbersMerge),
                    "Sort Program", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        } else if (hours >= 1) {
            JOptionPane.showConfirmDialog(null, "It took you:\n" + hours + " hours\n" + minutes + " minutes\n"
                    + seconds + " seconds", "Sort Program", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        } else if (minutes >= 1) {
            JOptionPane.showConfirmDialog(null, "It took you:\n" + minutes + " minutes\n" + seconds + " seconds",
                    "Sort Program", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showConfirmDialog(null, "It took you:\n" + seconds + " seconds", "Sort Program",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void CheckEmpty(String word) {
        if (word == null) {
            System.exit(0);
        }
    }
}
