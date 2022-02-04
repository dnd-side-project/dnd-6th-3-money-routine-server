package com.example.dnd6th3moneyroutineserver.expenditure;

import com.example.dnd6th3moneyroutineserver.expenditure.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {

}
