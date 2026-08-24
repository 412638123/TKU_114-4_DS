@SuppressWarnings("unchecked")
class DynamicArray<T> {
    private Object[] data;
    private int size;

    public DynamicArray() {
        this(4);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = 4;
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    private void ensureCapacity() {
        if (size >= data.length) {
            int newCapacity = data.length * 2;
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    public void add(T value) {
        ensureCapacity();
        data[size++] = value;
    }

    public void add(int index, T value) {
        if (index < 0 || index > size) {
            return;
        }
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return (T) data[index];
    }

    public T set(int index, T value) {
        if (index < 0 || index >= size) {
            return null;
        }
        T oldValue = (T) data[index];
        data[index] = value;
        return oldValue;
    }

    public T remove(int index) {
        if (index < 0 || index >= size || size == 0) {
            return null;
        }
        T removedValue = (T) data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
        return removedValue;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

public class DynamicArrayPractice {
    public static void main(String[] args) {
        System.out.println("=== 1. DynamicArray<String> 測試 (初始容量 2) ===");
        DynamicArray<String> strArr = new DynamicArray<>(2);
        System.out.println("初始 - size: " + strArr.size() + ", capacity: " + strArr.capacity());

        strArr.add("Java");
        strArr.add("Python");
        System.out.println("加入 2 個元素後 - " + strArr + " | size: " + strArr.size() + ", capacity: " + strArr.capacity());

        // 觸發兩倍擴容
        strArr.add("C++");
        System.out.println("觸發擴容後 - " + strArr + " | size: " + strArr.size() + ", capacity: " + strArr.capacity());

        strArr.add(1, "Go");
        System.out.println("在 index 1 插入 'Go' - " + strArr + " | size: " + strArr.size());

        System.out.println("get(2): " + strArr.get(2));
        System.out.println("set(2, 'Rust') 舊值: " + strArr.set(2, "Rust"));
        System.out.println("修改後 - " + strArr);

        System.out.println("remove(1) 移除值: " + strArr.remove(1));
        System.out.println("移除後 - " + strArr + " | size: " + strArr.size());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 2. DynamicArray<Integer> 測試 ===");
        DynamicArray<Integer> intArr = new DynamicArray<>(3);
        intArr.add(10);
        intArr.add(20);
        intArr.add(30);
        System.out.println("Integer 陣列 - " + intArr + " | size: " + intArr.size() + ", capacity: " + intArr.capacity());

        System.out.println("\n----------------------------------------\n");

        System.out.println("=== 3. 邊界與異常條件測試 ===");
        DynamicArray<String> emptyArr = new DynamicArray<>();
        
        System.out.println("空結構刪除 remove(0): " + emptyArr.remove(0));
        System.out.println("測試 index -1 get(-1): " + strArr.get(-1));
        System.out.println("測試 index -1 set(-1, 'X'): " + strArr.set(-1, "X"));
        System.out.println("測試 index -1 remove(-1): " + strArr.remove(-1));
        System.out.println("測試 index == size 刪除 remove(strArr.size()): " + strArr.remove(strArr.size()));
        
        strArr.add(-1, "Invalid");
        System.out.println("測試非法 index -1 插入，內容不變: " + strArr);
    }
}