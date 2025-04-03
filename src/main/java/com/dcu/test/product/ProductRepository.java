package com.dcu.test.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품명 또는 제조사에서 검색어가 포함된 상품 찾기
    Page<Product> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findAll(Pageable pageable);

}
