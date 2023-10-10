package com.hmall.service.impl;

import com.hmall.domain.po.OrderDetail;
import com.hmall.mapper.OrderDetailMapper;
import com.hmall.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author haenu
 * @since 2023-10-10
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
