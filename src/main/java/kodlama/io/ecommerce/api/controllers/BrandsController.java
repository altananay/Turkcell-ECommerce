package kodlama.io.ecommerce.api.controllers;

import kodlama.io.ecommerce.business.abstracts.BrandService;
import kodlama.io.ecommerce.business.dto.requests.create.CreateBrandRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateBrandRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateBrandResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllBrandsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetBrandResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/brands")
@RestController
@AllArgsConstructor
public class BrandsController {
    private final BrandService service;

    @GetMapping
    public List<GetAllBrandsResponse> getAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetBrandResponse getById(@PathVariable UUID id)
    {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBrandResponse add(@RequestBody CreateBrandRequest brand)
    {
        return service.add(brand);
    }

    @PutMapping("/{id}")
    public UpdateBrandResponse update(@PathVariable UUID id, @RequestBody UpdateBrandRequest brand)
    {
        return service.update(id, brand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id)
    {

        service.delete(id);
    }
}