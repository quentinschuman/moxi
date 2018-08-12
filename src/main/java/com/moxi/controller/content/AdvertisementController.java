package com.moxi.controller.content;

import com.moxi.model.content.Advertisements;
import com.moxi.model.content.AdvertisementsCategory;
import com.moxi.service.AdvertisementsCategroyService;
import com.moxi.service.AdvertisementsService;
import com.moxi.util.Constant;
import com.moxi.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * ProjectName: moxi
 * User: quent
 * Date: 2018/8/11
 * Time: 19:31
 */
@Controller
public class AdvertisementController {

    @Autowired
    private AdvertisementsService advertisementsService;

    @Autowired
    private AdvertisementsCategroyService advertisementsCategroyService;

    @RequestMapping("/admin/advertisementsManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String advertisementsManager(Advertisements advertisements, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model){

        //判断
        if (pageSize == 0)
            pageSize = 10;
        if (pageCurrent == 0)
            pageCurrent = 1;
        int rows = advertisementsService.count(advertisements);
        if (pageCount == 0)
            pageCount = rows %pageSize == 0 ? (rows/pageSize) : (rows/pageSize)+1;

        //查询
        advertisements.setStart((pageCurrent - 1)*pageSize);
        advertisements.setEnd(pageSize);
        if (advertisements.getOrderBy() == null)
            advertisements.setOrderBy(Constant.OrderByAddDateDesc);
        List<Advertisements> advertisementsList = advertisementsService.list(advertisements);

        //广告分类
        AdvertisementsCategory advertisementsCategory = new AdvertisementsCategory();
        advertisementsCategory.setStart(0);
        advertisementsCategory.setEnd(Integer.MAX_VALUE);
        List<AdvertisementsCategory> advertisementsCategoryList = advertisementsCategroyService.list(advertisementsCategory);

        //输出
        model.addAttribute("advertisementsCategoryList",advertisementsCategoryList);
        model.addAttribute("advertisementsList",advertisementsList);
        String pageHTML = PageUtil.getPageContent("advertisementsManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + advertisements.getTitle() + "&category=" + advertisements.getCategory() + "&commendState" + advertisements.getCommendState() + "&orderBy" + advertisements.getOrderBy(),pageCurrent,pageSize,pageCount);
        model.addAttribute("pageHTML",pageHTML);
        model.addAttribute("advertisements",advertisements);

        return "advertisements/advertisementsManage";
    }
}
