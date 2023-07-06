
package hac.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerTableRepository extends JpaRepository<PlayerTable, Long> {
    PlayerTable findByUserName(String name);
}
