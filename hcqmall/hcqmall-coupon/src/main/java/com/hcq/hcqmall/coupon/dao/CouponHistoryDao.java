package com.hcq.hcqmall.coupon.dao;

import com.hcq.hcqmall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author yaoxinjia
 * @email 894548575@qq.com
 * @date 2021-02-09 20:18:04
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
