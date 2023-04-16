/**
 * @auther:zfc
 * @Date:2023-04-16 19:48
 **/

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 1.多线程处于一直运行状态
 */
public class MyThreadPool {
    // 线程容器
    private List<WorkThread> threads;
    // 缓存线程任务
    private BlockingQueue<Runnable> queueRunnable;

    public MyThreadPool(int threadCount, int queueSize) {
        // 设置工作线程数量
        threads = new ArrayList<WorkThread>(threadCount);
        // 设置缓存任务容器大小
        queueRunnable = new LinkedBlockingQueue<Runnable>(queueSize);
        for (int i = 0; i < threadCount; i++) {
            new WorkThread().start();
        }

    }


    class WorkThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = queueRunnable.poll();
                if (runnable != null) {
                    runnable.run();
                }
            }
        }
    }

    public boolean execute(Runnable runnable) {
        return queueRunnable.offer(runnable);
    }

    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(2, 2);

        for (int i = 0; i < 10; i++) {
            final int fi = i;
            myThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " " + fi);
                }
            });
        }
    }
}
