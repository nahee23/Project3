package org.example.project3.Repository;

import org.example.project3.Entity.Category;
import org.example.project3.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods,Integer> {
    Integer id(int id);
    List<Goods> findByCategory(Category category);

    List<Goods> findByTitleContaining(String keyword);
}
