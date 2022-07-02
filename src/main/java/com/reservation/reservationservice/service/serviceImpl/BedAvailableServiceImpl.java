package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.BedAvailable;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.BedAvailableRepository;
import com.reservation.reservationservice.service.BedAvailableService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedAvailableServiceImpl implements BedAvailableService {
    private Util util = new Util();

    @Autowired
    BedAvailableRepository bedAvailableRepository;

    @Autowired
    UserService userService;


    @Override
    public void createBedAvailable(BedAvailable bedAvailable) {
        User user = userService.getAuthUser();
        Optional<BedAvailable> bedAvailable1= bedAvailableRepository.findByName(bedAvailable.getName());
        if (bedAvailable1.isPresent()) {
            throw new ResourceAlreadyExistException("Bed Available already exist");
        }
        bedAvailable.setId(util.generateId());
        bedAvailable.setUser(user);
        bedAvailable.setCreatedBy(user.getEmail());
        bedAvailableRepository.save(bedAvailable);
    }

    @Override
    public void editBedAvailable(String bedId, BedAvailable bedAvailable) {
        BedAvailable bedAvailable1 = getBedAvailable(bedId);
        bedAvailable1.setName(bedAvailable.getName());
        bedAvailableRepository.save(bedAvailable1);
    }

    @Override
    public List<BedAvailable> getAllBedsAvailable() {
        return bedAvailableRepository.findAll();
    }

    @Override
    public BedAvailable getBedAvailable(String bedId) {
        Optional<BedAvailable> bedAvailable = bedAvailableRepository.findById(bedId);
        //        bedAvailable.orElseThrow(() -> new ResourceNotFoundException("Bed available not found with id -  " + bedId));
        return bedAvailable.get();
    }

     @Override
    public BedAvailable getBedAvailableByName(String name) {
        Optional<BedAvailable> bedAvailable = bedAvailableRepository.findByName(name);
        bedAvailable.orElseThrow(() -> new ResourceNotFoundException("Bed available not found with name -  " + name));
        return bedAvailable.get();
    }

    @Override
    public void deleteBedAvailable(String bedId) {
        bedAvailableRepository.deleteById(bedId);
    }
}
