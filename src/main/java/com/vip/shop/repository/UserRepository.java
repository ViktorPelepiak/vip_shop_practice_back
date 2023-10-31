package com.vip.shop.repository;

import com.vip.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String DAYS_TO_DEBT = "14";
    String GET_DEBTORS_QUERY = "Select distinct u.* " +
            "from users as u " +
            "inner join carts as c on c.user_id=u.id " +
            "where c.status='ORDERED' and c.ordering_date < CURRENT_DATE-" + DAYS_TO_DEBT;

    Optional<User> findByUsernameOrEmail(String username, String email);

    default Optional<User> findByLogin(String login) {
        return this.findByUsernameOrEmail(login, login);
    }

    @Query(value = GET_DEBTORS_QUERY, nativeQuery = true)
    List<User> getDebtors();
}
