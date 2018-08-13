package com.moxi.controller.content;

import com.moxi.model.ResObject;
import com.moxi.model.content.AdvertisementsCategory;
import com.moxi.service.content.AdvertisementsCategoryService;
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
 * Time: 19:32
 */
@Controller
public class AdvertisementCategoryController {

    @Autowired
    private AdvertisementsCategoryService advertisementsCategoryService;

    @RequestMapping("/admin/advertisementsCategoryManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String advertisementsCategoryManage(AdvertisementsCategory advertisementsCategory, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model){
        //判断
        if (pageSize == 0)
            pageSize = 10;
        if (pageCurrent == 0)
            pageCurrent = 1;
        int rows = advertisementsCategoryService.count(advertisementsCategory);
        if (pageCount == 0)
            pageCount = rows %pageSize == 0 ? (rows/pageSize) : (rows/pageSize)+1;

        //查询
        advertisementsCategory.setStart((pageCurrent - 1)*pageSize);
        advertisementsCategory.setEnd(pageSize);
        List<AdvertisementsCategory> list = advertisementsCategoryService.list(advertisementsCategory);

        //输出
        model.addAttribute("list",list);
        String pageHTML = PageUtil.getPageContent("advertisementsCategoryManage_{pageCurrent}_{pageSize}_{pageCount}?name=" + advertisementsCategory.getName(),pageCurrent,pageSize,pageCount);
        model.addAttribute("pageHTML",pageHTML);
        model.addAttribute("advertisementsCategory",advertisementsCategory);
        return "advertisements/advertisementsCategoryManage";
    }

    @GetMapping("/admin/advertisementsCategoryEdit")
    public String advertisementsCategoryEditGet(Model model,AdvertisementsCategory advertisementsCategory){
        if (advertisementsCategory.getId() != 0){
            AdvertisementsCategory advertisementsCategoryT = advertisementsCategoryService.findById(advertisementsCategory);
            model.addAttribute("advertisementsCategory",advertisementsCategoryT);
        }
        return "advertisements/advertisementsCategoryEdit";
    }

    @PostMapping("/admin/advertisementsCategoryEdit")
    public String advertisementsCategoryEditPost(Model model, AdvertisementsCategory advertisementsCategory, @RequestParam MultipartFile[] imageFile, HttpSession httpSession){
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
                    advertisementsCategory.setImage("/userfiles/" + fileName);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        if (advertisementsCategory.getId() != 0){
            advertisementsCategoryService.update(advertisementsCategory);
        }else {
            advertisementsCategoryService.insert(advertisementsCategory);
        }
        return "redirect:advertisementsCategoryManage_0_0_0";
    }

    @ResponseBody
    @PostMapping("/admin/advertisementsCategoryEditState")
    public ResObject<Object> advertisementsCategoryEditState(AdvertisementsCategory advertisementsCategory){
        advertisementsCategoryService.updateState(advertisementsCategory);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01,Constant.Msg01,null,null);
        return object;
    }
}
