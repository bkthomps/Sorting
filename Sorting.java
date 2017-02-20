/*
 * Bailey Thompson
 * Sorting (1.2.1)
 * 20 February 2017
 */

import java.util.Arrays;
import javax.swing.JOptionPane;

import static java.lang.Integer.parseInt;

/**
 * Sorts a random array of numbers.
 */
class Sorting {

    private static final String PROGRAM_NAME = "Sorting";

    private int minNumber, maxNumber, sortingType;
    private int[] randomNumbers;
    private Integer[] randomNumbersMerge;

    public static void main(String[] args) {
        Sorting sort = new Sorting();
        sort.sort();
    }

    private void sort() {
        final String[] BUTTONS = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"};
        sortingType = JOptionPane.showOptionDialog(null, "Please pick your sorting mechanism.", PROGRAM_NAME,
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, BUTTONS, BUTTONS[2]);
        sortingType++;
        if (sortingType == 0) {
            System.exit(0);
        }
        final int SIZE_OF_ARRAY = userInput();
        randomArrayFill(SIZE_OF_ARRAY);
        final long START_TIME = System.nanoTime();
        switch (sortingType) {
            case 1:
                bubbleSort(SIZE_OF_ARRAY);
                break;
            case 2:
                selectionSort(SIZE_OF_ARRAY);
                break;
            case 3:
                insertionSort(SIZE_OF_ARRAY);
                break;
            case 4:
                mergeSort(randomNumbersMerge);
                break;
            case 5:
                final int LOW_QUICK_SORT = 0;
                final int HIGH_QUICK_SORT = randomNumbers.length - 1;
                quickSort(randomNumbers, LOW_QUICK_SORT, HIGH_QUICK_SORT);
                break;
        }
        final int NANOSECONDS_PER_SECOND = 1000000000;
        final long TOTAL_TIME = (System.nanoTime() - START_TIME) / NANOSECONDS_PER_SECOND;
        final int HOURS = (int) Math.floor(TOTAL_TIME / 3600);
        final int MINUTES = (int) Math.floor((TOTAL_TIME - HOURS * 3600) / 60);
        final int SECONDS = (int) Math.floor(TOTAL_TIME - MINUTES * 60);
        output(SIZE_OF_ARRAY, HOURS, MINUTES, SECONDS);
    }

    private int userInput() {
        final int SIZE_OF_ARRAY = userInputNumber("Please input the size of the array.\nSizes must be a real positive "
                + "integer value.", true);
        if (sortingType == 4) {
            randomNumbersMerge = new Integer[SIZE_OF_ARRAY];
        } else {
            randomNumbers = new int[SIZE_OF_ARRAY];
        }
        minNumber = userInputNumber("Please input the minimum value.\nValue must be a real integer value.", false);
        maxNumber = userInputNumber("Please input the maximum value.\nValue must be a real integer value.", false);
        maxLessThanMin();
        return SIZE_OF_ARRAY;
    }

    private int userInputNumber(String message, boolean mustBePositive) {
        String word, tempWord;
        do {
            word = userInput(message);
            checkEmpty(word);
            tempWord = word;
            if (!mustBePositive && tempWord.charAt(0) == '-') {
                tempWord = tempWord.substring(1);
            }
            tempWord = tempWord.replaceAll("[0123456789]", "");
        } while (!"".equals(tempWord));
        return parseInt(word);
    }

    private void maxLessThanMin() {
        while (maxNumber < minNumber) {
            maxNumber = userInputNumber("Please input the maximum value.\nValue must be a real integer value.\nMaximum "
                    + "value also cannot be less than minimum value.", false);
        }
    }

    private void randomArrayFill(int sizeOfArray) {
        for (int i = 0; i < sizeOfArray; i++) {
            if (sortingType == 4) {
                randomNumbersMerge[i] = ((int) (Math.random() * (maxNumber - minNumber + 1))) + minNumber;
            } else {
                randomNumbers[i] = ((int) (Math.random() * (maxNumber - minNumber + 1))) + minNumber;
            }
        }
    }

    private void bubbleSort(int sizeOfArray) {
        for (int i = 0; i < sizeOfArray; i++) {
            for (int j = 1; j < (sizeOfArray - i); j++) {
                if (randomNumbers[j - 1] > randomNumbers[j]) {
                    final int TEMP = randomNumbers[j - 1];
                    randomNumbers[j - 1] = randomNumbers[j];
                    randomNumbers[j] = TEMP;
                }
            }
        }
    }

