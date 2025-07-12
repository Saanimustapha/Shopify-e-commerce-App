package com.saani.shopify.repository;

import com.saani.shopify.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images,Long> {
}
