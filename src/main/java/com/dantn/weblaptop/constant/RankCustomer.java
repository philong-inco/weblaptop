package com.dantn.weblaptop.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RankCustomer {
    COPPER(0, "Đồng", new BigDecimal(100_000), BigDecimal.ZERO, new BigDecimal(1_000_000),0),
    SILVER(1, "Bạc", new BigDecimal(200_000), new BigDecimal(1_000_001), new BigDecimal(5_000_000),5),
    GOLD(2, "Vàng", new BigDecimal(300_000), new BigDecimal(5_000_001), new BigDecimal(10_000_000),10),
    PLATINUM(3, "Bạch Kim", new BigDecimal(400_000), new BigDecimal(10_000_001), new BigDecimal(20_000_000),20),
    DIAMOND(4, "Kim Cương", new BigDecimal(500_000), new BigDecimal(20_000_001), BigDecimal.valueOf(Long.MAX_VALUE),30);

    private Integer rank;
    private String name;
    private BigDecimal value;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private Integer purchaseCount; // lần mua nếu dùng


    // lấy giá trị giảm
    public static BigDecimal getValueByRank(Integer rank) {
        for (RankCustomer customerRank : RankCustomer.values()) {
            if (customerRank.getRank().equals(rank)) {
                return customerRank.getValue();
            }
        }
        return null;
    }

    // lấy tên
    public static String getNameByRank(Integer rank) {
        for (RankCustomer customerRank : RankCustomer.values()) {
            if (customerRank.getRank().equals(rank)) {
                return customerRank.getName();
            }
        }
        return null;
    }

    // tính rank dự vào tổng tiền đã chi
    public static Integer getRankByValue(BigDecimal amount) {
        for (RankCustomer customerRank : RankCustomer.values()) {
            if (amount.compareTo(customerRank.getMinValue()) >= 0 && amount.compareTo(customerRank.getMaxValue()) <= 0) {
                return customerRank.getRank(); // Trả về hạng tương ứng
            }
        }
        return -1;
    }

}
