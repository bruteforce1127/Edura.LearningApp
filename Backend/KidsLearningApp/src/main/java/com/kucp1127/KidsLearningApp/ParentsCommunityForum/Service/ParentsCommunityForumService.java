package com.kucp1127.KidsLearningApp.ParentsCommunityForum.Service;

import com.kucp1127.KidsLearningApp.ParentsCommunityForum.DataTransferObjects.SharedInfoDto;
import com.kucp1127.KidsLearningApp.ParentsCommunityForum.Model.ParentsCommunityForumModel;
import com.kucp1127.KidsLearningApp.ParentsCommunityForum.Repository.ParentsCommunityForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ParentsCommunityForumService {

    @Autowired
    private ParentsCommunityForumRepository parentsCommunityForumRepository;

    public List<ParentsCommunityForumModel> getForumByUsername(String username) {
        return parentsCommunityForumRepository.findByUsername(username);
    }

    public ParentsCommunityForumModel addSharedInfo(String username, SharedInfoDto sharedInfo) {
        ParentsCommunityForumModel parentsCommunityForumModel = new ParentsCommunityForumModel();
        parentsCommunityForumModel.setSharedInfo(sharedInfo.getSharedInfo());
        parentsCommunityForumModel.setUsername(username);
        parentsCommunityForumModel.setLocalDate(LocalDate.now());
        return parentsCommunityForumRepository.save(parentsCommunityForumModel);
    }
}
