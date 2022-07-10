package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Language;

import java.util.List;

public interface LanguageService {

    void createLanguage(Language language);

    void editLanguage(String languageId, Language language);

    List<Language> getAllLanguages();

    List<Language> getLanguagesByProperty(String propertyId);

    Language getLanguage(String languageId);

    Language getLanguageByName(String name);

    void deleteLanguage(String languageId);
}
