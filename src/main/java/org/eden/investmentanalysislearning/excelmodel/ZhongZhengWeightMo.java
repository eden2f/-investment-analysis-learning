package org.eden.investmentanalysislearning.excelmodel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class ZhongZhengWeightMo {

    /**
     * 日期Date
     */
    @ExcelProperty(index = 0)
    private String date;

    /**
     * 指数代码 Index Code
     */
    @ExcelProperty(index = 1)
    private String indexCode;

    /**
     * 指数名称 Index Name
     */
    @ExcelProperty(index = 2)
    private String  indexName;

    /**
     * 指数英文名称Index Name(Eng)
     */
    @ExcelProperty(index = 3)
    private String indexNameEng;

    /**
     * 成分券代码Constituent Code
     */
    @ExcelProperty(index = 4)
    private String constituentCode;

    /**
     * 成分券名称Constituent Name
     */
    @ExcelProperty(index = 5)
    private String constituentName;

    /**
     * 成分券英文名称Constituent Name(Eng)
     */
    @ExcelProperty(index = 6)
    private String constituentNameEng;

    /**
     * 交易所Exchange
     */
    @ExcelProperty(index = 7)
    private String exchange;

    /**
     * 交易所英文名称Exchange(Eng)
     */
    @ExcelProperty(index = 8)
    private String exchangeEng;

    /**
     * 权重(%)weight
     */
    @ExcelProperty(index = 9)
    private Double weight;

}
