package com.example.individualproject.controller;

import com.example.individualproject.models.NumberModel;
import com.example.individualproject.models.ReservationModel;
import com.example.individualproject.models.UserModel;
import com.example.individualproject.repo.NumberRepository;
import com.example.individualproject.repo.ReservationRepository;
import com.example.individualproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class reservEmplController {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final NumberRepository numberRepository;

    @Autowired
    public reservEmplController(ReservationRepository reservationRepository, NumberRepository numberRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.numberRepository = numberRepository;
    }

    @GetMapping("/allReservEmpl")
    public String showAllReserv(Model model) {
        Iterable<ReservationModel> reserv = reservationRepository.findAll();
        model.addAttribute("reservs", reserv);
        return "sotr/reservs/allReservs";
    }

    @GetMapping("/newReserv")
    public String showNewReserv(Model model) {
        model.addAttribute("reserv", new ReservationModel());
        return "sotr/reservs/addReserv";
    }

    @GetMapping("/editReserv/{id}")
    public String showUpdateReserv(@PathVariable("id") long id, Model model) {
        ReservationModel reserv = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
        model.addAttribute("reservs", reserv);
        return "sotr/reservs/updateReserv";
    }

    @GetMapping("/deleteReserv/{id}")
    public String deleteReserv(@PathVariable("id") long id, Model model) {
        ReservationModel reserv = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid laptop Id:" + id));
        reservationRepository.delete(reserv);
        model.addAttribute("reservs", reservationRepository.findAll());
        return "sotr/reservs/allReservs";
    }

    @PostMapping("/addReserv")
    public String addReserv(@Valid ReservationModel reservationModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sotr/reservs/addReserv";
        }

        reservationRepository.save(reservationModel);
        model.addAttribute("reservs", reservationRepository.findAll());

        return "sotr/reservs/allReservs";
    }

    @PostMapping("/updateReserv/{id}")
    public String updateReserv(@PathVariable("id") long id, @Valid ReservationModel reservationModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            reservationModel.setId(id);
            return "sotr/reservs/updateReserv";
        }
        System.out.println("Бронь" + reservationModel.getUserModel().getUsername());
        reservationRepository.save(reservationModel);
        model.addAttribute("reservs", reservationRepository.findAll());
        return "sotr/reservs/allReservs";
    }

    @ModelAttribute("allUsers")
    public List<UserModel> getAllType() {
        List<UserModel> types = userRepository.findAll();
        return types;
    }

    @ModelAttribute("allNumbs")
    public List<NumberModel> getAllNumbs() {
        List<NumberModel> numbs = numberRepository.findAll();
        return numbs;
    }
}
