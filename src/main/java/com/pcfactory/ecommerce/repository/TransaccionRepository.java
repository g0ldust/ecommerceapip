package com.pcfactory.ecommerce.repository;
import com.pcfactory.ecommerce.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository

public interface TransaccionRepository extends JpaRepository<Transaccion, String> {
    Optional<Transaccion> findByTokenWebpay(String tokenWebpay);


}
