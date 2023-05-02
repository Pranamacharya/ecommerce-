package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.Brand;
import com.example.final_UI_dev.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable int id) {
        return brandService.getBrandById(id);
    }

    @PostMapping("/")
    public Brand createBrand(@RequestBody Brand brand) {
        return brandService.createBrand(brand);
    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable int id) {
        brandService.deleteBrand(id);
    }

    @PutMapping("/{id}")
    public void updateBrand(@RequestBody Brand brand, @PathVariable int id) {
        Brand existingBrand = brandService.getBrandById(id);
        if (existingBrand != null) {
            brand.setBrandId(id);
            brandService.updateBrand(brand);
        }
    }

}