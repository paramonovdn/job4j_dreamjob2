package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger id = new AtomicInteger(-1);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();


    private MemoryCandidateRepository() {
        save(new Candidate(0, "Ivan Ivanov", "desc1", LocalDateTime.parse("2024-01-25T15:08:16")));
        save(new Candidate(0, "Petr Petrov", "desc2", LocalDateTime.parse("2024-02-25T15:08:16")));
        save(new Candidate(0, "Igor Igorev", "desc3", LocalDateTime.parse("2024-03-25T15:08:16")));
    }

    @Override
    public Candidate save(Candidate candidate) {
        int nextId = id.incrementAndGet();
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(oldCandidate.getId(), candidate.getName(), candidate.getDescription(),
                        candidate.getCreationDate())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
