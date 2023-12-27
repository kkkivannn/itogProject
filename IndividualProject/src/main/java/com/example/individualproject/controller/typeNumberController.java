package com.example.individualproject.controller;

import com.example.individualproject.models.TypeNumbModel;
import com.example.individualproject.repo.NumberRepository;
import com.example.individualproject.repo.TypeNumbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class typeNumberController {
    private final TypeNumbRepository typeNumbRepository;

    @Autowired
    public typeNumberController(TypeNumbRepository typeNumbRepository) {
        this.typeNumbRepository = typeNumbRepository;
    }

    @GetMapping("/allTypeNumb")
    public String showAllTypes(Model model) {
        Iterable<TypeNumbModel> types = typeNumbRepository.findAll();
        model.addAttribute("types", types);
        return "admin/types/allTypesNumb";
    }

    @GetMapping("/newType")
    public String showNewType(Model model) {
        model.addAttribute("type", new TypeNumbModel());
        return "admin/types/addTypeNumb";
    }

    @GetMapping("/editType/{id}")
    public String showUpdateType(@PathVariable("id") long id, Model model) {
        TypeNumbModel type = typeNumbRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
        model.addAttribute("type", type);
        return "admin/types/updateTypeNumb";
    }

    @GetMapping("/deleteType/{id}")
    public String deleteType(@PathVariable("id") long id, Model model) {
        TypeNumbModel type = typeNumbRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid laptop Id:" + id));
        typeNumbRepository.delete(type);
        model.addAttribute("types", typeNumbRepository.findAll());
        return "admin/types/allTypesNumb";
    }

    @PostMapping("/addType")
    public String addType(@Valid TypeNumbModel type, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/types/addTypeNumb";
        }

        typeNumbRepository.save(type);
        model.addAttribute("types", typeNumbRepository.findAll());

        return "admin/types/allTypesNumb";
    }

    @PostMapping("/updateType/{id}")
    public String updateType(@PathVariable("id") long id, @Valid TypeNumbModel type, BindingResult result, Model model) {
        if (result.hasErrors()) {
            type.setId(id);
            return "admin/types/updateTypeNumb";
        }

        typeNumbRepository.save(type);
        model.addAttribute("types", typeNumbRepository.findAll());
        return "admin/types/allTypesNumb";
    }
}
