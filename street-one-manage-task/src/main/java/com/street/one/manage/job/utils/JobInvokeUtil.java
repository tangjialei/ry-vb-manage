package com.street.one.manage.job.utils;

import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.job.entity.BasisJobEntity;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.utils
 * @ClassName: JobInvokeUtil
 * @Author: tjl
 * @Description: 任务执行工具
 * @Date: 2024/7/3 15:13
 * @modified modify person name
 * @Version: 1.0
 */
public class JobInvokeUtil {

    /**
     * 执行目标方法
     * @param jobEntity 计划任务
     */
    public static void invokeTargetMethod(BasisJobEntity jobEntity) throws Exception {
        //调用目标方法及参数，这里指的是service方法
        String invokeTargetMethod = jobEntity.getInvokeTargetMethod();
        //获取bean名称
        String beanName = getBeanName(invokeTargetMethod);
        //获取方法名称
        String methodName = getMethodName(invokeTargetMethod);
        //获取方法参数
        List<Object[]> methodParams = getMethodParams(invokeTargetMethod);
        //检查是否是内部包名
        if (!isValidClassName(beanName)) {
            //获取bean
            Object bean = SpringApplicationContext.getBean(beanName);
            //执行bean目标方法
            invokeMethod(bean, methodName, methodParams);
        } else {
            //获取到bean的实例
            Object bean = Class.forName(beanName).newInstance();
            //执行bean目标方法
            invokeMethod(bean, methodName, methodParams);
        }
    }


    /**
     * 调用任务方法
     *
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        //调用有参方法
        if (StringUtil.isNotNull(methodParams) && methodParams.size() > 0) {
            Method method = bean.getClass().getMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            //调用无参方法
            Method method = bean.getClass().getMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 校验是否为为class包名
     *
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }


    /**
     * 获取bean名称
     *
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     *
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     *
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr)) {
            return null;
        }
        String[] methodParams = methodStr.split(",(?=([^\"']*[\"'][^\"']*[\"'])*[^\"']*$)");
        List<Object[]> classs = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StringUtils.trimToEmpty(methodParam);
            // String字符串类型，以'或"开头
            if (StringUtils.startsWithAny(str, "'", "\"")) {
                classs.add(new Object[]{StringUtils.substring(str, 1, str.length() - 1), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                classs.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，以L结尾
            else if (StringUtils.endsWith(str, "L")) {
                classs.add(new Object[]{Long.valueOf(StringUtils.substring(str, 0, str.length() - 1)), Long.class});
            }
            // double浮点类型，以D结尾
            else if (StringUtils.endsWith(str, "D")) {
                classs.add(new Object[]{Double.valueOf(StringUtils.substring(str, 0, str.length() - 1)), Double.class});
            } else {
                // 其他类型归类为整形
                classs.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return classs;
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams)
    {
        Class<?>[] classs = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classs[index] = (Class<?>) os[1];
            index++;
        }
        return classs;
    }

    /**
     * 获取参数值
     *
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams)
    {
        Object[] classs = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams)
        {
            classs[index] = os[0];
            index++;
        }
        return classs;
    }

}
