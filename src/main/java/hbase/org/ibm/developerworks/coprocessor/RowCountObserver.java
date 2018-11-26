package hbase.org.ibm.developerworks.coprocessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.HRegion;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.RegionServerServices;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.zookeeper.ZKUtil;
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * RowCount Observer协处理器 服务端代码
 * hbase版本: 0.98.11-hadoop2
 */
public class RowCountObserver extends BaseRegionObserver {
    RegionCoprocessorEnvironment env;
    private static final Log LOG = LogFactory.getLog(RowCountObserver.class);
    private String zNodePath = "/hbase/ibmdeveloperworks/demo";
    private ZooKeeperWatcher zkw = null;
    private long myrowcount = 0;
    private boolean initcount = false;
    private HRegion m_region;

    @Override
    //todo 然而 start() 方法调用的时候，region 还未完全初始化完成，因此无法调用 scan 操作
    public void start(CoprocessorEnvironment e) throws IOException {
        env = (RegionCoprocessorEnvironment) e;
        RegionCoprocessorEnvironment re = (RegionCoprocessorEnvironment) e;
        RegionServerServices rss = re.getRegionServerServices();
        m_region = re.getRegion();
        zNodePath = zNodePath + m_region.getRegionNameAsString();
        zkw = rss.getZooKeeper();
        myrowcount = 0; //count;
        initcount = false;

        try {
            if (ZKUtil.checkExists(zkw, zNodePath) == -1) {
                LOG.error("LIULIUMI: cannot find the znode");
                ZKUtil.createWithParents(zkw, zNodePath);
                LOG.info("znode path is : " + zNodePath);
            }
        } catch (Exception ee) {
            LOG.error("LIULIUMI: create znode fail");
        }
    }

    @Override
    public void stop(CoprocessorEnvironment e) throws IOException {
        // nothing to do here
    }

    @Override
    public void preDelete(ObserverContext<RegionCoprocessorEnvironment> e,
                          Delete delete,
                          WALEdit edit,
                          Durability durability)
            throws IOException {
        //todo //计数器减 1
        myrowcount--;
        try {
            //todo 更新zk中的数据
            ZKUtil.setData(zkw, zNodePath, Bytes.toBytes(myrowcount));
        } catch (Exception ee) {
            LOG.info("setData exception");
        }
    }


    @Override
    //todo postOpen 在 Region 被打开成功之后调用，
    //todo 我们将利用 Scan 对象对 Region 进行遍历，求得行数，并用该值对 znode 进行初始化，
    public void postOpen(ObserverContext<RegionCoprocessorEnvironment> e) {
        LOG.info("LIULIUMI post open invoked");
        //todo 获取当前region的行数
        long count = 0;
        try {
            if (initcount == false) {
                Scan scan = new Scan();
                InternalScanner scanner = null;
                scanner = m_region.getScanner(scan);
                List<Cell> results = new ArrayList<Cell>();
                boolean hasMore = false;
                do {
                    hasMore = scanner.next(results);
                    if (results.size() > 0)
                        count++;
                } while (hasMore);
                initcount = true;
            }
            //todo 用当前的行数设置 ZooKeeper 中的计数器初始值
            ZKUtil.setData(zkw, zNodePath, Bytes.toBytes(count));
        } catch (Exception ee) {
            LOG.info("setData exception");
        }
    }

    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e,
                       Put put,
                       WALEdit edit,
                       Durability durability)
            throws IOException {
        //todo //计数器加 1
        myrowcount++;
        try {
            //todo 跟新zk数据
            ZKUtil.setData(zkw, zNodePath, Bytes.toBytes(myrowcount));
        } catch (Exception ee) {
            LOG.info("setData exception");
        }
    }

}
