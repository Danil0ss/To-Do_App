package com.example.To_Do_App.repository;

import com.example.To_Do_App.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByUserIdOrderByPositionAsc(String userId);

    @Query(value = "SELECT MAX (t.position) FROM Task t WHERE t.userId=:userId")
    Optional<Integer> findMaxPositionByUserId(@Param("userId") String userId);

    @Modifying
    @Query(value = "UPDATE Task t SET t.position=t.position-1 "+
                    "WHERE t.userId=:userId AND t.position>:position")
    void updateTaskPosition(@Param("userId") String userId,
                            @Param("position") Integer position);

    @Modifying
    @Query("UPDATE Task t SET t.position = t.position + 1 " +
            "WHERE t.userId = :userId AND t.position >= :newPosition AND t.position < :oldPosition")
    void incrementPositionsBetween(@Param("userId") String userId,
                                   @Param("newPosition") Integer newPosition,
                                   @Param("oldPosition") Integer oldPosition);

    @Modifying
    @Query("UPDATE Task t SET t.position = t.position - 1 " +
            "WHERE t.userId = :userId AND t.position <= :newPosition AND t.position > :oldPosition")
    void decrementPositionsBetween(@Param("userId") String userId,
                                   @Param("newPosition") Integer newPosition,
                                   @Param("oldPosition") Integer oldPosition);
}
