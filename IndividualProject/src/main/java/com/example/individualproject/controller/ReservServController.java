package com.example.individualproject.controller;

import com.example.individualproject.models.*;
import com.example.individualproject.repo.ReservServRepository;
import com.example.individualproject.repo.ServiceRepository;
import com.example.individualproject.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ReservServController {
    private final ReservServRepository reservServRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public ReservServController(ReservServRepository reservServRepository, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.reservServRepository = reservServRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/allReservServ")
    public String showAllReservServ(Model model) {
        Iterable<ReservServModel> reservServModels = reservServRepository.findAll();
        model.addAttribute("reservServs", reservServModels);
        return "sotr/reservServ/allReservServ";
    }

    @GetMapping("/newReservServ")
    public String showNewReservServ(Model model) {
        model.addAttribute("reservServ", new ReservServModel());
        return "sotr/reservServ/addReservServ";
    }

    @PostMapping("/addReservServ")
    public String addReservServ(@Valid ReservServModel reservServModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sotr/reservServ/allReservServ";
        }
        reservServRepository.save(reservServModel);
        model.addAttribute("reservServs", reservServRepository.findAll());

        return "sotr/reservServ/allReservServ";
    }

    @GetMapping("/editReservServ/{id}")
    public String showUpdateReservServ(@PathVariable("id") long id, Model model) {
        ReservServModel reservServModel = reservServRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
        model.addAttribute("reservServ", reservServModel);
        return "sotr/reservServ/updateReservServ";
    }

    @PostMapping("/updateReservServ/{id}")
    public String updateReservServ(@PathVariable("id") long id, @Valid ReservServModel reservServModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            reservServModel.setId(id);
            return "sotr/reservServ/updateReservServ";
        }
        reservServRepository.save(reservServModel);
        model.addAttribute("reservServs", reservServRepository.findAll());
        return "sotr/reservServ/allReservServ";
    }

    @GetMapping("/deleteReservServ/{id}")
    public String deleteReservServ(@PathVariable("id") long id, Model model) {
        ReservServModel service = reservServRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid laptop Id:" + id));
        reservServRepository.delete(service);
        model.addAttribute("reservServs", reservServRepository.findAll());
        return "sotr/reservServ/allReservServ";
    }

    @ModelAttribute("allUsers")
    public List<UserModel> getAllType() {
        List<UserModel> types = userRepository.findAll();
        return types;
    }

    @ModelAttribute("allServices")
    public List<ServiceModel> getAllServ() {
        List<ServiceModel> types = serviceRepository.findAll();
        return types;
    }
}
