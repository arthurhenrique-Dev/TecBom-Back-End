package com.tecbom.e_commerce.Infra.Adapters.Input.Controllers;

import com.tecbom.e_commerce.Application.DTOs.Products.*;
import com.tecbom.e_commerce.Application.UseCases.Products.*;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("TecBom/Shop")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {


    private final AdminSearchUseCase adminSearchUseCase;
    private final ApplyDiscountUseCase applyDiscountUseCase;
    private final DeleteModelUseCase deleteModelUseCase;
    private final NewModelUseCase newModelUseCase;
    private final NormalSearchUseCase normalSearchUseCase;
    private final RecordPurchaseUseCase recordPurchaseUseCase;
    private final SaveProductUseCase saveProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    public ProductController(AdminSearchUseCase adminSearchUseCase, ApplyDiscountUseCase applyDiscountUseCase, DeleteModelUseCase deleteModelUseCase, NewModelUseCase newModelUseCase, NormalSearchUseCase normalSearchUseCase, RecordPurchaseUseCase recordPurchaseUseCase, SaveProductUseCase saveProductUseCase, UpdateProductUseCase updateProductUseCase) {
        this.adminSearchUseCase = adminSearchUseCase;
        this.applyDiscountUseCase = applyDiscountUseCase;
        this.deleteModelUseCase = deleteModelUseCase;
        this.newModelUseCase = newModelUseCase;
        this.normalSearchUseCase = normalSearchUseCase;
        this.recordPurchaseUseCase = recordPurchaseUseCase;
        this.saveProductUseCase = saveProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
    }

    @GetMapping("/admin/products")
    public List<Product> adminSearch(@PathVariable String searchTerm, @PathVariable Integer idxModel, @PathVariable Category category, @PathVariable Price price, @PathVariable OrderBy orderBy, @RequestParam(defaultValue = "0") Natural pages, @RequestParam(defaultValue = "20") Natural size) {
        return adminSearchUseCase.searchProducts(searchTerm, idxModel, category, price, orderBy, pages, size);
    }

    @PutMapping("/admin/products/discount")
    public ResponseEntity applyDiscount(@RequestBody DTODiscount dtoDiscount) {
        applyDiscountUseCase.applyDiscount(dtoDiscount);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/products")
    public ResponseEntity updateProduct(@RequestBody DTOUpdateProduct dtoUpdateProduct) {
        updateProductUseCase.updateProduct(dtoUpdateProduct);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/products/model")
    public ResponseEntity deleteModel(@RequestBody DTODeleteModel deleteModel) {
        deleteModelUseCase.deleteModel(deleteModel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/products/model")
    public ResponseEntity newModel(@RequestBody DTONewModel dtoNewModel) {
        newModelUseCase.newModel(dtoNewModel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/products")
    public ResponseEntity saveProduct(@RequestBody DTOSaveProduct dtoSaveProduct) {
        saveProductUseCase.saveProduct(dtoSaveProduct);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/products/purchase")
    public ResponseEntity recordPurchase(@RequestBody DTORecordPurchase dtoRecordPurchase) {
        recordPurchaseUseCase.RecordPurchase(dtoRecordPurchase);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<DTOReturnNormalProduct> normalSearch(@PathVariable String searchTerm, @PathVariable Integer idxModel, @PathVariable Category category, @PathVariable Price price, @PathVariable OrderBy orderBy, @RequestParam(defaultValue = "0") Natural pages, @RequestParam(defaultValue = "20") Natural size) {
        return normalSearchUseCase.searchProducts(searchTerm, idxModel, category, price, orderBy, pages, size);
    }
}
