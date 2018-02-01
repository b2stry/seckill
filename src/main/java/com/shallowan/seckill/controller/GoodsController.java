package com.shallowan.seckill.controller;

import com.shallowan.seckill.domain.SeckillUser;
import com.shallowan.seckill.service.GoodsService;
import com.shallowan.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ShallowAn
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/to_list")
    public String list(Model model, SeckillUser user) {
        model.addAttribute("user", user);
        List<GoodsVO> goodsVOList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsVOList);
        return "goods_list";
    }

    @GetMapping("/to_detail/{goodsId}")
    public String detail(Model model, SeckillUser user,
                         @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        model.addAttribute("goods", goodsVO);

        long startAt = goodsVO.getStartTime().getTime();
        long endAt = goodsVO.getEndTime().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) {
            //秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}