    private void selectionSort(int sizeOfArray) {
        int temp;
        for (int i = 0; i < sizeOfArray - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < sizeOfArray; j++) {
                if (randomNumbers[minIndex] > randomNumbers[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                temp = randomNumbers[i];
                randomNumbers[i] = randomNumbers[minIndex];
                randomNumbers[minIndex] = temp;
            }
        }
    }

    private void insertionSort(int sizeOfArray) {
        for (int i = 1; i < sizeOfArray; i++) {
            final int NEW_VALUE = randomNumbers[i];
            int j;
            for (j = i; j > 0 && randomNumbers[j - 1] > NEW_VALUE; j--) {
                randomNumbers[j] = randomNumbers[j - 1];
            }
            randomNumbers[j] = NEW_VALUE;
        }
    }

    private static void mergeSort(Comparable[] randomNumbersMerge) {
        final Comparable[] TEMP = new Comparable[randomNumbersMerge.length];
        mergeSort(randomNumbersMerge, TEMP, 0, randomNumbersMerge.length - 1);
    }

    private static void mergeSort(Comparable[] randomNumbersMerge, Comparable[] temp, int left, int right) {
        if (left < right) {
            final int CENTER = (left + right) / 2;
            mergeSort(randomNumbersMerge, temp, left, CENTER);
            mergeSort(randomNumbersMerge, temp, CENTER + 1, right);
            merge(randomNumbersMerge, temp, left, CENTER + 1, right);
        }
    }

    private static void merge(Comparable[] randomNumbersMerge, Comparable[] tmp, int left, int right, int rightEnd) {
        final int LEFT_END = right - 1;
        final int NUM = rightEnd - left + 1;
        int k = left;

        while (left <= LEFT_END && right <= rightEnd) {
            if (randomNumbersMerge[left].compareTo(randomNumbersMerge[right]) <= 0) {
                tmp[k++] = randomNumbersMerge[left++];
            } else {
                tmp[k++] = randomNumbersMerge[right++];
            }
        }
        while (left <= LEFT_END) {
            tmp[k++] = randomNumbersMerge[left++];
        }
        while (right <= rightEnd) {
            tmp[k++] = randomNumbersMerge[right++];
        }
        for (int i = 0; i < NUM; i++, rightEnd--) {
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

        final int MIDDLE = lowQuickSort + (highQuickSort - lowQuickSort) / 2;
        final int PIVOT = arr[MIDDLE];

        // Make left < pivot and right > pivot.
        int i = lowQuickSort;
        int j = highQuickSort;
        while (i <= j) {
            while (arr[i] < PIVOT) {
                i++;
            }
            while (arr[j] > PIVOT) {
                j--;
            }
            if (i <= j) {
                final int TEMP = arr[i];
                arr[i] = arr[j];
                arr[j] = TEMP;
                i++;
                j--;
            }
        }

        // Recursively sort two sub parts.
        if (lowQuickSort < j) {
            quickSort(arr, lowQuickSort, j);
        }
        if (highQuickSort > i) {
            quickSort(arr, i, highQuickSort);
        }
    }

    private void output(int sizeOfArray, int hours, int minutes, int seconds) {
        if (sizeOfArray < 100 && sortingType != 4) {
            userConfirm("The Sorted Numbers Are: " + Arrays.toString(randomNumbers));
        } else if (sizeOfArray < 100 && sortingType == 4) {
            userConfirm("The Sorted Numbers Are: " + Arrays.toString(randomNumbersMerge));
        } else if (hours >= 1) {
            userConfirm("It took you:\n" + hours + " hours\n" + minutes + " minutes\n" + seconds + " seconds");
        } else if (minutes >= 1) {
            userConfirm("It took you:\n" + minutes + " minutes\n" + seconds + " seconds");
        } else {
            userConfirm("It took you:\n" + seconds + " seconds");
        }
    }

    private void checkEmpty(String word) {
        if (word == null) {
            System.exit(0);
        }
    }

    private String userInput(String message) {
        return JOptionPane.showInputDialog(null, message, PROGRAM_NAME, JOptionPane.PLAIN_MESSAGE);
    }

    private void userConfirm(String message) {
        JOptionPane.showConfirmDialog(null, message, PROGRAM_NAME, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }
}
