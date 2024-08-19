package com.street.one.manage.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.street.one.manage.common.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: EasyExcelUtil
 * @Author: tjl
 * @Description: excel 简单导入导出工具，复杂导出不适合
 * @Date: 2024/6/28 14:46
 * @modified modify person name
 * @Version: 1.0
 */
public class EasyExcelUtil {

    private EasyExcelUtil() {
    }

    public static <T> List<T> read(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                // 不要自动关闭，交给 Servlet 自己处理
                .autoCloseStream(false)
                .doReadAllSync();
    }

    /**
     * 一次性读取所有数据
     *
     * @param excel   excel 文件
     * @param sheetNo 读取第几个表格
     * @param clazz   读取类的class
     * @return 数据
     */
    public static <T> List<T> read(MultipartFile excel, int sheetNo, Class<T> clazz) {
        return read(excel, sheetNo, 1, clazz);
    }

    /**
     * 一次性读取所有数据
     *
     * @param excel         excel 文件
     * @param sheetNo       读取第几个表格
     * @param headRowNumber 标题行在第几行
     * @param clazz         读取类的class
     * @return 数据
     */
    public static <T> List<T> read(MultipartFile excel, int sheetNo, int headRowNumber, Class<T> clazz) {
        uploadCheck(excel);
        try (
                InputStream inputStream = new BufferedInputStream(excel.getInputStream());
        ) {
            return EasyExcel.read(inputStream).sheet(sheetNo).headRowNumber(headRowNumber).head(clazz).doReadSync();
        } catch (Exception e) {
            throw new BusinessException("读取文件未知异常", e.getMessage());
        }
    }

    /**
     * 读取所有数据并保存，默认每次读取 100 条数据
     *
     * @param excel    excel 文件
     * @param consumer 执行保存动作
     * @param clazz    映射类
     */
    public static <T> void readAndSave(MultipartFile excel, Consumer<List<T>> consumer, Class<T> clazz) {
        uploadCheck(excel);
        try (
                InputStream inputStream = new BufferedInputStream(excel.getInputStream());
        ) {
            // 默认每次读取 100 条数据
            EasyExcel.read(inputStream, clazz, new PageReadListener<T>(consumer)).sheet().headRowNumber(1).doRead();
        } catch (Exception e) {
            throw new BusinessException("读取文件未知异常", e.getMessage());
        }
    }

    /**
     * 检查上传文件
     *
     * @param excel 上传文件
     */
    public static void uploadCheck(MultipartFile excel) {
        String filename = excel.getOriginalFilename();
        if (StrUtil.isEmpty(filename)) {
            throw new BusinessException("请上传文件");
        } else if (!StrUtil.endWithAnyIgnoreCase(filename, ".xls", ".xlsx")) {
            throw new BusinessException("请上传正确的excel文件");
        }
    }


    /**
     * 导出文件
     *
     * @param response 返回体
     * @param dataList 数据体
     * @param clazz    映射类
     */
    public static <T> void export(HttpServletResponse response, List<T> dataList, Class<T> clazz) {
        export(response, DateUtil.format(new Date(), "yyyyMMddHHmmss"), "导出数据", dataList, clazz);
    }


    /**
     * 导出文件
     *
     * @param response 返回体
     * @param fileName 文件名，不包含后缀
     * @param dataList 数据体
     * @param clazz    映射类
     */
    public static <T> void export(HttpServletResponse response, String fileName, Class<T> clazz,List<T> dataList) {
        write(response, fileName, "数据", clazz,dataList);
    }

    /**
     * 导出文件
     *
     * @param response  返回体
     * @param fileName  文件名，不包含后缀
     * @param sheetName sheet名称
     * @param dataList  数据体
     * @param clazz     映射类
     */
    public static <T> void export(HttpServletResponse response, String fileName, String sheetName, List<T> dataList, Class<T> clazz) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            // 兼容多种浏览器下载
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(dataList);
        } catch (Exception e) {
            throw new BusinessException("导出文件未知异常", e.getMessage());
        }
    }

    /**
     * 导出文件并且添加水印
     *
     * @param response  返回体
     * @param fileName  文件名，不包含后缀
     * @param dataList  数据体
     * @param clazz     映射类
     * @param watermark 水印内容
     */
    public static <T> void exportAndWatermark(HttpServletResponse response, String fileName,
                                              List<T> dataList, Class<T> clazz,String watermark) {
        exportAndWatermark(response,fileName,"Sheet1",dataList,clazz, watermark);
    }

    /**
     * 导出文件并且添加水印
     *
     * @param response  返回体
     * @param fileName  文件名，不包含后缀
     * @param sheetName sheet名称
     * @param dataList  数据体
     * @param clazz     映射类
     * @param watermark 水印内容
     */
    public static <T> void exportAndWatermark(HttpServletResponse response, String fileName, String sheetName,
                                              List<T> dataList, Class<T> clazz,String watermark) {
        if (StrUtil.isEmpty(watermark)){
            throw new BusinessException("水印内容不允许为空");
        }
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), clazz)
                    // 注意，此项配置不能少
                    .inMemory(true)
                    .registerWriteHandler(new WaterMarkHandler(watermark))
                    .sheet(sheetName).doWrite(dataList);
        } catch (Exception e) {
            throw new BusinessException("导出文件未知异常", e.getMessage());
        }
    }


    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response  响应
     * @param filename  文件名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data){
        try {
            //设置 header 和 contentType
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            // 输出 Excel
            EasyExcel.write(response.getOutputStream(), head)
                    // 不要自动关闭，交给 Servlet 自己处理
                    .autoCloseStream(false)
                    // 基于 column 长度，自动适配。最大 255 宽度
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    // 避免 Long 类型丢失精度
                    .registerConverter(new LongStringConverter())
                    .sheet(sheetName).doWrite(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
