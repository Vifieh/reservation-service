package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Language;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.LanguageRepository;
import com.reservation.reservationservice.service.LanguageService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {
    private Util util = new Util();

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    UserService userService;


    @Override
    public void createLanguage(Language language) {
        User user = userService.getAuthUser();
        Optional<Language> language1= languageRepository.findByName(language.getName());
        if (language1.isPresent()) {
            throw new ResourceAlreadyExistException("Language already exist");
        }
        language.setId(util.generateId());
        language.setUser(user);
        language.setCreatedBy(user.getEmail());
        languageRepository.save(language);
    }

    @Override
    public void editLanguage(String languageId, Language language) {
        Language language1 = getLanguage(languageId);
        language.setName(language.getName());
        languageRepository.save(language1);
    }

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Language getLanguage(String languageId) {
        Optional<Language> language = languageRepository.findById(languageId);
        language.orElseThrow(() -> new ResourceNotFoundException("Language not found with id -  " + languageId));
        return language.get();
    }

    @Override
    public Language getLanguageByName(String name) {
        Optional<Language> language = languageRepository.findByName(name);
        language.orElseThrow(() -> new ResourceNotFoundException("Language not found with name -  " + name));
        return language.get();
    }

    @Override
    public void deleteLanguage(String languageId) {
        languageRepository.deleteById(languageId);
    }
}
