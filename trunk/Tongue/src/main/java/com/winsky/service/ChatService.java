package com.winsky.service;

import com.winsky.bean.CategoryBean;
import com.winsky.bean.ChatBean;
import com.winsky.bean.PhotoBean;
import com.winsky.dao.ChatDAO;
import com.winsky.enums.UserTypeEnum;
import com.winsky.page.Page;
import com.winsky.vo.ChatVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author: winsky
 * date: 2017/12/14
 * description:
 */
@Service
public class ChatService {
    @Resource
    private ChatDAO chatDAO;
    @Resource
    private PhotoService photoService;
    @Resource
    private ChatService chatService;
    @Resource
    private PhotoCategoryService photoCategoryService;
    @Resource
    private DiseaseService diseaseService;

    public List<ChatVO> getDiseaseChats(Page page, ChatBean chatBean) {
        if (chatBean == null) return new ArrayList<>();
        long diseaseId = chatBean.getDiseaseId();
        if (diseaseId <= 0) return new ArrayList<>();

        List<ChatBean> chats = chatDAO.getPageList(page, chatBean);
        List<ChatVO> vos = new ArrayList<>();
        for (ChatBean bean : chats) {
            long chatId = bean.getId();
            List<PhotoBean> photos = photoService.getPhotos(chatId);
            ChatVO chatVO = new ChatVO(bean);
            chatVO.setPhotos(photos);
            List<List<CategoryBean>> categories = photoCategoryService.getCategoryByChatId(chatId);
            chatVO.setCategories(categories);

            vos.add(chatVO);
        }
        return vos;
    }

    public ChatVO getDiseaseFirstChat(long diseaseId) {
        if (diseaseId <= 0) return null;
        ChatBean chat = chatDAO.getFirstChat(diseaseId);
        ChatVO chatVO = new ChatVO(chat);
        PhotoBean photo = photoService.getFirstPhoto(chat.getId());
        chatVO.addPhoto(photo);
        return chatVO;
    }

    public long save(ChatBean chatBean) {
        return chatDAO.save(chatBean);
    }

    public boolean delete(long id) {
        return chatDAO.delete(ChatBean.class, id);
    }

    public void insertChat(ChatBean chatBean, List<PhotoBean> photoBeans) throws Exception {
        // 插入会话记录
        long chatId = chatService.save(chatBean);
        if (chatId <= 0) {
            throw new Exception("创建会话记录失败");
        } else {
            // 插入图片记录
            for (PhotoBean photoBean : photoBeans) {
                photoBean.setChatId(chatId);
                long photoId = photoService.save(photoBean);
                if (photoId <= 0) {
                    throw new Exception("创建图片记录失败");
                }
            }

            // 更新问诊单的最后咨询状态
            boolean updateResult = diseaseService.updateLastChat(chatBean.getDiseaseId(), UserTypeEnum.fromValue(chatBean.getType()));
            if (!updateResult) {
                throw new Exception("更新问诊单状态失败");
            }
        }
    }
}
