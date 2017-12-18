package com.winsky.service;

import com.winsky.Config;
import com.winsky.bean.ChatBean;
import com.winsky.bean.DiseaseBean;
import com.winsky.bean.PhotoBean;
import com.winsky.bean.UserBean;
import com.winsky.dao.DiseaseDAO;
import com.winsky.enums.UserTypeEnum;
import com.winsky.page.Page;
import com.winsky.vo.ChatVO;
import com.winsky.vo.DiseaseVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
@Service
public class DiseaseService {
    @Resource
    private DiseaseDAO diseaseDAO;
    @Resource
    private UserService userService;
    @Resource
    private ChatService chatService;
    @Resource
    private PhotoService photoService;

    public List<DiseaseVO> getCoverList(Page page, DiseaseBean diseaseBean) {
        List<DiseaseBean> beans = diseaseDAO.getPageList(page, diseaseBean);
        List<DiseaseVO> diseases = new ArrayList<>();
        beans.forEach(bean -> {
            DiseaseVO vo = new DiseaseVO(bean);
            UserBean userBean = userService.getUser(bean.getOpenId());
            vo.setUserBean(userBean);
            ChatVO chat = chatService.getDiseaseFirstChat(bean.getId());
            List<PhotoBean> photos = chat.getPhotos();
            String description = chat.getDescription();
            vo.setDescription(description);
            String cover = Config.DEFAULT_PIC_URL;
            if (!CollectionUtils.isEmpty(photos)) {
                PhotoBean photoVO = photos.get(0);
                cover = photoVO.getPhotoUrl();
            }
            vo.setCover(cover);
            diseases.add(vo);
        });
        return diseases;
    }

    public long save(DiseaseBean bean) {
        return diseaseDAO.save(bean);
    }

    public boolean updateLastChat(long diseaseId, UserTypeEnum user) {
        return diseaseId > 0 && user != null && diseaseDAO.updateLastChat(diseaseId, user);
    }

    public DiseaseBean select(long diseaseId) {
        return diseaseDAO.select(DiseaseBean.class, diseaseId);
    }

    public long insertDisease(DiseaseBean diseaseBean, ChatBean chatBean, List<PhotoBean> photoBeans) throws Exception {
        long diseaseId = save(diseaseBean);
        if (diseaseId <= 0) {
            throw new Exception("创建问诊单失败");
        } else {
            chatBean.setDiseaseId(diseaseId);
            chatService.insertChat(chatBean, photoBeans);
        }
        return diseaseId;
    }
}
