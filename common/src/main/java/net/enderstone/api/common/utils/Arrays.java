package net.enderstone.api.common.utils;

public class Arrays {

    /**
     * Uses "for i" loop to loop over array and find the index of the given value.
     * Returns -1 if the value was not found in the given array
     */
    public static int findIndex(final Object[] array, final Object value) {
        if(array == null || array.length == 0) return -1;

        for(int i = 0; i < array.length; i++) {
            if(array[i] == null && value == null) return i;
            if(array[i] == null) continue;
            if(array[i].equals(value)) return i;
        }
        return -1;
    }

    /**
     * Copies src array to dest array whilst discarding all items within the selected range.<br>
     * <br>
     * rangeCopy(src, dest, 1, 2); <br>
     * src (0 | 1 | 2 | 3) -> dest (0 | 3) <br>
     * <br>
     * Deletes 2 items starting at index 1
     */
    public static void rangeCopy(Object[] src, Object[] dest, int start, int length) {
        if(start > 0) {
            System.arraycopy(src, 0, dest, 0, start);
        }
        if(start + length != src.length) {
            System.arraycopy(src, start + length, dest, start, src.length - (start + length));
        }
    }

}
