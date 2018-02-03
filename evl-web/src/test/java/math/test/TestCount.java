//package math.test;
//
//import java.util.concurrent.RecursiveAction;
//
//import com.tmser.schevaluation.evl.utils.ResourceConfig;
//import com.tmser.schevaluation.utils.StringUtils;
//
//public class TestCount extends RecursiveAction{
//	 
//    /**
//	 * <pre>
//	 *
//	 * </pre>
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private static final int THRESHOLD = 2;
//     
//    private int start;
//     
//    private int end;
//    
//    //private static AtomicInteger count = new AtomicInteger(0);
//    int count=0;
//	private ResourceConfig properties;
//     
//    public TestCount(int start, int end,ResourceConfig properties) {
//        this.start = start;
//        this.end = end;
//        this.properties = properties;
//    }
// 
//    @Override
//    protected void compute() {
//        boolean canCompute = (end - start) <= THRESHOLD;
//        if (canCompute) {
//			try {
//				Thread.sleep(100);
//				System.err.println("\n------------------------\n");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//            for (int i = start; i <= end; i++){
//            	//System.out.println(i);
//            	//count.incrementAndGet();
//            	String countStr=properties.getProperty("ForkJoinPool-1-worker-1", "0");
//            	if(!StringUtils.isBlank(countStr)){
//            		count=Integer.parseInt(countStr);
//            	}
//            	count++;
//            	System.out.println(Thread.currentThread().getName()+ "  "+  count);
//            }
//        } else {
//            //如果任务大于阀值，就分裂成两个子任务计算
//            int mid = (start + end) / 2;
//            TestCount leftTask = new TestCount(start, mid, properties);
//            TestCount rightTask = new TestCount(mid+1, end,properties);
//             
//            //执行子任务
//            leftTask.fork();
//            rightTask.fork();
//        }
//         
//    }
//}
