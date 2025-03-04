package com.ignit.internship.controller.temukarier;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.temukarier.BootcampRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.temukarier.Bootcamp;
import com.ignit.internship.service.temukarier.BootcampService;

@RestController
@RequestMapping("/api/bootcamp")
public class BootcampController {

    private final BootcampService bootcampService;

    public BootcampController(BootcampService bootcampService) {
        this.bootcampService = bootcampService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<Bootcamp>> createBootcamp(@RequestBody BootcampRequest request) throws IdNotFoundException, IOException {
        return ResponseEntity.ok().body(DefaultResponse.success(bootcampService.createBootcamp(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<Bootcamp>> getBootcamp(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(bootcampService.getBootcampById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<Bootcamp>> updateBootcamp(
        @RequestBody BootcampRequest request,
        @PathVariable Long id
    ) throws IdNotFoundException, IOException {
        return ResponseEntity.ok().body(DefaultResponse.success(bootcampService.updateBootcamp(request, id)));
    }   

    @GetMapping
    public ResponseEntity<DefaultResponse<Page<Bootcamp>>> getBootcampByTagsAndPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(required = false) String tag,
        @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        if (tag == null) {
            return ResponseEntity.ok().body(DefaultResponse.success(bootcampService.getBootcampByPage(pageable)));
        }
        else {
            return ResponseEntity.ok().body(DefaultResponse.success(bootcampService.getBootcampByPageAndTag(pageable, tag)));
        }
    }  
}
