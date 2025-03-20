package com.kucp1127.KidsLearningApp.ParentsCommunityForum.Repository;

import com.kucp1127.KidsLearningApp.ParentsCommunityForum.Model.ParentsCommunityForumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentsCommunityForumRepository extends JpaRepository<ParentsCommunityForumModel, Integer> {
     List<ParentsCommunityForumModel> findByUsername(String username);
}
