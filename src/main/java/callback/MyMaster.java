package callback;

public class MyMaster implements MyCallBackService {

    public void sendMessage(final String task){
        System.out.println("master>>发布任务："+task);
        new Thread(()-> {
            try {
                MyWorker.callBackMethod(MyMaster.this,task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        doMyThing();
    }

    public void doMyThing(){
        System.out.println("master>>干我自己的活");
    }

    /**
     * 获取worker执行结果
     * @param message
     */
    @Override
    public void resolveCallBackResult(String message) {
        System.out.println("master>>获取worker执行结果："+message);
    }
}
