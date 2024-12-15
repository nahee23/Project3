package org.example.project3.Repository;

import org.example.project3.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,Integer> {
    Integer id(int id);
}
