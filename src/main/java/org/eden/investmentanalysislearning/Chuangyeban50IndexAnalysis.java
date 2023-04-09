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
 * 创业板50和创业板指成分股分析
 *
 * @author Eden
 * @date 2023/4/5
 */
@Slf4j
public class Chuangyeban50IndexAnalysis {

    public static void main(String[] args) {
        String hushen300WeightFileName = "202303/000300closeweight.xls";
        List<ZhongZhengWeightMo> hushen300Weights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(hushen300WeightFileName).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(hushen300Weights)).sheet().doRead();

        String kechuangchuangye50WeightFileName = "202303/931643closeweight.xls";
        List<ZhongZhengWeightMo> kechuangchuangye50Weights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(kechuangchuangye50WeightFileName).getFile(), ZhongZhengWeightMo.class, new ZhongZhengWeightMoListener(kechuangchuangye50Weights)).sheet().doRead();

        String chuangyebanzhiWeightFileName = "202303/399006_cons_202303.xls";
        List<GuoZhengWeightMo> chuangyebanzhiWeights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(chuangyebanzhiWeightFileName).getFile(), GuoZhengWeightMo.class, new GuoZhengWeightMoListener(chuangyebanzhiWeights)).sheet().doRead();

        String chuangyeban50WeightFileName = "202303/399673_cons_202303.xls";
        List<GuoZhengWeightMo> chuangyeban50Weights = Lists.newArrayList();
        EasyExcel.read(ResourceUtil.getResource(chuangyeban50WeightFileName).getFile(), GuoZhengWeightMo.class, new GuoZhengWeightMoListener(chuangyeban50Weights)).sheet().doRead();

        System.out.println("创业板50数量:" + chuangyeban50Weights.size());
        System.out.println("创业板指数量:" + chuangyebanzhiWeights.size());
        System.out.println("科创创业50数量:" + kechuangchuangye50Weights.size());
        System.out.println("沪深300数量:" + hushen300Weights.size());

        Map<String, GuoZhengWeightMo> chuangyeban50WeightMap = chuangyeban50Weights.stream().collect(Collectors.toMap(GuoZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));
        Map<String, GuoZhengWeightMo> chuangyebanzhiWeightMap = chuangyebanzhiWeights.stream().collect(Collectors.toMap(GuoZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));
        Map<String, ZhongZhengWeightMo> kechuangchuangye50WeightMap = kechuangchuangye50Weights.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));
        Map<String, ZhongZhengWeightMo> hushen300WeightMap = hushen300Weights.stream().collect(Collectors.toMap(ZhongZhengWeightMo::getConstituentCode, item -> item, (v1, v2) -> v2));

        Comparator<GuoZhengWeightMo> guoZhengWeightMoComparator = Comparator.comparing(GuoZhengWeightMo::getWeight).reversed();
        chuangyeban50Weights.sort(guoZhengWeightMoComparator);
        chuangyebanzhiWeights.sort(guoZhengWeightMoComparator);

        System.out.println("创业板50跟创业板指相同的公司数量：" + chuangyeban50Weights.stream().filter(item -> chuangyebanzhiWeightMap.get(item.getConstituentCode()) != null).distinct().count());
        System.out.println("创业板50的公司在创业板指中所占的权重：" + chuangyeban50Weights.stream().map(item -> chuangyebanzhiWeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(GuoZhengWeightMo::getWeight).sum());
        System.out.println("创业板50的前十大权重股所占的权重：" + chuangyeban50Weights.subList(0, 10).stream().mapToDouble(GuoZhengWeightMo::getWeight).sum());
        System.out.println("创业板指的前十大权重股所占的权重：" + chuangyebanzhiWeights.subList(0, 10).stream().mapToDouble(GuoZhengWeightMo::getWeight).sum());

        System.out.println("创业板50跟科创创业50相同的公司数量：" + chuangyeban50Weights.stream().map(item -> kechuangchuangye50WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).count());
        System.out.println("创业板50的公司在科创创业50中所占的权重：" + chuangyeban50Weights.stream().map(item -> kechuangchuangye50WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
        System.out.println("创业板指跟科创创业50相同的公司数量：" + chuangyebanzhiWeights.stream().map(item -> kechuangchuangye50WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).count());
        System.out.println("创业板指的公司在科创创业50中所占的权重：" + chuangyebanzhiWeights.stream().map(item -> kechuangchuangye50WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());

        System.out.println("创业板50跟沪深300相同的公司数量：" + chuangyeban50Weights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).count());
        System.out.println("创业板50的公司在沪深300中所占的权重：" + chuangyeban50Weights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
        System.out.println("创业板指跟沪深300相同的公司数量：" + chuangyebanzhiWeights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).count());
        System.out.println("创业板指的公司在沪深300中所占的权重：" + chuangyebanzhiWeights.stream().map(item -> hushen300WeightMap.get(item.getConstituentCode())).filter(Objects::nonNull).mapToDouble(ZhongZhengWeightMo::getWeight).sum());
    }
}