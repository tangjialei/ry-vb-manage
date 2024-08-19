package com.street.one.manage.job.adapter;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.street.one.manage.job.thirdmode.ThirdConvertInfo;
import com.street.one.manage.job.thirdmode.ZdApiResult;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-new
 * @Package: com.juwei.xhxf.task.adapter
 * @ClassName: AbstractAdapter
 * @Author: tjl
 * @Description: 抽象适配类
 * @Date: 2023/7/14 上午10:00
 * @modified modify person name
 * @Version: 1.0
 */
public abstract class AbstractJobAdapter {

    /***
     * 从第三方拉取的数据转化
     * @param datas
     * @return
     */
    public abstract ThirdConvertInfo convertMiddleInfo(ZdApiResult datas);

    /***
     * 源数据转换成Maps 对象
     * @param datas
     * @return
     */
    protected  List<Map<String, Object>> sourceDataConvertMaps(ZdApiResult datas){
        JSONObject resultData = JSONUtil.parseObj(datas.getData()).getJSONObject("result");
        if(null == resultData){
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        try{
            //数据转换
            JSONArray fileList = resultData.getJSONArray("file_list");
            JSONArray values = resultData.getJSONArray("values");
            if(!CollectionUtils.isEmpty(values)){
                for (Object value : values) {
                    Map<String, Object> itemMap = new HashMap<>();
                    JSONArray v = JSONUtil.parseArray(value);
                    for (int i = 0; i < v.size(); i++) {
                        //ID还不能序列化我也不知道什么情况，这里先这样处理了，没时间弄了。
                        if("ID".equals(String.valueOf(fileList.get(i)))){
                            itemMap.put("id",v.get(i));
                        }else{
                            itemMap.put(String.valueOf(fileList.get(i)),v.get(i));
                        }
                    }
                    result.add(itemMap);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
