package org.eden.investmentanalysislearning.excelmodel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class GuoZhengWeightMo {

    /**
     * 日期
     */
    @ExcelProperty(index = 0)
    private String date;

    /**
     * 样本代码
     */
    @ExcelProperty(index = 1)
    private String constituentCode;

    /**
     * 样本简称
     */
    @ExcelProperty(index = 2)
    private String  constituentName;

    /**
     * 所属行业
     */
    @ExcelProperty(index = 3)
    private String constituentIndustry;

    /**
     * 自由流通市值(亿元)
     */
    @ExcelProperty(index = 4)
    private String freeFloatMarketCapitalization;

    /**
     * 总市值(亿元)
     */
    @ExcelProperty(index = 5)
    private String totalMarketValue;

    /**
     * 权重（%）
     */
    @ExcelProperty(index = 6)
    private Double weight;

}
