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
import com.ignit.internship.dto.temukarier.MagangRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.temukarier.Magang;
import com.ignit.internship.service.temukarier.MagangService;

@RestController
@RequestMapping("/api/magang")
public class MagangController {

    private final MagangService magangService;

    public MagangController(MagangService magangService) {
        this.magangService = magangService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<Magang>> createMagang(@RequestBody MagangRequest request) throws IdNotFoundException, IOException {
        return ResponseEntity.ok().body(DefaultResponse.success(magangService.createMagang(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<Magang>> getMagang(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(magangService.getMagangById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<Magang>> updateProject(
        @RequestBody MagangRequest request,
        @PathVariable Long id
    ) throws IdNotFoundException, IOException {
        return ResponseEntity.ok().body(DefaultResponse.success(magangService.updateMagang(request, id)));
    }   

    @GetMapping
    public ResponseEntity<DefaultResponse<Page<Magang>>> getProjectByTagsAndPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(required = false) String tag,
        @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        if (tag == null) {
            return ResponseEntity.ok().body(DefaultResponse.success(magangService.getMagangByPage(pageable)));
        }
        else {
            return ResponseEntity.ok().body(DefaultResponse.success(magangService.getMagangByPageAndTag(pageable, tag)));
        }
    }    
}
