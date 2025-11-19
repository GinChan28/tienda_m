package com.tienda_m.repository;

import com.tienda_m.domain.Producto;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(BigDecimal precioInf, BigDecimal precioSup);

    // Consulta JPQL
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(BigDecimal precioInf, BigDecimal precioSup);

    // Consulta SQL nativa
    @Query(
        value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC",
        nativeQuery = true
    )
    public List<Producto> consultaSQL(BigDecimal precioInf, BigDecimal precioSup);
    
    
    public List<Producto> findByCategoriaDescripcionContainingIgnoreCaseAndPrecioBetweenOrderByPrecioAsc(String categoria,BigDecimal precioInf,BigDecimal precioSup);
}
