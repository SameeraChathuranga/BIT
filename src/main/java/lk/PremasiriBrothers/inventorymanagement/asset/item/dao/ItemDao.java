package lk.PremasiriBrothers.inventorymanagement.asset.item.dao;

import lk.PremasiriBrothers.inventorymanagement.asset.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
    Item findFirstByOrderByIdDesc();

    Item findByCode(String code);

    List<Item> findByCreatedAtBetween(LocalDate from, LocalDate to);
}
