package com.ttmv.monitoring.filter.impl;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.filter.FiltratorAgent;
import com.ttmv.monitoring.imp.IWhiteListInterImpl;
import com.ttmv.monitoring.inter.IWhiteListInter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zbs on 15/10/15.
 */
public class DataSourceIPFilter extends FiltratorAgent{

    @Resource(name = "iWhiteListInterImpl")
    protected IWhiteListInter iWhiteListInterImpl;

    private static Logger logger = LogManager.getLogger(DataSourceIPFilter.class);
    private Map<String,BitSet> map;

    @Override
    public void inifWhiteList() throws Exception {
        map = new HashMap<String, BitSet>();
        List<WhiteList> list = iWhiteListInterImpl.queryListByConditions(null);
        if(list == null)
            throw new Exception("没有从数据库中找到 ip 白名单信息，请检查这是否是一个错误。");
        for(WhiteList whiteList :list){
            addWhiteListValue(whiteList);
        }
    }

    @Override
    public void addWhiteListValue(Object obj){
        try {
            WhiteList whiteList = (WhiteList) obj;
            String mapKey = getMapKey(whiteList.getStartIp());
            //如果map中有布隆过滤器，就获取map中的布隆过滤器
            map.put(mapKey, getBitSet(whiteList, map.get(mapKey)));
        }catch (Exception e){
            logger.error("对白名单追加值出现异常，请检查。",e);
        }
    }

    @Override
    public void updateWhiteValue(){
        try{
          inifWhiteList();
        }catch (Exception e){
            logger.error("更新白名单出现异常，请检查。",e);
        }
    }

    @Override
    protected String getMapKey(String str) throws Exception {
        if(str == null || "".equals(str)) {
            throw new Exception("传入的IP值为空");
        }
        String[] strSplit = str.split("\\.");
        if(strSplit == null || strSplit.length != 4) {
            throw new Exception("传入的IP不是一个有效的格式" + str);
        }
        return strSplit[0]+"."+strSplit[1]+"."+strSplit[2];
    }

    /**
     * 对ip进行截取，用.进行分割
     * @param str
     * @return
     */
    @Override
    protected Integer getBitSetKey(String str) throws Exception {
        if(str == null || "".equals(str))
            throw new Exception("传入的IP值为空");
        String[] strSplit = str.split("\\.");
        if(strSplit == null || strSplit.length != 4)
            throw new Exception("传入的IP不是一个有效的格式" + str);
        return Integer.valueOf(strSplit[3]);
    }

    @Override
    protected Map<String, BitSet> getWhiteList() {
        return this.map;
    }

    /**
     * 如果是区间，就for产生bitset
     * @param whiteList
     * @param bitSet
     * @return
     * @throws Exception
     */
    private BitSet getBitSet(WhiteList whiteList,BitSet bitSet) throws Exception {
        if(bitSet == null )
            bitSet = new BitSet(999);
        switch (whiteList.getType()){
            case 1:{
                bitSet.set(getBitSetKey(whiteList.getStartIp()), true);
                return bitSet;
            }
            case 2:{
                //如果是一个区间，就把区间中所有的ip 最后一段，加入到bitmap中
                for(int i=getBitSetKey(whiteList.getStartIp());i<=getBitSetKey(whiteList.getEndIp());i++)
                    bitSet.set(i,true);
                return bitSet;
            }
            default:{
                throw new Exception("ip白名单list中的类型为1，2之外的值，确认传入的数据是否正确");
            }
        }
    }
}
