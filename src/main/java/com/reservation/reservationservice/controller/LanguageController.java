package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Language;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.LanguageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/protected/")
@CrossOrigin
@RestController
public class LanguageController {

    @Autowired
    LanguageService languageService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("languages")
    public ResponseEntity<ResponseMessage> createLanguage(@RequestBody CustomPayload languagePayload) {
        Language language = modelMapper.map(languagePayload, Language.class);
        languageService.createLanguage(language);
        message = "Language created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("languages/{languageId}")
    public ResponseEntity<ResponseMessage> editLanguage(@PathVariable String languageId,
                                                            @RequestBody CustomPayload languagePayload) {
        Language language = modelMapper.map(languagePayload, Language.class);
        languageService.editLanguage(languageId, language);
        message = "Language edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("languages")
    public ResponseEntity<List<CustomDto>> getLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        List<CustomDto> languagesDtos = getLanguageDtoList(languages);
        return new ResponseEntity<>(languagesDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("languages/properties/{propertyId}")
    public ResponseEntity<List<CustomDto>> getLanguagesOfAProperty(@PathVariable String propertyId) {
        List<Language> languages = languageService.getLanguagesByProperty(propertyId);
        List<CustomDto> languagesDtos = getLanguageDtoList(languages);
        return new ResponseEntity<>(languagesDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("languages/{languageId}")
    public ResponseEntity<CustomDto> getLanguage(@PathVariable String languageId) {
        Language language = languageService.getLanguage(languageId);
        CustomDto languagesDto = modelMapper.map(language, CustomDto.class);
        return new ResponseEntity<>(languagesDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("languages/{languageId}")
    public ResponseEntity<ResponseMessage> deleteLanguage(@PathVariable String languageId) {
        languageService.deleteLanguage(languageId);
        message = "Language deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    private List<CustomDto> getLanguageDtoList(List<Language> languages) {
        return languages.stream().map(language ->
                modelMapper.map(language, CustomDto.class)).collect(Collectors.toList());
    }
}
