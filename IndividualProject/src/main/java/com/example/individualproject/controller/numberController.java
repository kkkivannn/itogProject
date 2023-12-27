package com.example.individualproject.controller;

import com.example.individualproject.models.NumberModel;
import com.example.individualproject.models.TypeNumbModel;
import com.example.individualproject.repo.NumberRepository;
import com.example.individualproject.repo.TypeNumbRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class numberController {
    private final TypeNumbRepository typeNumbRepository;
    private final NumberRepository numberRepository;

    public numberController(TypeNumbRepository typeNumbRepository, NumberRepository numberRepository) {
        this.typeNumbRepository = typeNumbRepository;
        this.numberRepository = numberRepository;
    }

    @GetMapping("/allNumbers")
    public String showAllNumbers(Model model) {
        Iterable<NumberModel> numbers = numberRepository.findAll();
        model.addAttribute("numbers", numbers);
        return "admin/numbers/allNumbers";
    }


    @GetMapping("/newNumbers")
    public String showNewNumber(Model model) {
        model.addAttribute("numbers", new NumberModel());
        return "admin/numbers/addNumber";
    }

    @GetMapping("/editNumber/{id}")
    public String showUpdateNumber(@PathVariable("id") long id, Model model) {
        NumberModel number = numberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("number", number);
        return "admin/numbers/updateNumber";
    }

    @GetMapping("/deleteNumber/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        NumberModel number = numberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        numberRepository.delete(number);
        model.addAttribute("numbers", numberRepository.findAll());
        return "redirect:/allNumbers";
    }


    @PostMapping("/addNumber")
    public String addUser(@Valid NumberModel number, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/numbers/addNumber";
        }
        NumberModel numberModel = new NumberModel();
        numberModel.setNumbNumber(number.getNumbNumber());
        numberModel.setCost(number.getCost());
        numberModel.setTypes(number.getTypes());
        numberRepository.save(numberModel);
        model.addAttribute("numbers", numberRepository.findAll());

        return "admin/numbers/allNumbers";
    }

    @PostMapping("/updateNumber/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid NumberModel number, BindingResult result, Model model) {
        if (result.hasErrors()) {
            number.setId(id);
            return "admin/numbers/updateNumber";
        }

        numberRepository.save(number);
        model.addAttribute("numbers", numberRepository.findAll());
        return "admin/numbers/allNumber";
    }

    @ModelAttribute("allTypes")
    public List<TypeNumbModel> getAllType() {
        List<TypeNumbModel> types = typeNumbRepository.findAll();
        return types;
    }
}
