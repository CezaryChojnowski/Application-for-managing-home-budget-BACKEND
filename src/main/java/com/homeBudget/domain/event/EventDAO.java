package com.homeBudget.domain.event;

import com.homeBudget.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EventDAO {

    public final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Event createNewEvent(String name, LocalDate startDate, LocalDate finishDate, String emailUser){
        Event event = new Event.Builder()
                .name(name)
                .startDate(startDate)
                .finishDate(finishDate)
                .over(false)
                .user(userRepository.findUserByEmail(emailUser).get())
                .build();
        return eventRepository.save(event);
    }
}
