
package hac.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerTableRepository extends JpaRepository<PlayerTable, Long> {
    
    @Modifying
    @Query("UPDATE PlayerTable p SET p.score = :newScore WHERE p.userName = :userName")
    void updateScore(@Param("userName") String userName, @Param("newScore") double newScore);

    PlayerTable findByUserName(String name);
}
