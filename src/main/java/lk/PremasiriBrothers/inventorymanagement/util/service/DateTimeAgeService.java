package lk.PremasiriBrothers.inventorymanagement.util.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class DateTimeAgeService {

    @Transactional
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    @Transactional
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    @Transactional
    public int getAge(LocalDate dateOfBirth){
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }
}
