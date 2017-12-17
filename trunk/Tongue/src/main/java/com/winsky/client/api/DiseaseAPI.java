package com.winsky.client.api;

import com.winsky.APIUtil;
import com.winsky.TimeUtil;
import com.winsky.Transaction;
import com.winsky.bean.ChatBean;
import com.winsky.bean.DiseaseBean;
import com.winsky.bean.PhotoBean;
import com.winsky.bean.UserBean;
import com.winsky.page.Page;
import com.winsky.service.DiseaseService;
import com.winsky.service.PhotoService;
import com.winsky.service.UserService;
import com.winsky.vo.DiseaseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
@Controller
@RequestMapping("/api/disease")
public class DiseaseAPI {
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private UserService userService;
    @Resource
    private PhotoService photoService;

    @RequestMapping(value = "/getDiseases")
    @ResponseBody
    public Object getDiseases(Integer pageNo, Integer pageSize, String sortName, String sortOrder, String userName, String date) {
        Page page = new Page();
        if (pageNo != null) page.setPageNo(pageNo);
        if (pageSize != null) page.setPageSize(pageSize);
        if (sortName != null) page.setSortname(sortName);
        if (sortOrder != null) page.setSortorder(sortOrder);
        DiseaseBean diseaseBean = new DiseaseBean();
        if (userName != null) {
            UserBean user = userService.getByName(userName);
            if (user != null) {
                diseaseBean.setOpenId(user.getOpenId());
            } else {
                diseaseBean.setOpenId("-1");//用户不存在，设置一个不存在的openId让接口返回空list
            }
        }
        if (date != null) diseaseBean.setCreateTime(TimeUtil.dateStrToLong(date));

        List<DiseaseVO> diseases = diseaseService.getCoverList(page, diseaseBean);
        return APIUtil.genPageDataObject(diseases, page);
    }

    @RequestMapping(value = "/getOwnDiseases")
    @ResponseBody
    public Object getOwnDiseases(Integer pageNo, Integer pageSize, String sortName, String sortOrder, String openId) {
        String name = null;
        if (StringUtils.isNotEmpty(openId)) {
            UserBean user = userService.getUser(openId);
            if (user != null) {
                name = user.getName();
            }
        }
        return getDiseases(pageNo, pageSize, sortName, sortOrder, name, null);
    }

    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public Object getUserInfo(long diseaseId) {
        DiseaseBean disease = diseaseService.select(diseaseId);
        if (disease == null) return APIUtil.genErrorResult("问诊记录不存在");

        String openId = disease.getOpenId();
        UserBean user = userService.getUser(openId);
        return APIUtil.genDataResult(user);
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String openId, String photos, String photoTime, String description) {
        if (StringUtils.isEmpty(photos)) return APIUtil.genErrorResult("请上传图片");
        boolean userExist = userService.checkExist(openId);
        if (!userExist) return APIUtil.genErrorResult("请先完善个人信息");

        DiseaseBean diseaseBean = new DiseaseBean();
        diseaseBean.setCreateTime(TimeUtil.getCurrentLong());
        diseaseBean.setLastChat(UserService.getUserTypeByOpenId(openId).value());
        diseaseBean.setOpenId(openId);

        ChatBean chatBean = new ChatBean();
        chatBean.setPhotoTimeStr(photoTime);
        chatBean.setDescription(description);
        chatBean.setType(UserService.getUserTypeByOpenId(openId).value());
        chatBean.setCreateTime(TimeUtil.getCurrentLong());

        List<PhotoBean> photoBeans = photoService.transferPhotos(photos);
        boolean result = new Transaction() {
            @Override
            public void run() throws Exception {
                diseaseService.insertDisease(diseaseBean, chatBean, photoBeans);
            }
        }.start();
        if (result) {
            return APIUtil.genSuccessResult();
        } else {
            return APIUtil.genErrorResult("创建问诊记录失败");
        }

    }
}
