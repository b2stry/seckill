package com.shallowan.seckill.service;

import com.shallowan.seckill.dao.GoodsDao;
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

    public boolean reduceStock(GoodsVO goods) {
        SeckillGoods g = new SeckillGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }

    public void resetStock(List<GoodsVO> goodsList) {
        for(GoodsVO goods : goodsList ) {
            SeckillGoods g = new SeckillGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }
}
