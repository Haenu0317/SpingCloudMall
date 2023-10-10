package com.hmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmall.domain.dto.OrderDetailDTO;
import com.hmall.domain.po.Item;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author haenu
 * @since 2023-10-10
 */
public interface ItemMapper extends BaseMapper<Item> {

    @Update("UPDATE item SET stock = stock - #{num} WHERE id = #{itemId}")
    void updateStock(OrderDetailDTO orderDetail);
}
