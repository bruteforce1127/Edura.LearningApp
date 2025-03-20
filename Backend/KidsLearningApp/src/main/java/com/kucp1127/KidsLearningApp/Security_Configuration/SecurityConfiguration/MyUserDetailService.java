package com.kucp1127.KidsLearningApp.Security_Configuration.SecurityConfiguration;

import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import com.kucp1127.KidsLearningApp.Security_Configuration.Repository.ParentRepository;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ChildService;
import com.kucp1127.KidsLearningApp.Security_Configuration.Service.ParentService;
import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Parents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private ParentRepository userRepository;

    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildService childService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1) Check parents
        Optional<Parents> parentOpt = parentService.getParentByUserName(username);
        if (parentOpt.isPresent()) {
            // Found in parents
            return new UserPrincipal(parentOpt.get());
        }

        // 2) Check children
        Optional<Child> childOpt = childService.getChildByUserName(username);
        if (childOpt.isPresent()) {
            // Found in child
            // But you need a separate ChildPrincipal or a common approach
            return new ChildPrincipal(childOpt.get());
        }

        // 3) If neither found
        System.out.println("Not found in parents or children");
        throw new UsernameNotFoundException("User 404");
    }

}
