package com.example.individualproject.controller;

import com.example.individualproject.models.*;
import com.example.individualproject.repo.NumberRepository;
import com.example.individualproject.repo.ReservationRepository;
import com.example.individualproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
public class reservUserController {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final NumberRepository numberRepository;

    @Autowired
    public reservUserController(ReservationRepository reservationRepository, UserRepository userRepository, NumberRepository numberRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.numberRepository = numberRepository;
    }
    @GetMapping("/allReservsUser")
    public String showAllReserves(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        UserModel user = userRepository.findByUsernameContaining(userEmail);
        Iterable<ReservationModel> reservations = null;
        if (user != null) {
            reservations = reservationRepository.findByUserModel(user);
        }

        model.addAttribute("reservations", reservations);
        return "user/reserv/allReservsUser";
    }

    @GetMapping("/newReservUser")
    public String showNewReserv(Model model) {
        model.addAttribute("reserv", new ReservationModel());
        return "user/reserv/addReservUser";
    }

    @GetMapping("/editReservUser/{id}")
    public String showUpdateService(@PathVariable("id") long id, Model model) {
        ReservationModel reserv = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
        model.addAttribute("reserv", reserv);
        return "user/reserv/updateReservUser";
    }

    @GetMapping("/deleteReservUser/{id}")
    public String deleteReserv(@PathVariable("id") long id, Model model) {
        ReservationModel reserv = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid laptop Id:" + id));
        reservationRepository.delete(reserv);
        model.addAttribute("reservs", reservationRepository.findAll());
        return "user/reserv/allReservsUser";
    }

    @PostMapping("/addResersvUser")
    public String addReserv(@Valid ReservationModel reserv, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            return "user/reserv/addReservUser";
        }
        String userEmail = authentication.getName();
        UserModel user = userRepository.findByUsernameContaining(userEmail);
        if (user != null) {
            reserv.setUserModel(user);
            reservationRepository.save(reserv);
        }
        model.addAttribute("reservs", reservationRepository.findAll());

        return "user/reserv/allReservsUser";
    }

    @PostMapping("/updateReservUser/{id}")
    public String updateReserv(@PathVariable("id") long id, @Valid ReservationModel reserv, BindingResult result, Model model) {
        if (result.hasErrors()) {
            reserv.setId(id);
            return "user/reserv/updateReservUser";
        }

        reservationRepository.save(reserv);
        model.addAttribute("reservs", reservationRepository.findAll());
        return "user/reserv/allReservsUser";
    }
    @ModelAttribute("allNumbs")
    public List<NumberModel> getAllType() {
        List<NumberModel> numbs = numberRepository.findAll();
        return numbs;
    }
}
