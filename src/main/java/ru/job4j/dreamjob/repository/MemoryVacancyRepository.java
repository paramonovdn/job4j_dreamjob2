package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "desc1", LocalDateTime.parse("2024-01-25T15:08:16")));
        save(new Vacancy(0, "Junior Java Developer", "desc2", LocalDateTime.parse("2024-02-25T15:08:16")));
        save(new Vacancy(0, "Junior+ Java Developer", "desc3", LocalDateTime.parse("2024-03-25T15:08:16")));
        save(new Vacancy(0, "Middle Java Developer", "desc4", LocalDateTime.parse("2024-04-25T15:08:16")));
        save(new Vacancy(0, "Middle+ Java Developer", "desc5", LocalDateTime.parse("2024-05-25T15:08:16")));
        save(new Vacancy(0, "Senior Java Developer", "desc6", LocalDateTime.parse("2024-06-25T15:08:16")));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                        vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}