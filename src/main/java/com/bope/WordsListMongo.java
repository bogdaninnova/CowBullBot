package com.bope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface WordsListMongo extends MongoRepository<WordMongo, String> {

    List<WordMongo> findByLang(String lang);
    List<WordMongo> findByLangAndLength(String lang, int length);
    boolean existsByLangAndWord(String lang, String word);

}
