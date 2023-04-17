package org.eden.investmentanalysislearning;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.eden.investmentanalysislearning.excelmodel.GuoZhengWeightMo;
import org.eden.investmentanalysislearning.excelmodel.GuoZhengWeightMoListener;
import org.eden.investmentanalysislearning.excelmodel.ZhongZhengWeightMo;
import org.eden.investmentanalysislearning.excelmodel.ZhongZhengWeightMoListener;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 证券龙头和证券公司和证券公司30成分股分析
 *
 * @author Eden
 * @date 2023/4/17
 */
@Slf4j
public class QuanShangIndexAnalysis {

    public static void main(String[] args) {
        String zhengquangongsiWeightFileName = "202303/399975closeweight.xls";
        List<ZhongZhengWeightMo> zhengquangongsiWeights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(zhengquangongsiWeightFileName).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(zhengquangongsiWeights)).sheet().doRead();

        String zhengquangongsi30WeightFileName = "202303/931412closeweight.xls";
        List<ZhongZhengWeightMo> zhengquangongsi30Weights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(zhengquangongsi30WeightFileName).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(zhengquangongsi30Weights)).sheet().doRead();

        String zhengquanlongtouWeightFileName = "202303/399437_cons_202303.xls";
        List<GuoZhengWeightMo> zhengquanlongtouWeights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(zhengquanlongtouWeightFileName).getFile(), GuoZhengWeightMo.class, new GuoZhengWeightMoListener(zhengquanlongtouWeights)).sheet().doRead();

        String hushen300WeightFileName = "202303/000300closeweight.xls";
        List<ZhongZhengWeightMo> hushen300Weights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(hushen300WeightFileName).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(hushen300Weights)).sheet().doRead();

        System.out.println("证券公司数量:" + zhengquangongsiWeights.size());
        System.out.println("证券龙头数量:" + zhengquanlongtouWeights.size());
        System.out.println("证券公司30数量:" + zhengquangongsi30Weights.size());

        Map<String, ZhongZhengWeightMo> zhengquangongsiWeightMap = zhengquangongsiWeights.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));
        Map<String, ZhongZhengWeightMo> zhengquangongsi30WeightMap = zhengquangongsi30Weights.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));
        Map<String, ZhongZhengWeightMo> hushen300WeightMap = hushen300Weights.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));

        Comparator<GuoZhengWeightMo> guoZhengWeightMoComparator = Comparator.comparing(GuoZhengWeightMo::getWeight).reversed();
        Comparator<ZhongZhengWeightMo> zhongzhengWeightMoComparator = Comparator.comparing(ZhongZhengWeightMo::getWeight).reversed();

        zhengquangongsiWeights.sort(zhongzhengWeightMoComparator);
        zhengquanlongtouWeights.sort(guoZhengWeightMoComparator);
        zhengquangongsi30Weights.sort(zhongzhengWeightMoComparator);

        System.out.println("证券龙头跟证券公司相同的公司数量：" + zhengquanlongtouWeights.stream().filter(item -> zhengquangongsiWeightMap.get(item.getConstituentCode()) != null).distinct().count());
        System.out.println("证券龙头的公司在证券公司中所占的权重：" + zhengquanlongtouWeights.stream().map(item -> zhengquangongsiWeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
        System.out.println("证券龙头的前十大权重股所占的权重：" + zhengquanlongtouWeights.subList(0, 10).stream().mapToDouble(GuoZhengWeightMo::getWeight).sum());
        System.out.println("证券公司的前十大权重股所占的权重：" + zhengquangongsiWeights.subList(0, 10).stream().mapToDouble(ZhongZhengWeightMo::getWeight).sum());

        System.out.println("证券公司30跟证券公司相同的公司数量：" + zhengquangongsi30Weights.stream().filter(item -> zhengquangongsiWeightMap.get(item.getConstituentCode()) != null).distinct().count());
        System.out.println("证券公司30的公司在证券公司中所占的权重：" + zhengquangongsi30Weights.stream().map(item -> zhengquangongsiWeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
        System.out.println("证券公司30的前十大权重股所占的权重：" + zhengquangongsi30Weights.subList(0, 10).stream().mapToDouble(ZhongZhengWeightMo::getWeight).sum());

        System.out.println("证券龙头跟沪深300相同的公司数量：" + zhengquanlongtouWeights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).count());
        System.out.println("证券龙头的公司在沪深300中所占的权重：" + zhengquanlongtouWeights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
        System.out.println("证券公司跟沪深300相同的公司数量：" + zhengquangongsiWeights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).count());
        System.out.println("证券公司的公司在沪深300中所占的权重：" + zhengquangongsiWeights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
    }
}