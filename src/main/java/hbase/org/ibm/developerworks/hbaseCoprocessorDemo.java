package hbase.org.ibm.developerworks;

import hbase.org.ibm.developerworks.getRowCount.getRowCountRequest;
import hbase.org.ibm.developerworks.getRowCount.getRowCountResponse;
import hbase.org.ibm.developerworks.getRowCount.ibmDeveloperWorksService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;
import org.apache.hadoop.hbase.ipc.CoprocessorRpcChannel;
import org.apache.hadoop.hbase.ipc.ServerRpcController;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * demo program to show how to program with HBase coprocessor
 */
public class hbaseCoprocessorDemo {
    void createTable(String tableName) {
        try {
            Configuration config = new Configuration();
            HBaseAdmin admin = new HBaseAdmin(config);
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            if (admin.tableExists(tableName) == true) {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            }
            tableDesc.addFamily(new HColumnDescriptor("c1")); //add column family
            tableDesc.addCoprocessor("hbase.org.ibm.developerworks.coprocessor.RowCountObserver");
            tableDesc.addCoprocessor("hbase.org.ibm.developerworks.coprocessor.RowCountEndpoint");
            admin.createTable(tableDesc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void populateTenRows(String tableName, int rowCount) {
        try {
            Configuration config = new Configuration();
            HConnection conn = HConnectionManager.createConnection(config);
            HTableInterface tbl = conn.getTable(tableName);
            //insert 1000
            for (int i = 0; i < 1000; i++) {
                String rowkey = "r" + Integer.toString(i);
                Put put = new Put(rowkey.getBytes());
                put.add("c1".getBytes(), "col1".getBytes(), "v".getBytes());
                tbl.put(put);
            }
            for (int i = 0; i < 1000 - rowCount; i++) {
                String rowkey = "r" + Integer.toString(i);
                Delete d = new Delete(rowkey.getBytes());
                tbl.delete(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    long getTableRowCountBatch(String tableName) {
        long totalRowCount = 0;
        Map<byte[], getRowCountResponse> results = null;
        try {
            Configuration config = new Configuration();
            HConnection connection = HConnectionManager.createConnection(config);
            HTableInterface table = connection.getTable(tableName);

            hbase.org.ibm.developerworks.getRowCount.getRowCountRequest.Builder builder = getRowCountRequest.newBuilder();
            builder.setReCount(false);

            byte[] s = Bytes.toBytes("r1");
            byte[] e = Bytes.toBytes("t1");
            results = table.batchCoprocessorService(ibmDeveloperWorksService.getDescriptor().findMethodByName("getRowCount"), builder.build(), s, e, getRowCountResponse.getDefaultInstance()); //, callback);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            ;
        }
        Collection<getRowCountResponse> resultsc = results.values();
        for (getRowCountResponse r : resultsc) {
            totalRowCount += r.getRowCount();
        }

        return totalRowCount;
    }

    //todo Table.coprocessorService(Class, byte[], byte[], Batch.Call, Batch.Callback)
    long getTableRowCountFast(String tableName) {
        System.out.println("getTableRowCountFast invoked for " + tableName);
        //todo 定义总的 rowCount 变量
        final AtomicLong totalRowCount = new AtomicLong();
        try {
            Configuration config = new Configuration();
            HConnection connection = HConnectionManager.createConnection(config);
            HTableInterface table = connection.getTable(tableName);


            Batch.Call<ibmDeveloperWorksService, getRowCountResponse> callable =
                    new Batch.Call<ibmDeveloperWorksService, getRowCountResponse>() {
                        ServerRpcController controller = new ServerRpcController();
                        BlockingRpcCallback<getRowCountResponse> rpcCallback =
                                new BlockingRpcCallback<getRowCountResponse>();

                        public getRowCountResponse call(ibmDeveloperWorksService instance) throws IOException {
                            hbase.org.ibm.developerworks.getRowCount.getRowCountRequest.Builder builder = getRowCountRequest.newBuilder();
                            builder.setReCount(false);
                            instance.getRowCount(controller, builder.build(), rpcCallback);
                            return rpcCallback.get();
                        }
                    };
            Batch.Callback<getRowCountResponse> callback =
                    new Batch.Callback<getRowCountResponse>() {

                        public void update(byte[] region, byte[] row, getRowCountResponse result) {
                            //todo 直接将 Batch.Call 的结果，即单个 region 的 rowCount 累加到 totalRowCount
                            totalRowCount.getAndAdd(result.getRowCount());
                        }
                    };

            table.coprocessorService(ibmDeveloperWorksService.class, null, null, callable, callback);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            ;
        }
        return totalRowCount.get();
    }


    //todo Table.coprocessorService(Class, byte[], byte[],Batch.Call),
    long getTableRowCountSlow(String tableName) {
        System.out.println("getTableRowCountSlow invoked for " + tableName);
        Map<byte[], getRowCountResponse> results = null;
        try {
            Configuration config = new Configuration();
            HConnection connection = HConnectionManager.createConnection(config);
            //todo 获取table实例
            HTableInterface table = connection.getTable(tableName);

            Batch.Call<ibmDeveloperWorksService, getRowCountResponse> callable =
                    new Batch.Call<ibmDeveloperWorksService, getRowCountResponse>() {

                        ServerRpcController controller = new ServerRpcController();
                        //todo 初始化 RPC 的入口参数，设置 reCount 为 true
                        //todo Server 端会进行慢速的遍历 region 的方法进行统计
                        BlockingRpcCallback<getRowCountResponse> rpcCallback =
                                new BlockingRpcCallback<getRowCountResponse>();

                        //todo 重载call方法
                        public getRowCountResponse call(ibmDeveloperWorksService instance) throws IOException {
                            hbase.org.ibm.developerworks.getRowCount.getRowCountRequest.Builder builder = getRowCountRequest.newBuilder();
                            builder.setReCount(true);
                            //todo  RPC调用
                            instance.getRowCount(controller, builder.build(), rpcCallback);
                            //todo 直接返回结果，即该 Region 的 rowCount
                            return rpcCallback.get();
                        }
                    };
            //todo 调用table.coprocessorService startRow和EndRow都为null，也就是意味着调用table上的所有region上的协处理器
            results = table.coprocessorService(ibmDeveloperWorksService.class, null, null,
                    callable);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            ;
        }

        long totalRowCount = 0;
        Collection<getRowCountResponse> resultsc = results.values();
        for (getRowCountResponse r : resultsc) {
            totalRowCount += r.getRowCount();
        }

        return totalRowCount;
    }

    long wholeTableRegionCount(String tableName, boolean fastMethod) {
        if (fastMethod == false) {
            return getTableRowCountSlow(tableName);
            //p.s. 没有使用batch方法，读者可以自己修改这行代码，进行测试。注释掉上面一行，使用下面这行代码即可。
            //return getTableRowCountBatch(tableName);
        } else
            return getTableRowCountFast(tableName);
    }

    //todo  获取单个 Region 的 rowcount
    //todo Table.coprocessorService(byte[])
    long singleRegionCount(String tableName, String rowkey, boolean reCount) {
        long rowcount = 0;
        try {
            Configuration config = new Configuration();
            HConnection conn = HConnectionManager.createConnection(config);
            HTableInterface tbl = conn.getTable(tableName);
            //todo 获取 Channel 该RPC 通道连接到由 rowkey 指定的 Region上通过这个通道就可以调用该Region上部署的协处理器 RPC。
            CoprocessorRpcChannel channel = tbl.coprocessorService(rowkey.getBytes());
            getRowCount.ibmDeveloperWorksService.BlockingInterface service = getRowCount.ibmDeveloperWorksService.newBlockingStub(channel);
            //todo 设置 RPC 入口参数
            getRowCount.getRowCountRequest.Builder request = getRowCount.getRowCountRequest.newBuilder();
            request.setReCount(reCount);

            //todo 调用 RPC
            getRowCount.getRowCountResponse ret = service.getRowCount(null, request.build());
            //todo 解析结果
            rowcount = ret.getRowCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowcount;
    }

    public static void main(String[] args) {
        System.out.println("Hello HBase Coprocessor!");
        int argsize = args.length;
        System.out.println("input " + argsize + " arguments");
        if (argsize < 3) return;
        String tblName = args[0];
        boolean fastMethod = (Integer.parseInt(args[1]) == 1) ? true : false;
        int num = Integer.parseInt(args[2]);
        String userRowKey = "r900"; //default one
        boolean wholeTableCount = true;
        if (argsize == 4) {
            userRowKey = args[3];
            wholeTableCount = false;
        }
        hbaseCoprocessorDemo demo = new hbaseCoprocessorDemo();
        demo.createTable(tblName);
        demo.populateTenRows(tblName, num);
        if (wholeTableCount) {
            long wholeCount = demo.wholeTableRegionCount(tblName, fastMethod);
            System.out.println("Get whole table count : " + wholeCount);
        } else {
            long singleCount = demo.singleRegionCount(tblName, userRowKey, fastMethod);
            System.out.println("Get single count: " + singleCount);
        }
        System.out.println("bye!");
    }
}
