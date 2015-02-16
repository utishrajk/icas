package com.feisystems.icas.service.reference;
import com.feisystems.icas.domain.reference.LanguageCode;
import java.util.List;

public interface LanguageCodeService {

	public abstract long countAllLanguageCodes();


	public abstract void deleteLanguageCode(LanguageCode languageCode);


	public abstract LanguageCode findLanguageCode(Long id);


	public abstract List<LanguageCode> findAllLanguageCodes();


	public abstract List<LanguageCode> findLanguageCodeEntries(int firstResult, int maxResults);


	public abstract void saveLanguageCode(LanguageCode languageCode);


	public abstract LanguageCode updateLanguageCode(LanguageCode languageCode);

}
