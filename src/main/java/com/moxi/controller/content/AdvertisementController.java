package com.moxi.controller.content;

import com.moxi.model.ResObject;
import com.moxi.model.content.Advertisements;
import com.moxi.model.content.AdvertisementsCategory;
import com.moxi.service.content.AdvertisementsCategoryService;
import com.moxi.service.content.AdvertisementsService;
import com.moxi.util.Constant;
import com.moxi.util.PageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    private AdvertisementsCategoryService advertisementsCategoryService;

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
        List<AdvertisementsCategory> advertisementsCategoryList = advertisementsCategoryService.list(advertisementsCategory);

        //输出
        model.addAttribute("advertisementsCategoryList",advertisementsCategoryList);
        model.addAttribute("advertisementsList",advertisementsList);
        String pageHTML = PageUtil.getPageContent("advertisementsManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + advertisements.getTitle() + "&category=" + advertisements.getCategory() + "&commendState" + advertisements.getCommendState() + "&orderBy" + advertisements.getOrderBy(),pageCurrent,pageSize,pageCount);
        model.addAttribute("pageHTML",pageHTML);
        model.addAttribute("advertisements",advertisements);

        return "advertisements/advertisementsManage";
    }

    @GetMapping("/admin/advertisementsEdit")
    public String advertisementsEditGet(Model model,Advertisements advertisements){
        AdvertisementsCategory advertisementsCategory = new AdvertisementsCategory();
        advertisementsCategory.setStart(0);
        advertisementsCategory.setEnd(Integer.MAX_VALUE);
        List<AdvertisementsCategory> advertisementsCategoryList = advertisementsCategoryService.list(advertisementsCategory);
        model.addAttribute("advertisementsCategoryList",advertisementsCategoryList);
        if (advertisements.getId() != 0){
            Advertisements advertisementsT = advertisementsService.findById(advertisements);
            model.addAttribute("advertisements",advertisementsT);
        }
        return "advertisements/advertisementsEdit";
    }

    @PostMapping("/admin/advertisementsEdit")
    public String advertisementsEditPost(Model model, Advertisements advertisements, @RequestParam MultipartFile[] imageFile, HttpSession httpSession){
        for (MultipartFile file : imageFile){
            if (file.isEmpty()){
                System.out.println("文件未上传");
            }else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Random random = new Random();
                Date date = new Date();
                String strDate = sdf.format(date);
                String fileName = strDate + "_" + random.nextInt(1000) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),file.getOriginalFilename().length());
                String realPath = httpSession.getServletContext().getRealPath("/userfiles");
                System.out.println("realPath : " + realPath);
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(),new File(realPath,fileName));
                    advertisements.setImage("/userfiles/" + fileName);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        if (advertisements.getId() != 0){
            advertisementsService.update(advertisements);
        }else {
            advertisementsService.insert(advertisements);
        }
        return "redirect:advertisementsManage_0_0_0";
    }

    @ResponseBody
    @PostMapping("/admin/advertisementsEditState")
    public ResObject<Object> advertisementsEditState(Advertisements advertisements){
        Advertisements advertisements3 = advertisementsService.findById(advertisements);
        if (advertisements.getState() == 0){
            advertisements.setState(advertisements3.getState());
        }
        if (advertisements.getCommendState() == 0){
            advertisements.setCommendState(advertisements3.getCommendState());
        }
        if (advertisements.getBrowses() == 0){
            advertisements.setBrowses(advertisements3.getBrowses());
        }
        if (advertisements.getLikes() == 0){
            advertisements.setLikes(advertisements3.getLikes());
        }
        if (advertisements.getComments() == 0){
            advertisements.setComments(advertisements3.getComments());
        }
        if (advertisements.getScore() == 0){
            advertisements.setScore(advertisements3.getScore());
        }
        advertisementsService.updateState(advertisements);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01,Constant.Msg01,null,null);
        return object;
    }
}
