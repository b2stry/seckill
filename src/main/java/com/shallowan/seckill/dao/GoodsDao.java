package com.shallowan.seckill.dao;

import com.shallowan.seckill.domain.Goods;
import com.shallowan.seckill.domain.SeckillGoods;
import com.shallowan.seckill.domain.User;
import com.shallowan.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ShallowAn
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*, sg.seckill_price, sg.stock_count, sg.start_time, sg.end_time from seckill_goods sg left join goods g on sg.goods_id=g.id")
    List<GoodsVO> listGoodsVO();

    @Select("select g.*, sg.seckill_price, sg.stock_count, sg.start_time, sg.end_time from seckill_goods sg left join goods g on sg.goods_id=g.id where g.id=#{goodsId}")
    GoodsVO getGoodsVOByGoodsId(@Param("goodsId") long goodsId);

    @Update("update seckill_goods set stock_count=stock_count-1 where goods_id=#{goodsId} and stock_count > 0")
    int reduceStock(SeckillGoods g);

    @Update("update seckill_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(SeckillGoods g);
}
