package com.street.one.manage.common.core.domain.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.street.one.manage.common.utils.StringUtil;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.easyexcel
 * @ClassName: EasyExcelListener
 * @Author: tjl
 * @Description: 读取excel数据监听类
 * @Date: 2024/7/1 13:48
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public class EasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    /***
     * 转换的数据
     */
    private List<Map<Integer, String>> excels = Lists.newArrayList();
    /***
     * 从第几行读取 默认为空，如果有合并的单元格就去获取EasyExcel 内置ReadSheetHolder 然后获取读取的开始行。
     * 没有合并的单元格，则用不上该属性
     */
    private Integer headRowNumber = null;

    /***
     * 表头处理
     */
    Map<Integer, String> headMaps = Maps.newHashMap();

    /***
     * sheet页索引,默认处理一个
     */
    private int sheetNo = 0;

    /**
     * 合并单元格
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());
        if(headRowNumber==null){
            headRowNumber = context.readSheetHolder().getHeadRowNumber();
        }
        // 获取行的索引
        int index = context.readRowHolder().getRowIndex();
        if (index == (headRowNumber -1)) {
            //第一行是表头
            headMaps = ConverterUtils.convertToStringMap(headMap, context);
        }
    }

    /***
     * 这个是每行的数据（每一行都会执行这个）
     * @param data
     * @param context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        // 读取excel内容
        int currentSheetNo = context.readSheetHolder().getSheetNo();
        if (currentSheetNo != sheetNo) {
            // 如果不根据sheet页索引更新状态重新创建list，list会反复添加前面的sheet页对象值
            excels = Lists.newArrayList();
            sheetNo = currentSheetNo;
        }
        //处理带符号的字符串
        Map<Integer, String> temp = Maps.newHashMap();
        for (Integer key : data.keySet()) {
            String v = data.get(key);
            if (!StringUtil.isEmptyOrNull(v) && v.matches(".*[\\r\\t\\n\\u25B2].*")) {
                v = v.replaceAll("[\\r\\t\\n\\u25B2]", "").trim();
            }
            temp.put(key,v);
        }
        excels.add(temp);
    }

    /***
     * 这个是全部读取完成后的回调
     * @param context
     */
    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("excel读取完毕！");
    }

    /***
     * 这个是读取异常是的回调
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败，但是继续解析下一行: " + exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            System.err.println("第{}行，第{}列解析异常" + excelDataConvertException.getRowIndex() +
                    excelDataConvertException.getColumnIndex());
        }
    }

    /***
     * 这个是读取单元格和并时的信息
     * @param extra
     * @param context
     */
    @SneakyThrows
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        //解析合并单元格的信息，如果有合并的行才去解析 利用反射给合并的单元格读不到值时，进行赋值
        if (extra.getType() == CellExtraTypeEnum.MERGE) {
            if (extra.getRowIndex() >= headRowNumber) {
                extraMergeInfoList.add(extra);
            }
        }
    }

    /**
     * 获取表格内容(简单excel读取可用该方法)
     * @return
     */
    public <T> List<T> getDataList() {
        return (List<T>) excels;
    }

    /***
     * 获取表头
     * @return
     */
    public Map<Integer, String> getExcelHeadList() {
        return headMaps;
    }


    /**
     * 返回解析出来的合并单元格List
     */
    public List<CellExtra> getExtraMergeInfoList() {
        return extraMergeInfoList;
    }

}
