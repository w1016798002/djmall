package com.dj.mall.model.util;
import org.dozer.DozerBeanMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86150
 */
public class DozerUtil {

    public static final DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 对象映射（源与目标属性不一致的情况，可在目标属性中通过@Mapping 手动映射）
     *
     * @param source 源
     * @param target 目标
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> target) {
        return dozer.map(source, target);
    }

    /**
     * List映射
     *
     * @param sourceList 源List
     * @param target     目标
     * @param <T>
     * @return
     */
    public static <T> List<T> mapList(List<?> sourceList, Class<T> target) {
        return sourceList.stream().map(source -> map(source, target)).collect(Collectors.toList());
    }

}

