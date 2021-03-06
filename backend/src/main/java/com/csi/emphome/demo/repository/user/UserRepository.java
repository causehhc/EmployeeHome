package com.csi.emphome.demo.repository.user;

import com.csi.emphome.demo.domain.user.UserItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<UserItem,Integer> , JpaSpecificationExecutor<UserItem> {
    UserItem findById(int id);
    List< UserItem> findAllByUsernameLike(String username);
    List< UserItem> findAllByLoginname(String username);
    UserItem findTopByOrderByIdDesc();
  /*  List<UserItem> findAllByUsername(String username);*/
  /*  List< UserItem> findAll(Specification spec);*/

}
