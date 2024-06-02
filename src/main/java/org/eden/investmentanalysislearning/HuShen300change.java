package org.eden.investmentanalysislearning;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import org.eden.investmentanalysislearning.excelmodel.ZhongZhengWeightMo;
import org.eden.investmentanalysislearning.excelmodel.ZhongZhengWeightMoListener;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Eden
 * @date 2024/6/2
 */
public class HuShen300change {

    public static void main(String[] args) {
        String newFile = "2024/hushen300change-20240602-new.xls";
        List<ZhongZhengWeightMo> newList = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(newFile).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(newList)).sheet().doRead();

        String oldFile = "2024/hushen300change-20240602-old.xls";
        List<ZhongZhengWeightMo> oldList = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(oldFile).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(oldList)).sheet().doRead();

        Map<String, String> newMap = newList.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, ZhongZhengWeightMo::getConstituentName));

        Map<String, String> oldMap = oldList.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, ZhongZhengWeightMo::getConstituentName));

        Set<String> newConstituentCode = newMap.keySet();

        Set<String> oldConstituentCode = oldMap.keySet();

        List<String> removeList = Lists.newArrayList(oldConstituentCode);
        removeList.removeAll(newConstituentCode);

        List<String> addList = Lists.newArrayList(newConstituentCode);
        addList.removeAll(oldConstituentCode);

        StringBuilder addStringBuilder = new StringBuilder();
        addStringBuilder.append("本次调入名单，共").append(addList.size()).append("只，分别是:");
        for (String add : addList) {
            addStringBuilder.append(newMap.get(add)).append(":").append(add).append(";");
        }
        System.out.println(addStringBuilder.toString());

        StringBuilder removeStringBuilder = new StringBuilder();
        removeStringBuilder.append("本次调出名单，共").append(addList.size()).append("只，分别是:");
        for (String remove : removeList) {
            removeStringBuilder.append(oldMap.get(remove)).append(":").append(remove).append(";");
        }
        System.out.println(removeStringBuilder.toString());
    }
}
