package com.dantn.weblaptop.generics;

import com.dantn.weblaptop.dto.response.ResponseLong;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class GenericsController<E, ID, C, U, R> {

    protected final GenericsService<E, ID, C, U, R> genericsService;

    public GenericsController(GenericsService<E, ID, C, U, R> genericsService) {
        this.genericsService = genericsService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseLong<Page<R>>> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") String page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "5") String size) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 5);
        }
        ResponseLong<Page<R>> result = new ResponseLong<>(200,
                "GET successfully", genericsService.getAllPage(pageable));
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseLong<R>> add(@Valid @RequestBody C create) {
        try {
            ResponseLong<R> result = new ResponseLong<>(200, "Add successfully", genericsService.add(create));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            ResponseLong<R> result = new ResponseLong<>(999, "Add failed", null);
            return ResponseEntity.ok().body(result);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseLong<R>> update(@Valid @RequestBody U update,
                                                  @PathVariable("id") ID id) {
        try {
            ResponseLong<R> result = new ResponseLong<>(200, "Update successfully", genericsService.update(id, update));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            ResponseLong<R> result = new ResponseLong<>(999, "Update failed: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ResponseLong<String>> delete(@PathVariable("id") ID id) {
        try {
            genericsService.delete(id);
            ResponseLong<String> result = new ResponseLong<>(200, "Delete successfully", null);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            ResponseLong<String> result = new ResponseLong<>(200, "Delete successfully", null);
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseLong<R>> detail(@PathVariable("id") ID id) {
        try {
            R result = genericsService.findResponseById(id);
            if (result == null) {
                throw new RuntimeException();
            }
            ResponseLong<R> response = new ResponseLong<>(200, "Find successfully", result);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseLong<R> response = new ResponseLong<>(999, "Not have data", null);
            return ResponseEntity.ok().body(response);
        }
    }

    // them get entity va get list va find by status findbycodeor name
}
