package com.atguigu.gmall.item.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Controller
public class ItemController {
    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId,ModelMap map,HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();

        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId,remoteAddr);

        //request.getHeader("");//nginx负载均衡
        //sku对象吃吃
        map.put("skuInfo",pmsSkuInfo);

        HashMap<String,String> skuSaleAttrHash = new HashMap<>();

        //查询当前sku的spu的其他的sku的集合hash表
       List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());

        for (PmsSkuInfo skuInfo : pmsSkuInfos) {

            String k = "";
            String v = pmsSkuInfo.getId();

            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();

            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                k+=pmsSkuSaleAttrValue.getSaleAttrId()+"|";
            }

            skuSaleAttrHash.put(k,v);
        }

        //将sku属性的hash表放在页面
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        map.put("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);


        //销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),pmsSkuInfo.getId());
       map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);
        return "item";
    }


















    @RequestMapping("index")
    public String index(ModelMap modelMap){

        List list = new ArrayList();
        int i=1;
        for (;i<5;i++){
            list.add("添加数据"+i);
        }
        modelMap.put("list",list);
        modelMap.put("check","1");
        modelMap.put("hello","hello thymeleaf!!!");
        return "index";
    }
}
