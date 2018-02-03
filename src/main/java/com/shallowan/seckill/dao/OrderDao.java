package com.shallowan.seckill.dao;

import com.shallowan.seckill.domain.OrderInfo;
import com.shallowan.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * @author ShallowAn
 */
@Mapper
public interface OrderDao {

    @Select("select * from seckill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_time)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createTime} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "SELECT LAST_INSERT_ID()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into seckill_order(user_id,goods_id,order_id) values(#{userId},#{goodsId},#{orderId})")
    void insertSeckillOrder(SeckillOrder seckillOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(long orderId);

    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from seckill_order")
    public void deleteSeckillOrders();
}
