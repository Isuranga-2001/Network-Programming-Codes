package Lecture5;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class MultiThreadCallableTCPServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            // create executor service with fixed thread pool
            ExecutorService executor = Executors.newFixedThreadPool(2);

            Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(2000); // simulate long-running computation
                    return 42;
                }
            };

            // submit the callable value to the executor
            Future<Integer> futureObj = executor.submit(callable);

            // do other work while the Callable is running
            System.out.println("Doing other work....");

            try {
                // retrieve the result of the computation
                Integer result = futureObj.get(); // this will block until the result is available
                System.out.println("Result from callable" + result);
            }
            catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
            finally {
                // shutdown the executor service
                executor.shutdown();
            }

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
