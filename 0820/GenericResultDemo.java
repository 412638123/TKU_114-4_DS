class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, "Operation successful", data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        if (success) {
            return "成功 [訊息: " + message + ", 資料: " + data + "]";
        } else {
            return "失敗 [訊息: " + message + ", 資料: " + data + "]";
        }
    }
}

public class GenericResultDemo {
    public static void main(String[] args) {
        Result<String> stringSuccess = Result.success("Hello Java Generics!", "取得字串成功");
        Result<String> stringFailure = Result.failure("查無使用者資料");

        String stringData = stringSuccess.getData();
        System.out.println("【String 成功案例】" + stringSuccess);
        System.out.println("取出資料內容：" + stringData);
        System.out.println("【String 失敗案例】" + stringFailure);

        System.out.println("\n----------------------------------------\n");

        Result<Integer> intSuccess = Result.success(200, "狀態碼獲取成功");
        Result<Integer> intFailure = Result.failure("計算過程發生除以零錯誤");

        Integer intData = intSuccess.getData();
        System.out.println("【Integer 成功案例】" + intSuccess);
        System.out.println("取出資料內容：" + intData);
        System.out.println("【Integer 失敗案例】" + intFailure);
    }
}