package com.tienda_m.service;

import com.tienda_m.domain.Producto;
import com.tienda_m.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;



@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Transactional(readOnly=true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }
    
    //Busca un registro por el idProducto y lo retorna...
     @Transactional(readOnly=true)
    public Optional<Producto> getProducto(Integer idProducto) {
       return productoRepository.findById(idProducto);
    }
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    //Actualiza o inserta y sube una imagen a la nube
    
    @Transactional
    public void save(Producto producto, MultipartFile imagenFile){
        producto = productoRepository.save(producto);
        if (!imagenFile.isEmpty()) { //Hay imagen..
            try {
                String rutaImagen=
                        firebaseStorageService.uploadImage(
                                imagenFile,
                                "producto",
                                producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);
        } catch (Exception e) {
            
        }
    }
  }
    // Elimina un registro..
    @Transactional
    public void delete(Integer idProducto){
        // Se verifica si existe la producto
        if (!productoRepository.existsById(idProducto)){
            //Se lanza una excepcion
            throw new IllegalArgumentException("La categoría con ID " + idProducto + " no existe");
        }
        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la producto, porque tiene datos asociados");
        }
  }
      @Transactional(readOnly=true)
    public List<Producto> ConsultaDerivada(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
    }
    
      @Transactional(readOnly=true)
    public List<Producto> ConsultaJPQL(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.consultaJPQL(precioInf, precioSup);
    }
      @Transactional(readOnly=true)
    public List<Producto> ConsultaSQL(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.consultaSQL(precioInf, precioSup);
    }
}

