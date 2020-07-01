package callback;

public class MyWorker {
    public static void callBackMethod(MyCallBackService callBack, String task) throws Exception {
        System.out.println("worker>>"+callBack.getClass()+"发布的任务--》"+task);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //throw new Exception("断电了");
        System.out.println("worker>>我干完了");
        String result="我干完了";
        callBack.resolveCallBackResult(result);
    }

    public static void noCallBackMethod(String task){
        System.out.println("worker>>发布的任务--》"+task);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("worker>>我干完了");
        //String result="我干完了";
        //callBack.getResult(result);
    }
}
