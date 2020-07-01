package mythread;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestCompletionService {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();

        //开启5个线程
        ExecutorService exs = Executors.newFixedThreadPool(5);
        try{
            int taskCount = 20;
            List<Integer> list = new ArrayList<>();
            List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

            //1.定义completionService
            //CompletionService的实现目标是任务先完成可优先获取到，即结果按照完成先后顺序排序。
            CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(exs);

            //2.添加任务
            for(int i =0 ; i < taskCount; i++){
                Future<Integer> future = completionService.submit(new MyTask(i+1));
                futureList.add(future);
            }

            //3、获取结果
            for(int i=0; i < taskCount; i++){
                Integer result = completionService.take().get();
                System.out.println("任务i=="+result+"完成!"+new Date());
                list.add(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            exs.shutdown();
        }
    }

    static class MyTask implements Callable<Integer>{
        Integer i;

        public MyTask(Integer i){
            super();
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            if(i==5){
                Thread.sleep(5000);
            }else{
                Thread.sleep(1000);
            }
            System.out.println("线程："+Thread.currentThread().getName()+"任务i="+i+",执行完成！");
            return i;
        }
    }
}
