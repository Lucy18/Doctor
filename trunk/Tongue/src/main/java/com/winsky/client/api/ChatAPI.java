package com.winsky.client.api;

import com.winsky.APIUtil;
import com.winsky.Config;
import com.winsky.TimeUtil;
import com.winsky.Transaction;
import com.winsky.bean.ChatBean;
import com.winsky.bean.PhotoBean;
import com.winsky.enums.UserTypeEnum;
import com.winsky.page.Page;
import com.winsky.service.ChatService;
import com.winsky.service.PhotoService;
import com.winsky.service.UserService;
import com.winsky.vo.ChatVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author winsky
 */
@Controller
@RequestMapping("/api/chat")
public class ChatAPI {
    @Resource
    private ChatService chatService;
    @Resource
    private PhotoService photoService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String openId, String description, String photoTime, String photos, long diseaseId) {
        if (openId == null) openId = Config.DOCTOR_OPEN_ID;
        UserTypeEnum userType = UserService.getUserTypeByOpenId(openId);
        ChatBean chatBean = new ChatBean();
        chatBean.setDiseaseId(diseaseId);
        chatBean.setDescription(description);
        if (photoTime == null) photoTime = TimeUtil.getTodayTimeStr();
        chatBean.setPhotoTimeStr(photoTime);
        chatBean.setType(userType.value());
        chatBean.setCreateTime(TimeUtil.getCurrentLong());

        //photos为null会过滤掉直接返回空数组
        List<PhotoBean> photoBeans = photoService.transferPhotos(photos);
        boolean result = new Transaction() {
            @Override
            public void run() throws Exception {
                chatService.insertChat(chatBean, photoBeans);
            }
        }.start();
        return result ? APIUtil.genSuccessResult() : APIUtil.genErrorResult("创建会话记录失败");
    }

    @Deprecated
    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del(long id) {
        boolean result = chatService.delete(id);
        return APIUtil.genBooleanResult(result);
    }

    @RequestMapping(value = "/getChat")
    @ResponseBody
    public Object getChat(Integer pageNo, Integer pageSize, String sortName, String sortOrder, long diseaseId) {
        Page page = new Page();
        if (pageNo != null) page.setPageNo(pageNo);
        if (pageSize != null) page.setPageSize(pageSize);
        if (sortName != null) page.setSortname(sortName);
        if (sortOrder != null) page.setSortorder(sortOrder);
        ChatBean chatBean = new ChatBean();
        chatBean.setDiseaseId(diseaseId);
        List<ChatVO> chats = chatService.getDiseaseChats(page, chatBean);
        return APIUtil.genPageDataObject(chats, page);
    }
}