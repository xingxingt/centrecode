/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hbase.org.ibm.developerworks.coprocessor;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import hbase.org.ibm.developerworks.getRowCount;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.CoprocessorException;
import org.apache.hadoop.hbase.coprocessor.CoprocessorService;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.RegionServerServices;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.zookeeper.ZKUtil;
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RowCount endpoint协处理器  服务端代码
 * hbase版本: 0.98.11-hadoop2
 */
public class RowCountEndpoint extends getRowCount.ibmDeveloperWorksService
        implements Coprocessor, CoprocessorService {
    private RegionCoprocessorEnvironment env;
    private static final Log LOG = LogFactory.getLog(RowCountEndpoint.class);

    private String zNodePath = "/hbase/ibmdeveloperworks/demo";
    private ZooKeeperWatcher zkw = null;

    public RowCountEndpoint() {
    }

    /**
     * Just returns a reference to this object, which implements the RowCounterService interface.
     */
    public Service getService() {
        return this;
    }

    /**
     * Stores a reference to the coprocessor environment provided by the
     * {@link org.apache.hadoop.hbase.regionserver.RegionCoprocessorHost} from the region where this
     * coprocessor is loaded.  Since this is a coprocessor endpoint, it always expects to be loaded
     * on a table region, so always expects this to be an instance of
     * {@link RegionCoprocessorEnvironment}.
     *
     * @param "env" the environment provided by the coprocessor host
     * @throws IOException if the provided environment is not an instance of
     *                     {@code RegionCoprocessorEnvironment}
     */
    //todo 调用器 start 接口，完成初始化工作。一般的该接口函数中仅仅需要将协处理器的运行上下文环境变量
    //todo CoprocessorEnviorment保存到本地即可。
    //todo CoprocessorEnviorment 保存了协处理器的运行环境，每个协处理器都是在一个 RegionServer 进程内运行，
    //todo 并隶属于某个 Region。通过该变量，可以获取 Region 的实例等 HBase 的运行时环境对象。
    public void start(CoprocessorEnvironment envi) throws IOException {
        if (envi instanceof RegionCoprocessorEnvironment) {
            this.env = (RegionCoprocessorEnvironment) envi;
            RegionCoprocessorEnvironment re = (RegionCoprocessorEnvironment) envi;
            RegionServerServices rss = re.getRegionServerServices();
             //todo 获取 ZooKeeper 对象，这个 ZooKeeper 就是本 HBase 实例所连接的 ZooKeeper
            zkw = rss.getZooKeeper();
            zNodePath = zNodePath + re.getRegion().getRegionNameAsString();
            try {
                if (ZKUtil.checkExists(zkw, zNodePath) == -1) {
                    LOG.info("LIULIUMI: create znode :" + zNodePath);
                    ZKUtil.createWithParents(zkw, zNodePath);
                } else {
                    LOG.info("LIULIUMI: znode exist");
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }

        } else {
            throw new CoprocessorException("Must be loaded on a table region!");
        }
    }

    public void stop(CoprocessorEnvironment env) throws IOException {
        // nothing to do
    }

    @Override
    //todo endpoint 协处理器真正的业务代码
    public void getRowCount(RpcController controller, getRowCount.getRowCountRequest request,
                            RpcCallback<getRowCount.getRowCountResponse> done) {
        boolean reCount = request.getReCount();
        long rowcount = 0;
        if (reCount == true) {
            InternalScanner scanner = null;
            try {
                //todo 用scan的方法获取行数,这样做效率低下，因为要遍历region
                Scan scan = new Scan();
                scanner = env.getRegion().getScanner(scan);
                List<Cell> results = new ArrayList<Cell>();
                boolean hasMore = false;

                do {
                    hasMore = scanner.next(results);
                    rowcount++;
                } while (hasMore);
            } catch (IOException ioe) {
            } finally {
                if (scanner != null) {
                    try {
                        scanner.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } else {
            try {
                //todo observer 协处理器负责维护行数计数器，而在 Endpoint 协处理器中仅仅需要读取该计数器即可。
                //todo 这里使用zk作为endpoint和observer数据共享中心
                byte[] data = ZKUtil.getData(zkw, zNodePath);
                rowcount = Bytes.toLong(data);
                LOG.info("LIULIUMI: get rowcount " + rowcount);
            } catch (Exception e) {
                LOG.info("Exception during getData");
            }
        }
        getRowCount.getRowCountResponse.Builder responseBuilder = getRowCount.getRowCountResponse.newBuilder();
        responseBuilder.setRowCount(rowcount);
        //todo 将 rowcount 设置为 CountResponse 消息的 rowCount
        done.run(responseBuilder.build());
    }

}
