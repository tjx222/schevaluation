<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="java.lang.management.RuntimeMXBean" %>
<%@ page import="java.lang.management.OperatingSystemMXBean" %>
<%@ page import="java.lang.management.ThreadInfo" %>
<%@ page import="java.lang.management.ThreadMXBean" %>
<div style="line-height:1.5em;font-size:12px;margin:5px;overflow:auto;" layoutH="20">
<%
long start = System.currentTimeMillis();
ManagementFactory.getMemoryMXBean().gc();
RuntimeMXBean rt =ManagementFactory.getRuntimeMXBean();
OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
%>
<h1>Java 虚拟机信息</h1>

<b>名称</b>: <%=rt.getVmName()%><br>
<b>版本</b>: <%=rt.getVmVersion()%><br>
<b>提供商</b>: <%=rt.getVmVendor()%><br>
<b>运行时间</b>: <%=((float)rt.getUptime())/(1000*60*60)%> hours<br>
<b>参数</b>: <%=rt.getInputArguments()%><br>
<b>库地址</b>: <%=rt.getLibraryPath()%><br>
<b>Class Path</b>: <%=rt.getClassPath()%><br><hr>
<h1>JVM 操作系统信息</h1>

<b>系统名称: </b><%=os.getName()%><br>
<b>系统版本: </b><%=os.getVersion()%><br>
<b>处理器个数: </b><%=os.getAvailableProcessors()%><br>
<b>系统架构: </b><%=os.getArch()%><br>

<h1>JVM 内存信息</h1>
<b>初始内存: </b><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit()/1000000%>M<br>
<b>最大内存: </b><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()/1000000%>M<br>
<b>已用内存: </b><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/1000000%>M<br>
<hr>
<h1>JVM 线程信息</h1>
<%
ThreadMXBean tm = ManagementFactory.getThreadMXBean();
tm.setThreadContentionMonitoringEnabled(true);
%>
<b>线程总数: </b><%=tm.getThreadCount()%><br>
<b>正在运行线程数: </b><%=tm.getTotalStartedThreadCount()%><br>
<b>thread contention monitoring is enabled? </b><%=tm.isThreadContentionMonitoringEnabled()%><br>
<b>if the Java virtual machine supports thread contention monitoring? </b><%=tm.isThreadContentionMonitoringSupported()%><br>
<b>thread CPU time measurement is enabled? </b><%=tm.isThreadCpuTimeEnabled()%><br>
<b>if the Java virtual machine implementation supports CPU time measurement for any thread? </b><%=tm.isThreadCpuTimeSupported()%><br>
<hr>
<%
long [] tid = tm.getAllThreadIds();
ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);

long [][] threadArray = new long[tia.length][2];

for (int i = 0; i < tia.length; i++) {          
    long threadId = tia[i].getThreadId();

    long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
    threadArray[i][0] = threadId;
    threadArray[i][1] = cpuTime;
}

long [] temp = new long[2];
for (int j = 0; j < threadArray.length - 1; j ++){
	for (int k = j + 1; k < threadArray.length; k++ )
    if (threadArray[j][1] < threadArray[k][1]){
        temp = threadArray[j];
        threadArray[j] = threadArray[k];
        threadArray[k] = temp;  
    }
}

for (int t = 0; t < threadArray.length; t ++)
{
  ThreadInfo ti = tm.getThreadInfo(threadArray[t][0],Integer.MAX_VALUE);
  if (ti == null) continue;
%>
<b>线程 ID: </b><%=threadArray[t][0]%><br>
<b>线程名称: </b><%=ti.getThreadName()%><br>
<b>线程状态: </b><%=ti.getThreadState()%><br>
<b>线程锁名称: </b><%=ti.getLockName()%><br>
<b>线程锁所有者名称: </b><%=ti.getLockOwnerName()%><br>
<b>线程使用CPU时间: </b><%=threadArray[t][1]%> sec<br>
<b>堆栈信息: (depth:<%=ti.getStackTrace().length%>)</b><br>
<%
StackTraceElement[] stes = ti.getStackTrace();
for(int j=0; j<stes.length; j++)
{
  StackTraceElement ste = stes[j];
%>
&nbsp;&nbsp;&nbsp;&nbsp;+<%=ste%><br>
<%
}
%>
<hr>
<%
}
%>
<h1> 获取统计信息消耗时间：  <%=System.currentTimeMillis()-start%> </h1>
</div>
