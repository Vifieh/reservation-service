package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Language;

import java.util.List;

public interface LanguageService {

    void createLanguage(Language Language);

    void editLanguage(String languageId, Language Language);

    List<Language> getAllLanguages();

    Language getLanguage(String languageId);

    void deleteLanguage(String languageId);
}
