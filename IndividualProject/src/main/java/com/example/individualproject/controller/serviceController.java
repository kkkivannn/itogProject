package com.example.individualproject.controller;

import com.example.individualproject.models.ServiceModel;
import com.example.individualproject.repo.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class serviceController {
    private final ServiceRepository serviceRepository;

    @Autowired
    public serviceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/allServices")
    public String showAllServices(Model model) {
        Iterable<ServiceModel> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return "admin/services/allServices";
    }

    @GetMapping("/newService")
    public String showNewService(Model model) {
        model.addAttribute("service", new ServiceModel());
        return "admin/services/addService";
    }

    @GetMapping("/editService/{id}")
    public String showUpdateService(@PathVariable("id") long id, Model model) {
        ServiceModel service = serviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
        model.addAttribute("service", service);
        return "admin/services/updateService";
    }

    @GetMapping("/deleteService/{id}")
    public String deleteService(@PathVariable("id") long id, Model model) {
        ServiceModel service = serviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid laptop Id:" + id));
        serviceRepository.delete(service);
        model.addAttribute("services", serviceRepository.findAll());
        return "admin/services/allServices";
    }

    @PostMapping("/addService")
    public String addService(@Valid ServiceModel serviceModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/services/addService";
        }

        serviceRepository.save(serviceModel);
        model.addAttribute("services", serviceRepository.findAll());

        return "admin/services/allServices";
    }

    @PostMapping("/updateService/{id}")
    public String updateService(@PathVariable("id") long id, @Valid ServiceModel service, BindingResult result, Model model) {
        if (result.hasErrors()) {
            service.setId(id);
            return "admin/services/updateService";
        }

        serviceRepository.save(service);
        model.addAttribute("services", serviceRepository.findAll());
        return "admin/services/allServices";
    }
}
