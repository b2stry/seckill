package com.shallowan.seckill.service;

import com.shallowan.seckill.dao.GoodsDao;
import com.shallowan.seckill.domain.Goods;
import com.shallowan.seckill.domain.SeckillGoods;
import com.shallowan.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ShallowAn
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVO> listGoodsVO() {
        return goodsDao.listGoodsVO();
    }

    public GoodsVO getGoodsVOById(long goodsId) {
        return goodsDao.getGoodsVOByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVO goods) {
        SeckillGoods g = new SeckillGoods();
        g.setId(goods.getId());
        goodsDao.reduceStock(g);
    }
}
