package com.dantn.weblaptop.generics;

import com.dantn.weblaptop.dto.response.ResponseLong;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public abstract class GenericsController<E, ID, C, U, R> {

    protected final GenericsService<E, ID, C, U, R> genericsService;

    public GenericsController(GenericsService<E, ID, C, U, R> genericsService) {
        this.genericsService = genericsService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseLong<List<R>>> getAllPage(@RequestParam(name = "page", required = false, defaultValue = "0") String page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "5") String size) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size), Sort.by(Sort.Direction.DESC, "ngayTao"));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 5);
        }
        Page<R> pageResult = genericsService.getAllPage(pageable);
        ResponseLong<List<R>> result = new ResponseLong<>(
                200,
                "GET successfully",
                pageResult.getContent(),
                String.valueOf(pageResult.getNumber()),
                String.valueOf(pageResult.getSize()),
                String.valueOf(pageResult.getTotalPages()),
                String.valueOf(pageResult.getTotalElements())
        );
        
        
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all-list")
    public ResponseEntity<ResponseLong<List<R>>> getAllList() {

        List<R> listResult = genericsService.getAllList();
        ResponseLong<List<R>> result = new ResponseLong<>(
                200, "GET successfully", listResult,
                null, null, null, null
        );
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all-list-active")
    public ResponseEntity<ResponseLong<List<R>>> getAllListActive() {

        List<R> listResult = genericsService.getAllListActive();
        ResponseLong<List<R>> result = new ResponseLong<>(
                200, "GET successfully", listResult,
                null, null, null, null
        );
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseLong<R>> add(@Valid @RequestBody C create) {
        try {
            ResponseLong<R> result = new ResponseLong<>(200, "Add successfully", genericsService.add(create), null, null, null, null);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            ResponseLong<R> result = new ResponseLong<>(999, "Add failed", null, null, null, null, null);
            return ResponseEntity.ok().body(result);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseLong<R>> update(@Valid @RequestBody U update,
                                                  @PathVariable("id") ID id) {
        try {
            ResponseLong<R> result = new ResponseLong<>(200, "Update successfully", genericsService.update(id, update), null, null, null, null);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            ResponseLong<R> result = new ResponseLong<>(999, "Update failed: " + e.getMessage(), null, null, null, null, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseLong<String>> delete(@PathVariable("id") ID id) {
        try {
            genericsService.delete(id);
            ResponseLong<String> result = new ResponseLong<>(200, "Delete successfully", null, null, null, null, null);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            ResponseLong<String> result = new ResponseLong<>(200, "Delete successfully", null, null, null, null, null);
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
            ResponseLong<R> response = new ResponseLong<>(200, "Find successfully", result, null, null, null, null);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseLong<R> response = new ResponseLong<>(999, "Not have data", null, null, null, null, null);
            return ResponseEntity.ok().body(response);
        }
    }

    @GetMapping("exist-code")
    public ResponseEntity<ResponseLong<Boolean>> existByCode(@RequestParam("code") String code) {
        Boolean result = genericsService.existByCode(code);
        if (result)
            return ResponseEntity.ok().body(new ResponseLong<>(200, "Code is existed", result, null, null, null, null));
        return ResponseEntity.ok().body(new ResponseLong<>(200, "Code is unique", result, null, null, null, null));
    }

    @GetMapping("exist-name")
    public ResponseEntity<ResponseLong<Boolean>> existByName(@RequestParam("name") String name) {
        Boolean result = genericsService.existByName(name);
        if (result)
            return ResponseEntity.ok().body(new ResponseLong<>(999, "Name is existed", result, null, null, null, null));
        return ResponseEntity.ok().body(new ResponseLong<>(200, "Name is unique", result, null, null, null, null));
    }

    @GetMapping("find-status-list")
    public ResponseEntity<ResponseLong<List<R>>> findByStatusList(@RequestParam("status") String stt) {
        try {
            Integer status = Integer.valueOf(stt);
        } catch (Exception e) {
            throw new RuntimeException("Status invalid");
        }
        List<R> result = genericsService.findByStatusList(Integer.valueOf(stt));
        return ResponseEntity.ok().body(new ResponseLong<>(200, "Find successfully", result, null, null, null, null));
    }

    @GetMapping("find-status-page")
    public ResponseEntity<ResponseLong<List<R>>> findByStatusPage(@RequestParam("status") String stt,
                                                                  @RequestParam(name = "page", required = false, defaultValue = "0") String page,
                                                                  @RequestParam(name = "size", required = false, defaultValue = "5") String size) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 5);
        }
        try {
            Integer status = Integer.valueOf(stt);
        } catch (Exception e) {
            throw new RuntimeException("Status invalid");
        }
        Page<R> result = genericsService.findByStatusPage(Integer.valueOf(stt), pageable);
        return ResponseEntity.ok().body(new ResponseLong<>(
                200,
                "Find successfully",
                result.getContent(),
                String.valueOf(result.getNumber()),
                String.valueOf(result.getSize()),
                String.valueOf(result.getTotalPages()),
                String.valueOf(result.getTotalElements())));
    }

}
// them get entity va get list va find by status findbycodeor name

