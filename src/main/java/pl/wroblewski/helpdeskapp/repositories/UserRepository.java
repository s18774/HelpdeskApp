package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.Group;
import pl.wroblewski.helpdeskapp.models.Role;
import pl.wroblewski.helpdeskapp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);

    @Query("Select u FROM _user u LEFT JOIN _Group g ON u.group.groupId=g.groupId " +
            "WHERE (:firstName IS NULL OR u.firstName LIKE %:firstName%) " +
            "AND (:secondName IS NULL OR u.secondName LIKE %:secondName%) " +
            "AND (:positionName IS NULL OR u.positionName LIKE %:positionName%) " +
            "AND (:groupName IS NULL OR u.group.groupName LIKE %:groupName%) ")
    List<User> findAllByFirstnameAndSecondNameAndPositionNameAndGroupName(@Param("firstName") String firstName,
                                                                          @Param("secondName") String secondName,
                                                                          @Param("positionName") String positionName,
                                                                          @Param("groupName") String groupName);

    @Query("Select u FROM _user u  " +
            "WHERE (:firstName IS NULL OR u.firstName LIKE %:firstName%) " +
            "AND (:secondName IS NULL OR u.secondName LIKE %:secondName%) " +
            "AND (:positionName IS NULL OR u.positionName LIKE %:positionName%)")
    List<User> findAllByFirstnameAndSecondNameAndPositionName(@Param("firstName") String firstName,
                                                              @Param("secondName") String secondName,
                                                              @Param("positionName") String positionName);

    List<User> findAllByGroup(Group group);

    Optional<User> findByUserIdAndGroup(Integer userId, Group group);

    boolean existsByUsername(String username);
}
